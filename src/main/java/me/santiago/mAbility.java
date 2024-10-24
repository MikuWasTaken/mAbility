package me.santiago;

import me.santiago.abilities.*;
import me.santiago.commands.GiveAbilityAllCommand;
import me.santiago.commands.GiveAbilityCommand;
import me.santiago.commands.SetCooldownCommand;
import me.santiago.commands.AbilityMenuCommand;
import me.santiago.gui.AbilityGUIManager;
import me.santiago.gui.AbilityChatListener;
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
    private AbilityGUIManager guiManager;
    private AbilityChatListener chatListener;

    private ExoticBone exoticBone;
    private FocusMode focusMode;
    private PortableBard portableBard;
    private RageBall rageBall;
    private CloseCall closeCall;
    private Switcher switcher;
    private ComboAbility comboAbility;
    private Firework firework;
    private TimeWarp timeWarp;
    private GuardianAngel guardianAngel;
    private GrapplingHook grapplingHook;

    @Override
    public void onEnable() {
        getLogger().info("mAbility ha sido activado!");
        getServer().getPluginManager().registerEvents(this, this);

        abilityManager = new AbilityManager();
        guiManager = new AbilityGUIManager(this);
        chatListener = new AbilityChatListener(guiManager);

        exoticBone = new ExoticBone(this);
        focusMode = new FocusMode(this);
        portableBard = new PortableBard(this);
        rageBall = new RageBall(this);
        closeCall = new CloseCall();
        switcher = new Switcher();
        comboAbility = new ComboAbility();
        firework = new Firework();
        grapplingHook = new GrapplingHook();
        guardianAngel = new GuardianAngel();


        getCommand("giveability").setExecutor(new GiveAbilityCommand(this));
        getCommand("ability").setExecutor(new SetCooldownCommand(abilityManager));
        getCommand("giveability").setExecutor(new GiveAbilityAllCommand(this));

        AbilityMenuCommand abilityMenuCommand = new AbilityMenuCommand(this, guiManager);
        getCommand("ability").setExecutor(abilityMenuCommand);
        getCommand("ability").setTabCompleter(abilityMenuCommand);

        // Registro de listeners
        getServer().getPluginManager().registerEvents(focusMode, this);
        getServer().getPluginManager().registerEvents(rageBall, this);
        getServer().getPluginManager().registerEvents(closeCall, this);
        getServer().getPluginManager().registerEvents(switcher, this);
        getServer().getPluginManager().registerEvents(comboAbility, this);
        getServer().getPluginManager().registerEvents(firework, this);
        getServer().getPluginManager().registerEvents(grapplingHook, this);
        getServer().getPluginManager().registerEvents(timeWarp,this);
        getServer().getPluginManager().registerEvents(guardianAngel, this);
        getServer().getPluginManager().registerEvents(new PocketBard(), this);
        getServer().getPluginManager().registerEvents(guiManager, this);
        getServer().getPluginManager().registerEvents(chatListener, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("mAbility ha sido desactivado!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR) return;

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
        } else if (item.isSimilar(exoticBone.createItem())) {
            exoticBone.use(player);
        } else if (item.isSimilar(focusMode.createItem())) {
            focusMode.use(player);
        } else if (item.isSimilar(portableBard.createItem())) {
            portableBard.use(player);
        } else if (item.isSimilar(rageBall.createItem())) {
            rageBall.use(player);
        } else if (item.isSimilar(createBerserkItem())) {
            useBerserkItem(player);
        } else if (item.isSimilar(closeCall.createItem())) {
            useCloseCallItem(player);
        } else if (item.isSimilar(createComboAbilityItem())) {
            useComboAbilityItem(player);
        } else if (item.isSimilar(createSamuraiAbilityItem())) {
            useSamuraiAbilityItem(player);
        } else if (item.isSimilar(createSwitcherItem())) {
            useSwitcherItem(player);
        }
    }

    // Métodos para crear ítems
    public ItemStack createStrength2Item() {
        return createCustomItem(Material.BLAZE_POWDER, ChatColor.RED + "Fuerza II");
    }

    public ItemStack createNinjaStarItem() {
        return createCustomItem(Material.NETHER_STAR, ChatColor.DARK_PURPLE + "Estrella Ninja");
    }

    public ItemStack createPocketBardItem() {
        return createCustomItem(Material.INK_SACK, ChatColor.GOLD + "Pocket Bard");
    }

    public ItemStack createResistance3Item() {
        return createCustomItem(Material.IRON_INGOT, ChatColor.GRAY + "Resistencia III");
    }

    public ItemStack createRegeneration3Item() {
        return createCustomItem(Material.GHAST_TEAR, ChatColor.LIGHT_PURPLE + "Regeneración III");
    }

    public ItemStack createBerserkItem() {
        return createCustomItem(Material.WATCH, ChatColor.RED + "Berserk");
    }

    public ItemStack createCloseCallItem() {
        return closeCall.createItem();
    }

    public ItemStack createComboAbilityItem() {
        return comboAbility.createItem();
    }

    public ItemStack createSamuraiAbilityItem() {
        return createCustomItem(Material.DIAMOND_SWORD, ChatColor.RED + "Samurai Ability");
    }

    public ItemStack createSwitcherItem() {
        return switcher.createItem();
    }

    public ItemStack createRageBallItem() {
        return rageBall.createItem();
    }

    public ItemStack createFocusModeItem() {
        return focusMode.createItem();
    }

    public ItemStack createExoticBoneItem() {
        return exoticBone.createItem();
    }

    public ItemStack createPortableBardItem() {
        return portableBard.createItem();
    }

    public ItemStack createFireworkItem() {
        return firework.createItem();
    }

    public ItemStack createGrapplingHookItem() {
        return grapplingHook.createItem();
    }

    public ItemStack createGuardianAngelItem() {
        return guardianAngel.createItem();
    }

    public ItemStack createTimeWarpItem() {
        return timeWarp.createItem();
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
        if (checkCooldown(player, "strength2", 1)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
            applyEffectToFactionMembers(player, PotionEffectType.INCREASE_DAMAGE, 100, 1);
            player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
            player.sendMessage(ChatColor.GREEN + "¡Has activado Fuerza II por 5 segundos!");
            player.getInventory().removeItem(createStrength2Item());
        }
    }

    private void useNinjaStarItem(Player player) {
        if (checkCooldown(player, "ninjastar", 1)) {
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

    private void usePocketBardItem(Player player) {
        if (checkCooldown(player, "pocketbard", 0)) {
            PocketBard pocketBard = new PocketBard();
            pocketBard.use(player);
            player.getInventory().removeItem(createPocketBardItem());
        }
    }

    private void useResistance3Item(Player player) {
        if (checkCooldown(player, "resistance3", 1)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 2));
            applyEffectToFactionMembers(player, PotionEffectType.DAMAGE_RESISTANCE, 100, 2);
            player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 0);
            player.sendMessage(ChatColor.GREEN + "¡Has activado Resistencia III por 5 segundos!");
            player.getInventory().removeItem(createResistance3Item());
        }
    }

    private void useRegeneration3Item(Player player) {
        if (checkCooldown(player, "regeneration3", 1)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
            applyEffectToFactionMembers(player, PotionEffectType.REGENERATION, 100, 2);
            player.getWorld().playEffect(player.getLocation(), Effect.POTION_BREAK, 0);
            player.sendMessage(ChatColor.GREEN + "¡Has activado Regeneracion III por 5 segundos!");
            player.getInventory().removeItem(createRegeneration3Item());
        }
    }

    private void useBerserkItem(Player player) {
        if (checkCooldown(player, "berserk", 1)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 240, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 240, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 240, 1));
            player.sendMessage(ChatColor.GREEN + "¡Has activado Berserk por 12 segundos!");
            player.getInventory().removeItem(createBerserkItem());
        }
    }

    private void useCloseCallItem(Player player) {
        if (checkCooldown(player, "closecall", 120)) {
            player.sendMessage(ChatColor.GREEN + "¡Close Call está listo para ser activado!");
            player.getInventory().removeItem(closeCall.createItem());
        }
    }

    private void useComboAbilityItem(Player player) {
        if (checkCooldown(player, "comboability", 180)) {
            player.sendMessage(ChatColor.RED + "¡Combo Ability activado! Golpea a tus enemigos para aumentar tu combo.");
        }
    }

    private void useSamuraiAbilityItem(Player player) {
        if (checkCooldown(player, "samuraiability", 1)) {
            Player lastAttacker = getLastAttacker(player);
            if (lastAttacker != null) {
                player.teleport(lastAttacker.getLocation());
                player.sendMessage(ChatColor.GREEN + "¡Te has teletransportado a " + lastAttacker.getName() + "!");
                player.getInventory().removeItem(createSamuraiAbilityItem());
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 240, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 2));
            } else {
                player.sendMessage(ChatColor.RED + "No hay jugadores que te hayan atacado recientemente.");
            }
        }
    }

    private void useSwitcherItem(Player player) {
        if (checkCooldown(player, "switcher", 30)) {
            switcher.use(player);
        }
    }

    private boolean checkCooldown(Player player, String ability, int cooldownTime) {
        long currentTime = System.currentTimeMillis() / 1000;
        if (cooldowns.containsKey(player.getUniqueId())) {
            long lastUsedTime = cooldowns.get(player.getUniqueId()) / 1000;
            long secondsLeft = lastUsedTime + cooldownTime - currentTime;
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
            if (timeSinceLastAttack <= 10000) {
                return getServer().getPlayer(lastAttackerUUID);
            }
        }
        return null;
    }

    private void applyEffectToFactionMembers(Player player, PotionEffectType effect, int duration, int amplifier) {
        for (Player nearbyPlayer : player.getWorld().getPlayers()) {
            if (nearbyPlayer.getLocation().distance(player.getLocation()) <= 10) {
                nearbyPlayer.addPotionEffect(new PotionEffect(effect, duration, amplifier));
            }
        }
    }
}