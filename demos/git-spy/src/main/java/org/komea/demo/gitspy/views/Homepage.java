package org.komea.demo.gitspy.views;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.Panel;
import org.komea.demo.gitspy.widgets.toppanel.TopStatPanel;

/**
 * Home page
 *
 * @author Sylvain Leroy
 *
 */
public class Homepage extends LayoutPage {
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

	/* (non-Javadoc)
	 * @see org.apache.wicket.Page#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Panel topCommiterPanel = new TopStatPanel("topCommiterPanel");
		this.add(topCommiterPanel);
	}
}