package me.santiago.abilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GuardianAngel implements Listener {
    private HashMap<UUID, Boolean> activeGuardians = new HashMap<>();

    public ItemStack createItem() {
        ItemStack item;
        try {
            // Try 1.8 material first
            item = new ItemStack(Material.WATCH);
        } catch (Exception e) {
            // Fallback for 1.7
            item = new ItemStack(Material.valueOf("GOLD_WATCH"));
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§eGuardian Angel");

        List<String> lore = new ArrayList<>();
        lore.add("§7Automatically heals you when");
        lore.add("§7health drops below 2 hearts!");
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(createItem());
    }

    public void activateGuardian(Player player) {
        activeGuardians.put(player.getUniqueId(), true);
    }

    public void deactivateGuardian(Player player) {
        activeGuardians.remove(player.getUniqueId());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!activeGuardians.containsKey(player.getUniqueId())) {
            return;
        }

        if (!player.getInventory().containsAtLeast(createItem(), 1)) {
            deactivateGuardian(player);
            return;
        }

        double finalHealth = player.getHealth() - event.getFinalDamage();

        if (finalHealth <= 4.0) {
            event.setCancelled(true);
            player.setHealth(player.getMaxHealth());

            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.hasItemMeta() &&
                        item.getItemMeta().getDisplayName().equals("§eGuardian Angel")) {
                    item.setAmount(item.getAmount() - 1);
                    break;
                }
            }

            deactivateGuardian(player);
        }
    }
}