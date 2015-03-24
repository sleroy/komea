/**
 *
 */
package org.komea.demo.gitspy.widgets.toppanel;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

import com.google.common.collect.Lists;

/**
 * This class defines a panel representing some top scores.
 *
 * @author sleroy
 *
 */
public class TopStatPanel extends Panel {

	public static class TopStatListView extends ListView<TopRecord> {

		public TopStatListView(final String _id,
				final IModel<? extends List<? extends TopRecord>> _model) {
			super(_id, _model);
		}

		public TopStatListView(final String _id, final List<? extends TopRecord> _list) {
			super(_id, _list);
		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html.list.ListItem)
		 */
		@Override
		protected void populateItem(final ListItem<TopRecord> _item) {
			this.add(new Label("topCommiterName", _item.getModelObject().getName()));
			this.add(new Label("topCommiterNumber", _item.getModelObject().getValue()));
			this.add(new Image("gravatar", new UrlResourceReference(Url.parse("http://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50.png?s=20"))));

		}

	}

	/**
	 * Top stat panel
	 *
	 * @param _markupID
	 *            the markupID
	 */
	public TopStatPanel(final String _markupID) {
		super(_markupID);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.wicket.MarkupContainer#onInitialize()
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
		this.add(new TopStatListView("topCommiters", Lists.<TopRecord> newArrayList()));
	}

}
