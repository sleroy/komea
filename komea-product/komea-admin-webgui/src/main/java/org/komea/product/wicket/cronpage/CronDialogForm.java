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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
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
    private DropDownChoice choiceMinute;
    private final List<String> hours = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
    private final List<String> minutes = Arrays.asList("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "31", "52", "53", "54", "55", "56", "57", "58", "59");

    public CronDialogForm(String id) {
        super(id);
        // Radio Button //
        numberEvery = new NumberGeneric(0);
        final IModel<String> radioModel = new Model<String>();
        final List<String> radioList = Arrays.asList(getString("cronpage.radio.setmin"), getString("cronpage.radio.sethours"), getString("cronpage.radio.setday"));
        RadioChoice<String> radioChoice = new RadioChoice<String>("radios", radioModel, radioList);
        AjaxFormChoiceComponentUpdatingBehavior ajaxRadio = new AjaxFormChoiceComponentUpdatingBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                activeStartTime(radioModel, target);

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

        add(TextFieldBuilder.<Integer>createRequired("Value", numberEvery, "Value").buildTextField());

        add(new AjaxButton("generate", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

//                feedBack.setVisible(true);
//                // repaint the feedback panel so errors are shown
//                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

//                feedBack.setVisible(false);
//                // repaint the feedback panel so that it is hidden
//                target.add(feedBack);
//                personGroup.setType(PersonGroupType.TEAM);
//                prService.saveOrUpdatePersonGroup(personGroup, null, (List<Project>) (List<?>) currentProjectList, (List<Person>) (List<?>) currentPersonList);
//                page.setResponsePage(new TeamPage(page.getPageParameters()));
            }
        });
    }

    private void activeStartTime(IModel<String> _model, AjaxRequestTarget _target) {
        if (_model.getObject().equals(getString("cronpage.radio.setday"))) {
            choiceHours.setEnabled(false);
            choiceMinute.setEnabled(false);

        } else {
            choiceHours.setEnabled(true);
            choiceMinute.setEnabled(true);
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
