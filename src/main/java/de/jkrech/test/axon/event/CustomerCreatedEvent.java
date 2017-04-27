package de.jkrech.test.axon.event;

public class CustomerCreatedEvent {

    private String id;
    private String name;

    public CustomerCreatedEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("eventType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", name=").append(name);
        return sb.toString();
    }
}
