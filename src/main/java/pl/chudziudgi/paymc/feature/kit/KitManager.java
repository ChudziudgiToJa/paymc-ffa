package pl.chudziudgi.paymc.feature.kit;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import pl.chudziudgi.paymc.util.ItemBuilder;

public class KitManager {
    public static void giveKit(final Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).hide(true).build());
        inventory.setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).hide(true).build());
        inventory.setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).hide(true).build());
        inventory.setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).hide(true).build());

        inventory.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).setTitle("&8Miecz: &3klasyczny").hide(true).build());
        inventory.setItem(1, new ItemBuilder(Material.GOLDEN_SWORD).setTitle("&8Miecz: &3bez obrażeń").hide(true).build());
        inventory.setItem(2, new ItemBuilder(Material.IRON_SWORD).setTitle("&8Miecz: &3bez krytow").hide(true).build());
    }
}