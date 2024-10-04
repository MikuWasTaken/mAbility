package me.santiago.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;

public class PortableBard extends Ability {
    private final JavaPlugin plugin;

    public PortableBard(JavaPlugin plugin) {
        super("portablebard", 300);
        this.plugin = plugin;
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Portable Bard");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        ArmorStand bard = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        bard.setVisible(false);
        bard.setGravity(false);
        bard.setCustomName(ChatColor.GOLD + "Portable Bard");
        bard.setCustomNameVisible(true);

        bard.setHelmet(new ItemStack(Material.GOLD_HELMET));
        bard.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        bard.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        bard.setBoots(new ItemStack(Material.GOLD_BOOTS));

        applyBardEffects(player);

        player.sendMessage(ChatColor.GREEN + "¡Has spawneado un Portable Bard por 15 segundos!");
        player.getInventory().removeItem(createItem());

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            bard.remove();
            player.sendMessage(ChatColor.RED + "Tu Portable Bard ha desaparecido.");
        }, 300L);
    }

    private void applyBardEffects(Player player) {
        // Implementar lógica para aplicar efectos de bardo
    }
}