package org.komea.connectors.sonar.launch;

import org.komea.connectors.sdk.main.impl.Connector;

public class Launcher {

    public static void main(String[] args) {
        final Connector connector = new Connector("Sonar Provider");
        connector.addCommand(new SonarPushCommand());
        connector.run(args);
    }
}
