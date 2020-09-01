package net.saifs.skycadeapplication.command;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.utils.SAMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            SAMethods.send(sender, "&cYou must be a player to do that!");
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(SkycadeApplication.itemShopGUI.getPage(0));
        return true;
    }
}
