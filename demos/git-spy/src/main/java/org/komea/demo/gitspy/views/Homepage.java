package org.komea.demo.gitspy.views;

import org.apache.wicket.markup.head.IHeaderResponse;

/**
 * Home page
 *
 * @author Sylvain Leroy
 *
 */
public class Homepage extends Layout {
	public Homepage() {
		super();

	}

	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		// final CssResourceReference cssResourceReference = new
		// CssResourceReference(WicketWebApplication.class,
		// "example.css");
		// response.render(CssHeaderItem.forReference(cssResourceReference));
	}
}