
package org.komea.product.wicket.console;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.wicket.LayoutPage;
import org.slf4j.LoggerFactory;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class ConsolePage extends LayoutPage
{
    
    
    @SpringBean
    private ISettingService settingService;
    
    
    
    public ConsolePage(final PageParameters _parameters) {
    
    
        super(_parameters);
        String consoleLog = "";
        try {
            final Reader reader =
                    new BufferedReader(new FileReader(new File(settingService.getProxy(
                            "logfile_path").getStringValue())));
            consoleLog = IOUtils.toString(reader);
        } catch (final Exception e) {
            LoggerFactory.getLogger(ConsolePage.class).error("Impossible to access to the log.", e);
        }
        final TextArea<String> textArea = new TextArea<String>("console");
        textArea.setModel(Model.of(consoleLog));
        add(textArea);
        
        
    }
    
    
}
