package net.saifs.skycadeapplication.item;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GrapplingHookItem extends Item {
    private List<Arrow> grapplingArrows;

    public GrapplingHookItem() {
        super("grappling-hook");
        grapplingArrows = new ArrayList<>();
    }

    @EventHandler
    public void onProjectileLaunch(EntityShootBowEvent e) {
        if (isItem(e.getBow()))
            grapple(e.getEntity(), e.getProjectile().getLocation());

    }

    private void grapple(LivingEntity livingEntity, Location location) {
        
    }

    @Override
    public ItemStack constructItem() {
        return null;
    }
}
