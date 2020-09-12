package net.saifs.skycadeapplication.command;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.utils.SAMethods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemShopCommand extends SACommand {
    public ItemShopCommand(String commandName) {
        super(commandName);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            SAMethods.send(sender, "&cYou must be a player to do that!");
            return;
        }
        Player player = (Player) sender;
        player.openInventory(SkycadeApplication.itemShopGUI.getPage(0));
    }

    @Override
    public String getNode() {
        return "sa.application.admin";
    }
}
