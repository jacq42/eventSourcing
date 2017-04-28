package de.jkrech.test.axon;

import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jkrech.test.axon.event.SubEntityCreatedEvent;
import de.jkrech.test.axon.event.SubEntityUpdatedEvent;

public class SubEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubEntity.class);

    @EntityId(routingKey = "subEntityNumber")
    private String subEntityNumber;

    // -- COMMANDS

    // -- EVENTS

    @EventSourcingHandler
    public void handleSubEntityCreated(SubEntityCreatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.subEntityNumber = event.getSubEntityNumber();
        LOGGER.info("ENTITY: {}", this);
    }

    @EventSourcingHandler
    public void handleSubEntityUpdated(SubEntityUpdatedEvent event) {
        LOGGER.info("Received event: {}", event);
        this.subEntityNumber = event.getSubEntityNumber();
        LOGGER.info("ENTITY: {}", this);
    }

    // --

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SUB ENTIY +++ ");
        sb.append(", subEntityNumber=").append(this.subEntityNumber);
        return sb.toString();
    }

}
