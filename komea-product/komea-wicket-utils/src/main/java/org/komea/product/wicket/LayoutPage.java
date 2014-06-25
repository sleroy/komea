
package org.komea.product.wicket;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.utils.IKomeaSecurityController;
import org.komea.product.wicket.utils.KomeaSecurityContextHolderAwareRequestWrapper;
import org.komea.product.wicket.widget.RedirectPageLink;
import org.komea.product.wicket.widget.gravatar.GravatarImageLink;
import org.komea.product.wicket.widget.model.ListDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionPanel;

/**
 * Defines the general layout for all komea pages.
 *
 * @author sleroy
 */
public abstract class LayoutPage extends WebPage {
    
    protected static final Logger   LOGGER       = LoggerFactory.getLogger(LayoutPage.class);
    private static final String     COLORHILIGHT = "#dbdbdb";
    
    @SpringBean(required = false)
    private IEventStatisticsService eventStatisticsService;
    
    @SpringBean(required = false)
    private IPersonService          personService;
    
    // protected final JQueryBehavior jQueryBehavior;
    public LayoutPage(final PageParameters _parameters) {
    
        super(_parameters);
        add(new Label("title", Model.of(getTitle())));
        
        String firstName = "";
        String lastName = "";
        String email = "";
        final UserDetails userDetails = obtainSecurityDetails();
        if (userDetails != null) {
            firstName = userDetails.getUsername();
            lastName = "";
            email = "";
            if (personService != null) {
                final Person person = personService.selectByKey(firstName);
                firstName = person.getFirstName();
                lastName = person.getLastName();
                email = person.getEmail();
            }
            
        }
        
        if (getSecurityController().isUserInRole("ADMIN")) {
            final Fragment fragment = new Fragment("signin", "signinfragment", this);
            fragment.add(new Label("profile", firstName + " " + lastName));
            fragment.add(new GravatarImageLink("avatar2", email, 215));
            buildAlerts(fragment);
            add(fragment);
            
            final Fragment hellofrag = new Fragment("hellopanel", "hellofragment", this);
            hellofrag.add(new Label("helloname", "Hello, " + firstName));
            hellofrag.add(new GravatarImageLink("avatar", email, 215));
            
            add(hellofrag);
            
        } else {
            add(new WebMarkupContainer("signin"));
            add(new WebMarkupContainer("hellopanel"));
            
        }
        buildBreadCrumb();
        
        addAccordingPanel(this);
    }
    
    /**
     * Returns the bread crumb.
     *
     * @return the breadcrumbs
     */
    @SuppressWarnings("rawtypes")
    public final List<? extends Entry<String, Class>> getBreadCrumbs() {
    
        final ArrayList<Entry<String, Class>> arrayList = new ArrayList<Entry<String, Class>>();
        arrayList.add(new KomeaEntry<String, Class>("Home", HomePage.class));
        arrayList.addAll(getMiddleLevelPages());
        arrayList.add(new KomeaEntry<String, Class>(getTitle(), getClass()));
        return arrayList;
    }
    
    /**
     * Returns the page links in breadcrumb between home and active page.
     *
     * @return the list of pages
     */
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {
    
        return Collections.emptyList();
    }
    
    /**
     * Provides the title to show in the panel (H1) markup.
     *
     * @return the title.
     */
    public String getTitle() {
    
        return getString("layout.title");
    }
    
    @Override
    public void renderHead(final IHeaderResponse response) {
    
        super.renderHead(response);
        
        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
    }
    
    /**
     *
     */
    private void buildAlerts(final MarkupContainer _fragment) {
    
        if (eventStatisticsService != null) {
            _fragment.add(new Label("critical-alerts", Model.of(eventStatisticsService.getNumberOfAlerts(Severity.BLOCKER))));
        } else {
            _fragment.add(new Label("critical-alerts", Model.of("NA")));
        }
        
    }
    
