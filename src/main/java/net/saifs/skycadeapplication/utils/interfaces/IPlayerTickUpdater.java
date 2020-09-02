package net.saifs.skycadeapplication.utils.interfaces;

import org.bukkit.entity.Player;

public interface IPlayerTickUpdater extends ITickUpdateHandler {
    void update(Player player);
}
