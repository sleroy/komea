
package org.komea.product.wicket.console;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.MessageFormat;

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
public class ConsolePage extends LayoutPage {
    
    /**
     * This field describes
     */
    private static final long serialVersionUID = 1L;
    @SpringBean
    private ISettingService   settingService;
    
    public ConsolePage(final PageParameters _parameters) {
    
        super(_parameters);
        String consoleLog = "";
        String logFilePAth = settingService.getProxy("logfile_path").getStringValue();
        try {
            File logFile = new File(logFilePAth);
            
            final Reader reader = new BufferedReader(new FileReader(logFile));
            consoleLog = IOUtils.toString(reader);
        } catch (final Exception e) {
            String logErrorMsg = MessageFormat.format("Impossible to access to the log file '{0}'. Please check the log file path.",
                    logFilePAth);
            LoggerFactory.getLogger(ConsolePage.class).error(logErrorMsg, e);
            consoleLog = logErrorMsg;
            
        }
        final TextArea<String> textArea = new TextArea<String>("console");
        textArea.setModel(Model.of(consoleLog));
        add(textArea);
        
    }
    
       @Override
    public String getTitle() {
        return getString("administration.title.logs");
    }
    
}
