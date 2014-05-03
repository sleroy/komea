
package org.komea.product.cep.tester;



import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matchers;
import org.komea.eventory.CEPConfiguration;
import org.komea.eventory.CEPEngine;
import org.komea.eventory.api.bridge.IEventBridge;
import org.komea.eventory.api.bridge.IEventBridgeFactory;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPConfiguration;
import org.komea.eventory.api.engine.ICEPEngine;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.bridge.MemoryBridge;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.eventory.query.CEPQuery;
import org.komea.product.cep.api.tester.ICEPQueryTestPredicate;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;
import org.komea.product.service.dto.EntityKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;



/**
 */
public class CEPQueryTester
{
    
    
    public static final IEventBridgeFactory  DEFAULT_BRIDGE_FACTORY = new IEventBridgeFactory()
                                                                    {
                                                                        
                                                                        
                                                                        @Override
                                                                        public IEventBridge newBridge(
                                                                                final ICEPConfiguration _arg0) {
                                                                        
                                                                        
                                                                            return new MemoryBridge(
                                                                                    _arg0);
                                                                        }
                                                                    };
    
    public static final ICacheStorageFactory DEFAULT_CACHE_FACTORY  = new ICacheStorageFactory()
                                                                    {
                                                                        
                                                                        
                                                                        @Override
                                                                        public ICacheStorage newCacheStorage(
                                                                                final ICacheConfiguration _arg0) {
                                                                        
                                                                        
                                                                            return new GoogleCacheStorage<Serializable>(
                                                                                    _arg0);
                                                                        }
                                                                    };
    
    static final Logger                      LOGGER                 =
                                                                            LoggerFactory
                                                                                    .getLogger("[CEP Query Test]");
    
    
    
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
            final CEPConfiguration cepConfiguration = new CEPConfiguration();
            
            cepConfiguration.setBridgeFactory(DEFAULT_BRIDGE_FACTORY);
            cepConfiguration.setCacheStorageFactory(DEFAULT_CACHE_FACTORY);
            esperEngineBean.initialize(cepConfiguration);
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
    
    
    
    private ICEPQuery<Serializable, KpiResult> cepQuery;
    
    private boolean                            dumpResult;
    
    
    private final List<Serializable>           events          = new ArrayList<Serializable>();
    
    private int                                expectedRows    = -1;
    
    private Integer                            expectedStorageSize;
    
    private final Map<String, EventType>       mockEventTypes  = new HashMap<String, EventType>();
    private final Map<String, PersonGroup>     mockGroup       = new HashMap<String, PersonGroup>();
    private final Map<String, Person>          mockPerson      = new HashMap<String, Person>();
    
    private final Map<String, Project>         mockProject     = new HashMap<String, Project>();
    
    private final Map<String, Provider>        mockProviders   = new HashMap<String, Provider>();
    
    private ICEPQueryImplementation            queryImplementationDefinition;
    
    private final List<ICEPQueryTestPredicate> queryPredicates =
                                                                       new ArrayList<ICEPQueryTestPredicate>();
    
    private Object                             singleResult;
    
    
    
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
     * Method dumpResult.
     * 
     * @return CEPQueryTester
     */
    public CEPQueryTester dump() {
    
    
        dumpResult = true;
        
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
     * @return
     */
    public CEPQueryTester expectStorageSize(final int _i) {
    
    
        expectedStorageSize = _i;
        return this;
    }
    
    
    /**
     * Method getEvents.
     * 
     * @return List<IEvent>
     */
    public List<IEvent> getEvents() {
    
    
        return (List) events;
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
     * Tests results against an array.
     * 
     * @param _objects
     * @return the query tester
     */
    public CEPQueryTester hasResults(final Object[][] _objects) {
    
    
        queryPredicates.add(new ArrayPredicate(_objects));
        return this;
    }
    
    
    /**
     * Creates a predicate based on the expectation the query will provide a
     * single value.
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
    
    
        for (final Serializable event : events) {
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
        
        if (singleResult != null) {
            final Number result = (Number) ((ICEPQuery) cepQuery).getResult();
            if (!Matchers.equalTo(result).matches(singleResult)) { throw new IllegalArgumentException(
                    "Single result does not match"); }
            
        } else {
            if (dumpResult) {
                dumpInfos();
            }
            
            validPredicates();
        }
        
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
    
    
    /**
     * @param _fakeCommit
     */
    public void sendCustomEvent(final Serializable _fakeCommit) {
    
    
        events.add(_fakeCommit);
        
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
    
    
        for (final ICEPQueryTestPredicate<Serializable> testPred : queryPredicates) {
            testPred.evaluate(cepQuery);
        }
    }
    
    
    public void validPredicates() {
    
    
        if (requiresStorageSizeValidation()) {
            final int size = cepQuery.getStatement().getDefaultStorage().size();
            if (!Matchers.equalTo(Integer.valueOf(size)).matches(expectedStorageSize)) { throw new IllegalArgumentException(
                    "Storage size should match"); }
            
            
        }
        validateQueryPredicates();
        
        
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
            eventType.setProviderType(ProviderType.OTHER);
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
    
    
        final KpiResult result = cepQuery.getResult();
        final Map<EntityKey, Number> asMap = result.getMap();
        LOGGER.info("RESULT : Received a table : {}", result);
        for (final Entry<EntityKey, Number> entry : asMap.entrySet()) {
            LOGGER.info("RESULT : {} = {}", entry.getKey(), entry.getValue());
        }
        
    }
    
    
    private boolean requiresStorageSizeValidation() {
    
    
        return expectedStorageSize != null;
    }
}
