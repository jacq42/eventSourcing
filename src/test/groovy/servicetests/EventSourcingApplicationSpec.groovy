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
import de.jkrech.test.axon.cmd.CreateSubEntityCommand
import de.jkrech.test.axon.cmd.UpdateCustomerCommand
import de.jkrech.test.axon.cmd.UpdateSubEntityCommand

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

    def "creating a customer with a sub entity"() {
        when:
        commandBus.dispatch(asCommandMessage(createCustomerCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(updateCustomerCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(createSubEntityCommand()), LoggingCallback.INSTANCE)
        commandBus.dispatch(asCommandMessage(updateSubEntityCommand()), LoggingCallback.INSTANCE)
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

    CreateSubEntityCommand createSubEntityCommand(String id = ID, String subEntityNumber = "1234567890") {
        return new CreateSubEntityCommand(id, subEntityNumber)
    }

    UpdateSubEntityCommand updateSubEntityCommand(String id = ID, String subEntityNumber = "4567890123") {
        return new UpdateSubEntityCommand(id, subEntityNumber)
    }
}
