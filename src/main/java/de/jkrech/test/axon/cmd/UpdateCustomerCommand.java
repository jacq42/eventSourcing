package de.jkrech.test.axon.cmd;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class UpdateCustomerCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;

    public UpdateCustomerCommand(String id, String name) {
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
        sb.append("cmdType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", name=").append(name);
        return sb.toString();
    }
}
