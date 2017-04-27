package de.jkrech.test.axon.event;

public class CustomerNotCreatedEvent {

    private String id;

    public CustomerNotCreatedEvent(String id) {
        this.id = id;
    }
}
