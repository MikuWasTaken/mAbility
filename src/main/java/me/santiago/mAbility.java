package me.santiago;

import me.santiago.commands.GiveAbilityCommand;
import me.santiago.commands.SetCooldownCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.ChatColor;
import org.bukkit.Effect;

import java.util.HashMap;
import java.util.UUID;

public class mAbility extends JavaPlugin implements Listener {

    private HashMap<UUID, Long> cooldowns = new HashMap<>();
    private HashMap<UUID, UUID> lastAttackers = new HashMap<>();
    private HashMap<UUID, Long> lastAttackTimes = new HashMap<>();
    private AbilityManager abilityManager;

    @Override
    public void onEnable() {
        getLogger().info("mAbility ha sido activado!");
        getServer().getPluginManager().registerEvents(this, this);

        // Inicializar AbilityManager aquí
        abilityManager = new AbilityManager();

        // Registrar comandos
        getCommand("giveability").setExecutor(new GiveAbilityCommand(this));
        getCommand("ability").setExecutor(new SetCooldownCommand(abilityManager));
    }

    @Override
    public void onDisable() {
        getLogger().info("mAbility ha sido desactivado!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;

        // Verifica cada ítem de habilidad
        if (item.isSimilar(createStrength2Item())) {
            useStrength2Item(player);
        } else if (item.isSimilar(createNinjaStarItem())) {
            useNinjaStarItem(player);
        } else if (item.isSimilar(createPocketBardItem())) {
            usePocketBardItem(player);
        } else if (item.isSimilar(createResistance3Item())) {
            useResistance3Item(player);
        } else if (item.isSimilar(createRegeneration3Item())) {
            useRegeneration3Item(player);
        } else if (item.isSimilar(createPortableBardItem())) {
            usePortableBardItem(player);
        } else if (item.isSimilar(createExoticBoneItem())) {
            useExoticBoneItem(player);
        } else if (item.isSimilar(createBerserkItem())) {
            useBerserkItem(player);
        } else if (item.isSimilar(createCloseCallItem())) {
            useCloseCallItem(player);
        } else if (item.isSimilar(createComboAbilityItem())) {
            useComboAbilityItem(player);
        } else if (item.isSimilar(createFocusModeItem())) {
            useFocusModeItem(player);
        } else if (item.isSimilar(createSamuraiAbilityItem())) {
            useSamuraiAbilityItem(player);
        } else if (item.isSimilar(createSwitcherItem())) {
            useSwitcherItem(player);
        } else if (item.isSimilar(createRageBallItem())) {
            useRageBallItem(player);
        }
    }

    // Métodos para crear ítems
    public ItemStack createStrength2Item() {
        return createCustomItem(Material.EGG, ChatColor.RED + "Huevo de Fuerza II");
    }

    public ItemStack createNinjaStarItem() {
        return createCustomItem(Material.NETHER_STAR, ChatColor.DARK_PURPLE + "Estrella Ninja");
    }

    public ItemStack createPocketBardItem() {
        return createCustomItem(Material.BOOK, ChatColor.GOLD + "Pocket Bard");
    }

    public ItemStack createResistance3Item() {
        return createCustomItem(Material.IRON_INGOT, ChatColor.GRAY + "Resistencia 3");
    }

    public ItemStack createRegeneration3Item() {
        return createCustomItem(Material.GHAST_TEAR, ChatColor.LIGHT_PURPLE + "Regeneración 3");
    }

    public ItemStack createPortableBardItem() {
        return createCustomItem(Material.GOLDEN_APPLE, ChatColor.YELLOW + "Portable Bard");
    }

    public ItemStack createExoticBoneItem() {
        return createCustomItem(Material.BONE, ChatColor.WHITE + "Exotic Bone");
    }

    public ItemStack createBerserkItem() {
        return createCustomItem(Material.BLAZE_POWDER, ChatColor.RED + "Berserk");
    }

    public ItemStack createCloseCallItem() {
        return createCustomItem(Material.COOKIE, ChatColor.GOLD + "Close Call");
    }

    public ItemStack createComboAbilityItem() {
        return createCustomItem(Material.DIAMOND_SWORD, ChatColor.AQUA + "Combo Ability");
    }

    public ItemStack createFocusModeItem() {
        return createCustomItem(Material.EYE_OF_ENDER, ChatColor.DARK_PURPLE + "Focus Mode");
    }

    public ItemStack createSamuraiAbilityItem() {
        return createCustomItem(Material.IRON_SWORD, ChatColor.RED + "Samurai Ability");
    }

    public ItemStack createSwitcherItem() {
        return createCustomItem(Material.SNOW_BALL, ChatColor.AQUA + "Switcher");
    }

    public ItemStack createRageBallItem() {
        return createCustomItem(Material.SLIME_BALL, ChatColor.GREEN + "Rage Ball");
    }

    private ItemStack createCustomItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    // Métodos para usar los ítems
    private void useStrength2Item(Player player) {
        if (checkCooldown(player, "strength2", 60)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
            applyEffectToFactionMembers(player, PotionEffectType.INCREASE_DAMAGE, 100, 1);
            player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
            player.sendMessage(ChatColor.GREEN + "¡Has activado Fuerza II por 5 segundos!");
            player.getInventory().removeItem(createStrength2Item());
        }
    }

    private void useNinjaStarItem(Player player) {
        if (checkCooldown(player, "ninjastar", 30)) {
            Player lastAttacker = getLastAttacker(player);
            if (lastAttacker != null) {
                player.teleport(lastAttacker.getLocation());
                player.sendMessage(ChatColor.GREEN + "¡Te has teletransportado a " + lastAttacker.getName() + "!");
                player.getInventory().removeItem(createNinjaStarItem());
            } else {
                player.sendMessage(ChatColor.RED + "No hay jugadores que te hayan atacado recientemente.");
            }
        }
    }

    // Implementa el resto de los métodos para usar los ítems aquí...
    private void usePocketBardItem(Player player) {
        // Implementación para PocketBard
    }

    private void useResistance3Item(Player player) {
        // Implementación para Resistencia3
    }

    private void useRegeneration3Item(Player player) {
        // Implementación para Regeneración3
    }

    private void usePortableBardItem(Player player) {
        // Implementación para PortableBard
    }

    private void useExoticBoneItem(Player player) {
        // Implementación para ExoticBone
    }

    private void useBerserkItem(Player player) {
        // Implementación para Berserk
    }

    private void useCloseCallItem(Player player) {
        // Implementación para CloseCall
    }

    private void useComboAbilityItem(Player player) {
        // Implementación para ComboAbility
    }

    private void useFocusModeItem(Player player) {
        // Implementación para FocusMode
    }

    private void useSamuraiAbilityItem(Player player) {
        // Implementación para SamuraiAbility
    }

    private void useSwitcherItem(Player player) {
        // Implementación para Switcher
    }

    private void useRageBallItem(Player player) {
        // Implementación para RageBall
    }

    private boolean checkCooldown(Player player, String ability, int cooldownTime) {
        if (cooldowns.containsKey(player.getUniqueId())) {
            long secondsLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                player.sendMessage(ChatColor.RED + "Debes esperar " + secondsLeft + " segundos para usar esto de nuevo.");
                return false;
            }
        }
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
        return true;
    }

    private Player getLastAttacker(Player player) {
        UUID lastAttackerUUID = lastAttackers.get(player.getUniqueId());
        if (lastAttackerUUID != null) {
            long timeSinceLastAttack = System.currentTimeMillis() - lastAttackTimes.get(player.getUniqueId());
            if (timeSinceLastAttack <= 10000) { // 10 segundos
                return getServer().getPlayer(lastAttackerUUID);
            }
        }
        return null;
    }

    private void applyEffectToFactionMembers(Player player, PotionEffectType effect, int duration, int amplifier) {
        // Implementa la lógica de la facción aquí
        // Por ahora, aplicaremos el efecto a jugadores cercanos
        for (Player nearbyPlayer : player.getWorld().getPlayers()) {
            if (nearbyPlayer.getLocation().distance(player.getLocation()) <= 10) { // 10 bloques de radio
                nearbyPlayer.addPotionEffect(new PotionEffect(effect, duration, amplifier));
            }
        }
    }
}
