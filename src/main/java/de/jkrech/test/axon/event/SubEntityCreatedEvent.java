package de.jkrech.test.axon.event;

public class SubEntityCreatedEvent {

    private String id;
    private String subEntityNumber;

    public SubEntityCreatedEvent(String id, String subEntityNumber) {
        this.id = id;
        this.subEntityNumber = subEntityNumber;
    }

    public String getId() {
        return id;
    }

    public String getSubEntityNumber() {
        return subEntityNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("eventType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", subEntityNumber=").append(subEntityNumber);
        return sb.toString();
    }
}
