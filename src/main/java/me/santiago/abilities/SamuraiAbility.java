package me.santiago.abilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SamuraiAbility extends Ability {
    public SamuraiAbility() {
        super("samuraiability", 180);
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.GOLD_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Samurai Ability");
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void use(Player player) {
        Player target = getLastAttacker(player);
        if (target != null && target.isOnline()) {
            player.teleport(target.getLocation());
            preventBuilding(target, 12); // Prevenir construcción por 12 segundos
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 240, 1)); // Fuerza II por 12 segundos
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1)); // Resistencia II por 10 segundos
            player.sendMessage(ChatColor.GREEN + "¡Has usado la Samurai Ability en " + target.getName() + "!");
            target.sendMessage(ChatColor.RED + player.getName() + " ha usado la Samurai Ability en ti. ¡No puedes construir por 12 segundos!");
            player.getInventory().removeItem(createItem());
        } else {
            player.sendMessage(ChatColor.RED + "No se encontró un objetivo válido para la Samurai Ability.");
        }
    }

    private void preventBuilding(Player player, int seconds) {
        // Implementar lógica para prevenir la construcción
        // Esto podría involucrar crear un listener temporal para cancelar eventos de colocación de bloques
    }
}