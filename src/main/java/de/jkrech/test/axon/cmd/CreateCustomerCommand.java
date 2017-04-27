package de.jkrech.test.axon.cmd;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CreateCustomerCommand {

    @TargetAggregateIdentifier
    private String id;

    private String name;
    private String shouldBeIgnored;

    public CreateCustomerCommand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CreateCustomerCommand(String id, String name, String shouldBeIgnored) {
        this.id = id;
        this.name = name;
        this.shouldBeIgnored = shouldBeIgnored;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShouldBeIgnored() {
        return shouldBeIgnored;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("cmdType=").append(getClass().getSimpleName())
            .append(", id=").append(id)
            .append(", name=").append(name)
            .append(", shouldBeIgnored=").append(shouldBeIgnored);
        return sb.toString();
    }
}
