package me.yukun.CrazyEncPlus.Soul;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;

public class SoulTracker implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	private void inventoryClickEvent(InventoryClickEvent event) {
		if (!(!event.isCancelled() && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR
				&& (event.getCurrentItem().getType().name().endsWith("SWORD")
						|| event.getCurrentItem().getType().name().endsWith("AXE"))
				&& event.getCursor() != null && event.getCursor().hasItemMeta()
				&& event.getCursor().getItemMeta().hasDisplayName() && event.getCursor().getItemMeta().hasLore()
				&& event.getCursor().getType().name().endsWith("PAPER"))) {
			return;
		}
		ArrayList<String> lore = new ArrayList<String>();
		Player player = (Player) event.getWhoClicked();
		ItemStack currentItem = event.getCurrentItem();
		ItemMeta currentItemMeta = currentItem.getItemMeta();
		if (currentItem.hasItemMeta() && currentItem.getItemMeta().hasLore()) {
			for (int i = 0; i <= 199; ++i) {
				if (currentItem.getItemMeta().getLore()
						.contains(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Simple.ApplyLore")
								.replace("%souls%", "" + i)))
						|| currentItem.getItemMeta().getLore()
								.contains(Methods.color(Main.settings.getSoulTracker()
										.getString("SoulTrackers.Unique.ApplyLore").replace("%souls%", "" + i)))
						|| currentItem.getItemMeta().getLore()
								.contains(Methods.color(Main.settings.getSoulTracker()
										.getString("SoulTrackers.Elite.ApplyLore").replace("%souls%", "" + i)))
						|| currentItem.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes(
								(char) '&',
								(String) Main.settings.getSoulTracker().getString("SoulTrackers.Ultimate.ApplyLore")
										.replace("%souls%", "" + i)))
						|| currentItem.getItemMeta().getLore().contains(Methods.color(Main.settings.getSoulTracker()
								.getString("SoulTrackers.Legendary.ApplyLore").replace("%souls%", "" + i)))) {
					return;
				}
			}
			String soulTrackerRarity = null;
			if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Simple.Name")))) {
				soulTrackerRarity = "Simple";
			} else if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Unique.Name")))) {
				soulTrackerRarity = "Unique";
			} else if (event.getCursor().getItemMeta().getDisplayName()
					.equalsIgnoreCase(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Elite.Name")))) {
				soulTrackerRarity = "Elite";
			} else if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Ultimate.Name")))) {
				soulTrackerRarity = "Ultimate";
			} else if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Legendary.Name")))) {
				soulTrackerRarity = "Legendary";
			} else {
				return;
			}
			lore.addAll(currentItemMeta.getLore());
			lore.add(0, Methods.color(Main.settings.getSoulTracker()
					.getString("SoulTrackers." + soulTrackerRarity + ".ApplyLore").replace("%souls%", "0")));
			currentItemMeta.setLore(lore);
			currentItem.setItemMeta(currentItemMeta);
			if (event.getCursor().getAmount() > 1) {
				event.getCursor().setAmount(event.getCursor().getAmount() - 1);
			} else {
				event.setCursor(new ItemStack(Material.AIR));
			}
			event.setCurrentItem(currentItem);
			event.setCancelled(true);
			for (int i2 = 1; i2 <= 10; ++i2) {
				player.getWorld().playEffect(player.getEyeLocation(), Effect.SPELL, 1);
			}
			player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			player.updateInventory();
			return;
		}
		if (!currentItem.hasItemMeta() || currentItem.hasItemMeta() && !currentItem.getItemMeta().hasLore()) {
			String soulTrackerRarity = null;
			if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Simple.Name")))) {
				soulTrackerRarity = "Simple";
			} else if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Unique.Name")))) {
				soulTrackerRarity = "Unique";
			} else if (event.getCursor().getItemMeta().getDisplayName()
					.equalsIgnoreCase(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Elite.Name")))) {
				soulTrackerRarity = "Elite";
			} else if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Ultimate.Name")))) {
				soulTrackerRarity = "Ultimate";
			} else if (event.getCursor().getItemMeta().getDisplayName().equalsIgnoreCase(
					Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Legendary.Name")))) {
				soulTrackerRarity = "Legendary";
			} else {
				return;
			}
			event.setCancelled(true);
			lore.add(0, Methods.color(Main.settings.getSoulTracker()
					.getString("SoulTrackers." + soulTrackerRarity + ".ApplyLore").replace("%souls%", "0")));
			currentItemMeta.setLore(lore);
			currentItem.setItemMeta(currentItemMeta);
			if (event.getCursor().getAmount() > 1) {
				event.getCursor().setAmount(event.getCursor().getAmount() - 1);
			} else {
				event.setCursor(new ItemStack(Material.AIR));
			}
			event.setCurrentItem(currentItem);
			for (int i = 1; i <= 10; ++i) {
				player.getWorld().playEffect(player.getEyeLocation(), Effect.SPELL, 1);
			}
			player.getWorld().playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			player.updateInventory();
			return;
		}
	}
}
