package org.komea.product.wicket.kpis;


import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.InputDialog;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
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
import org.komea.product.wicket.LayoutPage;
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
    // private Kpi kpi;
    private final KpiDao kpiDao;

    private final LayoutPage page;

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
        feedBack.setVisible(false);
        add(TextFieldBuilder.<String>createRequired("name", kpi, "name").highlightOnErrors()
                .simpleValidator(0, 255).build());

        add(TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("").build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("").build());
//        Provider selectByPrimaryKey = _providerDao.selectByPrimaryKey(kpi.getIdProvider());
        // String name = provider.getName();
        add(TextFieldBuilder.<String>create("idProvider", kpi, "idProvider").withTooltip("")
                .build());

         TextField<String> textminValue =TextFieldBuilder.<String>create("valueMin", kpi, "valueMin").withTooltip("").build();
        add(textminValue);

        TextField<String> textMaxValue = TextFieldBuilder.<String>create("valueMax", kpi, "valueMax").withTooltip("").build();
        add(textMaxValue);

        add(SelectBoxBuilder.<ValueDirection>createWithEnum("valueDirection", kpi,
                ValueDirection.class).build());

        add(SelectBoxBuilder.<ValueType>createWithEnum("valueType", kpi, ValueType.class).build());

        add(SelectBoxBuilder.<EntityType>createWithEnum("entityType", kpi, EntityType.class)
                .build());

        String entityName = "";
        if (kpi.getEntityType() != null && kpi.getEntityID() != null) {
            final IEntity entity = _entity.getEntity(kpi.getEntityType(), kpi.getEntityID());
            entityName = getEntityName(kpi.getEntityType(), entity);
        }
        add(TextFieldBuilder.<String>create("entityID", entityName, "toString").withTooltip("")
                .build());

        add(TextFieldBuilder.<String>createRequired("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors().withTooltip("").build());

        add(TextFieldBuilder.<String>createRequired("evictionRate", kpi, "evictionRate")
                .withTooltip("").build());

        add(SelectBoxBuilder.<EvictionType>createWithEnum("evictionType", kpi, EvictionType.class)
                .build());

        add(TextAreaBuilder.<String>create("esperRequest", kpi, "esperRequest").withTooltip("")
                .build());

        // AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);
        
        
        // ///////////////////////////////////////////////////////////////////////////////////
        // //////////////////////////////// Popup //////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
//        final KpiForm formular = this;
//        final InputDialog<String> dialog = new InputDialog<String>("dialog", "Input", "Please provide a value:", new Model<String>("a sample value")) {
//
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void onSubmit(AjaxRequestTarget target)
//			{
//				this.info("The form has been submitted");
//				this.info(String.format("The model object is: '%s'", this.getModelObject()));
//			}
//
//			@Override
//			public void onClose(AjaxRequestTarget target, DialogButton button)
//			{
//				this.info(button + " has been clicked");
//				target.add(formular);
//			}
//		};
//        this.add(dialog);
//        
//        add(new AjaxButton("openIdProvider") {
//
//		
//
//			@Override
//			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
//			{
//				dialog.open(target);
//			}
//		});
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

        final Kpi myKpi = this.kpi;
        add(new AjaxLinkLayout<TextField>("maxValueMax", textMaxValue) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                myKpi.setValueMax(Double.MAX_VALUE);

//                 ge System.out.println("de");tPageCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
                art.add(getCustom());
            }

        });
                add(new AjaxLinkLayout<TextField>("maxValueMin", textminValue) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                myKpi.setValueMin(Double.MAX_VALUE);

//                 ge System.out.println("de");tPageCustom().setResponsePage(new KpiPage(getCustom().getPageParameters()));
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
}