    private void buildBreadCrumb() {
    
        final List<Entry<String, Class>> breadPath = (List<Entry<String, Class>>) getBreadCrumbs();
        final List<Entry<String, Class>> notActivePages = new ArrayList();
        for (int i = 0, ni = breadPath.size() - 1; i < ni; ++i) {
            notActivePages.add(breadPath.get(i));
        }
        breadPath.get(breadPath.size() - 1);
        
        add(new ListView<Entry<String, Class>>("bread", notActivePages) {
            
            @Override
            protected void populateItem(final ListItem<Entry<String, Class>> _item) {
            
                _item.add(new RedirectPageLink("blink", _item.getModelObject()));
            }
            
        });
        add(new Label("breadactive", getTitle()));
    }
    
    protected IKomeaSecurityController getSecurityController() {
    
        final IKomeaSecurityController securityController = new KomeaSecurityContextHolderAwareRequestWrapper((ServletRequest) getRequest()
                .getContainerRequest(), "");
        return securityController;
    }
    
    /**
     * Returns the user details
     *
     * @return
     */
    protected UserDetails obtainSecurityDetails() {
    
        final IKomeaSecurityController securityController = getSecurityController();
        final UserDetails userDetails = securityController.getUserDetails();
        return userDetails;
    }
    
    // /////////////////////////////////////////////////////:
    // Menu accordeon wicket
    // /////////////////////////////////////////////////////
    private void addAccordingPanel(final LayoutPage page) {
    
        final Options options = new Options();
        options.set("heightStyle", Options.asString("content"));
        
        // Accordion //
        accordion = new AccordionPanel("accordion", newTabList(page), options) {
            
            private static final long serialVersionUID = 1L;
            
            @Override
            public void onActivate(final AjaxRequestTarget target, final int index, final ITab tab) {
            
                // rien a faire
            }
        };
        
        page.add(accordion);
    }
    
    private List<ITab> newTabList(final LayoutPage page) {
    
        final List<ITab> tabs = new ArrayList<ITab>();
        // tab #3, using AbstractTab //
        final AbstractTab tab1 = new AbstractTab(Model.of("Company")) {
            
            private static final long serialVersionUID = 1L;
            
            @Override
            public WebMarkupContainer getPanel(final String panelId) {
            
                List<IColumn<?, ?>> columns;
                columns = new ArrayList<IColumn<?, ?>>();
                FragmentPropertyColumn fcol;
                fcol = new FragmentPropertyColumn(Model.of("titre"), page, accordion);
                columns.add(fcol);
                final ISortableDataProvider<String, String> dataProvider = new ListDataModel<String>(menuCompany);
                final DataGridView defaultDataTable = new DataGridView("table-panel-1", columns, dataProvider);
                final Fragment fragment = new Fragment(panelId, "panel-1", page);
                fragment.add(defaultDataTable);
                return fragment;
            }
        };
        
        tabs.add(tab1);
        tabs.add(new AbstractTab(Model.of(getString("home.kpis"))) {
            
            private static final long serialVersionUID = 1L;
            
            @Override
            public WebMarkupContainer getPanel(final String panelId) {
            
                List<IColumn<?, ?>> columns;
                columns = new ArrayList<IColumn<?, ?>>();
                
                FragmentPropertyColumn fcol;
                fcol = new FragmentPropertyColumn(Model.of("titre"), page, accordion);
                
                columns.add(fcol);
                final ISortableDataProvider<String, String> dataProvider = new ListDataModel<String>(menuKpis);
                final DataGridView defaultDataTable = new DataGridView("table-panel-2", columns, dataProvider);
                final Fragment fragment = new Fragment(panelId, "panel-2", page);
                fragment.add(defaultDataTable);
                return fragment;
            }
        });
        //
        tabs.add(new AbstractTab(Model.of(getString("home.administration"))) {
            
            private static final long serialVersionUID = 1L;
            
            @Override
            public WebMarkupContainer getPanel(final String panelId) {
            
                List<IColumn<?, ?>> columns;
                columns = new ArrayList<IColumn<?, ?>>();
                FragmentPropertyColumn fcol;
                fcol = new FragmentPropertyColumn(Model.of("titre"), page, accordion);
                columns.add(fcol);
                final ISortableDataProvider<String, String> dataProvider = new ListDataModel<String>(menuAdministration);
                final DataGridView defaultDataTable = new DataGridView("table-panel-3", columns, dataProvider);
                
                // defaultDataTable.get(1).add(new AttributeModifier("class",new Model("danger") ));
                final Fragment fragment = new Fragment(panelId, "panel-3", page);
                fragment.add(defaultDataTable);
                return fragment;
            }
        });
        return tabs;
    }
    
