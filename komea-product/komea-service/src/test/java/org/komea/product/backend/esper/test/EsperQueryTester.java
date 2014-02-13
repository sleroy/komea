
package org.komea.product.backend.esper.test;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.service.esper.EPStatementResult;
import org.komea.product.backend.service.esper.ElFormula;
import org.komea.product.backend.service.esper.EsperEngineBean;
import org.komea.product.backend.service.esper.QueryDefinition;
import org.komea.product.backend.service.kpi.IEsperLineTestPredicate;
import org.komea.product.backend.service.kpi.IEsperTestPredicate;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;



public class EsperQueryTester
{
    
    
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
    
    
    private final List<IEvent>                  events              = new ArrayList<IEvent>();
    
    private final List<IEsperTestPredicate>     esperPredicates     =
                                                                            new ArrayList<IEsperTestPredicate>();
    
    
    private final List<IEsperLineTestPredicate> esperLinePredicates =
                                                                            new ArrayList<IEsperLineTestPredicate>();
    
    
    private EPStatement                         epStatement;
    
    private boolean                             dump;
    
    private int                                 expectedRows        = -1;
    
    
    private final Map<String, Provider>         mockProviders       =
                                                                            new HashMap<String, Provider>();
    
    
    private final Map<String, EventType>        mockEventTypes      =
                                                                            new HashMap<String, EventType>();
    
    
    private final Map<String, PersonGroup>      mockGroup           =
                                                                            new HashMap<String, PersonGroup>();
    private final Map<String, Person>           mockPerson          = new HashMap<String, Person>();
    private final Map<String, Project>          mockProject         =
                                                                            new HashMap<String, Project>();
    
    
    
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
    
    
    public EsperQueryTester hasLineResult(final IEsperLineTestPredicate _testPredicate) {
    
    
        esperLinePredicates.add(_testPredicate);
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
    
    
    public EsperQueryTester instantiatedQuery(final String _formula, final Project _project) {
    
    
        esperQuery = new ElFormula(_formula).getValue(_project, String.class);
        return this;
    }
    
    
    public void prepareAlerts(final IEsperEngine esperEngineBean) {
    
    
        for (final IEvent event : events) {
            LOGGER.info("Sending alert : " + event);
            esperEngineBean.sendAlert(event);
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
    
    
    public EsperQueryTester send(final IEvent _alert1) {
    
    
        events.add(_alert1);
        return this;
    }
    
    
    public EsperQueryTester send(final IEvent _alert1, int _numberTimes) {
    
    
        while (_numberTimes-- > 0) {
            events.add(_alert1);
        }
        return this;
    }
    
    
    /**
     * Send an event DTO into esper engine.
     * 
     * @param _eventDto
     *            the event dto
     */
    public EsperQueryTester sendEvent(final EventSimpleDto _eventDto) {
    
    
        if (_eventDto == null) {
            _eventDto.setDate(new Date());
            
        }
        final String providerName = _eventDto.getProvider();
        if (mockProviders.get(providerName) == null) {
            final Provider provider = new Provider();
            provider.setName(providerName);
            mockProviders.put(providerName, provider);
        }
        if (mockEventTypes.get(_eventDto.getEventType()) == null) {
            final EventType eventType = new EventType();
            eventType.setName(_eventDto.getEventType());
            eventType.setEventKey(_eventDto.getEventType());
            mockEventTypes.put(_eventDto.getEventType(), eventType);
        }
        
        final Event event = new Event();
        event.setDate(_eventDto.getDate());
        event.setEventType(mockEventTypes.get(_eventDto.getEventType()));
        event.setProvider(mockProviders.get(_eventDto.getProvider()));
        event.setMessage(_eventDto.getMessage());
        
        for (final String user : _eventDto.getPersons()) {
            if (mockPerson.get(user) == null) {
                final Person person = new Person();
                person.setLogin(user);
                mockPerson.put(user, person);
            }
            event.getPersons().add(mockPerson.get(user));
        }
        if (mockGroup.get(_eventDto.getPersonGroup()) == null) {
            final PersonGroup group = new PersonGroup();
            group.setName(_eventDto.getPersonGroup());
            mockGroup.put(_eventDto.getPersonGroup(), group);
        }
        final String projectName = _eventDto.getProject();
        event.setPersonGroup(mockGroup.get(projectName));
        if (mockProject.get(projectName) == null) {
            final Project group = new Project();
            group.setName(projectName);
            group.setProjectKey(projectName);
            mockProject.put(projectName, group);
        }
        event.setProject(mockProject.get(projectName));
        event.setValue(_eventDto.getValue());
        event.setUrl(_eventDto.getUrl());
        event.setProperties(_eventDto.getProperties());
        
        send(event);
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
