package de.jkrech.test.axon

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.test.axon.cmd.CreatePaybackCommand
import de.jkrech.test.axon.cmd.UpdatePaybackCommand
import de.jkrech.test.axon.event.CustomerCreatedEvent
import de.jkrech.test.axon.event.CustomerNotFoundEvent
import de.jkrech.test.axon.event.PaybackCreatedEvent
import de.jkrech.test.axon.event.PaybackUpdatedEvent

import spock.lang.Specification

class PaybackSpec extends Specification {

    private static final String ID = "123"
    private static final String PAYBACK_CUSTOMER_NUMBER = "456789"

    private FixtureConfiguration<Customer> fixture

    def setup() {
        fixture = new AggregateTestFixture(Customer.class)

        fixture.registerAnnotatedCommandHandler(new CustomerCommandHandler(fixture.getRepository(), fixture.getEventBus()))
    }

    // -- payback

    def "creating payback"() {
        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"))
            .when(new CreatePaybackCommand(ID, PAYBACK_CUSTOMER_NUMBER))
            .expectEvents(new PaybackCreatedEvent(ID, PAYBACK_CUSTOMER_NUMBER))
    }

    def "creating payback only with existing customer"() {
        expect:
        fixture.givenNoPriorActivity()
            .when(new CreatePaybackCommand(ID, PAYBACK_CUSTOMER_NUMBER))
            .expectEvents(new CustomerNotFoundEvent(ID))
    }

    def "updating payback"() {
        given:
        String paybackCustomerNumber = "789456"

        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"), new PaybackCreatedEvent(ID, PAYBACK_CUSTOMER_NUMBER))
            .when(new UpdatePaybackCommand(ID, paybackCustomerNumber))
            .expectEvents(new PaybackUpdatedEvent(ID, paybackCustomerNumber))
    }

    def "updating payback without creating"() {
        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"))
            .when(new UpdatePaybackCommand(ID, PAYBACK_CUSTOMER_NUMBER))
            .expectNoEvents()
    }
}
