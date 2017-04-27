package de.jkrech.test.axon.cmd;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class UpdatePaybackCommand {

    @TargetAggregateIdentifier
    private String id;

    private String paybackCustomerNumber;

    public UpdatePaybackCommand(String id, String paybackCustomerNumber) {
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
        sb.append("cmdType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", paybackCustomerNumber=").append(paybackCustomerNumber);
        return sb.toString();
    }
}
