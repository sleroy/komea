
package org.komea.product.backend.esper.test;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.junit.Assert;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.esper.EventEngineService;
import org.komea.product.backend.service.kpi.IEsperLineTestPredicate;
import org.komea.product.backend.service.kpi.IEsperTestPredicate;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.IQueryDefinition;
import org.komea.product.cep.api.formula.tuple.ITuple;
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

import com.google.common.base.Strings;



/**
 */
public class EsperQueryTester
{
    
    
    /**
     */
    private static class ArrayPredicate implements IEsperTestPredicate
    {
        
        
        private final Object[][] array;
        
        
        
        /**
         * @param _columnName
         * @param _expectedValue
         */
        public ArrayPredicate(final Object[][] _array) {
        
        
            super();
            array = _array;
            
        }
        
        
        /**
         * Method evaluate.
         * 
         * @param _epStatement
         *            EPStatement
         * @see org.komea.product.backend.service.kpi.IEsperTestPredicate#evaluate(EPStatement)
         */
        @Override
        public void evaluate(final ICEPQuery _epStatement) {
        
        
            final List<ITuple> listMapResult = _epStatement.getResult().asMap().asTupleRows();
            LOGGER.debug(listMapResult.toString());
            Assert.assertEquals("Expected same number of rows", array.length, listMapResult.size());
            for (int i = 0; i < listMapResult.size(); ++i) {
                
                LOGGER.debug("Evaluating line {} of esper request", i);
                final ITuple tuple = listMapResult.get(i);
                final List<Object> arrayList = new ArrayList<Object>(tuple.values());
                Assert.assertEquals("Expected same number of cols for iteration " + i,
                        array[i].length, arrayList.size());
                for (int j = 0; j < arrayList.size(); j++) {
                    final Object thisProperty = arrayList.get(j);
                    final Object expectedValue = array[i][j];
                    LOGGER.debug("Array[{}][{}]={} and should be {}", i, j, thisProperty,
                            expectedValue);
                    Assert.assertEquals(expectedValue, thisProperty);
                    
                }
            }
            
        }
    }
    
    
    
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
        public void evaluate(final ITuple _bean) {
        
        
            for (final Entry<String, Object> entry : objects.entrySet()) {
                Assert.assertEquals("Expected " + entry.getValue() + "  for " + entry.getKey(),
                        entry.getValue(), _bean.get(entry.getKey()));
            }
            
            
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
        public void evaluate(final ITuple _bean) {
        
        
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
    public static IEvent convertToEventDTO(final EventSimpleDto _eventDTO) {
    
    
        final EsperQueryTester esperQueryTester = new EsperQueryTester();
        return esperQueryTester.convertDto(_eventDTO);
        
        
    }
    
    
    /**
     * Method newEngine.
     * 
     * @return EventEngineService
     */
    public static EventEngineService newEngine() {
    
    
        final EventEngineService esperEngineBean = new EventEngineService();
        esperEngineBean.init();
        return esperEngineBean;
    }
    
    
    /**
     * Method newTest.
     * 
     * @param _testName
     *            String
     * @return EsperQueryTester
     */
    public static EsperQueryTester newTest() {
    
    
        return new EsperQueryTester();
    }
    
    
    
    private boolean                             dump;
    
    
    private ICEPQuery                           epStatement;
    
    
    private final List<IEsperLineTestPredicate> esperLinePredicates =
                                                                            new ArrayList<IEsperLineTestPredicate>();
    
    
    private final List<IEsperTestPredicate>     esperPredicates     =
                                                                            new ArrayList<IEsperTestPredicate>();
    
    
    private IQueryDefinition                    esperQuery;
    
    private final List<IEvent>                  events              = new ArrayList<IEvent>();
    
    private int                                 expectedRows        = -1;
    
    
    private Integer                             expectedStorageSize;
    private final Map<String, EventType>        mockEventTypes      =
                                                                            new HashMap<String, EventType>();
    private final Map<String, PersonGroup>      mockGroup           =
                                                                            new HashMap<String, PersonGroup>();
    
    
    private final Map<String, Person>           mockPerson          = new HashMap<String, Person>();
    
    
    private final Map<String, Project>          mockProject         =
                                                                            new HashMap<String, Project>();
    
    
    private final Map<String, Provider>         mockProviders       =
                                                                            new HashMap<String, Provider>();
    
    
    private Object                              singleResult;
    
    
    
    /**
     * Constructor for EsperQueryTester.
     * 
     * @param _statementName
     *            String
     */
    private EsperQueryTester() {
    
    
        super();
        
        
    }
    
    
    /**
     * Send an event DTO into esper engine.
     * 
     * @param _eventDto
     *            the event dto
     * @return EsperQueryTester
     */
    public IEvent convertDto(final EventSimpleDto _eventDto) {
    
    
        if (_eventDto == null) {
            _eventDto.setDate(new Date());
            
        }
        final String providerName = _eventDto.getProvider();
        if (getMockProviders().get(providerName) == null) {
            final Provider provider = new Provider();
            provider.setName(providerName);
            provider.setProviderType(ProviderType.CI_BUILD);
            provider.setIcon("");
            provider.setUrl("");
            provider.setId(providerName.hashCode());
            getMockProviders().put(providerName, provider);
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
            eventType.setId(eventTypeName.hashCode());
            mockEventTypes.put(eventTypeName, eventType);
        }
        
        final Event event = new Event();
        event.setDate(_eventDto.getDate());
        event.setEventType(mockEventTypes.get(eventTypeName));
        event.setProvider(getMockProviders().get(_eventDto.getProvider()));
        event.setMessage(_eventDto.getMessage());
        
        final String user = _eventDto.getPerson();
        if (!Strings.isNullOrEmpty(user) && mockPerson.get(user) == null) {
            final Person person = new Person();
            person.setLogin(user);
            person.setId(user.hashCode());
            mockPerson.put(user, person);
        }
        event.setPerson(mockPerson.get(user));
        
        if (!Strings.isNullOrEmpty(_eventDto.getPersonGroup())
                && mockGroup.get(_eventDto.getPersonGroup()) == null) {
            final PersonGroup group = new PersonGroup();
            group.setName(_eventDto.getPersonGroup());
            group.setId(group.getName().hashCode());
            mockGroup.put(_eventDto.getPersonGroup(), group);
        }
        final String projectName = _eventDto.getProject();
        event.setPersonGroup(mockGroup.get(projectName));
        if (!Strings.isNullOrEmpty(projectName) && mockProject.get(projectName) == null) {
            final Project group = new Project();
            group.setName(projectName);
            group.setId(projectName.hashCode());
            group.setProjectKey(projectName);
            mockProject.put(projectName, group);
        }
        event.setProject(mockProject.get(projectName));
        event.setValue(_eventDto.getValue());
        event.setUrl(_eventDto.getUrl());
        event.setProperties(_eventDto.getProperties());
        
        
        return event;
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
     * @param _i
     */
    public void expectStorageSize(final int _i) {
    
    
        expectedStorageSize = _i;
        
    }
    
    
    /**
     * Method getEvents.
     * 
     * @return List<IEvent>
     */
    public List<IEvent> getEvents() {
    
    
        return events;
    }
    
    
    public Map<String, EventType> getMockEventTypes() {
    
    
        return mockEventTypes;
    }
    
    
    public Map<String, PersonGroup> getMockGroup() {
    
    
        return mockGroup;
    }
    
    
    public Map<String, Person> getMockPerson() {
    
    
        return mockPerson;
    }
    
    
    public Map<String, Project> getMockProject() {
    
    
        return mockProject;
    }
    
    
    /**
     * @param _string
     * @return
     */
    public Project getMockProject(final String _string) {
    
    
        return mockProject.get(_string);
    }
    
    
    public Map<String, Provider> getMockProviders() {
    
    
        return mockProviders;
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
    
    
    public boolean hasMapPredicates() {
    
    
        return !esperLinePredicates.isEmpty() || expectedRows != -1;
    }
    
    
    /**
     * Tests results against an array.
     * 
     * @param _objects
     * @return the query tester
     */
    public EsperQueryTester hasResults(final Object[][] _objects) {
    
    
        esperPredicates.add(new ArrayPredicate(_objects));
        return this;
    }
    
    
    /**
     * Creates a predicate based on the expectation the query will provide a single value.
     * 
     * @param _singleResult
     *            the single result.
     * @return
     */
    public EsperQueryTester hasSingleResult(final Object _singleResult) {
    
    
        singleResult = _singleResult;
        return this;
    }
    
    
    /**
     * Method prepareAlerts.
     * 
     * @param esperEngineBean
     *            IEventEngineService
     */
    public void prepareAlerts(final IEventEngineService esperEngineBean) {
    
    
        for (final IEvent event : events) {
            LOGGER.debug("Sending alert : " + event);
            esperEngineBean.sendEvent(event);
        }
    }
    
    
    /**
     * Method prepareQuery.
     * 
     * @param esperEngineBean
     *            IEventEngineService
     */
    public void prepareQuery(final IEventEngineService esperEngineBean) {
    
    
        if (epStatement == null) {
            epStatement = esperEngineBean.createQuery(esperQuery);
        }
    }
    
    
    public void runTest() {
    
    
        final EventEngineService newEngine = newEngine();
        runTest(newEngine);
        newEngine.destroy();
    }
    
    
    /**
     * Method runTest.
     * 
     * @param esperEngineBean
     *            IEventEngineService
     */
    public void runTest(final IEventEngineService esperEngineBean) {
    
    
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
    
    
    public EsperQueryTester sendEvent(final EventSimpleDto _eventDTO) {
    
    
        send(convertDto(_eventDTO), 1);
        return this;
    }
    
    
    public EsperQueryTester sendEvent(final EventSimpleDto _eventDTO, final int _numberOfTimes) {
    
    
        send(convertDto(_eventDTO), _numberOfTimes);
        return this;
    }
    
    
    /**
     * 
     */
    public void validateQueryPredicates() {
    
    
        for (final IEsperTestPredicate testPred : esperPredicates) {
            testPred.evaluate(epStatement);
        }
    }
    
    
    public void validPredicates() {
    
    
        if (expectedStorageSize != null) {
            Assert.assertEquals("Expected storage size", expectedStorageSize,
                    Integer.valueOf(epStatement.getStatement().getDefaultStorage().size()));
        }
        if (esperPredicates.isEmpty() && !hasMapPredicates() && singleResult == null) { return; }
        validateQueryPredicates();
        
        if (!epStatement.getResult().isMap() && hasMapPredicates()) { throw new ClassCastException(
                "We were expecting a map but the query returned a single value"); }
        if (epStatement.getResult().isMap()) {
            final List<ITuple> tuples = epStatement.getResult().asMap().asTupleRows();
            Validate.notNull(tuples);
            int i = 0;
            for (final ITuple value : tuples) {
                
                LOGGER.debug("Testing line {} -> {}", i, value);
                if (i < esperLinePredicates.size()) {
                    esperLinePredicates.get(i).evaluate(value);
                }
                i++;
                
            }
            if (expectedRows != -1) {
                Assert.assertEquals("Expected fixed number of rows", expectedRows, i);
            }
        } else if (epStatement.getResult().isSingleValue() && singleResult != null) {
            Assert.assertEquals("Expected value from query : ", singleResult, epStatement
                    .getResult().asType());
            
        }
        
        
    }
    
    
    /**
     * Method withQuery.
     * 
     * @param _queryDefinition
     *            String
     * @return EsperQueryTester
     */
    public EsperQueryTester withQuery(final IQueryDefinition _queryDefinition) {
    
    
        esperQuery = _queryDefinition;
        return this;
    }
    
    
    /**
     * 
     */
    private void dumpInfos() {
    
    
        LOGGER.info("Received  : \n\t{}", epStatement.getResult().toString());
        
    }
    
}
