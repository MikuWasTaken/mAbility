package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NinjaStar extends Ability {
    public NinjaStar() {
        super("ninjastar", 30);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Estrella Ninja");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        Player target = getLastAttacker(player);
        if (target != null && target.isOnline()) {
            player.teleport(target.getLocation());
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 0));
            player.sendMessage(ChatColor.GREEN + "¡Te has teletransportado a " + target.getName() + "!");
            player.getInventory().removeItem(createItem());
        } else {
            player.sendMessage(ChatColor.RED + "No se encontró un objetivo válido.");
        }
    }
}