package de.jkrech.test.axon

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration

import de.jkrech.test.axon.cmd.CreateSubEntityCommand
import de.jkrech.test.axon.cmd.UpdateSubEntityCommand
import de.jkrech.test.axon.event.CustomerCreatedEvent
import de.jkrech.test.axon.event.CustomerNotFoundEvent
import de.jkrech.test.axon.event.SubEntityCreatedEvent
import de.jkrech.test.axon.event.SubEntityUpdatedEvent

import spock.lang.Specification

class SubEntitySpec extends Specification {

    private static final String ID = "123"
    private static final String SUB_ENTITY_NUMBER = "456789"

    private FixtureConfiguration<Customer> fixture

    def setup() {
        fixture = new AggregateTestFixture(Customer.class)

        fixture.registerAnnotatedCommandHandler(new CustomerCommandHandler(fixture.getRepository(), fixture.getEventBus()))
    }

    def "creating sub entity"() {
        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"))
            .when(new CreateSubEntityCommand(ID, SUB_ENTITY_NUMBER))
            .expectEvents(new SubEntityCreatedEvent(ID, SUB_ENTITY_NUMBER))
    }

    def "creating sub entity only with existing customer"() {
        expect:
        fixture.givenNoPriorActivity()
            .when(new CreateSubEntityCommand(ID, SUB_ENTITY_NUMBER))
            .expectEvents(new CustomerNotFoundEvent(ID))
    }

    def "updating sub entity"() {
        given:
        String subEntityNumber = "789456"

        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"), new SubEntityCreatedEvent(ID, SUB_ENTITY_NUMBER))
            .when(new UpdateSubEntityCommand(ID, subEntityNumber))
            .expectEvents(new SubEntityUpdatedEvent(ID, subEntityNumber))
    }

    def "updating sub entity without creating"() {
        expect:
        fixture.given(new CustomerCreatedEvent(ID, "name"))
            .when(new UpdateSubEntityCommand(ID, SUB_ENTITY_NUMBER))
            .expectNoEvents()
    }
}
