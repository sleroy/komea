package org.komea.product.wicket.kpis;

import com.googlecode.wicket.jquery.ui.form.slider.AbstractSlider.SliderBehavior;
import com.googlecode.wicket.jquery.ui.form.slider.Slider;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.komea.product.backend.service.cron.CronUtils;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.backend.service.esper.ConvertELIntoQuery;
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
    final TextField<Double> textminValue;
    private final TextField<Integer> goal;

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
                .simpleValidator(0, 255).withTooltip("Kpi need a name").build());
        TextFieldBuilder<String> keyField = TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("Kpi need a unique key");

        if (isNew) {
            keyField.UniqueStringValidator("Kpi key", kpiService);
        } else {
            keyField.buildTextField().setEnabled(false);
        }

        add(keyField.build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("Description of kpi").build());

        add(SelectBoxBuilder.<ProviderType>createWithEnum("providerType", kpi, ProviderType.class)
                .withTooltip("Choose a type of provider").build());

        textminValue
                = TextFieldBuilder.<Double>create("valueMin", kpi, "valueMin").withTooltip("Define the minimum value of the kpi")
                .buildTextField();

        add(textminValue);
//        final Form<Integer> form = new Form<Integer>("form", new Model<Integer>(15));
//        this.add(form);
        int i = 0;
        if (kpi.getObjective() != null) {
            i = kpi.getObjective().intValue();
        }
        Model<Integer> model = new Model<Integer>(i);
        goal = new TextField<Integer>("goal", model, Integer.class);
//        final Label label = new Label("label", model);
        add(goal);

//        SliderBehavior slider = new SliderBehavior("objectif", options);
        Slider slider = new Slider("objectif", model, goal);
        slider.setMin(kpi.getValueMin().intValue());
        slider.setMax(kpi.getValueMax().intValue());
        add(slider);

        final TextField<Double> textMaxValue
                = TextFieldBuilder.<Double>create("valueMax", kpi, "valueMax").withTooltip("Define the maximum value of the kpi")
                .buildTextField();
        textMaxValue.add(new IValidator<Double>() {

            @Override
            public void validate(IValidatable<Double> validatable) {
                Double valueMax = validatable.getValue();
                String value = textminValue.getValue();
                try {

                    Double valueMin = Double.valueOf(value);
                    if (valueMax <= valueMin) {
                        ValidationError error = new ValidationError();
                        error.setMessage("Max value must be greater than min value");
                        validatable.error(error);
                    }
                } catch (NumberFormatException e) {
                    // Si la valeur min n'est pas un double on ne fait simplemnet pas la validation
                    // l'erreur sera capter par le validateur de la valeur min
                }
            }
        });
        add(textMaxValue);

        add(SelectBoxBuilder.<ValueDirection>createWithEnum("valueDirection", kpi,
                ValueDirection.class).withTooltip("").build());

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

        TextArea<String> formulaField = TextAreaBuilder.<String>create("formula", kpi, "esperRequest").withTooltip("")
                .build();

        formulaField.add(new IValidator<String>() {

            @Override
            public void validate(IValidatable<String> validatable) {
                String value = validatable.getValue();
                if (!ConvertELIntoQuery.isValidFormula(value)) {
                    ValidationError error = new ValidationError();
                    error.setMessage("formula expression is invalid");
                    validatable.error(error);
                }
            }
        });

        add(formulaField);

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
                Double valueOf = kpi.getValueMin();
                try {
                    valueOf = Double.valueOf(goal.getValue());
                    if (valueOf > kpi.getValueMax()) {
                        valueOf = kpi.getValueMax();
                    }
                    if (valueOf < kpi.getValueMin()) {
                        valueOf = kpi.getValueMin();
                    }

                } catch (NumberFormatException e) {
                    //aucun retour a founir
                }
                kpi.setObjective(valueOf);
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
