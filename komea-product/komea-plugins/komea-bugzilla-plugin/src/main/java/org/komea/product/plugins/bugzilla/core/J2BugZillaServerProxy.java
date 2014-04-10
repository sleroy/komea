/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.core;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.product.plugins.bugzilla.data.BugzillaBug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class J2BugZillaServerProxy implements IBugZillaServerProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(J2BugZillaServerProxy.class);
    private BugzillaConnector conn;

    /**
     * Constructor for J2BugZillaServerProxy.
     *
     * @param conn BugzillaConnector
     */
    public J2BugZillaServerProxy(BugzillaConnector conn) {
        this.conn = conn;
    }

    /**
     * Method getListProjects.
     *
     * @return List<String>
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy#getListProjects()
     */
    @Override
    public List<String> getListProjects() {
        List<Product> products = getProduct();
        List<String> projects = new ArrayList<String>();
        for (Product product : products) {
            projects.add(product.getName());
        }
        return projects;
    }

    private List<String> GetLegalValues(final GetLegalValues.Fields field) {
        try {
            final GetLegalValues legalValues = new GetLegalValues(field);
            conn.executeMethod(legalValues);
            return new ArrayList<String>(legalValues.getLegalValues());
        } catch (BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return Lists.newArrayList();
    }

    @Override
    public List<String> getSeverities() {
        return GetLegalValues(GetLegalValues.Fields.SEVERITY);
    }

    @Override
    public List<String> getPriorities() {
        return GetLegalValues(GetLegalValues.Fields.PRIORITY);
    }

    /**
     * Method getListBugs.
     *
     * @param Project String
     * @return List<BugzillaBug>
     * @see
     * org.komea.backend.plugins.bugzilla.api.IBugZillaServerProxy#getListBugs(String)
     */
    @Override
    public List<BugzillaBug> getListBugs(String Project) {

        List<BugzillaBug> bugZillaBugs = new ArrayList<BugzillaBug>();
        try {
            BugSearch search = new BugSearch(new BugSearch.SearchQuery(BugSearch.SearchLimiter.PRODUCT, Project));
//            GetBug search = new GetBug(445296);
            conn.executeMethod(search);

            List<Bug> searchResults = search.getSearchResults();
            for (Bug bug : searchResults) {
                Map<Object, Object> parameterMap = bug.getParameterMap();
                Boolean isOpen = (Boolean) parameterMap.get("is_open");
                Date dateCreation = (Date) parameterMap.get("creation_time");
                String assign = (String) parameterMap.get("assigned_to");
                boolean is_assign = true;
                if ("".equals(assign)) {
                    is_assign = false;
                }
                bugZillaBugs.add(new BugzillaBug(bug.getID(), bug.getStatus(), isOpen.booleanValue(),
                        dateCreation, is_assign, bug.getSeverity(), bug.getPriority()));
            }

        } catch (BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return bugZillaBugs;
    }

    /**
     * Method getProduct.
     *
     * @return List<Product>
     */
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
            LOGGER.error(ex.getMessage(), ex);
        }
        return products;
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
        } catch (BugzillaException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
