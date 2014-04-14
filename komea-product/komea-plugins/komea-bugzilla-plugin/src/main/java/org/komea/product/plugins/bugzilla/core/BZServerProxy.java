/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.model.BugzillaBug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.Product;
import com.j2bugzilla.rpc.BugSearch;
import com.j2bugzilla.rpc.GetAccessibleProducts;
import com.j2bugzilla.rpc.GetLegalValues;
import com.j2bugzilla.rpc.GetProduct;
import com.j2bugzilla.rpc.LogOut;



/**
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BZServerProxy implements IBZServerProxy
{
    
    
    private static final Logger     LOGGER = LoggerFactory.getLogger("bugzilla-proxy");
    private final BugzillaConnector conn;
    
    
    
    /**
     * Constructor for BZServerProxy.
     * 
     * @param conn
     *            BugzillaConnector
     */
    public BZServerProxy(final BugzillaConnector conn) {
    
    
        this.conn = conn;
    }
    
    
    /**
     * Method close.
     * 
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() {
    
    
        try {
            conn.executeMethod(new LogOut());
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    
    
    /**
     * Method getListBugs.
     * 
     * @param Project
     *            String
     * @return List<BugzillaBug>
     * @see org.komea.backend.IBZServerProxy.bugzilla.api.IBugZillaServerProxy#getListBugs(String)
     */
    @Override
    public List<BugzillaBug> getListBugs(final String Project) {
    
    
        final List<BugzillaBug> bugZillaBugs = new ArrayList<BugzillaBug>();
        try {
            final BugSearch search =
                    new BugSearch(new BugSearch.SearchQuery(BugSearch.SearchLimiter.PRODUCT,
                            Project));
            // GetBug search = new GetBug(445296);
            conn.executeMethod(search);
            
            final List<Bug> searchResults = search.getSearchResults();
            for (final Bug bug : searchResults) {
                final Map<Object, Object> parameterMap = bug.getParameterMap();
                final Boolean isOpen = (Boolean) parameterMap.get("is_open");
                final Date dateCreation = (Date) parameterMap.get("creation_time");
                final String assign = (String) parameterMap.get("assigned_to");
                boolean is_assign = true;
                if ("".equals(assign)) {
                    is_assign = false;
                }
                bugZillaBugs.add(new BugzillaBug(bug.getID(), bug.getStatus(), isOpen
                        .booleanValue(), dateCreation, is_assign, bug.getSeverity(), bug
                        .getPriority()));
            }
            
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return bugZillaBugs;
    }
    
    
    /**
     * Method getListProjects.
     * 
     * @return List<String>
     * @see org.komea.backend.IBZServerProxy.bugzilla.api.IBugZillaServerProxy#getListProjects()
     */
    @Override
    public List<String> getListProjects() {
    
    
        final List<Product> products = getProducts();
        final List<String> projects = new ArrayList<String>();
        for (final Product product : products) {
            projects.add(product.getName());
        }
        return projects;
    }
    
    
    @Override
    public List<String> getPriorities() {
    
    
        return GetLegalValues(GetLegalValues.Fields.PRIORITY);
    }
    
    
    @Override
    public List<String> getSeverities() {
    
    
        return GetLegalValues(GetLegalValues.Fields.SEVERITY);
    }
    
    
    private List<String> GetLegalValues(final GetLegalValues.Fields field) {
    
    
        try {
            final GetLegalValues legalValues = new GetLegalValues(field);
            conn.executeMethod(legalValues);
            return new ArrayList<String>(legalValues.getLegalValues());
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return Lists.newArrayList();
    }
    
    
    /**
     * Method getProduct.
     * 
     * @return List<Product>
     */
    private List<Product> getProducts() {
    
    
        final List<Product> products = new ArrayList<Product>();
        try {
            final GetAccessibleProducts get = new GetAccessibleProducts();
            conn.executeMethod(get);
            final int[] ids = get.getProductIDs();
            for (final int i : ids) {
                final GetProduct getProduct = new GetProduct(i);
                conn.executeMethod(getProduct);
                if (getProduct.getProduct() != null) {
                    products.add(getProduct.getProduct());
                }
            }
            
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return products;
    }
}
