package net.saifs.skycadeapplication;

import org.bukkit.plugin.java.JavaPlugin;

public final class SkycadeApplication extends JavaPlugin {
    private static SkycadeApplication instance;

    public static SkycadeApplication getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
}
