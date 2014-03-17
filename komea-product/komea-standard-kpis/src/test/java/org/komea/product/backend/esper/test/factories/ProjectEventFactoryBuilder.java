
package org.komea.product.backend.esper.test.factories;



import java.io.File;
import java.io.IOException;

import org.komea.product.backend.event.test.DSLEventBuilder;
import org.komea.product.backend.event.test.EventFactoryBuilder;



/**
 */
public class ProjectEventFactoryBuilder
{
    
    
    /**
     * Method main.
     * @param args String[]
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
    
    
        final EventFactoryBuilder eventFactoryBuilder =
                new EventFactoryBuilder("ProjectEventFactory", "org.komea.event.factory", new File(
                        "src/test/java"));
        eventFactoryBuilder
                .register(DSLEventBuilder
                        .newEvent("sendProjectCharge", "project", "project_charge")
                        .withParameter("planned", Double.class)
                        .withParameter("estimated", Double.class)
                        .withParameter("consumed", Double.class)
                        .linkToProject()
                        .predefinedMessage(
                                "Project charges are estimated  and planned, deviation #estimated/#planned/#consumed")
                        .build());
        eventFactoryBuilder
                .register(DSLEventBuilder
                        .newEvent("sendProjectFunctionalities", "project",
                                "project_functionalities")
                        .linkToProject()
                        .withParameter("estimated", Double.class)
                        .withParameter("planned", Double.class)
                        .withParameter("done", Double.class)
                        .predefinedMessage(
                                "Project functionalities are estimated  and planned, deviation #estimated/#planned/#done")
                        .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendProjectTime", "project", "project_time")
                .linkToProject()
                .withParameter("estimated", Double.class)
                .withParameter("planned", Double.class)
                .withParameter("done", Double.class)
                .predefinedMessage(
                        "Project ETA are estimated  and planned, deviation #estimated/#planned")
                .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendDelayedStories", "project", "delayed_stories").linkToProject()
                .withValue().predefinedMessage("Number of delayed stories in project ").build());
        
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendUpdatedStories", "project", "updated_stories").linkToProject()
                .withValue().predefinedMessage("Number of updated stories in project ").build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendActivityTeam", "project", "activity_team").linkToProject()
                .withValue()
                .predefinedMessage("Activity rate of the team for the project $project is $value")
                .build());
        eventFactoryBuilder
                .register(DSLEventBuilder
                        .newEvent("sendMaintFunctionalities", "project", "functionalities_maint")
                        .linkToProject()
                        .withParameter("planned", Double.class)
                        .withParameter("done", Double.class)
                        .predefinedMessage(
                                "Number of maintenancy functionalities for the project $project")
                        .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendEvolutionFunctionalities", "project", "functionalities_evolution")
                .linkToProject().withParameter("planned", Double.class)
                .withParameter("done", Double.class)
                .predefinedMessage("Number of evolution functionalities for the project $project")
                .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendBugFixesFunctionalities", "project", "functionalities_bugfixes")
                .withParameter("planned", Double.class).withParameter("done", Double.class)
                .linkToProject()
                .predefinedMessage("Number of bug fixes functionalities for the project $project")
                .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendInnovationFunctionalities", "project", "functionalities_innovation")
                .linkToProject().withParameter("planned", Double.class)
                .withParameter("done", Double.class)
                .predefinedMessage("Number of innovation functionalities for the project $project")
                .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendReleasedStory", "project", "released_story").linkToProject()
                .withValue().predefinedMessage("Released story").build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendUserValue", "project", "user_value").linkToProject()
                .withParameter("planned", Double.class).withParameter("done", Double.class)
                .predefinedMessage("LdapUser value planned/performed").build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendStoryStatus", "project", "story_status").linkToProject()
                .withParameter("name", String.class).withParameter("status", String.class)
                .predefinedMessage("Story status").build());
        
        
        eventFactoryBuilder.generate();
    }
    
    
    public ProjectEventFactoryBuilder() {
    
    
        super();
        
    }
}
