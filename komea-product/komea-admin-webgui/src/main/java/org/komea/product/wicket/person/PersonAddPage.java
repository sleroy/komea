package org.komea.product.wicket.person;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IFormularService;
import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.forms.PersonFormData;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

/**
 * Person admin page
 *
 * @author sleroy
 */
public class PersonAddPage extends LayoutPage {

    @SpringBean
    private IFormularService formularService;

    @SpringBean
    private IPersonService personDAO;

    @SpringBean
    private IPersonRoleService personRoleDAO;

    @SpringBean
    private IProjectService projectDAO;

    @SpringBean
    private IPersonGroupService personGroupService;

    @SpringBean
    private IProjectService projectService;
    
    @SpringBean
    private IPasswordEncoder passEncoder;

    public PersonAddPage(final PageParameters _parameters) {

        this(_parameters, new Person());

    }

    public PersonAddPage(final PageParameters _parameters, final Person _person) {

        super(_parameters);

        final PersonFormData newPersonForm = formularService.newPersonForm();
        final PersonForm personForm
                = new PersonForm(passEncoder,personDAO, projectService, newPersonForm, "form",
                        new CompoundPropertyModel<Person>(_person), this, personGroupService);
        add(personForm);

        IChoiceRenderer<PersonGroup> iChoiceRenderer = new IChoiceRenderer<PersonGroup>() {

            @Override
            public Object getDisplayValue(PersonGroup t) {
                return t.getName();
            }

            @Override
            public String getIdValue(PersonGroup t, int i) {
                return String.valueOf(t.getId());
            }

        };

        final SelectDialog<PersonGroup> dialogPersonGroup = new SelectDialog<PersonGroup>("dialogGroup", "Choose a team or department", personGroupService, iChoiceRenderer) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                PersonGroup selectedPersonGroup = getSelected();
                if (selectedPersonGroup != null) {
                    personForm.getPerson().setIdPersonGroup(selectedPersonGroup.getId());
                    personForm.getGroupName().setName(selectedPersonGroup.getName());
                } else {
                    personForm.getPerson().setIdPersonGroup(null);
                    personForm.getGroupName().setName("");
                }
                target.add(personForm.getGroupField());
            }

        };
        add(dialogPersonGroup);
        personForm.add(new AjaxLinkLayout<LayoutPage>("btnGroup", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);

            }
        });

    }

    /**
     * @return the formularService
     */
    public IFormularService getFormularService() {

        return formularService;
    }

    @Override
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {

        return Collections.singletonList(new KomeaEntry<String, Class>(
                getString("PersonPage.title"), PersonPage.class));

    }

    /**
     * @return the personDAO
     */
    public IPersonService getPersonDAO() {

        return personDAO;
    }

    /**
     * @return the personRoleDAO
     */
    public IPersonRoleService getPersonRoleDAO() {

        return personRoleDAO;
    }

    /**
     * @return the projectDAO
     */
    public IProjectService getProjectDAO() {

        return projectDAO;
    }

    /**
     * @param _formularService the formularService to set
     */
    public void setFormularService(final IFormularService _formularService) {

        formularService = _formularService;
    }

    /**
     * @param _personDAO the personDAO to set
     */
    public void setPersonDAO(final IPersonService _personDAO) {

        personDAO = _personDAO;
    }

    /**
     * @param _personRoleDAO the personRoleDAO to set
     */
    public void setPersonRoleDAO(final IPersonRoleService _personRoleDAO) {

        personRoleDAO = _personRoleDAO;
    }

    /**
     * @param _projectDAO the projectDAO to set
     */
    public void setProjectDAO(final IProjectService _projectDAO) {

        projectDAO = _projectDAO;
    }

    @Override
    public String getTitle() {

        return getString("PersonAddPage.title");
    }
}
