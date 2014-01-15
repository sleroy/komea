package org.komea.providers.sonar;

import com.tocea.product.rest.client.RestClientFactory;
import com.tocea.product.rest.client.api.IProvidersAPI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Metric;
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
        final String komeaUrl = KomeaPlugin.getServerUrl(settings);
        if (komeaUrl == null) {
            return;
        }
        try {
            final ProviderDto providerDto = new ProviderDto();

            final Provider provider = KomeaPlugin.getProvider(server.getURL());
            providerDto.setProvider(provider);

            final List<EventType> eventTypes = new ArrayList<EventType>(KomeaPlugin.EVENT_TYPES);
            final Collection<String> metricKeys = KomeaPlugin.getMetricKeys(settings);
            final Collection<Metric> metrics = metricFinder.findAll(new ArrayList<String>(metricKeys));
            for (final Metric metric : metrics) {
                final EventType eventType = KomeaPlugin.createEventType(metric);
                eventTypes.add(eventType);
            }
            providerDto.setEventTypes(eventTypes);

            final IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI(komeaUrl);
            providersAPI.registerProvider(providerDto);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
