package net.saifs.skycadeapplication;

import net.saifs.skycadeapplication.command.ItemShopCommand;
import net.saifs.skycadeapplication.gui.ItemShopGUI;
import net.saifs.skycadeapplication.item.HelmetOfHadesItem;
import net.saifs.skycadeapplication.utils.TickUpdateHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkycadeApplication extends JavaPlugin {
    public static ItemShopGUI itemShopGUI;
    public static TickUpdateHandler tickUpdateHandler;
    private static SkycadeApplication instance;

    public static SkycadeApplication getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        tickUpdateHandler = new TickUpdateHandler();

        // register items here
        new HelmetOfHadesItem();

        itemShopGUI = new ItemShopGUI();

        getCommand("test").setExecutor(new ItemShopCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
}
