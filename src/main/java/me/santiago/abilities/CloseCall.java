package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CloseCall extends Ability implements Listener {
    public CloseCall() {
        super("closecall", 120);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.COOKIE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Close Call");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        // La activación se maneja en el EventHandler
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (player.getHealth() - event.getFinalDamage() <= 6) { // 3 corazones
            if (player.getInventory().containsAtLeast(createItem(), 1)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 4));
                player.sendMessage(ChatColor.GREEN + "¡Close Call activado!");
                player.getInventory().removeItem(createItem());
            }
        }
    }
}
