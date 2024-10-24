package me.santiago.commands;

import me.santiago.gui.AbilityGUIManager;
import me.santiago.mAbility;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbilityMenuCommand implements CommandExecutor, TabCompleter {
    private final mAbility plugin;
    private final AbilityGUIManager guiManager;

    public AbilityMenuCommand(mAbility plugin, AbilityGUIManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("mability.menu")) {
            player.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
            return true;
        }

        if (args.length == 0) {
            sendHelpMessage(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "menu":
                guiManager.openMainMenu(player);
                break;
            case "help":
                sendHelpMessage(player);
                break;
            default:
                player.sendMessage(ChatColor.RED + "Comando desconocido. Usa /ability help para ver los comandos disponibles.");
                break;
        }

        return true;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(ChatColor.GOLD + "=== Comandos de Abilities ===");
        player.sendMessage(ChatColor.YELLOW + "/ability menu" + ChatColor.GRAY + " - Abre el menú de configuración de abilities");
        player.sendMessage(ChatColor.YELLOW + "/ability help" + ChatColor.GRAY + " - Muestra este mensaje de ayuda");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("menu");
            completions.add("help");

            return completions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return completions;
    }
}