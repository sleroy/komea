package org.komea.microservices.events.query.rest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/database")
@Transactional
public class DatabaseController {

//	@Autowired
//	private IEventQueryManager	eventQueryService;
    /**
     * Count number of events from a given type.
     *
     * @param className the class name
     * @return the list of events of a given type.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/count/{className}")
    public long countElements(@PathVariable final String className) {
        // FIXME
//		return this.eventQueryService.countEventsOfType(className);
        return 0;
    }

}
