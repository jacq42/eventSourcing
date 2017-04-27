package de.jkrech.test.axon

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.test.axon.cmd.CreateCustomerCommand
import de.jkrech.test.axon.cmd.UpdateCustomerCommand
import de.jkrech.test.axon.event.CustomerAlreadyExistsEvent
import de.jkrech.test.axon.event.CustomerCreatedEvent
import de.jkrech.test.axon.event.CustomerSyncedEvent
import de.jkrech.test.axon.event.CustomerUpdatedEvent

import spock.lang.Ignore
import spock.lang.Specification

class CustomerSpec extends Specification {

    private static final String ID = "123"

    private FixtureConfiguration<Customer> fixture

    def setup() {
        fixture = new AggregateTestFixture(Customer.class)

        fixture.registerAnnotatedCommandHandler(new CustomerCommandHandler(fixture.getRepository(), fixture.getEventBus()))
    }

    // -- customer

    def "creating a new customer"() {
        expect:
        fixture.givenNoPriorActivity()
            .when(new CreateCustomerCommand(ID, "name", "should be ignored"))
            .expectEvents(new CustomerCreatedEvent(ID, "name"))
    }

    @Ignore
    def "an existing customer"() {
        expect:
        fixture.given(new CustomerCreatedEvent(ID, "existing customer"))
            .when(new CreateCustomerCommand(ID, "another customer"))
            .expectEvents(new CustomerAlreadyExistsEvent(ID))
    }

    def "updating a customer"() {
        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"))
            .when(new UpdateCustomerCommand(ID, "new name"))
            .expectEvents(new CustomerUpdatedEvent(ID, "new name"))
    }
}
