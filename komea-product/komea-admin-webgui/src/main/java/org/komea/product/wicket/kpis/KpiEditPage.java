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
//        final KpiForm KpiForm = null;
//        new KpiForm(PARENT_PATH, _kpi, feedbackPanel, null)

        final KpiForm KpiForm = new KpiForm("form", kpiDao, entityService, providerDao, feedbackPanel, new CompoundPropertyModel<Kpi>(_kpi), this);
        add(KpiForm);

//        Form formWithJavaScript = new Form("formWithJavaScript");
//        Button buttonWithJavaScript = new Button("buttonWithJavaScript") {
//
//            @Override
//            public void onSubmit() {
//                System.out.println("Doing my job");
//            }
//        };
//        buttonWithJavaScript.add(new SimpleAttributeModifier(
//                "onclick", "if(!confirm('Do you really want to perform this action?')) return false;"));
//        formWithJavaScript.add(buttonWithJavaScript);
//        add(formWithJavaScript);
//         final Form<Void> form = new Form<Void>("dform");
//        this.add(form);
        // FeedbackPanel //
//        form.add(new JQueryFeedbackPanel("feedback"));
        // Dialog //
        final SelectDialog dialog = new SelectDialog("dialog", "Select", providerDao) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
                target.add(KpiForm);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget art) {
                this.info("The form has been submitted");
//                this.info(String.format("The model object is: '%s'", this.getModelObject()));
            }
        };

        this.add(dialog);
        // Buttons //
        KpiForm.add(new AjaxLinkLayout<LayoutPage>("open", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialog.open(art);

//                getCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
            }
        });
    }

    @Override

    public String getTitle() {

        return "Add a kpi";
    }

}
