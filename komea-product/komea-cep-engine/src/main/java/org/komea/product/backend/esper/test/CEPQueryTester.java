
package org.komea.product.backend.esper.test;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.komea.product.backend.service.kpi.ICEPQueryLineTestPredicate;
import org.komea.product.backend.service.kpi.ICEPQueryTestPredicate;
import org.komea.product.cep.CEPConfiguration;
import org.komea.product.cep.CEPEngine;
import org.komea.product.cep.api.ICEPEngine;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.ICEPQueryImplementation;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ITupleResultMap;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.query.CEPQuery;
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
public class CEPQueryTester
{
    
    
    /**
     */
    private static class ArrayPredicate implements ICEPQueryTestPredicate
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
         * @see org.komea.product.backend.service.kpi.ICEPQueryTestPredicate#evaluate(EPStatement)
         */
        @Override
        public void evaluate(final ICEPQuery _epStatement) {
        
        
            final List<ITuple> listMapResult = _epStatement.getResult().asMap().asTupleRows();
            Collections.sort(listMapResult);
            LOGGER.debug("Sorted table : {}", listMapResult.toString());
            checkIfisEquals(array.length, listMapResult.size(), "Expected same number of rows");
            
            for (int i = 0; i < listMapResult.size(); ++i) {
                
                LOGGER.debug("Evaluating line {} of esper request", i);
                final ITuple tuple = listMapResult.get(i);
                final List<Object> arrayList = new ArrayList<Object>(tuple.values());
                checkIfisEquals(array[i].length, arrayList.size(),
                        "Comparison number of columns at the line " + i);
                for (int j = 0; j < arrayList.size(); j++) {
                    final Object thisProperty = arrayList.get(j);
                    final Object expectedValue = array[i][j];
                    LOGGER.debug("Array[{}][{}]={} and should be {}", i, j, thisProperty,
                            expectedValue);
                    checkIfisEquals(expectedValue, thisProperty, "Array value");
                    
                }
            }
            
        }
    }
    
    
    
    /**
     */
    private static class SingleLinePredicate implements ICEPQueryLineTestPredicate
    {
        
        
        private final Object expectedValue;
        private final int    number;
        
        
        
        /**
         * @param _columnName
         * @param _expectedValue
         */
        public SingleLinePredicate(final int _number, final Object _expectedValue) {
        
        
            super();
            number = _number;
            
            expectedValue = _expectedValue;
        }
        
        
        /**
         * Method evaluate.
         * 
         * @param _tuple
         *            Map<String,Object>
         * @see org.komea.product.backend.service.kpi.ICEPQueryLineTestPredicate#evaluate(Map<String,Object>)
         */
        @Override
        public void evaluate(final ITuple _tuple) {
        
        
            checkIfisEquals(expectedValue, _tuple.values().get(number), "Expected "
                    + expectedValue + "  for " + number);
            
            
        }
    }
    
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("[CEP Query Test]");
    
    
    
    public static void checkIfisEquals(
            final Object _object,
            final Object _object2,
            final String _string) {
    
    
        if (!_object.equals(_object2)) { throw new IllegalArgumentException(_string
                + ": expected " + _object + " received " + _object2); }
    }
    
    
    /**
     * Converts an event dto into a event.
     * 
     * @param _eventDTO
     *            the event dto
     * @return the event.
     */
    public static IEvent convertToEventDTO(final EventSimpleDto _eventDTO) {
    
    
        final CEPQueryTester esperQueryTester = new CEPQueryTester();
        return esperQueryTester.convertDto(_eventDTO);
        
        
    }
    
    
    /**
     * Method newEngine.
     * 
     * @return ICEPEngine
     */
    public static ICEPEngine newEngine() {
    
    
        final ICEPEngine esperEngineBean = new CEPEngine();
        try {
            esperEngineBean.initialize(new CEPConfiguration());
        } catch (final IOException e) {
            throw new IllegalArgumentException(e);
        }
        return esperEngineBean;
    }
    
    
    /**
     * Method newTest.
     * 
     * @param _testName
     *            String
     * @return CEPQueryTester
     */
    public static CEPQueryTester newTest() {
    
    
        return new CEPQueryTester();
    }
    
    
    
    private ICEPQuery                              cepQuery;
    
    
    private boolean                                dump;
    
    
    private final List<ICEPQueryLineTestPredicate> esperLinePredicates =
                                                                               new ArrayList<ICEPQueryLineTestPredicate>();
    
    
    private final List<ICEPQueryTestPredicate>     esperPredicates     =
                                                                               new ArrayList<ICEPQueryTestPredicate>();
    
    private final List<IEvent>                     events              = new ArrayList<IEvent>();
    
    private int                                    expectedRows        = -1;
    
    
    private Integer                                expectedStorageSize;
    private final Map<String, EventType>           mockEventTypes      =
                                                                               new HashMap<String, EventType>();
    private final Map<String, PersonGroup>         mockGroup           =
                                                                               new HashMap<String, PersonGroup>();
    
    
    private final Map<String, Person>              mockPerson          =
                                                                               new HashMap<String, Person>();
    
    
    private final Map<String, Project>             mockProject         =
                                                                               new HashMap<String, Project>();
    
    
    private final Map<String, Provider>            mockProviders       =
                                                                               new HashMap<String, Provider>();
    
    
    private ICEPQueryImplementation                queryImplementationDefinition;
    
    
    private Object                                 singleResult;
    
    
    
    /**
     * Constructor for CEPQueryTester.
     * 
     * @param _statementName
     *            String
     */
    private CEPQueryTester() {
    
    
        super();
        
        
    }
    
    
    /**
     * Send an event DTO into esper engine.
     * 
     * @param _eventDto
     *            the event dto
     * @return CEPQueryTester
     */
    public IEvent convertDto(final EventSimpleDto _eventDto) {
    
    
        if (_eventDto == null) {
            _eventDto.setDate(new Date());
            
        }
        buildProviderMock(_eventDto);
        final String eventTypeName = buildEventTypeMock(_eventDto);
        
        final Event event = new Event();
        event.setDate(_eventDto.getDate());
        event.setEventType(mockEventTypes.get(eventTypeName));
        event.setProvider(getMockProviders().get(_eventDto.getProvider()));
        event.setMessage(_eventDto.getMessage());
        
        final String user = buildUserMock(_eventDto);
        event.setPerson(mockPerson.get(user));
        
        buildPersonGroupMock(_eventDto);
        final String projectName = _eventDto.getProject();
        event.setPersonGroup(mockGroup.get(projectName));
        buildProjectMock(projectName);
        event.setProject(mockProject.get(projectName));
        event.setValue(_eventDto.getValue());
        event.setUrl(_eventDto.getUrl());
        event.setProperties(_eventDto.getProperties());
        
        
        return event;
    }
    
    
    /**
     * Method dump.
     * 
     * @return CEPQueryTester
     */
    public CEPQueryTester dump() {
    
    
        dump = true;
        
        return this;
    }
    
    
    /**
     * Method expectRows.
     * 
     * @param _numberRows
     *            int
     * @return CEPQueryTester
     */
    public CEPQueryTester expectRows(final int _numberRows) {
    
    
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
     *            ICEPQueryLineTestPredicate
     * @return CEPQueryTester
     */
    public CEPQueryTester hasLineResult(final ICEPQueryLineTestPredicate _testPredicate) {
    
    
        esperLinePredicates.add(_testPredicate);
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
    public CEPQueryTester hasResults(final Object[][] _objects) {
    
    
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
    public CEPQueryTester hasSingleResult(final Object _singleResult) {
    
    
        singleResult = _singleResult;
        return this;
    }
    
    
    /**
     * Method prepareAlerts.
     * 
     * @param esperEngineBean
     *            ICEPEngine
     */
    public void prepareAlerts(final ICEPEngine esperEngineBean) {
    
    
        for (final IEvent event : events) {
            LOGGER.debug("Sending alert : " + event);
            esperEngineBean.pushEvent(event);
        }
    }
    
    
    /**
     * Method prepareQuery.
     * 
     * @param esperEngineBean
     *            ICEPEngine
     */
    public void prepareQuery(final ICEPEngine esperEngineBean) {
    
    
        if (cepQuery == null) {
            cepQuery = new CEPQuery(queryImplementationDefinition);
            esperEngineBean.getQueryAdministration().registerQuery("query-test", cepQuery);
        }
    }
    
    
    public void runTest() {
    
    
        final ICEPEngine newEngine = newEngine();
        runTest(newEngine);
        try {
            newEngine.close();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * Method runTest.
     * 
     * @param _cepEngine
     *            ICEPEngine
     */
    public void runTest(final ICEPEngine _cepEngine) {
    
    
        prepareQuery(_cepEngine);
        prepareAlerts(_cepEngine);
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
     * @return CEPQueryTester
     */
    public CEPQueryTester send(final IEvent _alert1) {
    
    
        events.add(_alert1);
        return this;
    }
    
    
    public CEPQueryTester sendEvent(final EventSimpleDto _eventDTO) {
    
    
        send(convertDto(_eventDTO));
        return this;
    }
    
    
    public CEPQueryTester sendEvent(final EventSimpleDto _eventDTO, final int _numberOfTimes) {
    
    
        for (int i = 0; i < _numberOfTimes; ++i) {
            
            send(convertDto(_eventDTO));
        }
        return this;
    }
    
    
    /**
     * 
     */
    public void validateQueryPredicates() {
    
    
        for (final ICEPQueryTestPredicate testPred : esperPredicates) {
            testPred.evaluate(cepQuery);
        }
    }
    
    
    public void validPredicates() {
    
    
        if (expectedStorageSize != null) {
            final int size = cepQuery.getStatement().getDefaultStorage().size();
            checkIfisEquals(expectedStorageSize, Integer.valueOf(size),
                    "Expected storage size equals to " + expectedStorageSize + " with  " + size);
        }
        if (esperPredicates.isEmpty() && !hasMapPredicates() && singleResult == null) { return; }
        validateQueryPredicates();
        
        if (!cepQuery.getResult().isMap() && hasMapPredicates()) { throw new ClassCastException(
                "We were expecting a map but the query returned a single value"); }
        if (cepQuery.getResult().isMap()) {
            final List<ITuple> tuples = cepQuery.getResult().asMap().asTupleRows();
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
                checkIfisEquals(expectedRows, i, "Expected fixed number of rows");
                
            }
        } else if (cepQuery.getResult().isSingleValue() && singleResult != null) {
            
            checkIfisEquals(singleResult, cepQuery.getResult().asType(),
                    "Expected value from query : ");
            
            
        }
        
        
    }
    
    
    /**
     * Method withQuery.
     * 
     * @param _queryDefinition
     *            String
     * @return CEPQueryTester
     */
    public CEPQueryTester withQuery(final ICEPQueryImplementation _queryDefinition) {
    
    
        queryImplementationDefinition = _queryDefinition;
        return this;
    }
    
    
    private String buildEventTypeMock(final EventSimpleDto _eventDto) {
    
    
        final String eventTypeName = _eventDto.getEventType();
        if (mockEventTypes.get(eventTypeName) == null) {
            final EventType eventType = new EventType();
            eventType.setName(eventTypeName);
            eventType.setEventKey(eventTypeName);
            eventType.setCategory("");
            eventType.setDescription(eventTypeName);
            eventType.setEnabled(true);
            eventType.setSeverity(Severity.INFO);
            eventType.setId(mockEventTypes.size());
            mockEventTypes.put(eventTypeName, eventType);
        }
        return eventTypeName;
    }
    
    
    private void buildPersonGroupMock(final EventSimpleDto _eventDto) {
    
    
        if (!Strings.isNullOrEmpty(_eventDto.getPersonGroup())
                && mockGroup.get(_eventDto.getPersonGroup()) == null) {
            final PersonGroup group = new PersonGroup();
            group.setName(_eventDto.getPersonGroup());
            group.setId(mockGroup.size());
            mockGroup.put(_eventDto.getPersonGroup(), group);
        }
    }
    
    
    private void buildProjectMock(final String projectName) {
    
    
        if (!Strings.isNullOrEmpty(projectName) && mockProject.get(projectName) == null) {
            final Project group = new Project();
            group.setName(projectName);
            group.setId(mockProject.size());
            group.setProjectKey(projectName);
            mockProject.put(projectName, group);
        }
    }
    
    
    private void buildProviderMock(final EventSimpleDto _eventDto) {
    
    
        final String providerName = _eventDto.getProvider();
        if (getMockProviders().get(providerName) == null) {
            final Provider provider = new Provider();
            provider.setName(providerName);
            provider.setProviderType(ProviderType.CI_BUILD);
            provider.setIcon("");
            provider.setUrl("");
            provider.setId(getMockProviders().size());
            getMockProviders().put(providerName, provider);
        }
    }
    
    
    private String buildUserMock(final EventSimpleDto _eventDto) {
    
    
        final String user = _eventDto.getPerson();
        if (!Strings.isNullOrEmpty(user) && mockPerson.get(user) == null) {
            final Person person = new Person();
            person.setLogin(user);
            person.setId(mockPerson.size());
            mockPerson.put(user, person);
        }
        return user;
    }
    
    
    /**
     * 
     */
    private void dumpInfos() {
    
    
        final ICEPResult result = cepQuery.getResult();
        if (result.isSingleValue()) {
            LOGGER.info("RESULT : Received unique value : \n\t{}", result.asType());
        } else if (result.isMap()) {
            final ITupleResultMap<Object> asMap = result.asMap();
            LOGGER.info("RESULT : Received a table : {}", result);
            for (final Entry<ITuple, Object> entry : asMap.getTable().entrySet()) {
                LOGGER.info("RESULT : {} = {}", entry.getKey(), entry.getValue());
            }
        } else {
            LOGGER.info("RESULT : {}", result);
        }
        
        
    }
}
