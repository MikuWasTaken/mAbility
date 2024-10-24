package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PocketBard extends Ability implements Listener {

    public PocketBard() {
        super("pocketbard", 0);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Pocket Bard");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Pocket Bard");
        gui.setItem(2, new Strength2().createItem());
        gui.setItem(4, new Resistance3().createItem());
        gui.setItem(6, new Regeneration3().createItem());
        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Pocket Bard")) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            ItemStack clickedItem = event.getCurrentItem();
            ItemStack abilityItem = null;
            String abilityName = "";

            if (clickedItem.isSimilar(new Strength2().createItem())) {
                abilityItem = new Strength2().createItem();
                abilityName = "Fuerza II";
            } else if (clickedItem.isSimilar(new Resistance3().createItem())) {
                abilityItem = new Resistance3().createItem();
                abilityName = "Resistencia III";
            } else if (clickedItem.isSimilar(new Regeneration3().createItem())) {
                abilityItem = new Regeneration3().createItem();
                abilityName = "Regeneración III";
            }

            if (abilityItem != null) {
                player.getInventory().addItem(abilityItem);
                player.sendMessage(ChatColor.GREEN + "¡Has obtenido la habilidad " + abilityName + "!");
                player.closeInventory();
                player.getInventory().removeItem(createItem());
            }
        }
    }
}