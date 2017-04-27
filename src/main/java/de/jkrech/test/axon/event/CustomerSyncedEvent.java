package de.jkrech.test.axon.event;

public class CustomerSyncedEvent {

    private String id;

    public CustomerSyncedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("eventType=").append(getClass().getSimpleName())
            .append(", id=").append(id);
        return sb.toString();
    }
}
