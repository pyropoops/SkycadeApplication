package net.saifs.skycadeapplication;

import net.saifs.skycadeapplication.command.ItemShopCommand;
import net.saifs.skycadeapplication.command.SACommandHandler;
import net.saifs.skycadeapplication.gui.ItemShopGUI;
import net.saifs.skycadeapplication.item.HelmetOfHadesItem;
import net.saifs.skycadeapplication.item.TrackingBowItem;
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
        new TrackingBowItem();

        itemShopGUI = new ItemShopGUI();

        getCommand("sa").setExecutor(new SACommandHandler());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
}
