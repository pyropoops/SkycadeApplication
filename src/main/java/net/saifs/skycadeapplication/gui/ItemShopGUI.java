package net.saifs.skycadeapplication.gui;

import net.saifs.skycadeapplication.SkycadeApplication;
import net.saifs.skycadeapplication.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ItemShopGUI implements Listener {
    private final double SIZE = 18;
    private final Map<Inventory, Integer> pages;

    public ItemShopGUI() {
        this.pages = new HashMap<>();
        for (int i = 0; i < getMaxPageSize(); i++) {
            pages.put(createPage(i), i);
        }
        SkycadeApplication.getInstance().getServer().getPluginManager().registerEvents(this, SkycadeApplication.getInstance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!this.pages.containsKey(e.getClickedInventory())) return;
        e.setCancelled(true);
        int page = this.pages.get(e.getClickedInventory());
        int index = (int) (page * SIZE);
        index -= page == 0 ? 0 : 1 + (2 * page);
        int i = e.getSlot();
        int j = index + i;
        if (page != 0) {
            if (i == SIZE - 9) {
                e.getWhoClicked().openInventory(getPage(page - 1));
                return;
            }
            if (i > SIZE - 9)
                j++;
        }
        if (page != getMaxPageSize() - 1) {
            if (i == SIZE - 1) {
                e.getWhoClicked().openInventory(getPage(page + 1));
                return;
            }
        }
        Item item = Item.items.get(j);
        clicked((Player) e.getWhoClicked(), item);
    }

    private void clicked(Player player, Item item) {
        player.getInventory().addItem(item.getItemStack());
    }

    private Inventory createPage(int page) {
        String NAME = "Item Shop GUI";
        Inventory inventory = Bukkit.createInventory(null, (int) SIZE, NAME);
        int index = (int) (SIZE * page);
        if (page != 0) {
            index -= 1 + (page * 2);
        }
        for (int i = 0; i < SIZE; i++) {
            int j = index + i;
            if (page != 0 && i >= SIZE - 9) {
                j++;
            }
            if (j >= Item.items.size()) continue;
            Item item = Item.items.get(j);
            inventory.setItem(i, item.getItemStack());
        }
        if (page != 0) {
            ItemStack arrow = new ItemStack(Material.ARROW, 1);
            ItemMeta meta = arrow.getItemMeta();
            // TODO
            arrow.setItemMeta(meta);
            inventory.setItem((int) (SIZE - 9), arrow);
        }
        if (page != getMaxPageSize() - 1) {
            ItemStack arrow = new ItemStack(Material.ARROW, 1);
            ItemMeta meta = arrow.getItemMeta();
            // TODO
            arrow.setItemMeta(meta);
            inventory.setItem((int) (SIZE - 1), arrow);
        }
        return inventory;
    }

    public Inventory getPage(int i) {
        for (Inventory inventory : pages.keySet()) {
            if (pages.get(inventory) == i) {
                return inventory;
            }
        }
        return null;
    }

    private int getMaxPageSize() {
        int size = Item.items.size();
        int pages = (int) Math.ceil(size / this.SIZE);
        if (size > this.SIZE) {
            int offset = 1 + ((pages - 1) * 2);
            size += offset;
        }
        return (int) Math.ceil(size / this.SIZE);
    }
}
