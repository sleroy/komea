package org.komea.product.wicket.kpiimport;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.lang.Bytes;

/**
 * Form for uploads.
 */
public class FileUploadForm extends Form<Void> {

    private final WebPage kpiImportPage;
    private final File uploadFolder;
    FileUploadField fileUploadField;

    /**
     * Construct.
     *
     * @param _name Component name
     * @param _kpiImportPage
     */
    public FileUploadForm(final String _name, final File _uploadFolder, final WebPage _kpiImportPage) {

        super(_name);
        uploadFolder = _uploadFolder;
        kpiImportPage = _kpiImportPage;

        // set this form to multipart mode (allways needed for uploads!)
        setMultiPart(true);

        // Add one file input field
        add(fileUploadField = new FileUploadField("fileInput"));

        setMaxSize(Bytes.kilobytes(2000));

    }

    /**
     * Check whether the file allready exists, and if so, try to delete it.
     *
     * @param newFile the file to check
     */
    private void checkFileExists(final File newFile) {

        if (newFile.exists()) {
            // Try to delete the file
            if (!Files.remove(newFile)) {
                throw new IllegalStateException("Unable to overwrite " + newFile.getAbsolutePath());
            }
        }
    }

    /**
     * @see org.apache.wicket.markup.html.form.Form#onSubmit()
     */
    @Override
    protected void onSubmit() {

        final List<FileUpload> uploads = fileUploadField.getFileUploads();
        if (uploads != null) {
            for (final FileUpload upload : uploads) {
                // Create a new file
                final File newFile = new File(uploadFolder, upload.getClientFileName());

                // Check new file, delete if it already existed
                checkFileExists(newFile);
                try {
                    // Save to new file
                    newFile.createNewFile();
//                    System.out.println("step 1 " + newFile.getAbsolutePath());
                    upload.writeTo(newFile);

//                    System.out.println("saved file: " + upload.getClientFileName());
                    kpiImportPage.info("saved file: " + upload.getClientFileName());

//                    try {
//                        File writeToTempFile = uploads.get(0).writeToTempFile();
//                        System.out.println("step 2 "+writeToTempFile.getAbsolutePath());
                    setResponsePage(new KpiZipReadPage(kpiImportPage.getPageParameters(),
                            newFile));
//                    } catch (final IOException e) {
//                        error(e.getMessage());
//                        e.printStackTrace();
//                    }
                } catch (final Exception e) {

                    setResponsePage(new MyInternalErrorPage(getPage().getPageParameters(),
                            new IllegalStateException("Unable to write file", e)));
                }
            }
        } else {
            setResponsePage(new MyInternalErrorPage(getPage().getPageParameters(),
                    new IllegalArgumentException("No file has been uploaded")));
        }

    }
}
