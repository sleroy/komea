
package org.komea.product.wicket;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.wicket.utils.KomeaSecurityContextHolderAwareRequestWrapper;
import org.komea.product.wicket.widget.RedirectPageLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Defines the general layout for all komea pages.
 * 
 * @author sleroy
 */
public abstract class LayoutPage extends WebPage
{
    
    
    private static final Logger LOGGER           = LoggerFactory.getLogger(LayoutPage.class);
    
    
    private static final long   serialVersionUID = 1L;
    
    
    
    public LayoutPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        add(new Label("page_title", Model.of(getTitle())));
        final KomeaSecurityContextHolderAwareRequestWrapper securityContextHolderAwareRequestWrapper =
                new KomeaSecurityContextHolderAwareRequestWrapper((ServletRequest) getRequest()
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
        
        add(new ListView<Entry<String, Class>>("bread", notActivePages)
        {
            
            
            @Override
            protected void populateItem(final ListItem<Entry<String, Class>> _item) {
            
            
                _item.add(new RedirectPageLink("blink", _item.getModelObject()));
            }
            
        });
        add(new BookmarkablePageLink<Void>("breadactive", getPageClass()));
    }
}
