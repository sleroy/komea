
package org.komea.product.wicket.kpis;



import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class KpiEditPage extends LayoutPage
{
    
    
    @SpringBean
    private KpiDao kpiDao;

    @SpringBean
    private IKPIService kpiService;
    
    
    public KpiEditPage(final PageParameters _parameters) {
    
    
        this(_parameters, new Kpi());
        
        
    }
    
    
    public KpiEditPage(final PageParameters _parameters, final Kpi _kpi) {
    
    
        super(_parameters);
        
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
//        final KpiForm KpiForm = null;
//        new KpiForm(PARENT_PATH, _kpi, feedbackPanel, null)
        final KpiForm KpiForm = new KpiForm("form",kpiDao, feedbackPanel, new CompoundPropertyModel<Kpi>(_kpi),this);
        add(KpiForm);
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "Add a kpi";
    }
    
    
}
