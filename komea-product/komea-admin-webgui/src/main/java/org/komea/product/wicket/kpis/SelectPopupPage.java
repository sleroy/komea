/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.kpis;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.AjaxButton;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.dialog.InputDialog;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public class SelectPopupPage extends LayoutPage
{
	private static final long serialVersionUID = 1L;

	public SelectPopupPage(final PageParameters _parameters)
	{
             super(_parameters);
		this.init();
	}

	private void init()
	{
		final Form<Void> form = new Form<Void>("form");
		this.add(form);

		// FeedbackPanel //
		form.add(new JQueryFeedbackPanel("feedback"));

		// Dialog //
		final InputDialog<String> dialog = new InputDialog<String>("dialog", "Input", "Please provide a value:", new Model<String>("a sample value")) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit(AjaxRequestTarget target)
			{
				this.info("The form has been submitted");
				this.info(String.format("The model object is: '%s'", this.getModelObject()));
			}

			@Override
			public void onClose(AjaxRequestTarget target, DialogButton button)
			{
				this.info(button + " has been clicked");
				target.add(form);
			}
		};

		this.add(dialog); //the dialog is not within the form

		// Buttons //
		form.add(new AjaxButton("open") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				dialog.open(target);
			}
		});
	}
}