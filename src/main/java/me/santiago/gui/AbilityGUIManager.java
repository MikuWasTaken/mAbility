package me.santiago.gui;

import me.santiago.mAbility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AbilityGUIManager implements Listener {
    private final mAbility plugin;
    private final HashMap<String, AbilityConfig> abilities;
    private final HashMap<String, Inventory> abilityMenus;

    public AbilityGUIManager(mAbility plugin) {
        this.plugin = plugin;
        this.abilities = new HashMap<>();
        this.abilityMenus = new HashMap<>();
        initializeAbilities();
        createAbilityMenus();
    }

    private void initializeAbilities() {
        abilities.put("Strength II", new AbilityConfig("Strength II", Material.BLAZE_POWDER, 60));
        abilities.put("Ninja Star", new AbilityConfig("Ninja Star", Material.NETHER_STAR, 60));
        abilities.put("Pocket Bard", new AbilityConfig("Pocket Bard", Material.INK_SACK, 60));
        abilities.put("Resistance III", new AbilityConfig("Resistance III", Material.IRON_INGOT, 60));
        abilities.put("Regeneration III", new AbilityConfig("Regeneration III", Material.GHAST_TEAR, 60));
        abilities.put("Berserk", new AbilityConfig("Berserk", Material.WATCH, 60));
        abilities.put("Close Call", new AbilityConfig("Close Call", Material.FEATHER, 120));
        abilities.put("Combo Ability", new AbilityConfig("Combo Ability", Material.DIAMOND_SWORD, 180));
        abilities.put("Samurai", new AbilityConfig("Samurai", Material.DIAMOND_SWORD, 60));
        abilities.put("Switcher", new AbilityConfig("Switcher", Material.SNOW_BALL, 30));
        abilities.put("Rage Ball", new AbilityConfig("Rage Ball", Material.FIREWORK, 60));
        abilities.put("Focus Mode", new AbilityConfig("Focus Mode", Material.REDSTONE, 60));
        abilities.put("Exotic Bone", new AbilityConfig("Exotic Bone", Material.BONE, 60));
        abilities.put("Portable Bard", new AbilityConfig("Portable Bard", Material.GOLD_INGOT, 60));
        abilities.put("Time Warp", new AbilityConfig("Time Warp", Material.FEATHER, 40));
        abilities.put("Firework", new AbilityConfig("Firework", Material.FIREWORK, 15));
        abilities.put("Grappling Hook", new AbilityConfig("Grappling Hook", Material.FISHING_ROD, 30));
        abilities.put("Guardian Angel", new AbilityConfig("Guardian Angel", Material.WATCH, 60));

    }

    public void openMainMenu(Player player) {
        Inventory menu = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Abilities Menu");

        for (AbilityConfig ability : abilities.values()) {
            ItemStack item = new ItemStack(ability.getMaterial());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + ability.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Cooldown: " + ability.getCooldown() + "s");
            lore.add("");
            lore.add(ChatColor.YELLOW + "Click to configure!");
            meta.setLore(lore);
            item.setItemMeta(meta);
            menu.addItem(item);
        }

        player.openInventory(menu);
    }

    private void createAbilityMenus() {
        for (AbilityConfig ability : abilities.values()) {
            Inventory menu = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Configure " + ability.getName());

            // Rename option
            ItemStack renameItem = createGuiItem(Material.NAME_TAG,
                    ChatColor.YELLOW + "Rename Ability",
                    Arrays.asList(
                            ChatColor.GRAY + "Current name: " + ability.getName(),
                            "",
                            ChatColor.YELLOW + "Click to change!"
                    ));
            menu.setItem(11, renameItem);

            // Change material option
            ItemStack materialItem = createGuiItem(Material.WORKBENCH,
                    ChatColor.YELLOW + "Change Item",
                    Arrays.asList(
                            ChatColor.GRAY + "Current item: " + ability.getMaterial().name(),
                            "",
                            ChatColor.YELLOW + "Click to change!"
                    ));
            menu.setItem(13, materialItem);

            // Change cooldown option
            ItemStack cooldownItem = createGuiItem(Material.WATCH,
                    ChatColor.YELLOW + "Change Cooldown",
                    Arrays.asList(
                            ChatColor.GRAY + "Current cooldown: " + ability.getCooldown() + "s",
                            "",
                            ChatColor.YELLOW + "Click to change!"
                    ));
            menu.setItem(15, cooldownItem);

            abilityMenus.put(ability.getName(), menu);
        }
    }

    private ItemStack createGuiItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        if (title.equals(ChatColor.GOLD + "Abilities Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            ItemStack clicked = event.getCurrentItem();
            if (clicked.hasItemMeta() && clicked.getItemMeta().hasDisplayName()) {
                String abilityName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
                if (abilityMenus.containsKey(abilityName)) {
                    player.openInventory(abilityMenus.get(abilityName));
                }
            }
        } else if (title.startsWith(ChatColor.GOLD + "Configure ")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;

            String abilityName = title.replace(ChatColor.GOLD + "Configure ", "");
            AbilityConfig ability = abilities.get(abilityName);

            if (ability == null) return;

            switch (event.getSlot()) {
                case 11: // Rename
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "Type the new name for " + abilityName + " in chat:");
                    // Aquí necesitarías implementar un listener para el chat
                    break;
                case 13: // Change material
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "Type the new material for " + abilityName + " in chat:");
                    // Aquí necesitarías implementar un listener para el chat
                    break;
                case 15: // Change cooldown
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "Type the new cooldown in seconds for " + abilityName + " in chat:");
                    // Aquí necesitarías implementar un listener para el chat
                    break;
            }
        }
    }

    private static class AbilityConfig {
        private String name;
        private Material material;
        private int cooldown;

        public AbilityConfig(String name, Material material, int cooldown) {
            this.name = name;
            this.material = material;
            this.cooldown = cooldown;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Material getMaterial() { return material; }
        public void setMaterial(Material material) { this.material = material; }
        public int getCooldown() { return cooldown; }
        public void setCooldown(int cooldown) { this.cooldown = cooldown; }
    }
}