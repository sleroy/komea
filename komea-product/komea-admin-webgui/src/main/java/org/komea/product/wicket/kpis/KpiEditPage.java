package org.komea.product.wicket.kpis;

import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.InputDialog;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;

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
        
         final Form<Void> form = new Form<Void>("dform");
        this.add(form);

        // FeedbackPanel //
        form.add(new JQueryFeedbackPanel("feedback"));

        // Dialog //
        final InputDialog<String> dialog = new InputDialog<String>("dialog", "Input", "Please provide a value:", new Model<String>("a sample value")) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit(AjaxRequestTarget target) {
                this.info("The form has been submitted");
                this.info(String.format("The model object is: '%s'", this.getModelObject()));
            }

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
                this.info(button + " has been clicked");
                target.add(form);
            }
        };

        this.add(dialog); //the dialog is not within the form

        // Buttons //
        form.add(new AjaxButton("open") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                dialog.open(target);
            }
        });

    }

    @Override
    public String getTitle() {

        return "Add a kpi";
    }

}
