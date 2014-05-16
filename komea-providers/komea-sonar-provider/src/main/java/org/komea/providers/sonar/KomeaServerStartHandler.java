package org.komea.providers.sonar;

import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.platform.Server;
import org.sonar.api.platform.ServerStartHandler;

public class KomeaServerStartHandler implements ServerStartHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaServerStartHandler.class.getName());
    private final Settings settings;

    public KomeaServerStartHandler(final Settings settings) {
        this.settings = settings;
    }

    @Override
    public void onServerStart(Server server) {
        try {
            final String komeaUrl = KomeaPlugin.getKomeaUrl(settings);
            LOGGER.debug("onServerStart - Komea server url : {0}", komeaUrl);
            if (komeaUrl == null || !KomeaPlugin.isEnabled(settings)) {
                return;
            }
            final ProviderDto providerDto = new ProviderDto();
            final String serverUrl = KomeaPlugin.getSonarUrl(settings);
            final Provider provider = KomeaPlugin.getProvider(serverUrl);
            providerDto.setProvider(provider);
            providerDto.setEventTypes(KomeaPlugin.EVENT_TYPES);

            KomeaPlugin.registerProvider(komeaUrl, providerDto);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

}
