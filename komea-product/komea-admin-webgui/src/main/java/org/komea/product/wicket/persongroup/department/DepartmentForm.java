/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.department;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.widget.tooltip.TooltipBehavior;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.CustomUpdater;
import org.komea.product.wicket.utils.DataListSelectDialogBuilder;
import org.komea.product.wicket.utils.DialogFactory;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextAreaBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class DepartmentForm extends Form<PersonGroup> {

    private final IPersonGroupService prService;
    private final Component feedBack;
    private final LayoutPage page;
    private final PersonGroup personGroup;
    private final IPersonService personService;
    private final IProjectService projectService;
    private final boolean isNew;
    private List<IHasKey> currentTeamList;
    private List<IHasKey> selectedTeam;
    private List<IHasKey> currentMemberList;
    private List<IHasKey> selectedMember;
    private final List<IHasKey> teamMemberList;
    private final List<IHasKey> depMemberList;
    private final List<IHasKey> projectDisplayList;
    private List<IHasKey> projectSelectedList;

    DepartmentForm(IProjectService _projectService, IPersonService _personService, boolean _isNew, String form, IPersonGroupService personGroupService, FeedbackPanel feedbackPanel, CompoundPropertyModel<PersonGroup> compoundPropertyModel, DepartmentEditPage aThis) {
        super(form, compoundPropertyModel);
        this.personService = _personService;
        this.prService = personGroupService;
        this.feedBack = feedbackPanel;
        this.page = aThis;
        this.isNew = _isNew;
        this.projectService = _projectService;
        this.personGroup = compoundPropertyModel.getObject();
        selectedTeam = new ArrayList<IHasKey>();
        selectedMember = new ArrayList<IHasKey>();
        projectDisplayList = new ArrayList<IHasKey>();

        feedBack.setVisible(false);
        //field

        // member  component
        this.currentMemberList = new ArrayList<IHasKey>();
        teamMemberList = new ArrayList<IHasKey>();
        depMemberList = new ArrayList<IHasKey>();
        if (this.personGroup.getId() != null) {

            depMemberList.addAll(personService.getPersonsOfPersonGroup(this.personGroup.getId()));
            teamMemberList.addAll(personService.getPersonsOfPersonGroupRecursively(this.personGroup.getId()));
            teamMemberList.removeAll(depMemberList);
            this.currentMemberList.addAll(depMemberList);
            this.currentMemberList.addAll(teamMemberList);
        }

        List<Person> selectAll = personService.selectAll();

        ListMultipleChoice<IHasKey> listUser = new ListMultipleChoice<IHasKey>("listUser", new PropertyModel<List<IHasKey>>(this, "selectedMember"), this.currentMemberList) {

            @Override
            protected boolean isDisabled(IHasKey object, int index, String selected) {
                if (teamMemberList.contains(object)) {
                    return true;
                }
                return super.isDisabled(object, index, selected);
            }

        };

        listUser.setChoiceRenderer(DialogFactory.getChoiceRendenerEntity());
        listUser.setMaxRows(8);
        listUser.setOutputMarkupId(true);
        CustomUpdater cupdater = new CustomUpdater(listUser) {

            @Override
            public void update() {
                currentMemberList.removeAll(teamMemberList);
                depMemberList.clear();
                depMemberList.addAll(currentMemberList);
                currentMemberList.clear();
                teamMemberList.clear();
                for (IHasKey persong : currentTeamList) {
                    List<Person> personsOfPersonGroup = personService.getPersonsOfPersonGroup(persong.getId());
                    teamMemberList.addAll(personsOfPersonGroup);
                }
                currentMemberList.addAll(teamMemberList);
                currentMemberList.addAll(depMemberList);
            }
        };

        DataListSelectDialogBuilder dataMember = new DataListSelectDialogBuilder();
        dataMember.setPage(this);
        dataMember.setIdDialog("dialogAddMember");
        dataMember.setIdBtnAdd("btnAddMember");
        dataMember.setIdBtnDel("btnDelMember");
        dataMember.setDisplayDialogMessage(getString("departmentpage.save.form.field.popup.title.member"));
        dataMember.setCurrentEntityList(currentMemberList);
        dataMember.setChoiceEntityList(selectedMember);
        dataMember.setSelectDialogList((List<IHasKey>) (List<?>) selectAll);
        dataMember.setService(personService);
        dataMember.setListEntite(listUser);
        dataMember.addFilter(DialogFactory.getPersonWithoutPersonGroupFilter(personGroup.getId()));
        dataMember.setTooltips(getString("departmentpage.save.form.field.multiple.members"));
        DialogFactory.addMultipleListDialog(dataMember);

        // basic field part
        add(TextFieldBuilder.<String>createRequired("name", this.personGroup, "name").highlightOnErrors()
                .simpleValidator(0, 255).withTooltip(getString("global.field.tooltip.name")).build());

        TextFieldBuilder<String> keyFieldBuilder = TextFieldBuilder.<String>createRequired("personGroupKey", this.personGroup, "personGroupKey")
                .simpleValidator(0, 255).highlightOnErrors().withTooltip(getString("global.field.tooltip.key"));

        if (isNew) {
            keyFieldBuilder.UniqueStringValidator(getString("global.field.tooltip.key"), prService);
        } else {
            keyFieldBuilder.buildTextField().add(new AttributeModifier("readonly", "readonly"));
        }

        add(keyFieldBuilder.build());
        add(TextAreaBuilder.<String>create("description", this.personGroup, "description")
                .simpleValidator(0, 2048).highlightOnErrors().withTooltip(getString("global.field.tooltip.description")).build());

//        add(TextFieldBuilder.<String>create("idCustomer", this.project, "idCustomer").withTooltip("customer can be affected")
//                .build());
        //button
        add(new AjaxLinkLayout<LayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                LayoutPage page = getCustom();
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));
            }
        });

        // team component
        List<PersonGroup> allTeamsPG = prService.getAllTeamsPG();
        this.currentTeamList = new ArrayList<IHasKey>();
        for (PersonGroup personGroup1 : allTeamsPG) {
            if (personGroup.getId() != null && personGroup.getId().equals(personGroup1.getIdPersonGroupParent())) {
                currentTeamList.add(personGroup1);
            }
        }
        DataListSelectDialogBuilder dataTeam = new DataListSelectDialogBuilder();
        dataTeam.setPage(this);
        dataTeam.setIdList("table");
        dataTeam.setIdDialog("dialogAddPerson");
        dataTeam.setIdBtnAdd("btnAddPerson");
        dataTeam.setIdBtnDel("btnDelPerson");
        dataTeam.setNameFieldResult("selectedTeam");
        dataTeam.setDisplayDialogMessage(getString("departmentpage.save.form.field.popup.title.team"));
        dataTeam.setCurrentEntityList(currentTeamList);
        dataTeam.setChoiceEntityList(selectedTeam);
        dataTeam.setSelectDialogList((List<IHasKey>) (List<?>) allTeamsPG);
        dataTeam.setService(prService);
        dataTeam.addUpdater(cupdater);
        dataTeam.addFilter(DialogFactory.getPersonGroupWithoutParentFilter(personGroup.getId()));
        dataTeam.setTooltips(getString("departmentpage.save.form.field.multiple.teams"));
        DialogFactory.addMultipleListDialog(dataTeam);

        // project display
        if (personGroup.getId() != null) {
            projectDisplayList.addAll(projectService.getProjectsOfPersonGroupRecursively(personGroup.getId()));

            for (IHasKey key : this.currentMemberList) {
                List<IHasKey> projectsOfAMember = (List) projectService.getProjectsOfAMember(key.getId());
                DialogFactory.addDistictList(projectDisplayList, projectsOfAMember);
            }

        }

        ListMultipleChoice<IHasKey> listProject = new ListMultipleChoice<IHasKey>("tableProject", new PropertyModel<List<IHasKey>>(this, "projectSelectedList"), projectDisplayList);
        listProject.setChoiceRenderer(DialogFactory.getChoiceRendenerEntity());
        listProject.setMaxRows(8);
        listProject.setOutputMarkupId(true);
        listProject.setEnabled(false);
        final TooltipBehavior tooltipBehavior = new TooltipBehavior(IJQueryWidget.JQueryWidget.getSelector(listProject));
        listProject.add(new AttributeModifier("title", getString("departmentpage.save.form.field.tooltip.project")));
        listProject.add(tooltipBehavior);
        add(listProject);
        final CustomUpdater projectUpdater = new CustomUpdater(listProject) {

            @Override
            public void update() {
                projectDisplayList.clear();

                for (IHasKey key : currentMemberList) {
                    List<IHasKey> projectsOfAMember = (List) projectService.getProjectsOfAMember(key.getId());
                    DialogFactory.addDistictList(projectDisplayList, projectsOfAMember);

                }
                for (IHasKey key : currentTeamList) {
                    List<IHasKey> projectsOfAMember = (List) projectService.getProjectsOfPersonGroup(key.getId());;
                    DialogFactory.addDistictList(projectDisplayList, projectsOfAMember);

                }
            }
        };
        dataMember.addUpdater(projectUpdater);
        dataTeam.addUpdater(projectUpdater);
        //button
        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                target.add(feedBack);
                personGroup.setType(PersonGroupType.DEPARTMENT);
                currentMemberList.removeAll(teamMemberList);
                prService.saveOrUpdatePersonGroup(personGroup, (List<PersonGroup>) (List) currentTeamList, null, (List<Person>) (List) currentMemberList);
                page.setResponsePage(new DepartmentPage(page.getPageParameters()));

            }
        });
    }

    public List<IHasKey> getProjectSelectedList() {
        return projectSelectedList;
    }

    public void setProjectSelectedList(List<IHasKey> projectSelectedList) {
        this.projectSelectedList = projectSelectedList;
    }

    private void buildProjectList() {
    }

    public List<IHasKey> getCurrentTeamList() {
        return currentTeamList;
    }

    public void setCurrentTeamList(List<IHasKey> currentTeamList) {
        this.currentTeamList = currentTeamList;
    }

    public List<IHasKey> getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(List<IHasKey> selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public List<IHasKey> getCurrentMemberList() {
        return currentMemberList;
    }

    public void setCurrentMemberList(List<IHasKey> currentMemberList) {
        this.currentMemberList = currentMemberList;
    }

    public List<IHasKey> getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(List<IHasKey> selectedMember) {
        this.selectedMember = selectedMember;
    }

}
