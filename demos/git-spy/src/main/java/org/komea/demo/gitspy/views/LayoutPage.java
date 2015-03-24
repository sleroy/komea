package org.komea.demo.gitspy.views;

import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.komea.demo.gitspy.WicketWebApplication;

/**
 * Home page
 *
 * @author Sylvain Leroy
 *
 */
public class LayoutPage extends WebPage {
	public LayoutPage() {
		super();

	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);

		//		this.addCss(response, "static/css/bootstrap.css");
		//		this.addCss(response, "static/css/styles.css");
		//		this.addJs(response, "js/bootstrap.js");

		Bootstrap.renderHeadPlain(response);
	}

	/**
	 * @param response
	 * @param css1
	 */
	protected void addCss(final IHeaderResponse response, final String css1) {
		final CssResourceReference cssResourceReference = new
				CssResourceReference(WicketWebApplication.class,
				                     css1);
		response.render(CssHeaderItem.forReference(cssResourceReference));
	}


	/**
	 * @param response
	 * @param css1
	 */
	protected void addJs(final IHeaderResponse response, final String css1) {
		final JavaScriptResourceReference jsResourceReference = new
				JavaScriptResourceReference(WicketWebApplication.class,
				                            css1);
		response.render(JavaScriptHeaderItem.forReference(jsResourceReference));
	}
}