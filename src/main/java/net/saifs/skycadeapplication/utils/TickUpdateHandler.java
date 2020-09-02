package net.saifs.skycadeapplication.utils;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.utils.interfaces.IPlayerTickUpdater;
import net.saifs.skycadeapplication.utils.interfaces.IServerTickUpdater;
import net.saifs.skycadeapplication.utils.interfaces.ITickUpdateHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TickUpdateHandler extends BukkitRunnable {
    List<ITickUpdateHandler> tickUpdateHandlers;

    public TickUpdateHandler() {
        tickUpdateHandlers = new ArrayList<>();
        SkycadeApplication plugin = SkycadeApplication.getInstance();
        runTaskTimerAsynchronously(plugin, 0L, 0L);
    }

    public void register(ITickUpdateHandler tickUpdateHandler) {
        tickUpdateHandlers.add(tickUpdateHandler);
    }

    @Override
    public void run() {
        for (ITickUpdateHandler iTickUpdateHandler : tickUpdateHandlers) {
            if (iTickUpdateHandler instanceof IServerTickUpdater) {
                ((IServerTickUpdater) iTickUpdateHandler).update();
            }
            if (iTickUpdateHandler instanceof IPlayerTickUpdater) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ((IPlayerTickUpdater) iTickUpdateHandler).update(player);
                }
            }
        }
    }
}
