package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Strength2 extends Ability {
    public Strength2() {
        super("strength2", 60);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Fuerza II");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        applyEffectToFactionMembers(player, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
        player.sendMessage(ChatColor.GREEN + "¡Has activado Fuerza II por 5 segundos para ti y los miembros cercanos de tu facción!");
        player.getInventory().removeItem(createItem());
    }

    private void applyEffectToFactionMembers(Player player, PotionEffect potionEffect) {

    }
}