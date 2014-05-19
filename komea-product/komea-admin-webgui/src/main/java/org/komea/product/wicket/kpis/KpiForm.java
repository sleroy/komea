package org.komea.product.wicket.kpis;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.groovy.IGroovyEngineService;
import org.komea.product.backend.service.cron.CronUtils;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.enums.EntityType;
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

	private static final long	       serialVersionUID	= 1L;
	private final Component	           feedBack;
	private final boolean	           isNew;
	private final Kpi	               kpi;
	private final IKPIService	       kpiService;
	private final NameGeneric	       nameEntity;
	private final LayoutPage	       page;
	private final TextField<Double>	   textminValue;

	private final IGroovyEngineService	groovyEngineService;

	public KpiForm(final boolean _isNew, final String _id, final IKPIService _kpi, final IEntityService _entity,
	        final IProviderService _providerService, final Component _feedBack, final IModel<Kpi> _dto,
	        final LayoutPage _kpiPage, final IGroovyEngineService _groovyEngineService) {

		super(_id, _dto);
		isNew = _isNew;
		kpiService = _kpi;
		feedBack = _feedBack;
		kpi = _dto.getObject();
		page = _kpiPage;
		groovyEngineService = _groovyEngineService;
		feedBack.setVisible(false);
		nameEntity = new NameGeneric("");

		add(TextFieldBuilder.<String> createRequired("name", kpi, "name").highlightOnErrors().simpleValidator(0, 255)
		        .withTooltip(getString("global.field.tooltip.name")).build());

		final TextFieldBuilder<String> keyField = TextFieldBuilder.<String> createRequired("kpiKey", kpi, "kpiKey")
		        .simpleValidator(0, 255).highlightOnErrors().withTooltip(getString("global.field.tooltip.key"));

		if (isNew) {
			keyField.UniqueStringValidator(getString("global.field.key"), kpiService);
		} else {
			keyField.buildTextField().add(new AttributeModifier("readonly", "readonly"));
		}

		add(keyField.build());

		add(TextAreaBuilder.<String> create("description", kpi, "description").simpleValidator(0, 2048)
		        .highlightOnErrors().withTooltip(getString("global.field.tooltip.description")).build());

		add(SelectBoxBuilder.<ProviderType> createWithEnum("providerType", kpi, ProviderType.class)
		        .withTooltip(getString("kpipage.save.form.field.tooltip.category")).build());

		textminValue = TextFieldBuilder.<Double> create("valueMin", kpi, "valueMin")
		        .withTooltip(getString("kpipage.save.form.field.tooltip.minvalue")).buildTextField();

		add(textminValue);
		//
		final TextField<Double> textMaxValue = TextFieldBuilder.<Double> create("valueMax", kpi, "valueMax")
		        .withTooltip(getString("kpipage.save.form.field.tooltip.maxvalue")).buildTextField();
		textMaxValue.add(new IValidator<Double>() {

			@Override
			public void validate(final IValidatable<Double> validatable) {

				final Double valueMax = validatable.getValue();
				final String value = textminValue.getValue();
				try {

					final Double valueMin = Double.valueOf(value);
					if (valueMax <= valueMin) {
						final ValidationError error = new ValidationError();
						error.setMessage(getString("kpipage.save.form.error.minmax"));
						validatable.error(error);
					}
				} catch (final NumberFormatException e) {
					// Si la valeur min n'est pas un double on ne fait
					// simplemnet pas la validation
					// l'erreur sera capter par le validateur de la valeur min
				}
			}
		});
		add(textMaxValue);

		add(SelectBoxBuilder.<ValueDirection> createWithEnum("valueDirection", kpi, ValueDirection.class)
		        .withTooltip(getString("kpipage.save.form.field.tooltip.dirvalue")).build());

		add(SelectBoxBuilder.<ValueType> createWithEnum("valueType", kpi, ValueType.class)
		        .withTooltip(getString("kpipage.save.form.field.tooltip.typvalue")).build());

		add(SelectBoxBuilder.<EntityType> createWithEnum("entityType", kpi, EntityType.class)
		        .withTooltip(getString("kpipage.save.form.field.tooltip.typentity")).build());
		final TextField<String> buildCronField = TextFieldBuilder
		        .<String> create("cronExpression", kpi, "cronExpression").simpleValidator(0, 60).highlightOnErrors()
		        .withTooltip(getString("kpipage.save.form.field.tooltip.cron")).buildTextField();

		buildCronField.add(new IValidator<String>() {

			@Override
			public void validate(final IValidatable<String> validatable) {

				final String value = validatable.getValue();
				if (!CronUtils.isValidCronExpression(value)) {
					final ValidationError error = new ValidationError();
					error.setMessage(getString("kpipage.save.form.error.cron"));
					validatable.error(error);
				}
			}
		});

		add(buildCronField);

		// FIXME::
		// add(SelectBoxBuilder.<EvictionType>createWithEnum("evictionType",
		// kpi, EvictionType.class)
		// .withTooltip(getString("kpipage.save.form.field.tooltip.evicttype"))
		// .build());

		final TextArea<String> formulaField = TextAreaBuilder.<String> create("formula", kpi, "esperRequest")
		        .withTooltip("").withTooltip(getString("kpipage.save.form.field.tooltip.formula")).build();

		formulaField.add(new IValidator<String>() {

			@Override
			public void validate(final IValidatable<String> validatable) {

				final String value = validatable.getValue();
				if (!groovyEngineService.isValidFormula(value)) {
					final ValidationError error = new ValidationError();
					error.setMessage(getString("kpipage.save.form.error.formula"));
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
				// repaint the feedback panel so that it is hidden
				target.add(feedBack);
				kpi.getValueMin();

				// FIXME::kpi.setObjective(valueOf);
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
