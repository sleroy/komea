
package org.komea.product.wicket.kpis;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class KpiEditPage extends LayoutPage
{
    
    
    private static final long    serialVersionUID = 1L;
    
    @SpringBean
    private IEntityService       entityService;
    
    @SpringBean
    private IGroovyEngineService groovyEngineService;
    
    @SpringBean
    private IKPIService          kpiService;
    
    @SpringBean
    private IProviderService     providerService;
    
    
    
    public KpiEditPage(final PageParameters _parameters) {
    
    
        this(_parameters, new Kpi(), true);
        
    }
    
    
    public KpiEditPage(final PageParameters _parameters, final Kpi _kpi) {
    
    
        this(_parameters, _kpi, false);
    }
    
    
    private KpiEditPage(final PageParameters _parameters, final Kpi _kpi, final boolean isNew) {
    
    
        super(_parameters);
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        
        final KpiForm kpiForm =
                new KpiForm(isNew, "form", kpiService, entityService, providerService,
                        feedbackPanel, new CompoundPropertyModel<Kpi>(_kpi), this,
                        groovyEngineService);
        String message;
        if (isNew) {
            message = getString("kpipage.save.add.title");
        } else {
            message = getString("kpipage.save.edit.title");
        }
        kpiForm.add(new Label("legend", message));
        add(kpiForm);
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("kpipage.main.title");
    }
    
}
