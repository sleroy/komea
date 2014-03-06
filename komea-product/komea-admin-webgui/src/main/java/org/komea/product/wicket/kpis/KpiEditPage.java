package org.komea.product.wicket.kpis;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.List;
import java.util.Vector;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
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

        this(_parameters, new Kpi());

    }

    public KpiEditPage(final PageParameters _parameters, final Kpi _kpi) {

        super(_parameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        final KpiForm kpiForm = new KpiForm("form", kpiService, entityService, providerService, feedbackPanel, new CompoundPropertyModel<Kpi>(_kpi), this);
        add(kpiForm);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Dialog provider //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        IChoiceRenderer<Provider> iChoiceRenderer = new IChoiceRenderer<Provider>() {

            @Override
            public Object getDisplayValue(Provider t) {
                return t.getName();
            }

            @Override
            public String getIdValue(Provider t, int i) {
                return String.valueOf(t.getId());
            }

        };

        final SelectDialog<Provider> dialogProvider;
        dialogProvider = new SelectDialog<Provider>("dialog", "Select", providerService, iChoiceRenderer) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
                target.add(kpiForm);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                Provider selectedProvider = getSelectedProvider();
                kpiForm.getKpi().setIdProvider(selectedProvider.getId());
                kpiForm.getNameGeneric().setName(selectedProvider.getName());
                target.add(kpiForm);
//                this.info(String.format("The model object is: '%s'", this.getModelObject()));
            }
        };

        this.add(dialogProvider);
        // Buttons //
        kpiForm.add(new AjaxLinkLayout<LayoutPage>("open", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogProvider.open(art);

            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // dialog entity //
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final EntityType entityType = kpiForm.getKpi().getEntityType();
        List<IEntity> loadEntities = entityService.loadEntities(entityType);
       
             IChoiceRenderer<IEntity> iChoiceEntityRenderer = new IChoiceRenderer<IEntity>() {

            @Override
            public Object getDisplayValue(IEntity t) {
                
                return kpiForm.getEntityName(entityType, t);
            }

            @Override
            public String getIdValue(IEntity t, int i) {
                return String.valueOf(t.getId());
            }

        };

        final SelectDialog<IEntity> entityDialog = new SelectDialog<IEntity>("dialog", "Select", loadEntities, iChoiceEntityRenderer) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
                target.add(kpiForm);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                IEntity entity = getSelectedProvider();
                kpiForm.getKpi().setEntityID(entity.getId());
                kpiForm.getNameGeneric().setName(kpiForm.getEntityName(entityType, entity));
                target.add(kpiForm);
//                this.info(String.format("The model object is: '%s'", this.getModelObject()));
            }
        };
        
        this.add(entityDialog);
        kpiForm.add(new AjaxLinkLayout<LayoutPage>("btnentity", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                if (entityDialog != null) {
                    entityDialog.open(art);
                }
//                getCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
            }
        });
    }

 

    @Override

    public String getTitle() {

        return "Add a kpi";
    }

}
