package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Switcher extends Ability implements Listener {
    public Switcher() {
        super("switcher", 30);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.SNOW_BALL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Switcher");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        player.getInventory().removeItem(createItem());
        player.launchProjectile(Snowball.class);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball) || !(event.getEntity().getShooter() instanceof Player)) return;
        Player shooter = (Player) event.getEntity().getShooter();

        Player target = getNearestPlayer(event.getEntity().getLocation(), shooter);

        if (target != null && target.getLocation().distance(shooter.getLocation()) <= 5) {
            org.bukkit.Location shooterLoc = shooter.getLocation();
            shooter.teleport(target.getLocation());
            target.teleport(shooterLoc);
            shooter.sendMessage(ChatColor.GREEN + "¡Has intercambiado posiciones con " + target.getName() + "!");
            target.sendMessage(ChatColor.RED + shooter.getName() + " ha intercambiado posiciones contigo usando un Switcher.");
        }
    }

    private Player getNearestPlayer(org.bukkit.Location location, Player exclude) {
        Player nearest = null;
        double distance = Double.MAX_VALUE;
        for (Player player : location.getWorld().getPlayers()) {
            if (player.equals(exclude)) continue;
            double d = player.getLocation().distance(location);
            if (d < distance) {
                nearest = player;
                distance = d;
            }
        }
        return nearest;
    }
}