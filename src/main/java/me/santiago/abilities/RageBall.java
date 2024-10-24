package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class RageBall extends Ability implements Listener {
    private final JavaPlugin plugin;

    public RageBall(JavaPlugin plugin) {
        super("rageball", 1);
        this.plugin = plugin;
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.SNOW_BALL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Rage Ball");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        player.getInventory().removeItem(createItem());
        Snowball snowball = player.launchProjectile(Snowball.class);
        snowball.setMetadata("RageBall", new FixedMetadataValue(plugin, true));
        player.sendMessage(ChatColor.GREEN + "¡Has lanzado una Rage Ball!");
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball) || !(event.getEntity().getShooter() instanceof Player)) return;
        Snowball snowball = (Snowball) event.getEntity();
        if (!snowball.hasMetadata("RageBall")) return;

        Player shooter = (Player) event.getEntity().getShooter();

        for (Player nearby : shooter.getWorld().getPlayers()) {
            if (nearby.getLocation().distance(event.getEntity().getLocation()) <= 5) {
                if (isSameFaction(shooter, nearby)) {
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 2));
                    nearby.sendMessage(ChatColor.GREEN + "¡Has sido potenciado por la Rage Ball de " + shooter.getName() + "!");
                } else {
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1));
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1));
                    nearby.sendMessage(ChatColor.RED + "¡Has sido afectado por la Rage Ball de " + shooter.getName() + "!");
                }
            }
        }

        World world = shooter.getWorld();
        world.createExplosion(event.getEntity().getLocation(), 0F, false);
        world.playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0);
    }

    private boolean isSameFaction(Player player1, Player player2) {
        // Implementar lógica para verificar si los jugadores están en la misma facción
        // Por ahora, asumimos que no lo están si son diferentes jugadores
        return player1.equals(player2);
    }
}