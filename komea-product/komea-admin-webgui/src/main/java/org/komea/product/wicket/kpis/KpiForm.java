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

    private static final long serialVersionUID = 1L;
    private final DropDownChoice entityTypeField;
    private final Component feedBack;
    private final Kpi kpi;
    private final IKPIService kpiService;
    private final NameGeneric nameEntity;
    private final NameGeneric nameProvider;
    private final LayoutPage page;

    private final TextField providerField;

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

//        feedBack.setVisible(false);
        nameProvider = new NameGeneric("");
        nameEntity = new NameGeneric("");

        add(TextFieldBuilder.<String>createRequired("name", kpi, "name").highlightOnErrors()
                .simpleValidator(0, 255).build());

        add(TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("").build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("").build());

        final Provider selectByPrimaryKey
                = _providerService.selectByPrimaryKey(kpi.getIdProvider());
        if (selectByPrimaryKey != null) {
            nameProvider.setName(selectByPrimaryKey.getName());
        }
        providerField
                = TextFieldBuilder.<String>create("idProvider", nameProvider, "name")
                .withTooltip("").buildTextField();
        add(providerField);

        final TextField<String> textminValue
                = TextFieldBuilder.<String>create("valueMin", kpi, "valueMin").withTooltip("")
                .buildTextField();
        add(textminValue);

        final TextField<String> textMaxValue
                = TextFieldBuilder.<String>create("valueMax", kpi, "valueMax").withTooltip("")
                .buildTextField();
        add(textMaxValue);

        add(SelectBoxBuilder.<ValueDirection>createWithEnum("valueDirection", kpi,
                ValueDirection.class).build());

        add(SelectBoxBuilder.<ValueType>createWithEnum("valueType", kpi, ValueType.class).build());
        entityTypeField
                = SelectBoxBuilder.<EntityType>createWithEnum("entityType", kpi, EntityType.class)
                .build();

        // this.entityTypeField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
        //
        // @Override
        // protected void onUpdate(AjaxRequestTarget target) {
        // System.out.println("ca marche bro");
        // target.add(entityTypeField);
        // }
        // });
        add(entityTypeField);

        add(TextFieldBuilder.<String>create("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors().withTooltip("").build());

        add(TextFieldBuilder.<String>createRequired("evictionRate", kpi, "evictionRate")
                .withTooltip("").build());

        add(SelectBoxBuilder.<EvictionType>createWithEnum("evictionType", kpi, EvictionType.class)
                .build());

        add(TextAreaBuilder.<String>create("esperRequest", kpi, "esperRequest").withTooltip("")
                .build());

        final Kpi myKpi = kpi;

        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                final LayoutPage page = getCustom();
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
        // // add a button that can be used to submit the form via ajax
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

    public DropDownChoice getEntityTypeField() {

        return entityTypeField;
    }

    public Kpi getKpi() {

        return kpi;
    }

    public NameGeneric getNameEntity() {

        return nameEntity;
    }

    public NameGeneric getNameProvider() {

        return nameProvider;
    }

    public TextField getProviderField() {

        return providerField;
    }

    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {

        kpiService.saveOrUpdate(kpi);
        page.setResponsePage(new KpiPage(page.getPageParameters()));

    }

}
