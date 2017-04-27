package de.jkrech.test.axon

import org.axonframework.test.saga.FixtureConfiguration
import org.axonframework.test.saga.SagaTestFixture

import de.jkrech.test.axon.cmd.SyncCommand
import de.jkrech.test.axon.event.CustomerCreatedEvent
import de.jkrech.test.axon.event.CustomerUpdatedEvent

import spock.lang.Specification

class CustomerSagaSpec extends Specification {

    private static final String ID = "123"

    private FixtureConfiguration fixture

    def setup() {
        fixture = new SagaTestFixture<>(SyncManagementSaga.class)
    }

    // -- customer

    def "creating a new customer"() {
        given:
        String name = "create customer"

        expect:
        fixture.givenNoPriorActivity()
               .whenAggregate(ID).publishes(new CustomerCreatedEvent(ID, name))
               .expectActiveSagas(1)
               .expectDispatchedCommands(new SyncCommand(ID))
    }

    def "updating a new customer"() {
        given:
        String name = "update customer"

        expect:
        fixture.givenAggregate(ID).published(new CustomerCreatedEvent(ID, name))
               .whenAggregate(ID).publishes(new CustomerUpdatedEvent(ID, name))
               .expectActiveSagas(1)
               .expectDispatchedCommands(new SyncCommand(ID))
    }
}
