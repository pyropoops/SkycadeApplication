package net.saifs.skycadeapplication.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SAMethods {
    public static void send(CommandSender sender, String s) {
        sender.sendMessage(colour(s));
    }

    public static String colour(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
