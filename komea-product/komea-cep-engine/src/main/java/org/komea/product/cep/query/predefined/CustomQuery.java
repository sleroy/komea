/**
 * 
 */

package org.komea.product.cep.query.predefined;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ICEPFormula;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.api.formula.tuple.ITupleCreator;
import org.komea.product.cep.cache.CacheConfigurationParser;
import org.komea.product.cep.cache.ELCacheIndexer;
import org.komea.product.cep.filter.ElEventFilter;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.filter.XPathFilter;
import org.komea.product.cep.formula.XPathFormula;
import org.komea.product.cep.formula.tuple.ELTupleCreator;
import org.komea.product.cep.formula.tuple.GroupByFormula;



/**
 * This class defines the number of build per day.
 * "SELECT project as entity, COUNT(*) as value FROM Event.win:time(1 day) WHERE eventType.eventKey='build_started' GROUP BY project"
 * 
 * @author sleroy
 */
public class CustomQuery implements ICEPQueryImplementation
{
    
    
    private final List<IFilterDefinition> filterDefinitions = new ArrayList<IFilterDefinition>();
    private ICEPFormula                   formula;
    
    private ITupleCreator                 tupleCreator;
    
    
    
    /**
     * 
     */
    public CustomQuery() {
    
    
        super();
    }
    
    
    /**
     * Defines a unique filter
     * 
     * @param _elFilter
     *            the el filter.
     */
    public void elFilter(final String _elFilter, final String _cacheConfiguration) {
    
    
        filterDefinitions.add(EventFilterBuilder
                .create()
                .onlyIEvents()
                .chain(new ElEventFilter(_elFilter))
                .buildFilterDefinition(buildStream(),
                        new CacheConfigurationParser().parse(_cacheConfiguration)));
        
    }
    
    
    /**
     * Sets the formula.
     */
    public void formula(final String _formula) {
    
    
        if (tupleCreator != null) {
            Validate.notNull(tupleCreator, "A group by expression is required");
            formula = new GroupByFormula(tupleCreator, new XPathGroupByFormula(_formula));
        } else {
            formula = new XPathFormula(_formula);
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return filterDefinitions;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula getFormula() {
    
    
        Validate.notNull(formula, "formula is missing");
        return formula;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPQueryImplementation#getParameters()
     */
    @Override
    public Map<String, Object> getParameters() {
    
    
        return Collections.EMPTY_MAP;
    }
    
    
    /**
     * Group by
     * 
     * @param _elFormula
     *            the expression formula
     * @return the tuple creatoe;
     */
    public void groupBy(final String _elFormula) {
    
    
        tupleCreator = new ELTupleCreator(_elFormula);
    }
    
    
    /**
     * Defines a filter with group by formula
     * 
     * @param _filterFormula
     *            the filter.
     */
    public void indexedElFilter(
            final String _filterFormula,
            final String _indexationKey,
            final String _cacheConfiguration) {
    
    
        filterDefinitions.add(EventFilterBuilder
                .create()
                .onlyIEvents()
                .chain(new ElEventFilter(_filterFormula))
                .buildFilterDefinition(
                        buildStream(),
                        new CacheConfigurationParser().parseWithIndexer(_cacheConfiguration,
                                new ELCacheIndexer(_indexationKey))));
        
    }
    
    
    /**
     * Defines a unique xpath filter
     * 
     * @param _xpathFilter
     *            the filter.
     */
    public void indexedXpathFilter(
            final String _xpathFilter,
            final String _indexationKey,
            final String _cacheConfiguration) {
    
    
        filterDefinitions.add(EventFilterBuilder
                .create()
                .onlyIEvents()
                .chain(new XPathFilter(_xpathFilter))
                .buildFilterDefinition(
                        buildStream(),
                        new CacheConfigurationParser().parseWithIndexer(_cacheConfiguration,
                                new ELCacheIndexer(_indexationKey))));
        
    }
    
    
    /**
     * Defines a unique xpath filter
     * 
     * @param _xpathFilter
     *            the filter.
     */
    public void xpathFilter(final String _xpathFilter, final String _cacheConfiguration) {
    
    
        filterDefinitions.add(EventFilterBuilder
                .create()
                .onlyIEvents()
                .chain(new XPathFilter(_xpathFilter))
                .buildFilterDefinition(buildStream(),
                        new CacheConfigurationParser().parse(_cacheConfiguration)));
        
    }
    
    
    private String buildStream() {
    
    
        return "stream" + Integer.toString(filterDefinitions.size());
    }
}
