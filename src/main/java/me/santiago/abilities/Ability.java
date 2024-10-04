package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public abstract class Ability {
    private final String name;
    private int cooldown; // Cooldown en segundos

    public Ability(String name, int cooldown) {
        this.name = name;
        this.cooldown = cooldown;
    }

    public abstract ItemStack createItem();
    public abstract void use(Player player);

    protected Player getLastAttacker(Player player) {
        List<MetadataValue> metadata = player.getMetadata("lastAttacker");
        if (!metadata.isEmpty()) {
            return (Player) metadata.get(0).value();
        }
        return null;
    }

    protected void applyEffectToNearbyPlayers(Player player, PotionEffect effect, double radius) {
        for (Player nearbyPlayer : player.getWorld().getPlayers()) {
            if (nearbyPlayer.getLocation().distance(player.getLocation()) <= radius) {
                nearbyPlayer.addPotionEffect(effect);
            }
        }
    }

    // Método que las subclases pueden sobrescribir si necesitan lógica específica de facciones
    protected boolean isInSameFaction(Player player1, Player player2) {
        // Implementar lógica para verificar facciones aquí, si es necesario
        return true;
    }

    public String getName() {
        return name;
    }

    public int getCooldown() {
        return cooldown; // Retorna el cooldown específico de esta habilidad
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown; // Permite actualizar el cooldown
    }
}
