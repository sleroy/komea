/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.Comment;
import com.j2bugzilla.base.Product;
import com.j2bugzilla.rpc.BugComments;
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
     * @param _productName
     * @return List<BugzillaBug>
     * @see org.komea.backend.IBZServerProxy.bugzilla.api.IBugZillaServerProxy#getListBugs(String)
     */
    @Override
    public List<Bug> getBugs(final String _productName) {
    
    
        final List<Bug> Bugs = Lists.newArrayList();
        try {
            final BugSearch search =
                    new BugSearch(new BugSearch.SearchQuery(BugSearch.SearchLimiter.PRODUCT,
                            _productName));
            conn.executeMethod(search);
            final List<Bug> bugs = search.getSearchResults();
            for (final Bug bug : bugs) {
                
                Bugs.add(bug);
            }
            
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return Bugs;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.api.IBZServerProxy#getComments(com.j2bugzilla.base.Bug)
     */
    @Override
    public List<Comment> getComments(final Bug _bug) {
    
    
        final BugComments bugComments = new BugComments(_bug);
        try {
            conn.executeMethod(bugComments);
        } catch (final BugzillaException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return bugComments.getComments();

    }
    
    
    @Override
    public BugzillaConnector getConnector() {


        return conn;
    }
    
    
    /**
     * Get legal values.
     *
     * @param field
     * @return
     */
    @Override
    public List<String> GetLegalValues(final GetLegalValues.Fields field) {
    
    
        try {
            final GetLegalValues legalValues = new GetLegalValues(field);
            conn.executeMethod(legalValues);
            return new ArrayList<String>(legalValues.getLegalValues());
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return Lists.newArrayList();
    }
    
    
    @Override
    public List<String> getPriorities() {
    
    
        return GetLegalValues(GetLegalValues.Fields.PRIORITY);
    }


    @Override
    public List<String> getProductNames() {
    
    
        final List<String> productNames = Lists.newArrayList();
        try {
            final GetAccessibleProducts get = new GetAccessibleProducts();
            conn.executeMethod(get);
            final int[] ids = get.getProductIDs();
            for (final int i : ids) {
                final GetProduct getProduct = new GetProduct(i);
                conn.executeMethod(getProduct);
                final Product product = getProduct.getProduct();
                if (product != null) {
                    productNames.add(product.getName());
                }
            }
            
        } catch (final BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return productNames;
    }
    
    
    @Override
    public List<String> getSeverities() {
    
    
        return GetLegalValues(GetLegalValues.Fields.SEVERITY);
    }


    @Override
    public boolean testConnexion() {


        return conn != null;
    }
}
