/**
 *
 */

package org.komea.product.wicket;



import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.utils.StackTracePrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class MyInternalErrorPage extends StatelessLayoutPage
{


    private static final Logger LOGGER = LoggerFactory.getLogger(MyInternalErrorPage.class);
    
    
    
    public MyInternalErrorPage(final PageParameters _parameters) {


        super(_parameters);
        // In WebPage
        add(new FeedbackPanel("feedback"));
        error("An error has been encountered");

    }
    
    
    public MyInternalErrorPage(final PageParameters _pageParameters, final Exception _e) {


        super(_pageParameters);
        // In WebPage
        add(new FeedbackPanel("feedback"));
        LOGGER.error("Exception happened {}", _e);
        error("An error has been encountered : " + _e.getMessage());
        error("Exception : " + StackTracePrint.printTrace(_e));
    }
    
    
    @Override
    public boolean isErrorPage() {


        return true;
    }


    @Override
    public boolean isVersioned() {


        return false;
    }


    @Override
    protected void configureResponse(final WebResponse _response) {


        super.configureResponse(_response);
        _response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

}
