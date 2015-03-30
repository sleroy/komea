
package org.komea.connectors.bugzilla;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.komea.connectors.bugzilla.proxy.BugzillaPluginException;
import org.komea.connectors.bugzilla.proxy.IBugzillaAPI;
import org.komea.connectors.bugzilla.proxy.impl.BugzillaServerConfiguration;
import org.komea.connectors.bugzilla.schema.BugzillaComponent;
import org.komea.connectors.bugzilla.schema.BugzillaOS;
import org.komea.connectors.bugzilla.schema.BugzillaPlatform;
import org.komea.connectors.bugzilla.schema.BugzillaProduct;
import org.komea.connectors.bugzilla.schema.BugzillaProductVersion;
import org.komea.connectors.bugzilla.schema.impl.BugzillaSchemaBuilder;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.impl.IKomeaEntityFiller;
import org.komea.core.model.impl.OKomeaModelFactory;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
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

public class BugzillaProcessConnector
{
    
    private static final Logger               LOGGER = LoggerFactory.getLogger(BugzillaProcessConnector.class);
    
    private final BugzillaServerConfiguration configuration;
    private final IBugzillaAPI                bugzillaAPI;
    private final BugzillaEntitiesMapper      mapper;
    
    public BugzillaProcessConnector(final IBugzillaAPI bugzillaAPI, final IKomeaGraphStorage storage, final BugzillaServerConfiguration conf) {
    
        super();
        
        this.bugzillaAPI = bugzillaAPI;
        this.mapper = new BugzillaEntitiesMapper(new OKomeaModelFactory(storage));
        this.configuration = conf;
    }
    
    public void process() {
    
        try {
            this.initBugzillaConnection();
       
             // Fill product and versions
            final List<Product> products;
            if (Strings.isNullOrEmpty(this.configuration.getProject())) {
                products = this.bugzillaAPI.getProducts();
            } else {
                products = Collections.singletonList(this.bugzillaAPI.getProductDefinition(this.configuration.getProject()));
            }
            
            // Fill storage
            createEntities(products);
            createRelations(products);
        } catch (final Exception ex) {
            throw new BugzillaPluginException(ex);
        } finally {
            LOGGER.trace("Closing connection");
        }
        
    }
    
    private void initBugzillaConnection() throws ConnectionException, BugzillaException {
    
        this.bugzillaAPI.initConnection(this.configuration.getServerURL());
        if (this.configuration.hasLogin()) {
            this.bugzillaAPI.login(this.configuration.getUser(), this.configuration.getPassword());
        }
    }
    
    private void createEntities(final List<Product> products) throws BugzillaException {
    
        for (final Product product : products) {
            final IKomeaEntity productEntity = this.mapper.getOrCreateProductEntity(product);
            for (final ProductVersion pv : product.getProductVersions()) {
                final IKomeaEntity versionEntity = this.mapper.getOrCreateProductVersion(pv);
                productEntity.add("versions", versionEntity);
            }
            
        }
        
        /**
         * Fill platform fields
         */
        for (final String platform : this.bugzillaAPI.getLegalValues(GetLegalValues.Fields.REP_PLATFORM)) {
            this.mapper.getOrCreatePlatformEntity(platform);
            
        }
        /**
         * Fill os fields.
         */
        for (final String os : this.bugzillaAPI.getLegalValues(GetLegalValues.Fields.OP_SYS)) {
            this.mapper.getOrCreateOperationSystem(os);
            
        }
        
        for (final String component : this.bugzillaAPI.getLegalValues(GetLegalValues.Fields.COMPONENT)) {
            this.mapper.getOrCreateComponentEntity(component);
        }
        
    }
    
