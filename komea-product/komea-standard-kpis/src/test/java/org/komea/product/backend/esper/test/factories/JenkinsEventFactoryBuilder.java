package org.komea.product.backend.esper.test.factories;

import java.io.File;
import java.io.IOException;

import org.komea.product.backend.event.test.DSLEventBuilder;
import org.komea.product.backend.event.test.EventFactoryBuilder;

/**
 */
public class JenkinsEventFactoryBuilder {

    /**
     * Method main.
     *
     * @param args String[]
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        final EventFactoryBuilder eventFactoryBuilder
                = new EventFactoryBuilder("JenkinsEventFactory", "org.komea.event.factory", new File(
                                "src/test/java"));

        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendLevelIndustrialisation", "jenkins", "build_industrialization")
                .linkToProject().withValue()
                .predefinedMessage("Industrialization level for the project $project:$value")
                .build());

        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendBuildComplete", "jenkins", "build_complete").linkToProject()
                .withValue().withParameter("branch", String.class)
                .predefinedMessage("Build performed for the  project $project in $value min.")
                .build());
        eventFactoryBuilder
                .register(DSLEventBuilder.newEvent("sendBuildFailed", "jenkins", "build_failed")
                        .linkToProject().withValue().withParameter("branch", String.class)
                        .predefinedMessage("Build failed for the  project $project in $value min.")
                        .build());
        eventFactoryBuilder
                .register(DSLEventBuilder.newEvent("sendBuildUnstable", "jenkins", "build_unstable")
                        .linkToProject().withValue().withParameter("branch", String.class)
                        .predefinedMessage("Build unstable for the  project $project in $value min.")
                        .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendBuildInterrupted", "jenkins", "build_interrupted").linkToProject()
                .withValue().withParameter("branch", String.class)
                .predefinedMessage("Build interrupted for the  project $project in $value min.")
                .build());
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendBuildStarted", "jenkins", "build_started").linkToProject()
                .withValue().withParameter("branch", String.class)
                .predefinedMessage("Build started for the  project $project.")
                .build());

        eventFactoryBuilder.generate();
    }

    public JenkinsEventFactoryBuilder() {

        super();

    }
}
