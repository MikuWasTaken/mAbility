package me.santiago.commands;

import me.santiago.AbilityManager;
import me.santiago.abilities.Ability;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCooldownCommand implements CommandExecutor {
    private final AbilityManager abilityManager;

    public SetCooldownCommand(AbilityManager abilityManager) {
        this.abilityManager = abilityManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;

        // Verifica si se proporcionan los argumentos necesarios
        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Uso: /ability setcooldown <nombre> <tiempo_en_segundos>");
            return true;
        }

        String abilityName = args[0];
        int cooldownTime;

        // Intenta convertir el segundo argumento a un entero
        try {
            cooldownTime = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "El tiempo debe ser un número válido.");
            return true;
        }

        // Busca la habilidad en AbilityManager
        Ability ability = abilityManager.getAbility(abilityName);
        if (ability == null) {
            player.sendMessage(ChatColor.RED + "No se encontró la habilidad " + abilityName + ".");
            return true;
        }

        // Aquí se establece el cooldown de la habilidad
        ability.setCooldown(cooldownTime);

        // Mensaje de confirmación al jugador
        player.sendMessage(ChatColor.GREEN + "Cooldown de " + abilityName + " establecido a " + cooldownTime + " segundos.");
        return true;
    }
}
