package net.saifs.skycadeapplication.item;

import net.saifs.skycadeapplication.SkycadeApplication;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GrapplingHookItem extends Item {
    private Map<Entity, LivingEntity> grappleArrows;

    public GrapplingHookItem() {
        super("grappling-hook");
        grappleArrows = new HashMap<>();
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent e) {
        if (this.isItem(e.getBow())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    LivingEntity entity = e.getEntity();
                    Entity arrow = e.getProjectile();
                    grappleArrows.put(arrow, entity);
                    if (arrow.isDead()) {
                        this.cancel();
                    }
                    if (arrow.isOnGround() && entity.getLocation().distance(arrow.getLocation()) <= 1) {
                        grappleArrows.remove(arrow);
                        arrow.remove();
                        this.cancel();
                    }
                    if (entity instanceof Player && ((Player) entity).isSneaking()) {
                        if (arrow.isOnGround()) {
                            entity.setVelocity(entity.getLocation().getDirection().multiply(2D));
                        }
                        grappleArrows.remove(arrow);
                        arrow.remove();
                        this.cancel();
                    }
                    if (arrow.isOnGround() && !arrow.isDead()) {
                        Vector direction = arrow.getLocation().toVector()
                                .subtract(entity.getLocation().toVector()).normalize();
                        entity.setVelocity(direction.multiply(1.2D));
                        drawLine(e.getEntity().getLocation(),
                                arrow.getLocation(), 0.1D, new Vector(0, 1, 0));
                    }
                }
            }.runTaskTimer(SkycadeApplication.getInstance(), 0L, 0L);
        }
    }

    private void drawLine(Location from, Location to, double space, Vector offset) {
        World world = from.getWorld();
        Validate.isTrue(Objects.equals(to.getWorld(), world), "Particles cannot be drawn from different worlds!");

        double distance = from.distance(to);

        Vector v1 = from.toVector();
        Vector v2 = to.toVector();

        Vector vector = v2.clone().subtract(v1).normalize().multiply(space);

        for (double covered = 0; covered < distance; covered += space) {
            v1.add(vector);
            Location loc = new Location(world, v1.getX(), v1.getY(), v1.getZ());
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(30, 150, 0), 1);
            if (world == null) return;
            world.spawnParticle(Particle.REDSTONE, loc.add(offset), 1, dustOptions);
        }
    }

    @Override
    public ItemStack constructItem() {
        return null;
    }
}
