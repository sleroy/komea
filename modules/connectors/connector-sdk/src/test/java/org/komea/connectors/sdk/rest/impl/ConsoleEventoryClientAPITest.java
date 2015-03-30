package org.komea.connectors.sdk.rest.impl;

import org.junit.Test;

public class ConsoleEventoryClientAPITest {

    @Test
    public void test() throws Exception {
        final Object objectNotSerializable = new Runnable() {

            @Override
            public void run() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        final ConsoleEventoryClientAPI client = new ConsoleEventoryClientAPI();
        client.close();
        client.countEvents("");
        client.delete("", new String[0]);
        client.deleteURL(new String[0]);
        client.get(String.class, "");
        client.get("", String.class);
        client.get("", "");
        client.get("", String.class, "");
        client.get("", Integer.TYPE, "");
        client.getEventStorage();
        client.getLastEvent("");
        client.post("", "");
        client.post("", objectNotSerializable);
        client.post("", String.class, "");
        client.post(objectNotSerializable, String.class, "");
        client.post("", "", String.class);
        client.post("", objectNotSerializable, String.class);
        client.post("", "", Integer.TYPE);
        client.post("", objectNotSerializable, Integer.TYPE);
        client.postURL("", "");
        client.postURL(objectNotSerializable, "");
        client.setServerBaseURL("");
        client.testConnectionValid();
        client.testConnexion();
    }

}
