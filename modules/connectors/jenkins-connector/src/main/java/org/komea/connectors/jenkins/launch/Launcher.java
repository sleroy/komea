package org.komea.connectors.jenkins.launch;

import org.komea.connectors.sdk.main.impl.Connector;

public class Launcher {

    public static void main(String[] args) {
        final Connector connector = new Connector("Jenkins Provider");
        connector.addCommand(new JenkinsPushCommand());
        connector.run(args);
    }
}
