
package org.komea.product.web.rest.api;



import javax.validation.Valid;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.dto.ScmCommitDto;
import org.komea.product.plugins.scm.ScmRepositoryService;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.plugins.scm.api.plugin.ScmCommit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;



/**
 * Komea Rest api to provide scm notifications
 * <p>
 * 
 * @author $Author: sleroy $
 * @since 4 févr. 2014
 */
@Controller
@RequestMapping(value = "/scm/")
public class ScmCommitController
{
    
    
    private static final Logger   LOGGER = LoggerFactory.getLogger(ScmCommitController.class);
    
    
    @Autowired
    private IEventPushService     eventPushService;
    
    
    @Autowired
    private IPersonService        personService;
    
    @Autowired
    private IProjectService       projectService;
    
    
    @Autowired
    private IScmRepositoryService scmRepositoryService;
    
    
    
    /**
     * This method push a new commit inside Komea.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/new_commit/{project}/{user}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushNewCommit(@PathVariable
    final String project, @PathVariable
    final String user, @Valid
    @RequestBody
    final ScmCommitDto commitDTO) {
    
    
        LOGGER.info("Received new commit notification {} {} {}", project, user, commitDTO);
        final ScmCommit scmCommit = new ScmCommit();
        scmCommit.setAuthor(personService.findOrCreatePersonByLogin(user));
        scmCommit.setProject(projectService.selectByKey(project));
        scmCommit.setMessage(commitDTO.getMessage());
        scmCommit.setCommitTime(new DateTime());
        scmCommit.setId(commitDTO.getId());
        scmCommit.setNumberOfAddedlines(commitDTO.getNumberOfAddedlines());
        scmCommit.setNumberOfChangedLines(commitDTO.getNumberOfChangedLines());
        scmCommit.setNumberofDeletedLines(commitDTO.getNumberofDeletedLines());
        scmCommit.setNumberOfModifiedFiles(commitDTO.getNumberOfModifiedFiles());
        LOGGER.debug("Pushing scm commit  : {}", scmCommit);
        eventPushService.sendCustomEvent(scmCommit);
        
        triggerNewCOmmitEvent(project, user, scmCommit);
        
        
    }
    
    
    /**
     * This method push a new commit inside Komea.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/new_commit_inline/{project}/{user}/{message}/{numberOfAddedlines}/{numberOfChangedLines}/{numberofDeletedLines}/{numberOfModifiedFiles}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushNewCommitInline(@PathVariable
    final String project, @PathVariable
    final String user, @PathVariable
    final String message, @PathVariable
    final Integer numberOfAddedlines, @PathVariable
    final Integer numberOfChangedLines, @PathVariable
    final Integer numberofDeletedLines, @PathVariable
    final Integer numberOfModifiedFiles) {
    
    
        LOGGER.info("Received new commit notification");
        LOGGER.info("User {}\nProject {}\n", user, project);
        LOGGER.info("Message {}", message);
        LOGGER.info("NumberofAddlines {}", numberOfAddedlines);
        LOGGER.info("NumberOfChangedLines {}", numberOfChangedLines);
        LOGGER.info("NumberofDeletedLines {}", numberofDeletedLines);
        LOGGER.info("NumberOfModifiedFiles {}", numberOfModifiedFiles);
        final ScmCommit scmCommit = new ScmCommit();
        scmCommit.setAuthor(personService.findOrCreatePersonByLogin(user));
        scmCommit.setProject(projectService.selectByKey(project));
        scmCommit.setMessage(message);
        scmCommit.setCommitTime(new DateTime());
        scmCommit.setNumberOfAddedlines(numberOfAddedlines);
        scmCommit.setNumberOfChangedLines(numberOfChangedLines);
        scmCommit.setNumberofDeletedLines(numberofDeletedLines);
        scmCommit.setNumberOfModifiedFiles(numberOfModifiedFiles);
        LOGGER.debug("Pushing scm commit  : {}", scmCommit);
        eventPushService.sendCustomEvent(scmCommit);
        triggerNewCOmmitEvent(project, user, scmCommit);
    }
    
    
    /**
     * This method push a new commit inside Komea.
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/new_commit_light/{project}/{user}/{message}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushNewCommitLight(@PathVariable
    final String project, @PathVariable
    final String user, @PathVariable
    final String message) {
    
    
        LOGGER.info("Received new light commit notification");
        LOGGER.info("User {}\nProject {} Message {}\n", user, project, message);
        final ScmCommit scmCommit = new ScmCommit();
        scmCommit.setAuthor(personService.findOrCreatePersonByLogin(user));
        scmCommit.setProject(projectService.selectByKey(project));
        scmCommit.setMessage(message);
        scmCommit.setCommitTime(new DateTime());
        scmCommit.setNumberOfAddedlines(0);
        scmCommit.setNumberOfChangedLines(0);
        scmCommit.setNumberofDeletedLines(0);
        scmCommit.setNumberOfModifiedFiles(0);
        LOGGER.debug("Pushing scm commit  : {}", scmCommit);
        eventPushService.sendCustomEvent(scmCommit);
        triggerNewCOmmitEvent(project, user, scmCommit);
    }
    
    
    private void triggerNewCOmmitEvent(
            final String project,
            final String user,
            final ScmCommit scmCommit) {
    
    
        eventPushService.sendEventDto(EventDtoBuilder.newAlert()
                .at(scmCommit.getCommitTime().toDate()).eventType("scm-new-commit")
                .message("New commit has been made by " + user + " in project " + project)
                .project(project).provided(ScmRepositoryService.SCM_URL).build());
    }
}
