package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Regeneration3 extends Ability {
    public Regeneration3() {
        super("regeneration3", 1);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Regeneración III");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
        applyEffectToFactionMembers(player, new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
        player.sendMessage(ChatColor.GREEN + "¡Has activado Regeneración III por 5 segundos para ti y los miembros cercanos de tu facción!");
        player.getInventory().removeItem(createItem());
    }

    private void applyEffectToFactionMembers(Player player, PotionEffect potionEffect) {

    }
}