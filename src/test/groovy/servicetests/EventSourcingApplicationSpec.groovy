package servicetests

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.eventhandling.EventBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

import de.jkrech.test.axon.EventSourcingApplication
import de.jkrech.test.axon.cmd.CreateCustomerCommand
import de.jkrech.test.axon.cmd.CreatePaybackCommand
import de.jkrech.test.axon.cmd.UpdateCustomerCommand
import de.jkrech.test.axon.cmd.UpdatePaybackCommand

import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(classes = EventSourcingApplication, webEnvironment = RANDOM_PORT)
class EventSourcingApplicationSpec extends Specification {

    private static final String ID = "123456"

    @Autowired
    CommandBus commandBus

    @Autowired
    EventBus eventBus

    def setup() {
    }

    def "creating a customer with payback"() {
        when:
        commandBus.dispatch(asCommandMessage(createCustomerCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(updateCustomerCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(createPaybackCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(updatePaybackCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(updateCustomerCommand(ID, "fancy name")), LoggingCallback.INSTANCE)

        then:
        notThrown(Exception)
    }

    CreateCustomerCommand createCustomerCommand(String id = ID, String name = "name") {
        return new CreateCustomerCommand(id, name)
    }

    UpdateCustomerCommand updateCustomerCommand(String id = ID, String name = "another name") {
        return new UpdateCustomerCommand(id, name)
    }

    CreatePaybackCommand createPaybackCommand(String id = ID, String paybackCustomerNumber = "1234567890") {
        return new CreatePaybackCommand(id, paybackCustomerNumber)
    }

    UpdatePaybackCommand updatePaybackCommand(String id = ID, String paybackCustomerNumber = "4567890123") {
        return new UpdatePaybackCommand(id, paybackCustomerNumber)
    }
}
