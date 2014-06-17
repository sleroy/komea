
package org.komea.product.wicket.kpiimport;



import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.wicket.LayoutPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Kpi importation page.
 *
 * @author sleroy
 */
public class KpiImportPage extends LayoutPage
{
    
    
    private static final Logger LOGGER           = LoggerFactory.getLogger(KpiImportPage.class);
    
    
    private static final long   serialVersionUID = 825152658028992367L;
    
    @SpringBean
    private IKomeaFS            komeaFS;
    
    
    
    public KpiImportPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        // Add upload form with progress bar
        final FileUploadForm progressUploadForm =
                new FileUploadForm("progressUpload", komeaFS.getFileSystemFolder("upload"), this);
        
        progressUploadForm.add(new UploadProgressBar("progress", progressUploadForm,
                progressUploadForm.fileUploadField));
        add(progressUploadForm);
        
    }
    
    
}
