package me.santiago.abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class GrapplingHook implements Listener {


    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6Grappling Hook");

        List<String> lore = new ArrayList<>();
        lore.add("§7Right click to throw the hook");
        lore.add("§7Right click again to pull yourself!");
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }

    public void giveItem(Player player) {
        player.getInventory().addItem(createItem());
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item == null || !item.hasItemMeta() ||
                !item.getItemMeta().getDisplayName().equals("§6Grappling Hook")) {
            return;
        }

        if (event.getState() == PlayerFishEvent.State.FISHING) {
            return;
        }

        event.setCancelled(true);
        FishHook hook = event.getHook();

        Location hookLoc = hook.getLocation();
        Location playerLoc = player.getLocation();
        Vector direction = hookLoc.subtract(playerLoc).toVector();

        direction.normalize();
        direction.setY(0.5);
        direction.multiply(1.5);

        player.setVelocity(direction);
        hook.remove();
    }

    @EventHandler
    public void onFishCatch(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();

        if (item != null && item.hasItemMeta() &&
                item.getItemMeta().getDisplayName().equals("§6Grappling Hook")) {
            if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                event.setCancelled(true);
            }
        }
    }
}