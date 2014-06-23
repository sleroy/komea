
package org.komea.product.wicket;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;



/**
 * Defines the general layout for all komea pages.
 *
 * @author sleroy
 */
public abstract class LayoutPage extends WebPage
{
    
    
    protected static final Logger   LOGGER = LoggerFactory.getLogger(LayoutPage.class);
    
    
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
            fragment.add(new Label("fullname", firstName + " " + lastName));
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
    
    
    /**
     *
     */
    private void buildAlerts(final MarkupContainer _fragment) {
    
    
        if (eventStatisticsService != null) {
            _fragment.add(new Label("critical-alerts", Model.of(eventStatisticsService
                    .getNumberOfAlerts(Severity.BLOCKER))));
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
        
        add(new ListView<Entry<String, Class>>("bread", notActivePages)
        {
            
            
            @Override
            protected void populateItem(final ListItem<Entry<String, Class>> _item) {
            
            
                _item.add(new RedirectPageLink("blink", _item.getModelObject()));
            }
            
        });
        add(new Label("breadactive", getTitle()));
    }
    
    
    protected IKomeaSecurityController getSecurityController() {
    
    
        final IKomeaSecurityController securityController =
                new KomeaSecurityContextHolderAwareRequestWrapper((ServletRequest) getRequest()
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
    
}
