package me.santiago.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TimeWarp implements Listener {
    private final Plugin plugin;
    private HashMap<UUID, Location> lastPearlLocation = new HashMap<>();
    private HashMap<UUID, Long> lastPearlTime = new HashMap<>();

    public TimeWarp(Plugin plugin) {
        this.plugin = plugin;
    }


    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§dTime Warp");

        List<String> lore = new ArrayList<>();
        lore.add("§7Returns you to your last");
        lore.add("§7ender pearl location within");
        lore.add("§710 seconds of throwing!");
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(createItem());
    }

    @EventHandler
    public void onPearlThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof EnderPearl)) {
            return;
        }

        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();
        lastPearlLocation.put(player.getUniqueId(), player.getLocation());
        lastPearlTime.put(player.getUniqueId(), System.currentTimeMillis());

        new BukkitRunnable() {
            @Override
            public void run() {
                lastPearlLocation.remove(player.getUniqueId());
                lastPearlTime.remove(player.getUniqueId());
            }
        }.runTaskLater(plugin, 200L);
    }

    @EventHandler
    public void onTimeWarpUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.hasItemMeta() ||
                !item.getItemMeta().getDisplayName().equals("§dTime Warp")) {
            return;
        }

        UUID playerId = player.getUniqueId();
        if (!lastPearlLocation.containsKey(playerId)) {
            player.sendMessage("§cNo recent ender pearl location found!");
            return;
        }

        long currentTime = System.currentTimeMillis();
        long pearlTime = lastPearlTime.get(playerId);

        if (currentTime - pearlTime > 10000) {
            player.sendMessage("§cToo much time has passed since your last ender pearl!");
            return;
        }

        player.teleport(lastPearlLocation.get(playerId));

        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().setItemInHand(null);
        }

        lastPearlLocation.remove(playerId);
        lastPearlTime.remove(playerId);
    }
}