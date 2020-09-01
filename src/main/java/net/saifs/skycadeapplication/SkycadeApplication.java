package net.saifs.skycadeapplication;

import net.saifs.skycadeapplication.command.ItemShopCommand;
import net.saifs.skycadeapplication.gui.ItemShopGUI;
import net.saifs.skycadeapplication.item.Item;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkycadeApplication extends JavaPlugin {
    public static ItemShopGUI itemShopGUI;
    private static SkycadeApplication instance;

    public static SkycadeApplication getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // register items here

        itemShopGUI = new ItemShopGUI();

        getCommand("test").setExecutor(new ItemShopCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
}
