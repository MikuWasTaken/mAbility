package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SamuraiAbility extends Ability {
    public SamuraiAbility() {
        super("samuraiability", 180); // Asumiendo un cooldown de 180 segundos
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Samurai Ability");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        Player target = getLastAttacker(player);
        if (target != null && target.isOnline()) {
            player.teleport(target.getLocation());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 240, 1)); // Fuerza II por 12 segundos
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 2)); // Resistencia III por 10 segundos
            player.sendMessage(ChatColor.GREEN + "¡Has usado la Samurai Ability en " + target.getName() + "!");
            target.sendMessage(ChatColor.RED + player.getName() + " ha usado la Samurai Ability en ti!");
            player.getInventory().removeItem(createItem());
        } else {
            player.sendMessage(ChatColor.RED + "No se encontró un objetivo válido para la Samurai Ability.");
        }
    }
}