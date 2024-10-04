package me.santiago.commands;

import me.santiago.mAbility;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class GiveAbilityCommand implements CommandExecutor {
    private final mAbility plugin;

    public GiveAbilityCommand(mAbility plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando solo puede ser usado por jugadores.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Uso: /giveability [itemname]");
            return true;
        }

        ItemStack item = null;
        String itemName = args[0].toLowerCase();

        switch (itemName) {
            case "strength2":
                item = plugin.createStrength2Item();
                break;
            case "ninjastar":
                item = plugin.createNinjaStarItem();
                break;
            // Agrega más casos para los otros ítems...
            default:
                player.sendMessage(ChatColor.RED + "Item no reconocido.");
                return true;
        }

        if (item != null) {
            player.getInventory().addItem(item);
            player.sendMessage(ChatColor.GREEN + "Has recibido el item " + itemName + "!");
        }

        return true;
    }
}
