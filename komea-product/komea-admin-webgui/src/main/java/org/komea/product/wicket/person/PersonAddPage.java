package org.komea.product.wicket.person;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

/**
 * Person admin page
 *
 * @author sleroy
 */
public class PersonAddPage extends LayoutPage {

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

    @SpringBean
    private IPersonRoleService personRole;

    public PersonAddPage(final PageParameters _parameters) {

        this(_parameters, new Person(), true);

    }

    public PersonAddPage(final PageParameters _parameters, final Person _person) {
        this(_parameters, _person, false);
    }

    private PersonAddPage(final PageParameters _parameters, final Person _person, boolean isNew) {

        super(_parameters);

        final PersonForm personForm
                = new PersonForm(isNew, personRole, passEncoder, personDAO, projectService, "form",
                        new CompoundPropertyModel<Person>(_person), this, personGroupService);
        String message;
        if (isNew) {
            message = getString("memberpage.save.add.title");
        } else {
            message = getString("memberpage.save.edit.title");
        }
        personForm.add(new Label("legend", message));
        add(personForm);

    }

    /**
     * @return the formularService
     */


    @Override
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {

        return Collections.singletonList(new KomeaEntry<String, Class>(
                getString("memberpage.main.title"), PersonPage.class));

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

        return getString("memberpage.main.title");
    }
}
