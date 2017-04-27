package de.jkrech.test.axon.config;

import org.axonframework.config.SagaConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.jkrech.test.axon.Customer;
import de.jkrech.test.axon.CustomerCommandHandler;
import de.jkrech.test.axon.SyncManagementSaga;

@Configuration
public class AxonConfig {

    @Autowired
    private AxonConfiguration axonConfiguration;

    @Autowired
    private EventBus eventBus;

    @Bean
    public CustomerCommandHandler customerCommandHandler() {
        return new CustomerCommandHandler(axonConfiguration.repository(Customer.class), eventBus);
    }

    @Bean
    public SagaConfiguration syncManagementSagaConfiguration() {
        return SagaConfiguration.trackingSagaManager(SyncManagementSaga.class);
    }

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(new InMemoryEventStorageEngine());
    }
}
