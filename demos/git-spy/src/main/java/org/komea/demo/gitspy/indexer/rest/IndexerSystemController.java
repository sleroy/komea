/**
 *
 */
package org.komea.demo.gitspy.indexer.rest;

import org.komea.demo.gitspy.indexer.api.IIndexerSystemFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class defines the controller to manipulate the indexer system.
 * @author sleroy
 *
 */
@RestController
@RequestMapping(value = "/repositories")
public class IndexerSystemController {

	@Autowired
	private IIndexerSystemFacade indexerSystem;

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexerSystemController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/sync")
	public void syncRepositories() {
		LOGGER.info("Synchronization of repositories");
		this.indexerSystem.requestSynchronization();
	}
}
