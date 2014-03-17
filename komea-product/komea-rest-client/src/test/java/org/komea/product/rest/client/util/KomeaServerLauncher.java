
package org.komea.product.rest.client.util;



import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;



public class KomeaServerLauncher implements Runnable
{
    
    
    private final ServerConnector connector;
    private final Server          server;
    private ExecutorService       service;
    private final WebAppContext   webAppContext;
    
    
    
    public KomeaServerLauncher() throws Exception {
    
    
        server = new Server();
        connector = new ServerConnector(server);
        server.setConnectors(new Connector[] {
            connector });
        final File file = new File("../komea-packaging-war/src/main/webapp");
        System.out.println(file.getCanonicalPath());
        webAppContext = new WebAppContext("src/test/resources/webapp", "/komea");
        webAppContext.setLogUrlOnStart(true);
        server.setHandler(webAppContext);
        
    }
    
    
    public int getLocalPort() {
    
    
        return connector.getLocalPort();
    }
    
    
    @Override
    public void run() {
    
    
        try {
            server.start();
            System.out.println("Server started");
            server.join();
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    
    public void startServer() throws Exception {
    
    
        service = Executors.newSingleThreadExecutor();
        service.execute(this);
        
        while (!server.isRunning() && !server.isFailed()) {
            Thread.sleep(1000);
            System.out.println("Waiting initialization of Jetty");
        }
        Thread.sleep(40000);
        
        
    }
    
    
    public void stopServer() throws Exception {
    
    
        server.stop();
        server.join();
        System.out.println("Server stopped");
        if (service != null) {
            service.shutdownNow();
            service.awaitTermination(1, TimeUnit.MINUTES);
        }
        
    }
    
}
