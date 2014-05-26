package org.komea.product.wicket;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import org.apache.wicket.AttributeModifier;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.wicket.utils.KomeaSecurityContextHolderAwareRequestWrapper;
import org.komea.product.wicket.widget.RedirectPageLink;
import org.komea.product.wicket.widget.model.ListDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the general layout for all komea pages.
 *
 * @author sleroy
 */
public abstract class LayoutPage extends WebPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutPage.class);

    private static final long serialVersionUID = 1L;

    protected AccordionPanel accordion;

    public final static int COMPANY_INDEX = 0;
    public final static int KPIS_INDEX = 1;
    public final static int ADMIN_INDEX = 2;

//    protected final JQueryBehavior jQueryBehavior;
    public LayoutPage(final PageParameters _parameters) {

        super(_parameters);
        add(new Label("page_title", Model.of(getTitle())));

//         jQueryBehavior = new JQueryBehavior("#accordion", "accordion");
//        this.add(jQueryBehavior);
        addAccordingPanel(this);

        final KomeaSecurityContextHolderAwareRequestWrapper securityContextHolderAwareRequestWrapper
                = new KomeaSecurityContextHolderAwareRequestWrapper((ServletRequest) getRequest()
                        .getContainerRequest(), "");

        if (securityContextHolderAwareRequestWrapper.isUserInRole("ADMIN")) {
            add(new WebMarkupContainer("signinpanel"));
            add(new Fragment("personalpanel", "personal", this));
        } else {
            add(new Fragment("signinpanel", "signin", this));
            add(new WebMarkupContainer("personalpanel"));
        }
        buildBreadCrumb();
        setStatelessHint(true);
        // DEBUG MODE
        checkStateless();

    }

    private void addAccordingPanel(final LayoutPage page) {

        // Recommended options when using dynamic content (AjaxTab) //
        Options options = new Options();
        options.set("heightStyle", Options.asString("content"));

        // Accordion //
        accordion = new AccordionPanel("accordion", this.newTabList(page), options) {

//            target.add(accordion.setActiveTab(accordion.getLastTabIndex()));
            private static final long serialVersionUID = 1L;

            @Override
            public void onActivate(AjaxRequestTarget target, int index, ITab tab) {
            }
        };

        page.add(accordion);
    }

    private List<ITab> newTabList(final LayoutPage page) {
        List<ITab> tabs = new ArrayList<ITab>();
        // tab #3, using AbstractTab //
        AbstractTab tab1 = new AbstractTab(Model.of(getString("home.company"))) {

            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                     List<String> listData = new ArrayList<String>();
                listData.add("panel-101");
                listData.add("panel-102");
                listData.add("panel-103");
                listData.add("panel-104");
                listData.add("panel-105");
                listData.add("panel-empty");

                List<IColumn<?, ?>> columns;
                columns = new ArrayList<IColumn<?, ?>>();
                FragmentPropertyColumn fcol;
                fcol = new FragmentPropertyColumn(Model.of("titre"),page);
                columns.add(fcol);
                final ISortableDataProvider<String, String> dataProvider
                        = new ListDataModel<String>(listData);
                DataGridView defaultDataTable = new DataGridView("table-panel-1", columns, dataProvider);
                Fragment fragment = new Fragment(panelId, "panel-1", page);
                fragment.add(defaultDataTable);
                return fragment;
            }
        };

        tabs.add(tab1);

        tabs.add(new AbstractTab(Model.of(getString("home.kpis"))) {

            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {

                List<String> listData = new ArrayList<String>();
                listData.add("panel-201");
                listData.add("panel-202");
                listData.add("panel-203");
                listData.add("panel-empty");


                List<IColumn<?, ?>> columns;
                columns = new ArrayList<IColumn<?, ?>>();

                FragmentPropertyColumn fcol;
                fcol = new FragmentPropertyColumn(Model.of("titre"),page);

                columns.add(fcol);
                final ISortableDataProvider<String, String> dataProvider
                        = new ListDataModel<String>(listData);
                DataGridView defaultDataTable = new DataGridView("table-panel-2", columns, dataProvider);
                Fragment fragment = new Fragment(panelId, "panel-2", page);
                fragment.add(defaultDataTable);
                return fragment;
            }
        });

        tabs.add(new AbstractTab(Model.of(getString("home.administration"))) {

            private static final long serialVersionUID = 1L;

            @Override
            public WebMarkupContainer getPanel(String panelId) {
                       List<String> listData = new ArrayList<String>();
                listData.add("panel-301");
                listData.add("panel-302");
                listData.add("panel-303");
                listData.add("panel-304");
                listData.add("panel-305");
                listData.add("panel-306");
                listData.add("panel-empty");

                List<IColumn<?, ?>> columns;
                columns = new ArrayList<IColumn<?, ?>>();
                FragmentPropertyColumn fcol;
                fcol = new FragmentPropertyColumn(Model.of("titre"),page);
                columns.add(fcol);
                final ISortableDataProvider<String, String> dataProvider
                        = new ListDataModel<String>(listData);
                DataGridView defaultDataTable = new DataGridView("table-panel-3", columns, dataProvider);
            
//                defaultDataTable.get(1).add(new AttributeModifier("class",new Model("danger") ));
                Fragment fragment = new Fragment(panelId, "panel-3", page);
                fragment.add(defaultDataTable);
                return fragment;
            }
        });

        return tabs;
    }

    public static class FragmentPropertyColumn extends AbstractColumn<String, String> {

      
        private LayoutPage page;

        public FragmentPropertyColumn(IModel<String> _displayModel, LayoutPage _page) {
            super(_displayModel);
   
            page = _page;
        }

        @Override
        public void populateItem(Item<ICellPopulator<String>> item, String string, IModel<String> imodel) {
            String object = imodel.getObject();

             item.add(new Fragment(string, object, page));

             System.out.println("%%%%%%%%% verif de type %%%%%%%%%");
             System.out.println(page.getClass().getCanonicalName());
             System.out.println(page.getClass().getName());
             
             
             switch(page.getClass().getName())
             {
                 case ; 
                
             
             }
             
              if("panel-303".equals(object))
            {
            item.add(new AttributeModifier("style",new Model("background-color: #C4E3F0") ));

            }

           
        }

    }

    /**
     * Check if the page is stateless
     */
    public void checkStateless() {

        if (!isPageStateless()) {
            final Iterator<Component> iterator = this.iterator();
            while (iterator.hasNext()) {
                final Component next = iterator.next();
                if (!next.isStateless()) {
                    LOGGER.info("Page {} component {} is not stateless", getPageRelativePath(),
                            next.getPath());
                }
            }
        }
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

        response.render(JavaScriptHeaderItem.forReference(getApplication()
                .getJavaScriptLibrarySettings().getJQueryReference()));
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
        add(new BookmarkablePageLink<Void>("breadactive", getPageClass()));
    }
}
