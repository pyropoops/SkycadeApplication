package net.saifs.skycadeapplication.command;

import org.bukkit.command.CommandSender;

public abstract class SACommand {
    private String commandName;

    public SACommand(String commandName) {
        this.commandName = commandName;
        SACommandHandler.commands.put(commandName, this);
    }

    // override in CMSCommand child to use permissions
    public String getNode() {
        return null;
    }

    public abstract void onCommand(CommandSender sender, String[] args);
}
