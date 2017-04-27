package de.jkrech.test.axon;

import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jkrech.test.axon.event.PaybackCreatedEvent;
import de.jkrech.test.axon.event.PaybackUpdatedEvent;

public class Payback {

    private static final Logger LOGGER = LoggerFactory.getLogger(Payback.class);

    @EntityId(routingKey = "paybackCustomerNumber")
    private String paybackCustomerNumber;

    // -- COMMANDS

    // -- EVENTS

    @EventSourcingHandler
    public void handlePaybackCreated(PaybackCreatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.paybackCustomerNumber = event.getPaybackCustomerNumber();
        LOGGER.info("ENTITY: {}", this);
    }

    @EventSourcingHandler
    public void handlePaybackUpdated(PaybackUpdatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.paybackCustomerNumber = event.getPaybackCustomerNumber();
        LOGGER.info("ENTITY: {}", this);
    }

    // --

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PAYBACK +++ ");
        sb.append(", paybackCustomerNumber=").append(this.paybackCustomerNumber);
        return sb.toString();
    }

}
