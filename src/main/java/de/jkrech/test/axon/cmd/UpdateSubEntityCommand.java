package de.jkrech.test.axon.cmd;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class UpdateSubEntityCommand {

    @TargetAggregateIdentifier
    private String id;

    private String subEntityNumber;

    public UpdateSubEntityCommand(String id, String subEntityNumber) {
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
        sb.append("cmdType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", subEntityNumber=").append(subEntityNumber);
        return sb.toString();
    }
}
