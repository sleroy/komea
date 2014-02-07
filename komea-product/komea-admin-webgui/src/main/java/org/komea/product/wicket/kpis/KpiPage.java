
package org.komea.product.wicket.kpis;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.wicket.LayoutPage;



/**
 * KPI admin page
 * 
 * @author sleroy
 */
public class KpiPage extends LayoutPage
{
    
    
    public KpiPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        // add(new DataTable<T, S>("montableau", columns, dataProvider, rowsPerPage));
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "KPI / Metrics Administration";
    }
}
