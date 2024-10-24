package me.santiago.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Firework implements Listener {


    public ItemStack createItem() {
        ItemStack item;
        try {
            // Try 1.8 material first
            item = new ItemStack(Material.FIREWORK);
        } catch (Exception e) {
            // Fallback for 1.7
            item = new ItemStack(Material.valueOf("FIREWORK_CHARGE"));
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§bBoost Firework");

        List<String> lore = new ArrayList<>();
        lore.add("§7Right click to boost yourself!");
        lore.add("§76 blocks forward, 7 blocks up");
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(createItem());
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta() ||
                !item.getItemMeta().getDisplayName().equals("§bBoost Firework")) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR ||
                event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            event.setCancelled(true);

            Vector direction = player.getLocation().getDirection();
            direction.normalize();
            direction.multiply(1.2); // Forward boost
            direction.setY(1.4);     // Upward boost

            player.setVelocity(direction);

            // Remove one firework from the player's hand
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().setItemInHand(null);
            }
        }
    }
}