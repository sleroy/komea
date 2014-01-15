
package org.komea.product.web.admin.views;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/auth")
public class AuthView
{
    
    @RequestMapping(method = RequestMethod.GET, value = { "/accessdenied" })
    public String accessdenied(final ModelMap model) {
    
        model.addAttribute("error", "true");
        System.out.println("Failed");
        return "login";
        
    }
    
    @RequestMapping(method = RequestMethod.GET, value = { "/login" })
    public String login() {
    
        System.out.println("Login");
        return "login";
        
    }
    
    @RequestMapping(method = RequestMethod.GET, value = { "/logout" })
    public String logout() {
    
        System.out.println("Logout");
        return "login";
        
    }
}
