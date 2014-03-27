package org.komea.providers.sonar;

import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.MetricFinder;
import org.sonar.api.platform.Server;
import org.sonar.api.platform.ServerStartHandler;

public class KomeaServerStartHandler implements ServerStartHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaServerStartHandler.class.getName());
    private final MetricFinder metricFinder;
    private final Settings settings;

    public KomeaServerStartHandler(final Settings settings, final MetricFinder metricFinder) {
        this.metricFinder = metricFinder;
        this.settings = settings;
    }

    @Override
    public void onServerStart(Server server) {
        final String komeaUrl = KomeaPlugin.getKomeaUrl(settings);
        final String serverUrl = KomeaPlugin.getSonarUrl(settings);
        if (komeaUrl == null || !KomeaPlugin.isEnabled(settings)) {
            return;
        }
        final ProviderDto providerDto = new ProviderDto();
        final Provider provider = KomeaPlugin.getProvider(serverUrl);
        providerDto.setProvider(provider);
        providerDto.setEventTypes(KomeaPlugin.EVENT_TYPES);

        KomeaPlugin.registerProvider(komeaUrl, providerDto);
    }

}
