
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
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.espertech.esper.client.EPStatement;



/**
 */
public class EsperQueryTester
{
    
    
    /**
     */
    private static class MapLinePredicate implements IEsperLineTestPredicate
    {
        
        
        private final Map<String, Object> objects;
        
        
        
        /**
         * @param _objects
         *            Map<String,Object>
         */
        public MapLinePredicate(final Map<String, Object> _objects) {
        
        
            super();
            objects = _objects;
            
        }
        
        
        /**
         * Method evaluate.
         * 
         * @param _bean
         *            Map<String,Object>
         * @see org.komea.product.backend.service.kpi.IEsperLineTestPredicate#evaluate(Map<String,Object>)
         */
        @Override
        public void evaluate(final Map<String, Object> _bean) {
        
        
            for (final Entry<String, Object> entry : objects.entrySet()) {
                Assert.assertEquals("Expected " + entry.getValue() + "  for " + entry.getKey(),
                        entry.getValue(), _bean.get(entry.getKey()));
            }
            
            
        }
    }
    
    
    
    /**
     */
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
        
        
        /**
         * Method evaluate.
         * 
         * @param _epStatement
         *            EPStatement
         * @see org.komea.product.backend.service.kpi.IEsperTestPredicate#evaluate(EPStatement)
         */
        @Override
        public void evaluate(final EPStatement _epStatement) {
        
        
            Assert.assertEquals("Expected " + expectedValue + "  for " + columnName, expectedValue,
                    EPStatementResult.build(_epStatement).singleResult(columnName));
            
        }
    }
    
    
    
    /**
     */
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
        
        
        /**
         * Method evaluate.
         * 
         * @param _bean
         *            Map<String,Object>
         * @see org.komea.product.backend.service.kpi.IEsperLineTestPredicate#evaluate(Map<String,Object>)
         */
        @Override
        public void evaluate(final Map<String, Object> _bean) {
        
        
            Assert.assertEquals("Expected " + expectedValue + "  for " + columnName, expectedValue,
                    _bean.get(columnName));
            
            
        }
    }
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("[Esper Query Test]");
    
    
    
    /**
     * Converts an event dto into a event.
     * 
     * @param _eventDTO
     *            the event dto
     * @return the event.
     */
    public static IEvent convertEventDTO(final EventSimpleDto _eventDTO) {
    
    
        final EsperQueryTester esperQueryTester = new EsperQueryTester("TRUC");
        esperQueryTester.sendEvent(_eventDTO);
        
        return CollectionUtil.singleOrNull(esperQueryTester.getEvents());
    }
    
    
    /**
     * Method newEngine.
     * 
     * @return EsperEngineBean
     */
    public static EsperEngineBean newEngine() {
    
    
        final EsperEngineBean esperEngineBean = new EsperEngineBean();
        esperEngineBean.init();
        return esperEngineBean;
    }
    
    
    /**
     * Method newTest.
     * 
     * @param _string
     *            String
     * @return EsperQueryTester
     */
    public static EsperQueryTester newTest(final String _string) {
    
    
        return new EsperQueryTester(_string);
    }
    
    
    
    private boolean                             dump;
    
    
    private EPStatement                         epStatement;
    
    private final List<IEsperLineTestPredicate> esperLinePredicates =
                                                                            new ArrayList<IEsperLineTestPredicate>();
    
    
    private final String                        esperName;
    
    
    private final List<IEsperTestPredicate>     esperPredicates     =
                                                                            new ArrayList<IEsperTestPredicate>();
    
    private String                              esperQuery;
    
    private final List<IEvent>                  events              = new ArrayList<IEvent>();
    
    
    private int                                 expectedRows        = -1;
    
    
    private final Map<String, EventType>        mockEventTypes      =
                                                                            new HashMap<String, EventType>();
    
    
    private final Map<String, PersonGroup>      mockGroup           =
                                                                            new HashMap<String, PersonGroup>();
    private final Map<String, Person>           mockPerson          = new HashMap<String, Person>();
    private final Map<String, Project>          mockProject         =
                                                                            new HashMap<String, Project>();
    
    
    private final Map<String, Provider>         mockProviders       =
                                                                            new HashMap<String, Provider>();
    
    
    
    /**
     * Constructor for EsperQueryTester.
     * 
     * @param _statementName
     *            String
     */
    private EsperQueryTester(final String _statementName) {
    
    
        super();
        esperName = _statementName;
        
    }
    
    
    /**
     * Method dump.
     * 
     * @return EsperQueryTester
     */
    public EsperQueryTester dump() {
    
    
        dump = true;
        
        return this;
    }
    
    
    /**
     * Method expectRows.
     * 
     * @param _numberRows
     *            int
     * @return EsperQueryTester
     */
    public EsperQueryTester expectRows(final int _numberRows) {
    
    
        expectedRows = _numberRows;
        
        return this;
    }
    
    
    /**
     * Method getEvents.
     * 
     * @return List<IEvent>
     */
    public List<IEvent> getEvents() {
    
    
        return events;
    }
    
    
    /**
     * Method hasLineResult.
     * 
     * @param _testPredicate
     *            IEsperLineTestPredicate
     * @return EsperQueryTester
     */
    public EsperQueryTester hasLineResult(final IEsperLineTestPredicate _testPredicate) {
    
    
        esperLinePredicates.add(_testPredicate);
        return this;
    }
    
    
    /**
     * Method hasLineResult.
     * 
     * @param _map
     *            Map<String,Object>
     * @return EsperQueryTester
     */
    public EsperQueryTester hasLineResult(final Map<String, Object> _map) {
    
    
        esperLinePredicates.add(new MapLinePredicate(_map));
        return this;
    }
    
    
    /**
     * Method hasLineResult.
     * 
     * @param _columnName
     *            String
     * @param _expectedValue
     *            Object
     * @return EsperQueryTester
     */
    public EsperQueryTester hasLineResult(final String _columnName, final Object _expectedValue) {
    
    
        esperLinePredicates.add(new SingleLinePredicate(_columnName, _expectedValue));
        return this;
        
        
    }
    
    
    /**
     * Method hasSingleResult.
     * 
     * @param _columnName
     *            String
     * @param _expectedValue
     *            Object
     * @return EsperQueryTester
     */
    public EsperQueryTester hasSingleResult(final String _columnName, final Object _expectedValue) {
    
    
        esperPredicates.add(new SingleColumnPredicate(_columnName, _expectedValue));
        return this;
    }
    
    
    /**
     * Method instantiatedQuery.
     * 
     * @param _formula
     *            String
     * @param _project
     *            Project
     * @return EsperQueryTester
     */
    public EsperQueryTester instantiatedQuery(final String _formula, final Project _project) {
    
    
        esperQuery = new ElFormula(_formula).getValue(_project, String.class);
        return this;
    }
    
    
    /**
     * Method prepareAlerts.
     * 
     * @param esperEngineBean
     *            IEsperEngine
     */
    public void prepareAlerts(final IEsperEngine esperEngineBean) {
    
    
        for (final IEvent event : events) {
            LOGGER.info("Sending alert : " + event);
            esperEngineBean.sendEvent(event);
        }
    }
    
    
    /**
     * Method prepareQuery.
     * 
     * @param esperEngineBean
     *            IEsperEngine
     */
    public void prepareQuery(final IEsperEngine esperEngineBean) {
    
    
        if (epStatement == null) {
            epStatement = esperEngineBean.createEPL(new QueryDefinition(esperQuery, esperName));
        }
    }
    
    
    public void runTest() {
    
    
        final EsperEngineBean newEngine = newEngine();
        runTest(newEngine);
        newEngine.destroy();
    }
    
    
    /**
     * Method runTest.
     * 
     * @param esperEngineBean
     *            IEsperEngine
     */
    public void runTest(final IEsperEngine esperEngineBean) {
    
    
        prepareQuery(esperEngineBean);
        prepareAlerts(esperEngineBean);
        if (dump) {
            dumpInfos();
        }
        validPredicates();
        
    }
    
    
    /**
     * Method send.
     * 
     * @param _alert1
     *            IEvent
     * @return EsperQueryTester
     */
    public EsperQueryTester send(final IEvent _alert1) {
    
    
        events.add(_alert1);
        return this;
    }
    
    
    /**
     * Method send.
     * 
     * @param _alert1
     *            IEvent
     * @param _numberTimes
     *            int
     * @return EsperQueryTester
     */
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
     * @return EsperQueryTester
     */
    public EsperQueryTester sendEvent(final EventSimpleDto _eventDto) {
    
    
        if (_eventDto == null) {
            _eventDto.setDate(new Date());
            
        }
        final String providerName = _eventDto.getProvider();
        if (mockProviders.get(providerName) == null) {
            final Provider provider = new Provider();
            provider.setName(providerName);
            provider.setProviderType(ProviderType.CI_BUILD);
            provider.setIcon("");
            provider.setUrl("");
            mockProviders.put(providerName, provider);
        }
        final String eventTypeName = _eventDto.getEventType();
        if (mockEventTypes.get(eventTypeName) == null) {
            final EventType eventType = new EventType();
            eventType.setName(eventTypeName);
            eventType.setEventKey(eventTypeName);
            eventType.setCategory("");
            eventType.setDescription(eventTypeName);
            eventType.setEnabled(true);
            eventType.setSeverity(Severity.INFO);
            
            mockEventTypes.put(eventTypeName, eventType);
        }
        
        final Event event = new Event();
        event.setDate(_eventDto.getDate());
        event.setEventType(mockEventTypes.get(eventTypeName));
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
    
    
        if (esperLinePredicates.isEmpty() && esperPredicates.isEmpty() && expectedRows == -1) { return; }
        for (final IEsperTestPredicate testPred : esperPredicates) {
            testPred.evaluate(epStatement);
        }
        final List<Map<String, Object>> listMapResult =
                EPStatementResult.build(epStatement).listMapResult();
        
        int i = 0;
        for (final Map<String, Object> value : listMapResult) {
            
            LOGGER.info("Testing line {} -> {}", i, value);
            if (i < esperLinePredicates.size()) {
                esperLinePredicates.get(i).evaluate(value);
            }
            i++;
            
        }
        if (expectedRows != -1) {
            Assert.assertEquals("Expected fixed number of rows", expectedRows, i);
        }
        
    }
    
    
    /**
     * Method withQuery.
     * 
     * @param _string
     *            String
     * @return EsperQueryTester
     */
    public EsperQueryTester withQuery(final String _string) {
    
    
        esperQuery = _string;
        return this;
    }
    
    
    /**
     * 
     */
    private void dumpInfos() {
    
    
        int i = 0;
        final List<Object> listUnderlyingObjects =
                EPStatementResult.build(epStatement).listUnderlyingObjects();
        for (final Object object : listUnderlyingObjects) {
            LOGGER.info("[{}] Received {}", i++, object);
        }
        
    }
    
}