    private void createRelations(final List<Product> products) throws BugzillaException {
    
        /**
         * Building links platform/os/components/products
         */
        LOGGER.info("Processing bugs to create network");
        for (final Product product : products) {
            LOGGER.info("Processing product {}", product.getName());
            final IKomeaEntity productEntity = this.mapper.productToEntities.get(product.getName());
            for (final Bug bug : this.bugzillaAPI.getBugList(product.getName())) {
                if (!Strings.isNullOrEmpty(bug.getPlatform())) {
                    productEntity.add("platforms_supported", this.mapper.platformToEntities.get(bug.getPlatform()));
                }
                if (!Strings.isNullOrEmpty(bug.getOperatingSystem())) {
                    productEntity.add("os_supported", this.mapper.osToEntities.get(bug.getOperatingSystem()));
                }
                if (!Strings.isNullOrEmpty(bug.getComponent())) {
                    final IKomeaEntity componentEntity = this.mapper.componentToEntities.get(bug.getComponent());
                    
                    productEntity.add("components", componentEntity);
                    componentEntity.add("owned_by", productEntity);
                }
                
            }
        }
    }
    
    private static class BugzillaEntitiesMapper
    {
        
        private final OKomeaModelFactory        modelFactory;
        private final IEntityType               bugzillaProduct;
        private final IEntityType               bugzillaProductComponent;
        private final IEntityType               bugzillaOS;
        private final IEntityType               bugzillaPlatform;
        private final IEntityType               bugzillaProductVersion;
        
        private final Map<String, IKomeaEntity> productToEntities   = Maps.newHashMap();
        private final Map<String, IKomeaEntity> componentToEntities = Maps.newHashMap();
        private final Map<String, IKomeaEntity> platformToEntities  = Maps.newHashMap();
        private final Map<String, IKomeaEntity> osToEntities        = Maps.newHashMap();
        
        public BugzillaEntitiesMapper(final OKomeaModelFactory modelFactory) {
        
            super();
            this.modelFactory = modelFactory;
            IKomeaSchema schema = modelFactory.getStorageService().getSchema();
            this.bugzillaProduct = schema.findType(BugzillaSchemaBuilder.BUGZILLA_PRODUCT);
            this.bugzillaProductComponent = schema.findType(BugzillaSchemaBuilder.BUGZILLA_COMPONENT);
            this.bugzillaOS = schema.findType(BugzillaSchemaBuilder.BUGZILLA_OPERATION_SYSTEM);
            this.bugzillaPlatform = schema.findType(BugzillaSchemaBuilder.BUGZILLA_PLATFORM);
            this.bugzillaProductVersion = schema.findType(BugzillaSchemaBuilder.BUGZILLA_PRODUCT_VERSION);
        }
        
        private void getOrCreateComponentEntity(final String component) {
        
            final IKomeaEntityFiller<BugzillaComponent> componentFiller = this.modelFactory.newEntityFiller(this.bugzillaProductComponent);
            this.componentToEntities.put(component, componentFiller.put(new BugzillaComponent(component)));
            
        }
        
        private IKomeaEntity getOrCreateOperationSystem(final String os) {
        
            final IKomeaEntityFiller<BugzillaOS> osFiller = this.modelFactory.newEntityFiller(this.bugzillaOS);
            final IKomeaEntity entity = osFiller.put(new BugzillaOS(os));
            this.osToEntities.put(os, entity);
            return entity;
        }
        
        private IKomeaEntity getOrCreatePlatformEntity(final String platform) {
        
            final IKomeaEntityFiller<BugzillaPlatform> platformFiller = this.modelFactory.newEntityFiller(this.bugzillaPlatform);
            final IKomeaEntity entity = platformFiller.put(new BugzillaPlatform(platform));
            this.platformToEntities.put(platform, entity);
            return entity;
        }
        
        private IKomeaEntity getOrCreateProductEntity(final Product product) {
        
            IKomeaEntityFiller<BugzillaProduct> productFiller = this.modelFactory.newEntityFiller(this.bugzillaProduct);
            final IKomeaEntity productEntity = productFiller.put(new BugzillaProduct(product.getID(), product.getName(), product
                    .getDescription()));
            this.productToEntities.put(product.getName(), productEntity);
            return productEntity;
        }
        
        private IKomeaEntity getOrCreateProductVersion(final ProductVersion pv) {
        
            final IKomeaEntityFiller<BugzillaProductVersion> versionFiller = this.modelFactory.newEntityFiller(this.bugzillaProductVersion);
            final IKomeaEntity versionEntity = versionFiller.put(new BugzillaProductVersion(pv.getID(), pv.getName()));
            return versionEntity;
        }
        
    }
}