    private AccordionPanel     accordion;
    
    public final static int    ADMIN_INDEX        = 2;
    
    public final static int    COMPANY_INDEX      = 0;
    
    public final static int    KPIS_INDEX         = 1;
    
    private final List<String> menuAdministration = Arrays.asList("panel-settings", "panel-plugins", "panel-viewlog", "panel-stats",
                                                          "panel-events", "panel-cronpage", "panel-empty");
    private final List<String> menuCompany        = Arrays.asList("panel-users", "panel-teams", "panel-departments", "panel-customers",
                                                          "panel-projects");
    
    private final List<String> menuKpis           = Arrays.asList("panel-kpiview", "panel-alerts", "panel-kpis", "panel-empty");
    
    public static class FragmentPropertyColumn extends AbstractColumn<String, String> {
        
        private final AccordionPanel accordeon;
        private final LayoutPage     page;
        
        public FragmentPropertyColumn(final IModel<String> _displayModel, final LayoutPage _page, final AccordionPanel _accordion) {
        
            super(_displayModel);
            accordeon = _accordion;
            page = _page;
        }
        
        @Override
        public void populateItem(final Item<ICellPopulator<String>> item, final String string, final IModel<String> imodel) {
        
            final String object = imodel.getObject();
            
            item.add(new Fragment(string, object, page));
            
            if (testMenu(object, "panel-teams", "TeamPage", "TeamEditPage")
                    || testMenu(object, "panel-users", "PersonPage", "PersonAddPage")
                    || testMenu(object, "panel-departments", "DepartmentPage", "DepartmentEditPage")
                    || testMenu(object, "panel-customers", "CustomerPage", "CustomerEditPage")
                    || testMenu(object, "panel-projects", "ProjectPage", "ProjectEditPage")) {
                accordeon.setActiveTab(COMPANY_INDEX);
                item.add(new AttributeModifier("style", new Model("background-color: " + COLORHILIGHT)));
            }
            
            if (testMenu(object, "panel-kpis", "KpiPage", "KpiEditPage") || testMenu(object, "panel-kpiview", "KpiChartPage")
                    || testMenu(object, "panel-kpiview", "KpiValuesPage") || testMenu(object, "panel-alerts", "AlertPage", "AlertEditPage")) {
                accordeon.setActiveTab(KPIS_INDEX);
                item.add(new AttributeModifier("style", new Model("background-color: " + COLORHILIGHT)));
            }
            
            if (testMenu(object, "panel-settings", "SettingsPage")
                    || testMenu(object, "panel-plugins", "ProviderPage", "ProviderPanel", "ProviderTableActionPanel")
                    || testMenu(object, "panel-viewlog", "ConsolePage", "EditPage")
                    || testMenu(object, "panel-stats", "StatPage", "EditPage") || testMenu(object, "panel-events", "EventsPage")
                    || testMenu(object, "panel-cronpage", "CronPage")) {
                accordeon.setActiveTab(ADMIN_INDEX);
                item.add(new AttributeModifier("style", new Model("background-color: " + COLORHILIGHT)));
            }
            
        }
        
        private boolean testMenu(final String _idPanel, final String _idPanelConstruc, final String... _pages) {
        
            if (_idPanel.equals(_idPanelConstruc)) {
                for (final String string : _pages) {
                    if (string.equals(page.getClass().getSimpleName())) {
                        return true;
                    }
                }
            }
            return false;
            
        }
        
    }
    
}
