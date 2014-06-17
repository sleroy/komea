
package org.komea.product.wicket.kpiimport;



import java.io.File;
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
public class FileUploadForm extends Form<Void>
{


    private final WebPage kpiImportPage;
    private final File    uploadFolder;
    FileUploadField       fileUploadField;



    /**
     * Construct.
     *
     * @param _name
     *            Component name
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
     * @param newFile
     *            the file to check
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
                    upload.writeTo(newFile);

                    kpiImportPage.info("saved file: " + upload.getClientFileName());
                } catch (final Exception e) {
                    throw new IllegalStateException("Unable to write file", e);
                }
            }
        }
    }
}
