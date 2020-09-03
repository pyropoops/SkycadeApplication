package net.saifs.skycadeapplication.item;

import net.saifs.skycadeapplication.SkycadeApplication;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class TrackingBowItem extends Item implements Listener {
    public TrackingBowItem() {
        super("tracking-bow");
    }

    @EventHandler
    public void onProjectileLaunch(EntityShootBowEvent e) {
        if (!this.isItem(e.getBow())) return;

        new BukkitRunnable() {
            private double tickMultiplier = 0.1D;
            
            @Override
            public void run() {
                if (e.getProjectile().isDead() || e.getProjectile().isOnGround()) {
                    this.cancel();
                }

                if (!(e.getEntity() instanceof Player) || ((Player) e.getEntity()).isSneaking()) {
                    double targetDistance = 30;
                    LivingEntity target = null;
                    for (Entity entity : e.getProjectile().getNearbyEntities(targetDistance, targetDistance, targetDistance)) {
                        if (!(entity instanceof LivingEntity)) continue;
                        if (entity == e.getEntity()) continue;
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (e.getProjectile().getLocation().distance(livingEntity.getLocation()) < targetDistance) {
                            targetDistance = e.getProjectile().getLocation().distance(livingEntity.getLocation());
                            target = livingEntity;
                        }
                    }
                    if (target == null) return;
                    Vector direction = target.getLocation().clone().toVector().subtract(e.getProjectile().getLocation().toVector()).normalize();
                    e.getProjectile().setVelocity(direction.multiply(tickMultiplier));
                    tickMultiplier += 0.1D;
                }
            }
        }.runTaskTimer(SkycadeApplication.getInstance(), 0L, 0L);

    }

    @Override
    public ItemStack constructItem() {
        return null;
    }
}
