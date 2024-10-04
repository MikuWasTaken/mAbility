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

public class RageBall extends Ability implements Listener {
    public RageBall() {
        super("rageball", 180);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.FIREBALL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Rage Ball");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        // La activación se maneja cuando el jugador lanza la bola
        player.getInventory().removeItem(createItem());
        player.launchProjectile(Snowball.class);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball) || !(event.getEntity().getShooter() instanceof Player)) return;
        Player shooter = (Player) event.getEntity().getShooter();

        // Efectos para el lanzador y aliados
        for (Player nearby : shooter.getWorld().getPlayers()) {
            if (nearby.getLocation().distance(event.getEntity().getLocation()) <= 5) {
                if (isSameFaction(shooter, nearby)) {
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1)); // Fuerza II por 10 segundos
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 2)); // Resistencia III por 8 segundos
                    nearby.sendMessage(ChatColor.GREEN + "¡Has sido potenciado por la Rage Ball de " + shooter.getName() + "!");
                } else {
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 1)); // Debilidad II por 4 segundos
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1)); // Wither II por 4 segundos
                    nearby.sendMessage(ChatColor.RED + "¡Has sido afectado por la Rage Ball de " + shooter.getName() + "!");
                }
            }
        }

        // Efecto visual de explosión
        World world = shooter.getWorld();
        world.createExplosion(event.getEntity().getLocation(), 0F, false); // Efecto visual de explosión sin daño
        world.playEffect(event.getEntity().getLocation(), Effect.EXPLOSION_LARGE, 0); // Efecto de explosión grande
    }

    private boolean isSameFaction(Player player1, Player player2) {
        // Implementar lógica para verificar si los jugadores están en la misma facción
        // Por ahora, asumimos que no lo están
        return false;
    }
}
