
package org.komea.product.backend.service.kpi;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.service.esper.EPStatementResult;
import org.komea.product.backend.service.esper.EsperEngineBean;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.database.alert.IAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;



public class EsperQueryTester
{
    
    
    public interface IEsperLineTestPredicate
    {
        
        
        void evaluate(final Map<String, Object> _bean);
    }
    
    
    
    public interface IEsperTestPredicate
    {
        
        
        void evaluate(final EPStatement _epStatement);
    }
    
    
    
    private static class MapLinePredicate implements IEsperLineTestPredicate
    {
        
        
        private final Map<String, Object> objects;
        
        
        
        /**
         * @param _columnName
         * @param _expectedValue
         */
        public MapLinePredicate(final Map<String, Object> _objects) {
        
        
            super();
            objects = _objects;
            
        }
        
        
        @Override
        public void evaluate(final Map<String, Object> _bean) {
        
        
            for (final Entry<String, Object> entry : objects.entrySet()) {
                Assert.assertEquals("Expected " + entry.getValue() + "  for " + entry.getKey(),
                        entry.getValue(), _bean.get(entry.getKey()));
            }
            
            
        }
    }
    
    
    
    private static class SingleColumnPredicate implements IEsperTestPredicate
    {
        
        
        private final String columnName;
        private final Object expectedValue;
        
        
        
        /**
         * @param _columnName
         * @param _expectedValue
         */
        public SingleColumnPredicate(final String _columnName, final Object _expectedValue) {
        
        
            super();
            columnName = _columnName;
            expectedValue = _expectedValue;
        }
        
        
        @Override
        public void evaluate(final EPStatement _epStatement) {
        
        
            Assert.assertEquals("Expected " + expectedValue + "  for " + columnName, expectedValue,
                    EPStatementResult.build(_epStatement).singleResult(columnName));
            
        }
    }
    
    
    
    private static class SingleLinePredicate implements IEsperLineTestPredicate
    {
        
        
        private final String columnName;
        private final Object expectedValue;
        
        
        
        /**
         * @param _columnName
         * @param _expectedValue
         */
        public SingleLinePredicate(final String _columnName, final Object _expectedValue) {
        
        
            super();
            columnName = _columnName;
            expectedValue = _expectedValue;
        }
        
        
        @Override
        public void evaluate(final Map<String, Object> _bean) {
        
        
            Assert.assertEquals("Expected " + expectedValue + "  for " + columnName, expectedValue,
                    _bean.get(columnName));
            
            
        }
    }
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EsperQueryTester.class);
    
    
    
    public static IEsperEngine newEngine() {
    
    
        final EsperEngineBean esperEngineBean = new EsperEngineBean();
        esperEngineBean.init();
        return esperEngineBean;
    }
    
    
    public static EsperQueryTester newTest(final String _string) {
    
    
        return new EsperQueryTester(_string);
    }
    
    
    
    private final String                        esperName;
    
    private String                              esperQuery;
    
    
    private final List<IAlert>                  alerts              = new ArrayList<IAlert>();
    
    private final List<IEsperTestPredicate>     esperPredicates     =
                                                                            new ArrayList<EsperQueryTester.IEsperTestPredicate>();
    
    
    private final List<IEsperLineTestPredicate> esperLinePredicates =
                                                                            new ArrayList<EsperQueryTester.IEsperLineTestPredicate>();
    
    
    private EPStatement                         epStatement;
    
    private boolean                             dump;
    
    private int                                 expectedRows        = -1;
    
    
    
    private EsperQueryTester(final String _statementName) {
    
    
        super();
        esperName = _statementName;
        
    }
    
    
    public EsperQueryTester dump() {
    
    
        dump = true;
        
        return this;
    }
    
    
    public EsperQueryTester expectRows(final int _numberRows) {
    
    
        expectedRows = _numberRows;
        
        return this;
    }
    
    
    public EsperQueryTester hasLineResult(final Map<String, Object> _map) {
    
    
        esperLinePredicates.add(new MapLinePredicate(_map));
        return this;
    }
    
    
    public EsperQueryTester hasLineResult(final String _columnName, final Object _expectedValue) {
    
    
        esperLinePredicates.add(new SingleLinePredicate(_columnName, _expectedValue));
        return this;
    }
    
    
    public EsperQueryTester hasSingleResult(final String _columnName, final Object _expectedValue) {
    
    
        esperPredicates.add(new SingleColumnPredicate(_columnName, _expectedValue));
        return this;
    }
    
    
    public void prepareAlerts(final IEsperEngine esperEngineBean) {
    
    
        for (final IAlert alert : alerts) {
            esperEngineBean.sendAlert(alert);
        }
    }
    
    
    public void prepareQuery(final IEsperEngine esperEngineBean) {
    
    
        if (epStatement == null) {
            epStatement = esperEngineBean.createEPL(new QueryDefinition(esperQuery, esperName));
        }
    }
    
    
    public void runTest() {
    
    
        runTest(newEngine());
    }
    
    
    public void runTest(final IEsperEngine esperEngineBean) {
    
    
        prepareQuery(esperEngineBean);
        prepareAlerts(esperEngineBean);
        validPredicates();
        
    }
    
    
    public EsperQueryTester send(final IAlert _alert1) {
    
    
        alerts.add(_alert1);
        return this;
    }
    
    
    public EsperQueryTester send(final IAlert _alert1, int _numberTimes) {
    
    
        while (_numberTimes-- > 0) {
            alerts.add(_alert1);
        }
        return this;
    }
    
    
    public void validPredicates() {
    
    
        for (final IEsperTestPredicate testPred : esperPredicates) {
            testPred.evaluate(epStatement);
        }
        final List<Map<String, Object>> listMapResult =
                EPStatementResult.build(epStatement).listMapResult();
        dumpResults(listMapResult);
        int i = 0;
        for (final Map<String, Object> value : listMapResult) {
            
            
            if (i < esperLinePredicates.size()) {
                esperLinePredicates.get(i).evaluate(value);
            }
            i++;
            
        }
        if (expectedRows != -1) {
            Assert.assertEquals("Expected fixed number of rows", expectedRows, i);
        }
        
    }
    
    
    public EsperQueryTester withQuery(final String _string) {
    
    
        esperQuery = _string;
        return this;
    }
    
    
    private void dumpResults(final List<Map<String, Object>> listMapResult) {
    
    
        if (dump) {
            int i = 0;
            for (final Map<String, Object> value : listMapResult) {
                LOGGER.info("#{}-{} received : {}", i++, esperName, value);
            }
        }
    }
    
    
}
