package org.komea.microservices.events.configuration;

import javax.sql.DataSource;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.storage.IEventDBFactory;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.sql.impl.EventDBFactory;
import org.komea.microservices.events.messaging.service.KomeaMessageListenerContainer;
import org.komea.modules.messaging.producer.JmsMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.MessageListenerContainer;

@Configuration
@EnableConfigurationProperties({EventStorageSettings.class, MQSettings.class})
public class SpringBeanFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBeanFactory.class.getName());
    @Autowired
    private DataSource dataSource;

    @Autowired
    private EventStorageSettings settings;

    @Autowired
    private MQSettings mQSettings;

    @Autowired
    private IEventStorage eventStorage;

    @Bean()
    public IEventDBFactory newEventDbFactory() {
        return new EventDBFactory(dataSource, settings.getSerializerType());
    }

    @Bean()
    public IMessageSender newMessageSender() {
        return new JmsMessageSender(mQSettings.getBrokerUrl(), mQSettings.getDestination());
    }

    @Bean()
    public MessageListenerContainer newMessageListenerContainer() {
        return new KomeaMessageListenerContainer(mQSettings, eventStorage);

    }

}
