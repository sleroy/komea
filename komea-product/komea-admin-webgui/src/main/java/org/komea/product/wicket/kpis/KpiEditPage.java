package org.komea.product.wicket.kpis;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.kpi.IKPIService;
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
    private IEntityService entityService;

    @SpringBean
    private IProviderService providerService;

    @SpringBean
    private IKPIService kpiService;

    private static final long serialVersionUID = 1L;

    public KpiEditPage(final PageParameters _parameters) {

        this(_parameters, new Kpi(), true);

    }

    public KpiEditPage(final PageParameters _parameters, final Kpi _kpi) {
        this(_parameters, _kpi, false);
    }

    private KpiEditPage(final PageParameters _parameters, final Kpi _kpi, boolean isNew) {

        super(_parameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);

        final KpiForm kpiForm = new KpiForm(isNew, "form", kpiService, entityService, providerService, feedbackPanel, new CompoundPropertyModel<Kpi>(_kpi), this);
                String message;
        if (isNew) {
            message = "Add kpi";
        } else {
            message = "Edit kpi";
        }
        kpiForm.add(new Label("legend", message));
        add(kpiForm);
    }

    @Override

    public String getTitle() {

        return "Add a kpi";
    }

}
