package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ComboAbility extends Ability implements Listener {
    private Map<UUID, Integer> comboHits = new HashMap<>();

    public ComboAbility() {
        super("comboability", 180);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Combo Ability");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        // La activación se maneja en el EventHandler
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        Player attacker = (Player) event.getDamager();
        if (attacker.getInventory().getItemInHand().isSimilar(createItem())) {
        }            UUID attackerId = attacker.getUniqueId();
            int hits = comboHits.getOrDefault(attackerId, 0) + 1;
            comboHits.put(attackerId, Math.min(hits, 12));

            int duration = hits * 20; // 1 segundo por hit, máximo 12 segundos
            attacker.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, 1));
            attacker.sendMessage(ChatColor.GREEN + "Combo: " + hits + " hit" + (hits > 1 ? "s" : "") + "! Fuerza II por " + (duration/20) + " segundos.");

            if (hits >= 12) {
                comboHits.remove(attackerId);
                attacker.getInventory().removeItem(createItem());
            }
        }
    }
