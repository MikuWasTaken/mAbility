package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FocusMode extends Ability implements Listener {
    private final JavaPlugin plugin;
    private final Map<UUID, UUID> focusedPlayers = new HashMap<>();

    public FocusMode(JavaPlugin plugin) {
        super("focusmode", 120);
        this.plugin = plugin;
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Focus Mode");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        Player lastAttacker = getLastAttacker(player);
        if (lastAttacker != null) {
            UUID lastAttackerUUID = lastAttacker.getUniqueId();
            focusedPlayers.put(player.getUniqueId(), lastAttackerUUID);
            player.sendMessage(ChatColor.GREEN + "¡Has activado Focus Mode en " + lastAttacker.getName() + "!");
            player.getInventory().removeItem(createItem());

            new BukkitRunnable() {
                @Override
                public void run() {
                    focusedPlayers.remove(player.getUniqueId());
                    player.sendMessage(ChatColor.RED + "Tu Focus Mode ha terminado.");
                }
            }.runTaskLater(plugin, 1200L); // 60 segundos
        } else {
            player.sendMessage(ChatColor.RED + "No hay un jugador válido para enfocar.");
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        Player attacker = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        if (focusedPlayers.containsKey(attacker.getUniqueId()) &&
                focusedPlayers.get(attacker.getUniqueId()).equals(victim.getUniqueId())) {
            event.setDamage(event.getDamage() * 1.3); // 30% más de daño
            attacker.sendMessage(ChatColor.RED + "¡Daño aumentado en un 30% contra tu objetivo enfocado!");
        }
    }

    // Eliminamos la implementación de getLastAttacker aquí, ya que usaremos la de la clase padre
}