package me.santiago.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiveAbilityAllCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public GiveAbilityAllCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("giveability")) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /giveability <usuario> all");
                return true;
            }

            String playerName = args[0];
            Player targetPlayer = Bukkit.getPlayer(playerName);

            if (targetPlayer == null) {
                sender.sendMessage("El jugador no está en línea.");
                return true;
            }

            if (!args[1].equalsIgnoreCase("all")) {
                sender.sendMessage("Solo se permite el argumento 'all'.");
                return true;
            }

            List<String> abilities = getAbilities();

            // Otorgar todas las Abilityes al jugador
            List<String> grantedAbilities = new ArrayList<>();
            for (String ability : abilities) {
                grantedAbilities.add(ability); // Asignamos las Abilities
                giveAbility(targetPlayer, ability); // Otorgar Ability
            }

            targetPlayer.sendMessage("Se te han dado las siguientes Abilities: " + grantedAbilities);
            sender.sendMessage("Se han dado todas las Abilities a " + playerName + ".");
            return true;
        }
        return false;
    }

    private void giveAbility(Player player, String ability) {
        // Lógica para otorgar la Ability al jugador
        switch (ability.toLowerCase()) {
            case "focusmode":
                // Implementar lógica para focusmode
                player.sendMessage("Ability 'focusmode' otorgada.");
                break;
            case "closecall":
                // Implementar lógica para closecall
                player.sendMessage("Ability 'closecall' otorgada.");
                break;
            case "samuraiability":
                // Implementar lógica para samuraiability
                player.sendMessage("Ability 'samuraiability' otorgada.");
                break;
            case "ninjastar":
                // Implementar lógica para ninjastar
                player.sendMessage("Ability 'ninjastar' otorgada.");
                break;
            case "strength2":
                // Implementar lógica para strength2
                player.sendMessage("Ability 'strength2' otorgada.");
                break;
            case "resistance3":
                // Implementar lógica para resistance3
                player.sendMessage("Ability 'resistance3' otorgada.");
                break;
            case "regeneration3":
                // Implementar lógica para regeneration3
                player.sendMessage("Ability 'regeneration3' otorgada.");
                break;
            case "exoticbone":
                // Implementar lógica para exoticbone
                player.sendMessage("Ability 'exoticbone' otorgada.");
                break;
            case "comboability":
                // Implementar lógica para comboability
                player.sendMessage("Ability 'comboability' otorgada.");
                break;
            case "rageball":
                // Implementar lógica para rageball
                player.sendMessage("Ability 'rageball' otorgada.");
                break;
            case "portablebard":
                // Implementar lógica para portablebard
                player.sendMessage("Ability 'portablebard' otorgada.");
                break;
            case "switcher":
                // Implementar lógica para switcher
                player.sendMessage("Ability 'switcher' otorgada.");
                break;
            case "pocketbard":
                // Implementar lógica para pocketbard
                player.sendMessage("Ability 'pocketbard' otorgada.");
                break;
            case "timewarp":
                player.sendMessage("Ability 'timewarp' otorgada");
                break;
            case "firework":
                player.sendMessage("Ability 'firework' otorgada");
                break;
            case "guardianangel":
                player.sendMessage("Ability 'guardianangel' otorgada");
                break;
            case "grapplinghook":
                player.sendMessage("Ability 'grapplinghook' otorgada");

            default:
                player.sendMessage("Ability desconocida: " + ability);
                break;
        }
    }

    private List<String> getAbilities() {
        return Arrays.asList(
                "focusmode",
                "closecall",
                "samuraiability",
                "ninjastar",
                "strength2",
                "resistance3",
                "regeneration3",
                "exoticbone",
                "comboability",
                "rageball",
                "portablebard",
                "switcher",
                "pocketbard"
        );
    }
}
