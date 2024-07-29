package br.dev.phsaraiva.windCharge.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.dev.phsaraiva.windCharge.WindCharge;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;



public enum EnumTools {

    WIND_CHARGE;

    public static boolean giveItem(Player player, EnumTools type, int amount) {
        if (countFreeSlots(player) < 1) {
            return false;
        }

        FileConfiguration config = WindCharge.getInstance().getConfig();
        String wb = "windBurstEnchantment.windburst_";
        ItemStack hand = player.getInventory().getItemInMainHand();
        hand = (hand == null || hand.getType() == Material.AIR) ? new ItemStack(Material.ENCHANTED_BOOK, amount) : hand;

        switch (type) {
            case WIND_CHARGE:
                player.getInventory().addItem(setItemMeta(config, new ItemStack(WindCharge.SNOWBALL, amount), "windCharge.name", "windCharge.lore"));
                break;
        }
        player.updateInventory();
        return true;
    }

    private static ItemStack setItemMeta(FileConfiguration config, ItemStack item, String nameNode, String loreNode) {
        ItemMeta meta = item.getItemMeta();
        if (config.getString(nameNode) != null && !config.getString(nameNode).isEmpty()) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(nameNode)));
        }
        if (loreNode.contains("name")) {
            if (config.getString(loreNode) != null && !config.getString(loreNode).isEmpty()) {
                meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&7" + config.getString(loreNode))));
            }
        } else {
            if (config.getStringList(loreNode) != null && !config.getStringList(loreNode).isEmpty()) {
                List<String> text = new ArrayList<>();
                for (String line : config.getStringList(loreNode)) {
                    text.add(ChatColor.translateAlternateColorCodes('&', line));
                }
                Collections.reverse(text);
                meta.setLore(text);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    public static int countFreeSlots(Player player) {
        int i = 0;
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item == null || item.getType() == Material.AIR) {
                i++;
            }
        }
        return i;
    }
}