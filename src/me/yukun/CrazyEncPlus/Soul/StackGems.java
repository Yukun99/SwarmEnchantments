package me.yukun.CrazyEncPlus.Soul;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;

public class StackGems implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOW)
	public void onStackGem(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		Inventory inv = e.getInventory();
		ItemStack item = e.getCurrentItem();
		ItemStack item2 = e.getCursor();
		ArrayList<String> lore = new ArrayList<String>();
		for (String line : Main.settings.getSoulTracker().getStringList("SoulGemOptions.Lore")) {
			lore.add(Methods.color(line));
		}
		if (inv != null) {
			if (item != null && item2 != null) {
				if (item.getType() == Material.EMERALD && item2.getType() == Material.EMERALD) {
					if (item.hasItemMeta() && item2.hasItemMeta()) {
						if (item.getItemMeta().hasDisplayName() && item2.getItemMeta().hasDisplayName()) {
							if (Methods.color(item.getItemMeta().getDisplayName()).contains(Methods.color("&c&lSoul Gem"))
									&& Methods.color(item2.getItemMeta().getDisplayName())
											.contains(Methods.color("&c&lSoul Gem"))) {
								if (item.getItemMeta().hasLore() && item2.getItemMeta().hasLore()) {
									if (item2.getItemMeta().getLore()
											.contains(Methods.color("&c&l* &r&cClick this item to activate &nSoul Mode"))) {
										if (item.getAmount() == 1
												&& (item2.getAmount() == 2 || item2.getAmount() == 1)) {
											e.setCancelled(true);
											ItemStack gem = new ItemStack(Material.EMERALD, 1);
											ItemMeta gemMeta = gem.getItemMeta();
											int amount = Methods.getArgument("%souls%", item,
													Main.settings.getSoulTracker().getString("SoulGemOptions.Name"));
											int amount2 = Methods.getArgument("%souls%", item2,
													Main.settings.getSoulTracker().getString("SoulGemOptions.Name"));
											int totalsouls = amount + amount2;
											gemMeta.setDisplayName(Methods.color(
													Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
															.replace("%souls%", "" + totalsouls)));
											gemMeta.setLore(lore);
											gem.setItemMeta(gemMeta);
											e.setCursor(new ItemStack(Material.AIR));
											e.setCurrentItem(gem);
											player.updateInventory();
											for (int i2 = 1; i2 <= 10; ++i2) {
												player.getWorld().playEffect(player.getEyeLocation(), Effect.SPELL, 1);
											}
											player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f,
													1.0f);
										}
									}
								} else {
									player.sendMessage(Methods.color(
											"&6Swarm&eEnchants &f>> &cYou can only stack 1 gem onto 1 gem at once!"));
								}
							}
						}
					}
				}
			}
		}
	}
}