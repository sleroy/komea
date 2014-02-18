package org.komea.product.wicket.kpis;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.time.Duration;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 * Formular to edit properties in the settings page.
 *
 * @author sleroy
 */
public final class KpiForm extends Form<Kpi> {

//    private Kpi kpi;
    private KpiDao kpiDao;
    private Component feedBack;
    private Kpi kpi;
    private LayoutPage page;

    public KpiForm(String _id, KpiDao _kpi, final Component _feedBack, final IModel<Kpi> _dto, LayoutPage _kpiPage) {

        super(_id, _dto);
        this.kpiDao = _kpi;
        feedBack = _feedBack;
        kpi = _dto.getObject();
        this.page = _kpiPage;

        add(TextFieldBuilder.<String>createRequired("name", kpi, "name")
                .highlightOnErrors().simpleValidator(0, 255)
                .build());

        add(TextFieldBuilder.<String>createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors()
                .withTooltip("").build());

        add(TextAreaBuilder.<String>create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors()
                .withTooltip("").build());

        add(TextFieldBuilder.<String>create("idProvider", kpi, "idProvider")
                .withTooltip("").build());

        add(TextFieldBuilder.<String>create("valueMin", kpi, "valueMin")
                .withTooltip("").build());

        add(TextFieldBuilder.<String>create("valueMax", kpi, "valueMax")
                .withTooltip("").build());

        add(SelectBoxBuilder.<ValueDirection>createWithEnum("valueDirection", kpi, ValueDirection.class).build());

        add(SelectBoxBuilder.<ValueType>createWithEnum("valueType", kpi, ValueType.class).build());

        add(SelectBoxBuilder.<EntityType>createWithEnum("entityType", kpi, EntityType.class).build());

        add(TextFieldBuilder.<String>create("entityID", kpi, "entityID")
                .withTooltip("").build());

        add(TextFieldBuilder.<String>createRequired("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors()
                .withTooltip("").build());

        add(TextFieldBuilder.<String>createRequired("evictionRate", kpi, "evictionRate")
                .withTooltip("").build());

        add(SelectBoxBuilder.<EvictionType>createWithEnum("evictionType", kpi, EvictionType.class).build());

        add(TextAreaBuilder.<String>create("esperRequest", kpi, "esperRequest")
                .withTooltip("").build());

//        AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);

        add(new AjaxLinkLayout("cancel", this.page) {

            @Override
            public void onClick(AjaxRequestTarget art) {
                this.getPageCustom().setResponsePage(new KpiPage(this.getPageCustom().getPageParameters()));
            }
        });

        // add a button that can be used to submit the form via ajax
        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);

            }
        });

    }

    public static abstract class AjaxLinkLayout extends AjaxLink {

        private LayoutPage page;

        public AjaxLinkLayout(String string, LayoutPage page) {
            super(string);
            this.page = page;
        }

        public AjaxLinkLayout(String string, IModel imodel, LayoutPage page) {
            super(string, imodel);
            this.page = page;
        }

        public LayoutPage getPageCustom() {
            return page;
        }

    }

    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {

        final Kpi kpi = new Kpi();
        kpi.setId(this.kpi.getId());
        kpi.setCronExpression(this.kpi.getCronExpression());
        kpi.setDescription(this.kpi.getDescription());
        kpi.setName(this.kpi.getName());
        kpi.setKpiKey(this.kpi.getKpiKey());

        kpi.setEntityID(this.kpi.getEntityID());
        kpi.setEntityType(this.kpi.getEntityType());
        kpi.setEvictionRate(this.kpi.getEvictionRate());
        kpi.setEvictionType(this.kpi.getEvictionType());

        kpi.setValueMin(this.kpi.getValueMin());
        kpi.setValueMax(this.kpi.getValueMax());
        kpi.setValueDirection(this.kpi.getValueDirection());
        kpi.setValueType(this.kpi.getValueType());

        kpi.setEsperRequest(this.kpi.getEsperRequest());

        if (kpi.getId() != null) {
            this.kpiDao.updateByPrimaryKey(kpi);
        } else {
            this.kpiDao.insert(kpi);
        }
        this.page.setResponsePage(new KpiPage(this.page.getPageParameters()));
        // faire la modification dans la base
        //////////////////////////////
//        this.kpiDao.i
        ////////////////////////////////
        // exemple
//        if (selectedRole != null) {
//            person.setIdPersonRole(selectedRole.getId());
//        }
//        if (person.getId() != null) {
//            personDAO.updateByPrimaryKey(person);
//        } else {
//            personDAO.insert(person);
//        }
    }

}
