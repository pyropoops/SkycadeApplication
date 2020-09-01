package net.saifs.skycadeapplication.item;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.nms.NMSHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
    public static List<Item> items = new ArrayList<>();
    public final String KEY = "special-item-";
    private String id;
    private ItemStack itemStack;

    public Item(String id) {
        this.id = id;
        this.itemStack = NMSHandler.writeNBT(constructItem(), KEY + id, true);

        if (this instanceof Listener) {
            SkycadeApplication plugin = SkycadeApplication.getInstance();
            plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
        }
        items.add(this);
        Bukkit.broadcastMessage("gU" + items.size());
    }

    public abstract ItemStack constructItem();

    public String getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isItem(ItemStack itemStack) {
        return NMSHandler.readNBTBoolean(itemStack, KEY + id);
    }
}
