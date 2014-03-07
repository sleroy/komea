package org.komea.product.wicket.kpis;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.InputDialog;
import java.io.Serializable;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * Formular to edit properties in the settings page.
 *
 * @author sleroy
 */
public final class KpiForm extends Form<Kpi> {

    private final Component feedBack;
    private final Kpi kpi;
    private final KpiDao kpiDao;
    private final ProviderDao providerDao;
    private final LayoutPage page;
    private final NameProvider nameProvider;

    public KpiForm(
            final String _id,
            final KpiDao _kpi,
            final IEntityService _entity,
            final ProviderDao _providerDao,
            final Component _feedBack,
            final IModel<Kpi> _dto,
            final LayoutPage _kpiPage) {
        super(_id, _dto);
        kpiDao = _kpi;
        feedBack = _feedBack;
        kpi = _dto.getObject();
        page = _kpiPage;
        providerDao = _providerDao;
        feedBack.setVisible(false);
        add(TextFieldBuilder.<String>createRequired("name", kpi, "name").highlightOnErrors()
                .simpleValidator(0, 255).build());

        add(TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("").build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("").build());
        this.nameProvider = new NameProvider("");
        Provider selectByPrimaryKey = _providerDao.selectByPrimaryKey(kpi.getIdProvider());
        if (selectByPrimaryKey != null) {
            this.nameProvider.setName(selectByPrimaryKey.getName());
        }
        TextField<String> fieldIdProvider = TextFieldBuilder.<String>create("idProvider", nameProvider, "name").withTooltip("")
                .build();
        add(fieldIdProvider);

        TextField<String> textminValue = TextFieldBuilder.<String>create("valueMin", kpi, "valueMin").withTooltip("").build();
        add(textminValue);

        TextField<String> textMaxValue = TextFieldBuilder.<String>create("valueMax", kpi, "valueMax").withTooltip("").build();
        add(textMaxValue);

        add(SelectBoxBuilder.<ValueDirection>createWithEnum("valueDirection", kpi,
                ValueDirection.class).build());

        add(SelectBoxBuilder.<ValueType>createWithEnum("valueType", kpi, ValueType.class).build());

        add(SelectBoxBuilder.<EntityType>createWithEnum("entityType", kpi, EntityType.class)
                .build());

//        String entityName = "";
//        if (kpi.getEntityType() != null && kpi.getEntityID() != null) {
//            final IEntity entity = _entity.getEntity(new EntityKey(kpi.getEntityType(), kpi.getEntityID()));
//            entityName = getEntityName(kpi.getEntityType(), entity);
//        }
        add(TextFieldBuilder.<String>create("entityID", kpi, "entityID").withTooltip("")
                .build());

        add(TextFieldBuilder.<String>createRequired("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors().withTooltip("").build());

        add(TextFieldBuilder.<String>createRequired("evictionRate", kpi, "evictionRate")
                .withTooltip("").build());

        add(SelectBoxBuilder.<EvictionType>createWithEnum("evictionType", kpi, EvictionType.class)
                .build());

        add(TextAreaBuilder.<String>create("esperRequest", kpi, "esperRequest").withTooltip("")
                .build());

        final Kpi myKpi = this.kpi;
        // ///////////////////////////////////////////////////////////////////////////////////
        // //////////////////////////////// Popup //////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
//        final TeamSelectDialog<TextField> dialog;
//        dialog = new TeamSelectDialog<TextField>("dialog", "Select", providerDao, fieldIdProvider) {
//
//            @Override
//            public void onClose(AjaxRequestTarget target, DialogButton button) {
////                target.add(KpiForm);
//            }
//
//            @Override
//            protected void onSubmit(AjaxRequestTarget art) {
//                Provider selectedProvider = getSelectedProvider();
//                myKpi.setIdProvider(selectedProvider.getId());
//                System.out.println("on Submit dialog " + selectedProvider.getName());
////                art.add(getCustom());
////                this.info(String.format("The model object is: '%s'", this.getModelObject()));
//            }
//        };
//
//        this.add(dialog);
//        // Buttons //
//        add(new AjaxLinkLayout<LayoutPage>("open", page) {
//
//            @Override
//            public void onClick(final AjaxRequestTarget art) {
//                dialog.open(art);
//
////                getCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
//            }
//        });
        // ///////////////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new KpiPage(page.getPageParameters()));
            }
        });

        add(new AjaxLinkLayout<LayoutPage>("btnentity", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

//                getCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
            }
        });

        add(new AjaxLinkLayout<TextField>("maxValueMax", textMaxValue) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                myKpi.setValueMax(Double.MAX_VALUE);

                System.out.println("de max");
//                  tPageCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
                art.add(getCustom());
            }

        });
        add(new AjaxLinkLayout<TextField>("maxValueMin", textminValue) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                myKpi.setValueMin(Double.MAX_VALUE);

                System.out.println("de min");
//                  tPageCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
                art.add(getCustom());
            }

        });
//
//        // add a button that can be used to submit the form via ajax
        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }
//

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);

            }
        });

        // ///////////////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
    }

    private String getEntityName(final EntityType type, final IEntity entity) {

        String result = "";
        if (type.equals(EntityType.PERSON)) {
            result = ((Person) entity).getFirstName() + " " + ((Person) entity).getLastName();
        } else if (type.equals(EntityType.PROJECT)) {
            result = ((Project) entity).getName();
        } else if (type.equals(EntityType.TEAM) && type.equals(EntityType.DEPARTMENT)) {
            result = ((PersonGroup) entity).getName();
        }

        return result;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public NameProvider getNameProvider() {
        return nameProvider;
    }

    private static final long serialVersionUID = 1L;

    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {

        final Kpi kpiInsert = new Kpi();
        kpiInsert.setId(this.kpi.getId());
        kpiInsert.setCronExpression(this.kpi.getCronExpression());
        kpiInsert.setDescription(this.kpi.getDescription());
        kpiInsert.setName(this.kpi.getName());
        kpiInsert.setKpiKey(this.kpi.getKpiKey());
        kpiInsert.setIdProvider(this.kpi.getIdProvider());
        kpiInsert.setEntityID(this.kpi.getEntityID());
        kpiInsert.setEntityType(this.kpi.getEntityType());
        kpiInsert.setEvictionRate(this.kpi.getEvictionRate());
        kpiInsert.setEvictionType(this.kpi.getEvictionType());

        kpiInsert.setValueMin(this.kpi.getValueMin());
        kpiInsert.setValueMax(this.kpi.getValueMax());
//        System.out.println("kpi max :" + this.kpi.getValueMax());
        kpiInsert.setValueDirection(this.kpi.getValueDirection());
        kpiInsert.setValueType(this.kpi.getValueType());

        kpiInsert.setEsperRequest(this.kpi.getEsperRequest());

        if (kpiInsert.getId() != null) {
            kpiDao.updateByPrimaryKey(kpiInsert);
        } else {
            kpiDao.insert(kpiInsert);
        }
        page.setResponsePage(new KpiPage(page.getPageParameters()));

    }

    public static class NameProvider implements Serializable {

        private String name;

        public NameProvider(String name) {
            this.name = name;
        }

        public NameProvider() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
