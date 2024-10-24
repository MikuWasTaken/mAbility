package me.santiago;

import me.santiago.abilities.Ability;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AbilityManager {
    private final Map<String, Ability> abilities;
    private final Map<UUID, Long> abilityCooldowns; // Cooldowns específicos por habilidad
    private final Map<UUID, Long> generalCooldowns; // Cooldown general de 6 segundos

    public AbilityManager() {
        abilities = new HashMap<>();
        abilityCooldowns = new HashMap<>();
        generalCooldowns = new HashMap<>();
    }

    public void registerAbility(Ability ability) {
        abilities.put(ability.getName().toLowerCase(), ability);
    }

    public Ability getAbility(String name) {
        return abilities.get(name.toLowerCase());
    }

    public boolean useAbility(Player player, ItemStack item) {
        Ability ability = findAbility(item);
        if (ability != null) {
            if (canUseAbility(player, ability) && canUseGeneralAbility(player)) {
                ability.use(player);
                return true;
            }
        }
        return false; // No se pudo usar la habilidad
    }

    private Ability findAbility(ItemStack item) {
        for (Ability ability : abilities.values()) {
            if (ability.createItem().isSimilar(item)) {
                return ability;
            }
        }
        return null; // No se encontró la habilidad
    }

    public ItemStack createAbility(String name) {
        Ability ability = getAbility(name);
        return ability != null ? ability.createItem() : null;
    }

    private boolean canUseAbility(Player player, Ability ability) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Verifica el cooldown específico de la habilidad
        if (abilityCooldowns.containsKey(playerId)) {
            long lastUsed = abilityCooldowns.get(playerId);
            long cooldownTime = ability.getCooldown() * 1000; // Convertir a milisegundos
            if (currentTime - lastUsed < cooldownTime) {
                long secondsLeft = (cooldownTime - (currentTime - lastUsed)) / 1000;
                player.sendMessage(ChatColor.RED + "Debes esperar " + secondsLeft + " segundos para usar " + ability.getName() + " de nuevo.");
                return false;
            }
        }
        abilityCooldowns.put(playerId, currentTime); // Actualiza el tiempo de uso de la habilidad
        return true;
    }

    private boolean canUseGeneralAbility(Player player) {
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Verifica el cooldown general
        if (generalCooldowns.containsKey(playerId)) {
            long lastUsed = generalCooldowns.get(playerId);
            long generalCooldownTime = 6 * 1000; // 6 segundos en milisegundos
            if (currentTime - lastUsed < generalCooldownTime) {
                long secondsLeft = (generalCooldownTime - (currentTime - lastUsed)) / 1000;
                player.sendMessage(ChatColor.RED + "Debes esperar " + secondsLeft + " segundos antes de usar otra habilidad.");
                return false;
            }
        }
        generalCooldowns.put(playerId, currentTime); // Actualiza el tiempo de uso general
        return true;
    }
}
