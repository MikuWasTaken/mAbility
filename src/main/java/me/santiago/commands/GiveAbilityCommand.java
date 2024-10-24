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
            case "closecall":
                item = plugin.createCloseCallItem();
                break;
            case "berserk":
                item = plugin.createBerserkItem();
                break;
            case "rageball":
                item = plugin.createRageBallItem();
                break;
            case "focusmode":
                item = plugin.createFocusModeItem();
                break;
            case "exoticbone":
                item = plugin.createExoticBoneItem();
                break;
                case "resistance3":
                item = plugin.createResistance3Item();
                break;
            case "regeneration3":
                item = plugin.createRegeneration3Item();
                break;
            case "samuraiability":
                item = plugin.createSamuraiAbilityItem();
                break;
            case "switcher":
                item = plugin.createSwitcherItem();
                break;
            case "portablebard":
                item = plugin.createPortableBardItem();
                break;
            case "pocketbard":
                item = plugin.createPocketBardItem();
                break;
            case "comboability":
                item = plugin.createComboAbilityItem();
                break;
            case "ninjastar":
                item = plugin.createNinjaStarItem();
                break;
            case "firework":
                item = plugin.createFireworkItem();
                break;
            case "grapplinghook":
                item = plugin.createGrapplingHookItem();
                break;
            case "timewarp":
                item = plugin.createTimeWarpItem();
                break;
            case "guardianangel":
                item = plugin.createGuardianAngelItem();
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
