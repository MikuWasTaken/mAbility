package me.santiago.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class AbilityChatListener implements Listener {
    private final HashMap<UUID, ConfigurationState> playerStates;
    private final AbilityGUIManager guiManager;

    public AbilityChatListener(AbilityGUIManager guiManager) {
        this.guiManager = guiManager;
        this.playerStates = new HashMap<>();
    }

    public void setPlayerState(Player player, String ability, ConfigurationType type) {
        playerStates.put(player.getUniqueId(), new ConfigurationState(ability, type));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ConfigurationState state = playerStates.get(player.getUniqueId());

        if (state == null) return;

        event.setCancelled(true);
        String input = event.getMessage();

        switch (state.getType()) {
            case NAME:
                // Implementar cambio de nombre
                player.sendMessage(ChatColor.GREEN + "Name updated successfully!");
                break;
            case MATERIAL:
                try {
                    Material material = Material.valueOf(input.toUpperCase());
                    // Implementar cambio de material
                    player.sendMessage(ChatColor.GREEN + "Material updated successfully!");
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.RED + "Invalid material! Please try again.");
                }
                break;
            case COOLDOWN:
                try {
                    int cooldown = Integer.parseInt(input);
                    if (cooldown < 0) {
                        player.sendMessage(ChatColor.RED + "Cooldown must be positive!");
                        return;
                    }
                    // Implementar cambio de cooldown
                    player.sendMessage(ChatColor.GREEN + "Cooldown updated successfully!");
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Please enter a valid number!");
                }
                break;
        }

        playerStates.remove(player.getUniqueId());
        guiManager.openMainMenu(player);
    }

    private static class ConfigurationState {
        private final String ability;
        private final ConfigurationType type;

        public ConfigurationState(String ability, ConfigurationType type) {
            this.ability = ability;
            this.type = type;
        }

        public String getAbility() { return ability; }
        public ConfigurationType getType() { return type; }
    }

    public enum ConfigurationType {
        NAME,
        MATERIAL,
        COOLDOWN
    }
}