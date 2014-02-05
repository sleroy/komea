
package org.komea.product.rest.client.util;


import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class KomeaServerLauncher implements Runnable
{
    
    private final WebAppContext webAppContext;
    private final Server        server;
    
    public KomeaServerLauncher() throws Exception {
    
        server = new Server(8585);
        File file = new File("../komea-packaging-war/src/main/webapp");
        System.out.println(file.getCanonicalPath());
        webAppContext = new WebAppContext("src/test/resources/webapp", "/komea");
        webAppContext.setLogUrlOnStart(true);
        server.setHandler(webAppContext);
        
    }
    
    public void startServer() throws Exception {
    
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(this);
        
    }
    
    public void stopServer() throws Exception {
    
        server.stop();
        server.join();
        System.out.println("Server stopped");
    }
    
    @Override
    public void run() {
    
        try {
            server.start();
            System.out.println("Server started");
            server.join();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
