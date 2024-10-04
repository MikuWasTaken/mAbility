package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Resistance3 extends Ability {
    public Resistance3() {
        super("resistance3", 90);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Resistencia III");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        applyEffectToFactionMembers(player, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 2));
        player.sendMessage(ChatColor.GREEN + "¡Has activado Resistencia III por 5 segundos para ti y los miembros cercanos de tu facción!");
        player.getInventory().removeItem(createItem());
    }

    private void applyEffectToFactionMembers(Player player, PotionEffect potionEffect) {

    }
}