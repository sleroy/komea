/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.persongroup.team.TeamPage;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class CronDialogForm extends Form<String> {

    private NumberGeneric numberEvery;
    private DropDownChoice choiceHours;
    private final IModel<String> radioModel;
    private DropDownChoice choiceMinute;
    private CronDialog cronDialog;
    private final FeedbackPanel feedback;
    private final List<String> hours = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private final List<String> minutes = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "31", "52", "53", "54", "55", "56", "57", "58", "59");

    public CronDialogForm(String id, CronDialog _cronDialog,FeedbackPanel feedbackPanel) {
        super(id);
        cronDialog = _cronDialog;
        // Radio Button //
        feedback=feedbackPanel;
        numberEvery = new NumberGeneric(1);
        radioModel = new Model<String>(getString("cronpage.radio.setday"));
        final List<String> radioList = Arrays.asList(getString("cronpage.radio.setmin"), getString("cronpage.radio.sethours"), getString("cronpage.radio.setday"));
        RadioChoice<String> radioChoice = new RadioChoice<String>("radios", radioModel, radioList);
        AjaxFormChoiceComponentUpdatingBehavior ajaxRadio = new AjaxFormChoiceComponentUpdatingBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                activeStartTime(target);

            }
        };
        radioChoice.add(ajaxRadio);
        add(radioChoice);

        choiceHours = new DropDownChoice<String>("starthours", new Model<String>(hours.get(12)), hours);
        choiceHours.setOutputMarkupId(true);
        choiceHours.setOutputMarkupPlaceholderTag(true);
        choiceHours.setRequired(true);
        choiceHours.setNullValid(false);

        choiceMinute = new DropDownChoice<String>("startminute", new Model<String>(minutes.get(0)), minutes);
        choiceMinute.setOutputMarkupId(true);
        choiceMinute.setOutputMarkupPlaceholderTag(true);
        choiceMinute.setRequired(true);
        choiceMinute.setNullValid(false);

        add(choiceHours);
        add(choiceMinute);

        TextField<Integer> buildTextField = TextFieldBuilder.<Integer>createRequired("Value", numberEvery, "Value").buildTextField();
        buildTextField.add(new IValidator<Integer>() {

            @Override
            public void validate(IValidatable<Integer> iv) {
                
                    if (iv.getValue() < 1) {
                        final ValidationError error = new ValidationError();
                        error.setMessage(getString("cronpage.form.dialog.error.supzero"));
                        iv.error(error);
                    }
            }
        });
        add(buildTextField);

        add(new AjaxButton("generate", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedback.setVisible(true);
                target.add(feedback);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedback.setVisible(false);
                target.add(feedback);
                String result = "";
                if (radioModel.getObject().equals(getString("cronpage.radio.setmin"))) {
                    result = "0 0/" + numberEvery.getValue().toString() + " * 1/1 * ? *";

                } else if (radioModel.getObject().equals(getString("cronpage.radio.sethours"))) {
                    result = "0 0 0/" + numberEvery.getValue().toString() + " 1/1 * ? *";
                } else {
                    result = "0 " + choiceMinute.getModelValue() + " " + choiceHours.getModelValue() + " 1/" + numberEvery.getValue().toString() + " * ? *";
                }
                cronDialog.setCronValue(result, target);

            }
        });
    }

    private void activeStartTime(AjaxRequestTarget _target) {
        if (radioModel.getObject().equals(getString("cronpage.radio.setday"))) {

            choiceHours.setEnabled(true);
            choiceMinute.setEnabled(true);
        } else {
            choiceHours.setEnabled(false);
            choiceMinute.setEnabled(false);

        }
        _target.add(choiceHours);
        _target.add(choiceMinute);
    }

    private static class NumberGeneric implements Serializable {

        private Integer value;

        public NumberGeneric(Integer value) {
            this.value = value;
        }

        public NumberGeneric() {
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }
}
