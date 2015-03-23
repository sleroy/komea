package org.komea.connectors.cra.launch;

import org.komea.connectors.sdk.main.impl.Connector;

public class Launcher {

    public static void main(String[] args) {
        final Connector connector = new Connector("Cra Provider");
        connector.addCommand(new CraPushCsvCommand());
        connector.run(args);
    }
}
