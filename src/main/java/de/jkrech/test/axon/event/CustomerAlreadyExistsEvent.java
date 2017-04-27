package de.jkrech.test.axon.event;

public class CustomerAlreadyExistsEvent {

    private String id;

    public CustomerAlreadyExistsEvent(String id) {
        this.id = id;
    }
}
