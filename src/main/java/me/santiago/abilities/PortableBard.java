package me.santiago.abilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Witch;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PortableBard extends Ability {
    private final JavaPlugin plugin;
    private final Random random = new Random();

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
        Witch bard = (Witch) player.getWorld().spawnEntity(player.getLocation(), EntityType.WITCH);
        bard.setCustomName(ChatColor.GOLD + "Portable Bard");
        bard.setCustomNameVisible(true);

        applyBardEffects(player, bard);

        player.sendMessage(ChatColor.GREEN + "¡Has spawneado un Portable Bard por 30 segundos!");
        player.getInventory().removeItem(createItem());

        new BukkitRunnable() {
            int timeLeft = 30;
            @Override
            public void run() {
                if (timeLeft <= 0 || !bard.isValid()) {
                    bard.remove();
                    player.sendMessage(ChatColor.RED + "Tu Portable Bard ha desaparecido.");
                    this.cancel();
                    return;
                }
                timeLeft--;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private void applyBardEffects(Player player, Witch bard) {
        new BukkitRunnable() {
            int tickCounter = 0;
            @Override
            public void run() {
                if (!bard.isValid()) {
                    this.cancel();
                    return;
                }

                // Aplicar efectos constantes
                applyConstantEffects(player);

                // Aplicar efecto aleatorio cada 5 segundos (100 ticks)
                if (tickCounter % 100 == 0) {
                    applyRandomEffect(player);
                }

                tickCounter++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void applyConstantEffects(Player player) {
        for (Player nearby : player.getWorld().getPlayers()) {
            if (nearby.getLocation().distance(player.getLocation()) <= 10) {
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0));
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 0));
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 0));
                nearby.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 0));
            }
        }
    }

    private void applyRandomEffect(Player player) {
        PotionEffect effect;
        switch (random.nextInt(5)) {
            case 0:
                effect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1);
                break;
            case 1:
                effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 2);
                break;
            case 2:
                effect = new PotionEffect(PotionEffectType.REGENERATION, 120, 2);
                break;
            case 3:
                effect = new PotionEffect(PotionEffectType.SPEED, 160, 2);
                break;
            default:
                effect = new PotionEffect(PotionEffectType.JUMP, 200, 2);
                break;
        }

        for (Player nearby : player.getWorld().getPlayers()) {
            if (nearby.getLocation().distance(player.getLocation()) <= 10) {
                nearby.addPotionEffect(effect);
            }
        }
    }
}