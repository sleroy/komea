package org.komea.demo.gitspy;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.komea.demo.gitspy.views.Homepage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * The web application class also serves as spring boot starting point by using
 * spring boot's EnableAutoConfiguration annotation and providing the main
 * method.
 *
 * @author Stefan Kloe
 *
 */
@Component
public class WicketWebApplication extends WebApplication {

	private final static Logger logger = LoggerFactory
			.getLogger(WicketWebApplication.class);


	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * provides page for default request
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return Homepage.class;
	}

	/**
	 * <ul>
	 * <li>making the wicket components injectable by activating the
	 * SpringComponentInjector</li>
	 * <li>mounting the test page</li>
	 * <li>logging spring service method output to showcase working
	 * integration</li>
	 * </ul>
	 */
	@Override
	protected void init() {
		super.init();
		this.getComponentInstantiationListeners().add(
		                                              new SpringComponentInjector(this, this.applicationContext));
		this.mountPage("/index.html", Homepage.class);
		//this.mountPage("/mounted.html", MountedPage.class);
		// best place to do this is in Application#init()

	}

}
