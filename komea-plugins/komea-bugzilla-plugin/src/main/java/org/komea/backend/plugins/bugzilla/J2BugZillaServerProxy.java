/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.base.Product;
import com.j2bugzilla.rpc.BugSearch;
import com.j2bugzilla.rpc.GetAccessibleProducts;
import com.j2bugzilla.rpc.GetProduct;
import com.j2bugzilla.rpc.LogIn;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy;

/**
 *
 * @author rgalerme
 */
public class J2BugZillaServerProxy implements IBugZillaServerProxy {

    private BugzillaConnector conn=null;
    private final String address;
    private final String login;
    private final String mdp;

    public J2BugZillaServerProxy(String address, String login, String mdp) {
        this.address = address;
        this.login = login;
        this.mdp = mdp;
    }

    @Override
    public List<String> getListProjects() {
        checkConnexion();
        List<Product> products = getProduct();
        List<String> projects = new ArrayList<String>();
        for (Product product : products) {
            projects.add(product.getName());
        }
        return projects;
    }

    @Override
    public List<BugzillaBug> getListBugs(String Project) {
        checkConnexion();
       List<BugzillaBug> bugZillaBugs = new ArrayList<BugzillaBug>();
        try {
            BugSearch search = new BugSearch(new BugSearch.SearchQuery(BugSearch.SearchLimiter.PRODUCT, Project));
//            GetBug search = new GetBug(445296);
            conn.executeMethod(search);
            
            List<Bug> searchResults = search.getSearchResults();
            for (Bug bug : searchResults) {

                bugZillaBugs.add(new BugzillaBug(bug.getID(), bug.getStatus()));
            }

        } catch (BugzillaException ex) {
            Logger.getLogger(J2BugZillaServerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bugZillaBugs;
    }

    @Override
    public List<BugzillaBug> getFilterBugs(String Project, BugZillaStatus... status) {
        checkConnexion();
        return null;
    }

    private List<Product> getProduct() {
        List<Product> products = new ArrayList<Product>();
        try {
            GetAccessibleProducts get = new GetAccessibleProducts();
            conn.executeMethod(get);
            int[] ids = get.getProductIDs();
            for (int i : ids) {
                GetProduct getProduct = new GetProduct(i);
                conn.executeMethod(getProduct);
                if (getProduct.getProduct() != null) {
                    products.add(getProduct.getProduct());
                }
            }

        } catch (BugzillaException ex) {
            Logger.getLogger(J2BugZillaServerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    private void checkConnexion()
    {
        if(this.conn == null)
        {
        connexion();
        }
    }
    
    private void connexion() {
        try {
            conn = new BugzillaConnector();
            conn.connectTo(address);
            LogIn logIn = new LogIn(login, mdp);
            try {
                conn.executeMethod(logIn);
            } catch (BugzillaException ex) {
                Logger.getLogger(BugZillaServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ConnectionException ex) {
            Logger.getLogger(BugZillaServerConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
