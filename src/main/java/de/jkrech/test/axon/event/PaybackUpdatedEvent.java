package de.jkrech.test.axon.event;

public class PaybackUpdatedEvent {

    private String id;
    private String paybackCustomerNumber;

    public PaybackUpdatedEvent(String id, String paybackCustomerNumber) {
        this.id = id;
        this.paybackCustomerNumber = paybackCustomerNumber;
    }

    public String getId() {
        return id;
    }

    public String getPaybackCustomerNumber() {
        return paybackCustomerNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("eventType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", paybackCustomerNumber=").append(paybackCustomerNumber);
        return sb.toString();
    }
}
