package de.jkrech.test.axon;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.jkrech.test.axon.cmd.CreateSubEntityCommand;
import de.jkrech.test.axon.cmd.SyncCommand;
import de.jkrech.test.axon.cmd.UpdateCustomerCommand;
import de.jkrech.test.axon.cmd.UpdateSubEntityCommand;
import de.jkrech.test.axon.event.CustomerNotFoundEvent;

public class CustomerCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCommandHandler.class);

    private Repository<Customer> repository;
    private EventBus eventBus;

    @Autowired
    public CustomerCommandHandler(Repository<Customer> repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @CommandHandler
    public void updateCustomer(UpdateCustomerCommand cmd) {
        LOGGER.info("Received cmd: {}", cmd);
        try {
            Aggregate<Customer> customerAggregate = repository.load(cmd.getId());
            customerAggregate.execute(customer -> customer.updateCustomer(cmd.getName()));
        } catch (AggregateNotFoundException exception) {
            eventBus.publish(asEventMessage(new CustomerNotFoundEvent(cmd.getId())));
        }
    }

    @CommandHandler
    public void createSubEntity(CreateSubEntityCommand cmd) {
        LOGGER.info("Received cmd: {}", cmd);
        try {
            Aggregate<Customer> customerAggregate = repository.load(cmd.getId());
            customerAggregate.execute(customer -> customer.createSubEntity(cmd.getSubEntityNumber()));
        } catch (AggregateNotFoundException exception) {
            eventBus.publish(asEventMessage(new CustomerNotFoundEvent(cmd.getId())));
        }
    }

    @CommandHandler
    public void updateSubEntity(UpdateSubEntityCommand cmd) {
        LOGGER.info("Received cmd: {}", cmd);
        try {
            Aggregate<Customer> customerAggregate = repository.load(cmd.getId());
            customerAggregate.execute(customer -> customer.updateSubEntity(cmd.getSubEntityNumber()));
        } catch (AggregateNotFoundException exception) {
            eventBus.publish(asEventMessage(new CustomerNotFoundEvent(cmd.getId())));
        }
    }

    @CommandHandler
    public void sync(SyncCommand cmd) {
        LOGGER.info("Received cmd: {}", cmd);
        try {
            Aggregate<Customer> customerAggregate = repository.load(cmd.getId());
            customerAggregate.execute(customer -> customer.sync());
        } catch (AggregateNotFoundException exception) {
            eventBus.publish(asEventMessage(new CustomerNotFoundEvent(cmd.getId())));
        }
    }

}
