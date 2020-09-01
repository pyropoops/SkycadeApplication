package net.saifs.skycadeapplication.item;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.nms.NMSHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Item {
    public static Map<String, Item> items = new HashMap<>();
    public static final String KEY = "special-item";

    private String id;
    private ItemStack itemStack;

    public Item(String id) {
        this.id = id;
        this.itemStack = NMSHandler.writeNBT(constructItem(), KEY, getId());

        if (this instanceof Listener) {
            SkycadeApplication plugin = SkycadeApplication.getInstance();
            plugin.getServer().getPluginManager().registerEvents((Listener) this, plugin);
        }

        items.put(id, this);
    }

    public abstract ItemStack constructItem();

    public String getId() {
        return id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isItem(ItemStack itemStack) {
        return NMSHandler.readNBT(itemStack, KEY).equals(getId());
    }
}
