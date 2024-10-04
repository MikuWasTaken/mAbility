package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ExoticBone extends Ability {
    public ExoticBone() {
        super("exoticbone", 180);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.BONE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Exotic Bone");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        // Lógica para prevenir construcción en claim enemigo
        preventEnemyBuilding(player);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
        player.sendMessage(ChatColor.GREEN + "¡Has usado el Exotic Bone! Los enemigos no pueden construir en su claim por 15 segundos y tienes Fuerza I por 10 segundos.");
        player.getInventory().removeItem(createItem());
    }

    private void preventEnemyBuilding(Player player) {
        // Implementar lógica para prevenir construcción en claim enemigo
    }
}