package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ExoticBone extends Ability {
    private final JavaPlugin plugin;
    private final Set<UUID> preventBuildingPlayers = new HashSet<>();

    public ExoticBone(JavaPlugin plugin) {
        super("exoticbone", 180);
        this.plugin = plugin;
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
        preventEnemyBuilding(player);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
        player.sendMessage(ChatColor.GREEN + "¡Has usado el Exotic Bone! Los enemigos no pueden construir en su claim por 15 segundos y tienes Fuerza I por 10 segundos.");
        player.getInventory().removeItem(createItem());
    }

    private void preventEnemyBuilding(Player player) {
        preventBuildingPlayers.add(player.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                preventBuildingPlayers.remove(player.getUniqueId());
                player.sendMessage(ChatColor.RED + "El efecto de Exotic Bone ha terminado.");
            }
        }.runTaskLater(plugin, 300L); // 15 segundos
    }

    public boolean canBuild(Player player) {
        return !preventBuildingPlayers.contains(player.getUniqueId());
    }
}