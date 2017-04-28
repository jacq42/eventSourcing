package de.jkrech.test.axon;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jkrech.test.axon.cmd.CreateCustomerCommand;
import de.jkrech.test.axon.event.CustomerCreatedEvent;
import de.jkrech.test.axon.event.CustomerSyncedEvent;
import de.jkrech.test.axon.event.CustomerUpdatedEvent;
import de.jkrech.test.axon.event.SubEntityCreatedEvent;
import de.jkrech.test.axon.event.SubEntityUpdatedEvent;

@Aggregate
public class Customer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

    @AggregateIdentifier
    private String id;

    private String name;

    @AggregateMember
    private SubEntity subEntity;

    Customer() {
    }

    // -- COMMANDS

    @CommandHandler
    public Customer(CreateCustomerCommand cmd) {
        LOGGER.info("Received cmd: {}", cmd);
        if(this.id == null) {
            apply(new CustomerCreatedEvent(cmd.getId(), cmd.getName()));
        }
    }

    public void updateCustomer(String name) {
        apply(new CustomerUpdatedEvent(id, name));
    }

    public void createSubEntity(String subEntityNumber) {
        apply(new SubEntityCreatedEvent(this.id, subEntityNumber));
    }

    public void updateSubEntity(String subEntityNumber) {
        if(this.subEntity != null) {
            apply(new SubEntityUpdatedEvent(this.id, subEntityNumber));
        }
    }

    public void sync() {
        LOGGER.info("SYNC +++ {}", this);
        apply(new CustomerSyncedEvent(this.id));
    }

    // -- EVENTS

    @EventSourcingHandler
    public void handleCustomerCreated(CustomerCreatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.id = event.getId();
        this.name = event.getName();
        LOGGER.info("AGGREGATE: {}", this);
    }

    @EventSourcingHandler
    public void handleCustomerUpdated(CustomerUpdatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.name = event.getName();
        LOGGER.info("AGGREGATE: {}", this);
    }

    @EventSourcingHandler
    public void handleSubEntityCreated(SubEntityCreatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.subEntity = new SubEntity();
    }

    // --

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CUSTOMER +++ ");
        sb.append("id=").append(this.id)
            .append(", name=").append(this.name)
            .append(" > ").append(subEntity);
        return sb.toString();
    }
}
