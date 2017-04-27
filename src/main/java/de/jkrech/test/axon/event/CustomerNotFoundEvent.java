package de.jkrech.test.axon.event;

public class CustomerNotFoundEvent {

    private String id;

    public CustomerNotFoundEvent(String id) {
        this.id = id;
    }
}
