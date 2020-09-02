package net.saifs.skycadeapplication.item;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.utils.interfaces.IPlayerTickUpdater;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class HelmetOfHadesItem extends Item implements IPlayerTickUpdater {
    private List<Player> users;
    private Map<UUID, ItemStack[]> inventories;

    public HelmetOfHadesItem() {
        super("helmet-of-hades");

        this.users = new ArrayList<>();
        this.inventories = new HashMap<>();

        SkycadeApplication.tickUpdateHandler.register(this);
    }

    @Override
    public ItemStack constructItem() {

        return null;
    }

    private void setInvisible(Player player, boolean value) {
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
        if (value) {
            if (!player.isInvulnerable()) {
                player.setInvulnerable(true);
            }
            if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                player.addPotionEffect(potionEffect);
            }
            inventories.put(player.getUniqueId(), player.getInventory().getContents());
            player.getInventory().clear();
        } else {
            if (inventories.containsKey(player.getUniqueId())) {
                player.getInventory().setContents(inventories.get(player.getUniqueId()));
                inventories.remove(player.getUniqueId());
            }
            if (player.isInvulnerable()) {
                player.setInvulnerable(false);
            }
            if (player.hasPotionEffect(PotionEffectType.INVISIBILITY))
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    @Override
    public void update(Player player) {
        if (!player.isSneaking()) {
            if (this.users.contains(player)) {
                setInvisible(player, false);
                this.users.remove(player);
            }
            return;
        }
        if (isItem(player.getInventory().getHelmet())) {
            if (!this.users.contains(player)) {
                setInvisible(player, true);
                this.users.add(player);
            }
        }
        if (this.users.contains(player)) {
            Location playerLoc = player.getLocation().clone().add(new Vector(0, 1, 0));
            boolean dark = false;
            for (double x = playerLoc.getX() - 0.5D; x < playerLoc.getX() + 0.5D; x += 0.2D) {
                for (double y = playerLoc.getY() - 0.5D; y < playerLoc.getY() + 0.5D; y += 0.2D) {
                    for (double z = playerLoc.getZ() - 0.5D; z < playerLoc.getZ() + 0.5D; z += 0.2D) {
                        Location loc = new Location(player.getLocation().getWorld(), x, y, z);
                        double distance = loc.distance(player.getLocation());
                        if (distance >= 0.5D) {
                            Color color = dark ? Color.fromRGB(60, 60, 60) : Color.fromRGB(30, 30, 30);
                            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 2);
                            if (loc.getWorld() == null) return;
                            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, dustOptions);
                            dark = !dark;
                        }
                    }
                }
            }
            player.setVelocity(player.getLocation().getDirection());
        }
    }
}
