
package org.komea.product.wicket.kpis;



import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
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
import org.komea.product.service.dto.EntityKey;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;



/**
 * Formular to edit properties in the settings page.
 * 
 * @author sleroy
 */
public final class KpiForm extends Form<Kpi>
{
    
    
    public static abstract class AjaxLinkLayout extends AjaxLink
    {
        
        
        private final LayoutPage page;
        
        
        
        public AjaxLinkLayout(final String string, final IModel imodel, final LayoutPage page) {
        
        
            super(string, imodel);
            this.page = page;
        }
        
        
        public AjaxLinkLayout(final String string, final LayoutPage page) {
        
        
            super(string);
            this.page = page;
        }
        
        
        public LayoutPage getPageCustom() {
        
        
            return page;
        }
        
    }
    
    
    
    private final Component  feedBack;
    private final Kpi        kpi;
    
    // private Kpi kpi;
    private final KpiDao     kpiDao;
    
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
        add(TextFieldBuilder.<String> createRequired("name", kpi, "name").highlightOnErrors()
                .simpleValidator(0, 255).build());
        
        add(TextFieldBuilder.<String> createRequired("kpiKey", kpi, "kpiKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip("").build());
        
        add(TextAreaBuilder.<String> create("description", kpi, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip("").build());
        _providerDao.selectByPrimaryKey(kpi.getIdProvider());
        // String name = provider.getName();
        add(TextFieldBuilder.<String> create("idProvider", kpi, "idProvider").withTooltip("")
                .build());
        
        add(TextFieldBuilder.<String> create("valueMin", kpi, "valueMin").withTooltip("").build());
        
        add(TextFieldBuilder.<String> create("valueMax", kpi, "valueMax").withTooltip("").build());
        
        
        add(SelectBoxBuilder.<ValueDirection> createWithEnum("valueDirection", kpi,
                ValueDirection.class).build());
        
        add(SelectBoxBuilder.<ValueType> createWithEnum("valueType", kpi, ValueType.class).build());
        
        add(SelectBoxBuilder.<EntityType> createWithEnum("entityType", kpi, EntityType.class)
                .build());
        
        final IEntity entity =
                _entity.getEntity(EntityKey.of(kpi.getEntityType(), kpi.getEntityID()));
        final String entityName = getEntityName(kpi.getEntityType(), entity);
        add(TextFieldBuilder.<String> create("entityID", entityName, "toString").withTooltip("")
                .build());
        
        add(TextFieldBuilder.<String> createRequired("cronExpression", kpi, "cronExpression")
                .simpleValidator(0, 60).highlightOnErrors().withTooltip("").build());
        
        add(TextFieldBuilder.<String> createRequired("evictionRate", kpi, "evictionRate")
                .withTooltip("").build());
        
        add(SelectBoxBuilder.<EvictionType> createWithEnum("evictionType", kpi, EvictionType.class)
                .build());
        
        add(TextAreaBuilder.<String> create("esperRequest", kpi, "esperRequest").withTooltip("")
                .build());
        
        // AjaxFormValidatingBehavior.addToAllFormComponents(this, "onkeyup", Duration.ONE_SECOND);
        // ///////////////////////////////////////////////////////////////////////////////////
        // //////////////////////////////// Popup //////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
        // ///////////////////////////////////////////////////////////////////////////////////
        add(new AjaxLinkLayout("cancel", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                getPageCustom().setResponsePage(new KpiPage(getPageCustom().getPageParameters()));
            }
        });
        
        add(new AjaxLinkLayout("btnentity", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                getPageCustom().setResponsePage(new KpiPage(getPageCustom().getPageParameters()));
            }
        });
        
        final Kpi trKpi = kpi;
        
        add(new AjaxButton("maxValueMax", this)
        {
            
            
            @Override
            public void onSubmit() {
            
            
                trKpi.setValueMax(Double.MAX_VALUE);
            }
            
        });
        
        // add a button that can be used to submit the form via ajax
        add(new AjaxButton("submit", this)
        {
            
            
            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
            
            
                feedBack.setVisible(true);
                error("error found");
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                feedBack.setVisible(false);
                info("Submitted information");
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                
            }
        });
        
    }
    
    
    private String getEntityName(final EntityType type, final IEntity entity) {
    
    
        String result = "";
        if (type == EntityType.PERSON) {
            result = ((Person) entity).getFirstName() + " " + ((Person) entity).getLastName();
        } else if (type == EntityType.PROJECT) {
            result = ((Project) entity).getName();
        } else if (type == EntityType.TEAM && type == EntityType.DEPARTMENT) {
            result = ((PersonGroup) entity).getName();
        }
        
        return result;
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
            kpiDao.updateByPrimaryKey(kpi);
        } else {
            kpiDao.insert(kpi);
        }
        page.setResponsePage(new KpiPage(page.getPageParameters()));
        // faire la modification dans la base
        // ////////////////////////////
        // this.kpiDao.i
        // //////////////////////////////
        // exemple
        // if (selectedRole != null) {
        // person.setIdPersonRole(selectedRole.getId());
        // }
        // if (person.getId() != null) {
        // personDAO.updateByPrimaryKey(person);
        // } else {
        // personDAO.insert(person);
        // }
    }
    
    
}
