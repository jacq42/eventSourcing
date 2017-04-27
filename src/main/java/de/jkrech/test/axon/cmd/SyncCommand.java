package de.jkrech.test.axon.cmd;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class SyncCommand {

    @TargetAggregateIdentifier
    private String id;

    public SyncCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("cmdType=").append(getClass().getSimpleName())
            .append(", id=").append(id);
        return sb.toString();
    }
}
