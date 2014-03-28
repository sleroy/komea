package org.komea.product.plugins.sample;

import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;

@ProviderPlugin(
        type = ProviderType.OTHER,
        name = "Sample provider plugin",
        icon = "/truc.gif",
        eventTypes = {
            @EventTypeDef(
                    providerType = ProviderType.CI_BUILD,
                    description = "Event to notify a build is started",
                    key = "BUILD_STARTED",
                    name = "Build started",
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO),
            @EventTypeDef(
                    providerType = ProviderType.CI_BUILD,
                    description = "Event to notify a build is finished with success",
                    key = "BUILD_FINISHED",
                    name = "Build finished",
                    entityType = EntityType.PROJECT,
                    severity = Severity.INFO),
            @EventTypeDef(
                    providerType = ProviderType.CI_BUILD,
                    description = "Event to notify a build is finished with failure",
                    key = "BUILD_FAILURE",
                    name = "Build failure",
                    entityType = EntityType.PROJECT,
                    severity = Severity.MAJOR)})
public class SampleProviderBean {

    @EventTypeDef()
    private org.komea.product.database.model.EventType BUILD_FAILURE;

    public SampleProviderBean() {

        super();

    }

    public org.komea.product.database.model.EventType getBUILD_FAILURE() {

        return BUILD_FAILURE;
    }

    public void setBUILD_FAILURE(final org.komea.product.database.model.EventType _bUILD_FAILURE) {

        BUILD_FAILURE = _bUILD_FAILURE;
    }

}
