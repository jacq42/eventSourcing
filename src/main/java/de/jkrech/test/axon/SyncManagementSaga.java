package de.jkrech.test.axon;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.jkrech.test.axon.cmd.SyncCommand;
import de.jkrech.test.axon.event.CustomerCreatedEvent;
import de.jkrech.test.axon.event.CustomerSyncedEvent;
import de.jkrech.test.axon.event.CustomerUpdatedEvent;
import de.jkrech.test.axon.event.PaybackCreatedEvent;
import de.jkrech.test.axon.event.PaybackUpdatedEvent;

@Saga
public class SyncManagementSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncManagementSaga.class);

    private transient CommandBus commandBus;

    @Autowired
    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(CustomerCreatedEvent event) {
        LOGGER.info("SYNC START CREATE CUSTOMER");
        createSyncCommand(event.getId());
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(CustomerUpdatedEvent event) {
        LOGGER.info("SYNC START UPDATE CUSTOMER");
        createSyncCommand(event.getId());
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(PaybackCreatedEvent event) {
        LOGGER.info("SYNC START CREATE PAYBACK");
        createSyncCommand(event.getId());
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(PaybackUpdatedEvent event) {
        LOGGER.info("SYNC START UPDATE PAYBACK");
        createSyncCommand(event.getId());
    }

    private void createSyncCommand(String id) {
        SyncCommand command = new SyncCommand(id);
        commandBus.dispatch(asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "id")
    public void on(CustomerSyncedEvent event) {
        LOGGER.info("SYNC END");
    }
}
