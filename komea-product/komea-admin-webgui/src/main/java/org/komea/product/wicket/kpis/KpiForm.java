package org.komea.product.wicket.kpis;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.komea.product.backend.service.cron.CronUtils;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
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
    private final Component feedBack;
    private final Kpi kpi;
    private final IKPIService kpiService;
    private final NameGeneric nameEntity;
    private final LayoutPage page;
    private final boolean isNew;

    public KpiForm(
            final boolean _isNew,
            final String _id,
            final IKPIService _kpi,
            final IEntityService _entity,
            final IProviderService _providerService,
            final Component _feedBack,
            final IModel<Kpi> _dto,
            final LayoutPage _kpiPage) {

        super(_id, _dto);
        this.isNew = _isNew;
        kpiService = _kpi;
        feedBack = _feedBack;
        kpi = _dto.getObject();
        page = _kpiPage;

        feedBack.setVisible(false);
        nameEntity = new NameGeneric("");

        add(TextFieldBuilder.<String>createRequired("name", kpi, "name").highlightOnErrors()
                .simpleValidator(0, 255).build());
        TextFieldBuilder<String> keyField = TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("");

        if (isNew) {
            keyField.UniqueStringValidator("Kpi key", kpiService);
        } else {
            keyField.buildTextField().setEnabled(false);
        }

        add(keyField.build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("").build());

        add(SelectBoxBuilder.<ProviderType>createWithEnum("providerType", kpi, ProviderType.class)
                .build());

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

        add(SelectBoxBuilder.<EntityType>createWithEnum("entityType", kpi, EntityType.class)
                .build());
        TextField<String> buildCronField = TextFieldBuilder.<String>create("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors().withTooltip("").buildTextField();

        buildCronField.add(new IValidator<String>() {

            @Override
            public void validate(IValidatable<String> validatable) {
                String value = validatable.getValue();
                if (!CronUtils.isValidCronExpression(value)) {
                    ValidationError error = new ValidationError();
                    error.setMessage("Cron expression is invalid");
                    validatable.error(error);
                }
            }
        }
        );

        add(buildCronField);

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

                myKpi.setValueMin(-Double.MAX_VALUE);
                art.add(getCustom());
            }

        });
        //
        // // add a button that can be used to submit the form via ajax
        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
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
                kpiService.saveOrUpdate(kpi);
                page.setResponsePage(new KpiPage(page.getPageParameters()));

            }
        });

        // ///////////////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
    }

    public Kpi getKpi() {

        return kpi;
    }

    public NameGeneric getNameEntity() {

        return nameEntity;
    }

    /**
     * Validation the formular : settings are updated from the DTO
     */
}
