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
import de.jkrech.test.axon.event.PaybackCreatedEvent;
import de.jkrech.test.axon.event.PaybackUpdatedEvent;

@Aggregate
public class Customer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

    @AggregateIdentifier
    private String id;

    private String name;

    @AggregateMember
    private Payback paybackAggregate;

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

    public void createPayback(String paybackCustomerNumber) {
        apply(new PaybackCreatedEvent(this.id, paybackCustomerNumber));
    }

    public void updatePayback(String paybackCustomerNumber) {
        if(this.paybackAggregate != null) {
            apply(new PaybackUpdatedEvent(this.id, paybackCustomerNumber));
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
    public void handlePaybackCreated(PaybackCreatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.paybackAggregate = new Payback();
    }

    // --

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CUSTOMER +++ ");
        sb.append("id=").append(this.id)
            .append(", name=").append(this.name)
            .append(" > ").append(paybackAggregate);
        return sb.toString();
    }
}
