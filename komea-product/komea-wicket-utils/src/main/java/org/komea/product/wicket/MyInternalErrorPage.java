/**
 *
 */

package org.komea.product.wicket;



import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * @author sleroy
 */
public class MyInternalErrorPage extends StatelessLayoutPage
{


    public MyInternalErrorPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        // In WebPage
        add(new FeedbackPanel("feedback"));
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
