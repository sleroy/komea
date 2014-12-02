package org.komea.connectors.bugzilla.schema.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.connectors.bugzilla.events.IBugzillaConnectorInformations;
import org.komea.connectors.bugzilla.proxy.BugzillaPluginException;
import org.komea.connectors.bugzilla.proxy.IBugzillaAPI;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaAPI;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration;
import org.komea.connectors.bugzilla.schema.BugzillaComponent;
import org.komea.connectors.bugzilla.schema.BugzillaOS;
import org.komea.connectors.bugzilla.schema.BugzillaPlatform;
import org.komea.connectors.bugzilla.schema.BugzillaProduct;
import org.komea.connectors.bugzilla.schema.BugzillaProductVersion;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.IKomeaEntityFiller;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.orientdb.session.IGraphSessionFactory;
import org.komea.software.model.impl.MinimalCompanySchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;
import com.j2bugzilla.base.ConnectionException;
import com.j2bugzilla.base.Product;
import com.j2bugzilla.base.ProductVersion;
import com.j2bugzilla.rpc.GetLegalValues;

/**
 * This class implements a connector for bugzilla that stores into the org.
 * database the list of products, versions, components and supported platform
 * and OS.
 *
 *
 * @author sleroy
 *
 */
public class BugzillaSchemaConnector implements IBugzillaConnectorInformations {

	private static final Logger	              LOGGER	          = LoggerFactory
			.getLogger(BugzillaSchemaConnector.class);

	private final IGraphSessionFactory	      graphStorage;
	private final IBugzillaAPI	              bugzillaAPI;
	private final BugzillaServerConfiguration	configuration;

	private final Map<String, IKomeaEntity>	  productToEntities	  = Maps.newHashMap();
	private final Map<String, IKomeaEntity>	  componentToEntities	= Maps.newHashMap();
	private final Map<String, IKomeaEntity>	  platformToEntities	= Maps.newHashMap();
	private final Map<String, IKomeaEntity>	  osToEntities	      = Maps.newHashMap();

	public BugzillaSchemaConnector(final IBugzillaAPI _bugzillaAPI, final IGraphSessionFactory _graphStorage,
			final BugzillaServerConfiguration _configuration) {
		this.graphStorage = _graphStorage;
		this.bugzillaAPI = _bugzillaAPI;
		this.configuration = _configuration;

	}

	public BugzillaSchemaConnector(final IGraphSessionFactory _graphStorage,
			final BugzillaServerConfiguration _configuration) {
		this.graphStorage = _graphStorage;
		this.bugzillaAPI = new BugzillaAPI();
		this.configuration = _configuration;

	}

	/**
	 * Example :
	 * <ul>
	 * <li>https://issues.apache.org/bugzilla/</li>
	 * <li>https://bugs.eclipse.org/bugs/</li>
	 */
	public void updateSchema() {
		try {
			this.createSchemaAndEntities();
		} catch (final Exception ex) {
			throw new BugzillaPluginException(ex);
		} finally {
			LOGGER.trace("Closing connection");
		}

	}

