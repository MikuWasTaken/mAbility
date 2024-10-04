package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class PocketBard extends Ability {
    public PocketBard() {
        super("pocketbard", 120);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Pocket Bard");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Pocket Bard");
        gui.setItem(2, createEffectItem("Fuerza II", Material.BLAZE_POWDER));
        gui.setItem(4, createEffectItem("Resistencia III", Material.IRON_INGOT));
        gui.setItem(6, createEffectItem("Regeneración III", Material.GHAST_TEAR));
        player.openInventory(gui);
    }

    private ItemStack createEffectItem(String name, Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + name);
        item.setItemMeta(meta);
        return item;
    }
}