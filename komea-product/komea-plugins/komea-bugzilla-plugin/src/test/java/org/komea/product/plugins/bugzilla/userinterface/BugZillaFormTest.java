/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.userinterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.komea.product.plugins.bugzilla.userinterface.BugZillaForm.ENDLINE;

/**
 *
 * @author rgalerme
 */
public class BugZillaFormTest {
    
    public BugZillaFormTest() {
    }

    @Test
    public void testRegisterExeption() {
    }

    @Test
    public void testRecursiveDisplayTrace() {
        
      
        BufferedReader bufferedReader = new BufferedReader(new StringReader("./fichequiexistepa"));
        try {
            bufferedReader.close();
            bufferedReader.readLine();
            
            System.out.println("ca marche");
        } catch (IOException ex) {
            System.out.println("ca marche pas");
            String recursiveDisplayTrace = recursiveDisplayTrace(ex);
            System.out.println(recursiveDisplayTrace);
        }
    }
    
     String recursiveDisplayTrace(Throwable cause) {
        if (cause == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cause.getMessage()).append(ENDLINE);
        for (StackTraceElement element : cause.getStackTrace()) {
            sb.append(element.toString());
            sb.append(ENDLINE);
        }
        sb.append(recursiveDisplayTrace(cause.getCause()));
        return sb.toString();

    }
    
}
