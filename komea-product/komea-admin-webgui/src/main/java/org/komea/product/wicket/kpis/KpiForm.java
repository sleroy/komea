package org.komea.product.wicket.kpis;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.NameGeneric;
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
    private final IKPIService kpiService;
    private final LayoutPage page;
    private final NameGeneric nameProvider;
    private final NameGeneric nameEntity;
    private final DropDownChoice entityTypeField;
    private final TextField providerField;

    private static final long serialVersionUID = 1L;
    public KpiForm(
            final String _id,
            final IKPIService _kpi,
            final IEntityService _entity,
            final IProviderService _providerService,
            final Component _feedBack,
            final IModel<Kpi> _dto,
            final LayoutPage _kpiPage) {
        super(_id, _dto);
        kpiService = _kpi;
        feedBack = _feedBack;
        kpi = _dto.getObject();
        page = _kpiPage;

        feedBack.setVisible(false);
        this.nameProvider = new NameGeneric("");
        this.nameEntity = new NameGeneric("");

        add(TextFieldBuilder.<String>createRequired("name", kpi, "name").highlightOnErrors()
                .simpleValidator(0, 255).build());

        add(TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("").build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("").build());

        Provider selectByPrimaryKey = _providerService.selectByPrimaryKey(kpi.getIdProvider());
        if (selectByPrimaryKey != null) {
            this.nameProvider.setName(selectByPrimaryKey.getName());
        }
        providerField = TextFieldBuilder.<String>create("idProvider", nameProvider, "name").withTooltip("")
                .build();
        add(providerField);

        TextField<String> textminValue = TextFieldBuilder.<String>create("valueMin", kpi, "valueMin").withTooltip("").build();
        add(textminValue);

        TextField<String> textMaxValue = TextFieldBuilder.<String>create("valueMax", kpi, "valueMax").withTooltip("").build();
        add(textMaxValue);

        add(SelectBoxBuilder.<ValueDirection>createWithEnum("valueDirection", kpi,
                ValueDirection.class).build());

        add(SelectBoxBuilder.<ValueType>createWithEnum("valueType", kpi, ValueType.class).build());
        this.entityTypeField = SelectBoxBuilder.<EntityType>createWithEnum("entityType", kpi, EntityType.class).build();

//        this.entityTypeField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
//           
//            @Override
//            protected void onUpdate(AjaxRequestTarget target) {
//                System.out.println("ca marche bro");
//                target.add(entityTypeField);
//            }
//        });

        add(this.entityTypeField);


        add(TextFieldBuilder.<String>createRequired("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors().withTooltip("").build());

        add(TextFieldBuilder.<String>createRequired("evictionRate", kpi, "evictionRate")
                .withTooltip("").build());

        add(SelectBoxBuilder.<EvictionType>createWithEnum("evictionType", kpi, EvictionType.class)
                .build());

        add(TextAreaBuilder.<String>create("esperRequest", kpi, "esperRequest").withTooltip("")
                .build());

        final Kpi myKpi = this.kpi;

        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new KpiPage(page.getPageParameters()));
            }
        });

        add(new AjaxLinkLayout<TextField>("maxValueMax", textMaxValue) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                myKpi.setValueMax(Double.MAX_VALUE);
                art.add(getCustom());
            }

        });
        add(new AjaxLinkLayout<TextField>("maxValueMin", textminValue) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                myKpi.setValueMin(Double.MAX_VALUE);
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
    public Kpi getKpi() {
        return kpi;
    }

    public TextField getProviderField() {
        return providerField;
    }

    
    
    public NameGeneric getNameProvider() {
        return nameProvider;
    }

    public NameGeneric getNameEntity() {
        return nameEntity;
    }

    public DropDownChoice getEntityTypeField() {
        return entityTypeField;
    }


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
        IKPIService service;
        if (kpiInsert.getId() != null) {
            kpiService.updateByPrimaryKey(kpiInsert);
        } else {
            kpiService.insert(kpiInsert);
        }
        page.setResponsePage(new KpiPage(page.getPageParameters()));

    }


}