	private void createSchemaAndEntities() throws Exception {
		final BugzillaSchemaBuilder bugzillaSchemaBuilder = new BugzillaSchemaBuilder(new MinimalCompanySchema());
		final OKomeaModelFactory modelFactory = new OKomeaModelFactory(bugzillaSchemaBuilder.getSchema(),
				this.graphStorage);
		this.initBugzillaConnection();

		/**
		 * Fill product and versions
		 */
		final List<Product> products;
		if (Strings.isNullOrEmpty(this.configuration.getProject())) {
			products = this.bugzillaAPI.getProducts();
		} else {
			products = Collections
					.singletonList(this.bugzillaAPI.getProductDefinition(this.configuration.getProject()));
		}

		for (final Product product : products) {
			final IKomeaEntityFiller<BugzillaProduct> productFiller = modelFactory
					.newEntityFiller(bugzillaSchemaBuilder.getBugzillaProduct());
			final IKomeaEntity productEntity = this.getOrCreateProductEntity(product, productFiller);
			for (final ProductVersion pv : product.getProductVersions()) {
				final IKomeaEntity versionEntity = this.getOrCreateProductVersion(bugzillaSchemaBuilder, modelFactory,
						pv);
				productEntity.add("versions", versionEntity);
			}

		}

		/**
		 * Fill platform fields
		 */
		for (final String platform : this.bugzillaAPI.getLegalValues(GetLegalValues.Fields.REP_PLATFORM)) {
			this.getOrCreatePlatformEntity(bugzillaSchemaBuilder, modelFactory, platform);

		}
		/**
		 * Fill os fields.
		 */
		for (final String os : this.bugzillaAPI.getLegalValues(GetLegalValues.Fields.OP_SYS)) {
			this.getOrCreateOperationSystem(bugzillaSchemaBuilder, modelFactory, os);

		}

		for (final String component : this.bugzillaAPI.getLegalValues(GetLegalValues.Fields.COMPONENT)) {
			this.getOrCreateComponentEntity(bugzillaSchemaBuilder, modelFactory, component);
		}

		/**
		 * Building links platform/os/components/products
		 */
		LOGGER.info("Processing bugs to create network");
		for (final Product product : products) {
			LOGGER.info("Processing product {}", product.getName());
			final IKomeaEntity productEntity = this.productToEntities.get(product.getName());
			for (final Bug bug : this.bugzillaAPI.getBugList(product.getName())) {
				if (!Strings.isNullOrEmpty(bug.getPlatform())) {
					productEntity.add("platforms_supported", this.platformToEntities.get(bug.getPlatform()));
				}
				if (!Strings.isNullOrEmpty(bug.getOperatingSystem())) {
					productEntity.add("os_supported", this.osToEntities.get(bug.getOperatingSystem()));
				}
				if (!Strings.isNullOrEmpty(bug.getComponent())) {
					final IKomeaEntity componentEntity = this.componentToEntities.get(bug.getComponent());

					productEntity.add("components", componentEntity);
					componentEntity.add("owned_by", productEntity);
				}

			}
		}
	}

	private void getOrCreateComponentEntity(final BugzillaSchemaBuilder bugzillaSchemaBuilder,
			final OKomeaModelFactory modelFactory, final String component) {
		final IKomeaEntityFiller<BugzillaComponent> componentFiller = modelFactory
		        .newEntityFiller(bugzillaSchemaBuilder.getBugzillaProductComponent());
		this.componentToEntities.put(component, componentFiller.put(new BugzillaComponent(component)));

	}

	private IKomeaEntity getOrCreateOperationSystem(final BugzillaSchemaBuilder bugzillaSchemaBuilder,
			final OKomeaModelFactory modelFactory, final String os) {
		final IKomeaEntityFiller<BugzillaOS> osFiller = modelFactory.newEntityFiller(bugzillaSchemaBuilder
				.getBugzillaOS());
		final IKomeaEntity entity = osFiller.put(new BugzillaOS(os));
		this.osToEntities.put(os, entity);
		return entity;
	}

	private IKomeaEntity getOrCreatePlatformEntity(final BugzillaSchemaBuilder bugzillaSchemaBuilder,
			final OKomeaModelFactory modelFactory, final String platform) {
		final IKomeaEntityFiller<BugzillaPlatform> platformFiller = modelFactory.newEntityFiller(bugzillaSchemaBuilder
				.getBugzillaPlatform());
		final IKomeaEntity entity = platformFiller.put(new BugzillaPlatform(platform));
		this.platformToEntities.put(platform, entity);
		return entity;
	}

	private IKomeaEntity getOrCreateProductEntity(final Product product,
			final IKomeaEntityFiller<BugzillaProduct> productFiller) {
		final IKomeaEntity productEntity = productFiller.put(new BugzillaProduct(product.getID(), product.getName(),
				product.getDescription()));
		this.productToEntities.put(product.getName(), productEntity);
		return productEntity;
	}

	private IKomeaEntity getOrCreateProductVersion(final BugzillaSchemaBuilder bugzillaSchemaBuilder,
			final OKomeaModelFactory modelFactory, final ProductVersion pv) {
		final IKomeaEntityFiller<BugzillaProductVersion> versionFiller = modelFactory
				.newEntityFiller(bugzillaSchemaBuilder.getBugzillaProductVersion());
		final IKomeaEntity versionEntity = versionFiller.put(new BugzillaProductVersion(pv.getID(), pv.getName()));
		return versionEntity;
	}

	private void initBugzillaConnection() throws ConnectionException, BugzillaException {
		this.bugzillaAPI.initConnection(this.configuration.getServerURL());
		if (this.configuration.hasLogin()) {
			this.bugzillaAPI.login(this.configuration.getUser(), this.configuration.getPassword());
		}
	}
}
