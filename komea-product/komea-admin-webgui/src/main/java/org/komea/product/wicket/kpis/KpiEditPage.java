package org.komea.product.wicket.kpis;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Vector;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

/**
 * Person admin page
 *
 * @author sleroy
 */
public class KpiEditPage extends LayoutPage {

    @SpringBean
    private KpiDao kpiDao;

    @SpringBean
    private ProviderDao providerDao;

    @SpringBean
    private IEntityService entityService;

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


        final KpiForm kpiForm = new KpiForm("form", kpiDao, entityService, providerDao, feedbackPanel, new CompoundPropertyModel<Kpi>(_kpi), this);
        add(kpiForm);


        // Dialog //
        final SelectDialog dialog;
        dialog = new SelectDialog("dialog", "Select", providerDao) {
            
            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
                target.add(kpiForm);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                Provider selectedProvider = getSelectedProvider();
                kpiForm.getKpi().setIdProvider(selectedProvider.getId());
                kpiForm.getNameProvider().setName(selectedProvider.getName());
                target.add(kpiForm);
//                this.info(String.format("The model object is: '%s'", this.getModelObject()));
            }
        };

        this.add(dialog);
        // Buttons //
        kpiForm.add(new AjaxLinkLayout<LayoutPage>("open", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialog.open(art);


            }
        });
    }

    @Override

    public String getTitle() {

        return "Add a kpi";
    }

}
