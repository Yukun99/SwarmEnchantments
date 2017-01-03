package me.yukun.CrazyEncPlus.Soul;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;

public class SplitSouls implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage((Object) ChatColor.RED + "Only players can execute this command!");
			return true;
		}
		Player player = (Player) sender;
		if (player.getInventory().getItemInHand() == null || !player.getInventory().getItemInHand().hasItemMeta()
				|| !player.getInventory().getItemInHand().getItemMeta().hasLore()) {
			player.sendMessage(
					Methods.color(Main.settings.getSoulTracker().getString("SplitSouls.Messages.ItemNoSoulTracker")));
			return true;
		}
		if (args.length == 0) {
			if (player.getInventory().getItemInHand() != null) {
				if (player.getInventory().getItemInHand().hasItemMeta()) {
					if (player.getInventory().getItemInHand().getItemMeta().hasLore()) {
						if (player.getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
							if (player.getInventory().getItemInHand().getItemMeta().getLore()
									.contains(Methods.color(Main.settings.getSoulTracker()
											.getString("SoulTrackers.Legendary.ApplyLore").replace("%souls%", "0")))
									|| player.getInventory().getItemInHand().getItemMeta().getLore()
											.contains(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulTrackers.Ultimate.ApplyLore")
													.replace("%souls%", "0")))
									|| player.getInventory().getItemInHand().getItemMeta().getLore()
											.contains(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulTrackers.Elite.ApplyLore").replace("%souls%", "0")))
									|| player.getInventory().getItemInHand().getItemMeta().getLore()
											.contains(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulTrackers.Unique.ApplyLore")
													.replace("%souls%", "0")))
									|| player.getInventory().getItemInHand().getItemMeta().getLore()
											.contains(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulTrackers.Simple.ApplyLore")
													.replace("%souls%", "0")))) {
								player.sendMessage(Methods.color(
										Main.settings.getSoulTracker().getString("SplitSouls.Messages.NoSouls")));
								return true;
							}
							if (player.getInventory().getItemInHand().getItemMeta().getLore()
									.contains(Methods.color("&c&l* &r&cClick this item to activate &nSoul Mode"))) {
								if (player.getInventory().getItemInHand().getItemMeta().getDisplayName()
										.contains(Methods.color("&c&lSoul Gem"))) {
									ItemStack item = player.getItemInHand();
									int amount = Methods.getArgument("%souls%", item,
											Main.settings.getSoulTracker().getString("SoulGemOptions.Name"));
									if (amount == 0) {
										player.sendMessage(Methods.color(Main.settings.getSoulTracker()
												.getString("SplitSouls.Messages.NoSouls")));
										return true;
									}
								}
							}
						}
					}
				}
			}
			ItemStack gem = new ItemStack(Material.EMERALD, 1);
			ItemMeta gemMeta = gem.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			ItemStack itemInHand = player.getInventory().getItemInHand();
			ItemMeta itemInHandMeta = itemInHand.getItemMeta();
			for (String line : Main.settings.getSoulTracker().getStringList("SoulGemOptions.Lore")) {
				lore.add(0, Methods.color(line));
			}
			ItemStack gem2 = new ItemStack(Material.EMERALD, 1);
			ItemMeta gem2Meta = gem2.getItemMeta();
			for (int i = 1; i <= 199; ++i) {
				if (player.getInventory().getItemInHand().getItemMeta().getLore().contains(Methods.color(Main.settings
						.getSoulTracker().getString("SoulTrackers.Legendary.ApplyLore").replace("%souls%", "" + i)))) {
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.SplitLegendary").replace("%souls%", "" + i)));
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.AddSoulGems").replace("%souls%", "" + i * 5)));
					gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
							.replace("%souls%", "" + i * 5)));
					gemMeta.setLore(lore);
					gem.setItemMeta(gemMeta);
					lore.clear();
					player.getInventory().addItem(new ItemStack[] { gem });
					lore.addAll(itemInHandMeta.getLore());
					lore.remove(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Legendary.ApplyLore")
							.replace("%souls%", "" + i)));
					lore.add(0, Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Legendary.ApplyLore")
							.replace("%souls%", "0")));
					itemInHandMeta.setLore(lore);
					itemInHand.setItemMeta(itemInHandMeta);
					player.getInventory().setItemInHand(itemInHand);
					player.updateInventory();
					return true;
				}
				if (player.getInventory().getItemInHand().getItemMeta().getLore().contains(Methods.color(Main.settings
						.getSoulTracker().getString("SoulTrackers.Ultimate.ApplyLore").replace("%souls%", "" + i)))) {
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.SplitUltimate").replace("%souls%", "" + i)));
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.AddSoulGems").replace("%souls%", "" + i * 4)));
					gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
							.replace("%souls%", "" + i * 4)));
					gemMeta.setLore(lore);
					gem.setItemMeta(gemMeta);
					lore.clear();
					player.getInventory().addItem(new ItemStack[] { gem });
					lore.addAll(itemInHandMeta.getLore());
					lore.remove(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Ultimate.ApplyLore")
							.replace("%souls%", "" + i)));
					lore.add(0, Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Ultimate.ApplyLore")
							.replace("%souls%", "0")));
					itemInHandMeta.setLore(lore);
					itemInHand.setItemMeta(itemInHandMeta);
					player.getInventory().setItemInHand(itemInHand);
					player.updateInventory();
					return true;
				}
				if (player.getInventory().getItemInHand().getItemMeta().getLore().contains(Methods.color(Main.settings
						.getSoulTracker().getString("SoulTrackers.Elite.ApplyLore").replace("%souls%", "" + i)))) {
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.SplitElite").replace("%souls%", "" + i)));
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.AddSoulGems").replace("%souls%", "" + i * 3)));
					gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
							.replace("%souls%", "" + i * 3)));
					gemMeta.setLore(lore);
					gem.setItemMeta(gemMeta);
					lore.clear();
					player.getInventory().addItem(new ItemStack[] { gem });
					lore.addAll(itemInHandMeta.getLore());
					lore.remove(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Elite.ApplyLore")
							.replace("%souls%", "" + i)));
					lore.add(0, Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Elite.ApplyLore")
							.replace("%souls%", "0")));
					itemInHandMeta.setLore(lore);
					itemInHand.setItemMeta(itemInHandMeta);
					player.getInventory().setItemInHand(itemInHand);
					player.updateInventory();
					return true;
				}
				if (player.getInventory().getItemInHand().getItemMeta().getLore().contains(Methods.color(Main.settings
						.getSoulTracker().getString("SoulTrackers.Unique.ApplyLore").replace("%souls%", "" + i)))) {
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.SplitUnique").replace("%souls%", "" + i)));
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.AddSoulGems").replace("%souls%", "" + i * 2)));
					gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
							.replace("%souls%", "" + i * 2)));
					gemMeta.setLore(lore);
					gem.setItemMeta(gemMeta);
					lore.clear();
					player.getInventory().addItem(new ItemStack[] { gem });
					lore.addAll(itemInHandMeta.getLore());
					lore.remove(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Unique.ApplyLore")
							.replace("%souls%", "" + i)));
					lore.add(0, Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Unique.ApplyLore")
							.replace("%souls%", "0")));
					itemInHandMeta.setLore(lore);
					itemInHand.setItemMeta(itemInHandMeta);
					player.getInventory().setItemInHand(itemInHand);
					player.updateInventory();
					return true;
				}
				if (player.getInventory().getItemInHand().getItemMeta().getLore().contains(Methods.color(Main.settings
						.getSoulTracker().getString("SoulTrackers.Simple.ApplyLore").replace("%souls%", "" + i)))) {
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.SplitSimple").replace("%souls%", "" + i)));
					player.sendMessage(Methods.color(Main.settings.getSoulTracker()
							.getString("SplitSouls.Messages.AddSoulGems").replace("%souls%", "" + i * 1)));
					gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
							.replace("%souls%", "" + i * 1)));
					gemMeta.setLore(lore);
					gem.setItemMeta(gemMeta);
					lore.clear();
					player.getInventory().addItem(new ItemStack[] { gem });
					lore.addAll(itemInHandMeta.getLore());
					lore.remove(Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Simple.ApplyLore")
							.replace("%souls%", "" + i)));
					lore.add(0, Methods.color(Main.settings.getSoulTracker().getString("SoulTrackers.Simple.ApplyLore")
							.replace("%souls%", "0")));
					itemInHandMeta.setLore(lore);
					itemInHand.setItemMeta(itemInHandMeta);
					player.getInventory().setItemInHand(itemInHand);
					player.updateInventory();
					return true;
				}
				if (player.getInventory().getItemInHand().getItemMeta().getLore()
						.contains(Methods.color("&c&l* &r&cClick this item to activate &nSoul Mode"))) {
					ItemStack item = player.getItemInHand();
					player.sendMessage(
							Methods.color("&6Swarm&eEnchants&f >> &7Your &c&lSoul Gem &r&7has been split into 2!"));
					int amount = Methods.getArgument("%souls%", item,
							Main.settings.getSoulTracker().getString("SoulGemOptions.Name"));
					if ((amount & 1) == 0) {
						gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
								.replace("%souls%", "" + amount / 2)));
						gemMeta.setLore(lore);
						gem.setItemMeta(gemMeta);
						lore.clear();
						player.getInventory().removeItem(item);
						player.getInventory().addItem(new ItemStack[] { gem });
						player.getInventory().addItem(new ItemStack[] { gem });
						player.updateInventory();
						return true;
					}
					if ((amount & 1) != 0) {
						gemMeta.setDisplayName(Methods.color(Main.settings.getSoulTracker().getString("SoulGemOptions.Name")
								.replace("%souls%", "" + (amount + 1) / 2)));
						gem2Meta.setDisplayName(Methods.color(Main.settings.getSoulTracker()
								.getString("SoulGemOptions.Name").replace("%souls%", "" + (amount - 1) / 2)));
						gemMeta.setLore(lore);
						gem.setItemMeta(gemMeta);
						gem2Meta.setLore(lore);
						gem2.setItemMeta(gem2Meta);
						lore.clear();
						player.getInventory().removeItem(item);
						player.getInventory().addItem(new ItemStack[] { gem });
						player.getInventory().addItem(new ItemStack[] { gem2 });
						player.updateInventory();
						return true;
					}
				}
			}
		} else {
			if (args.length >= 1) {
				player.sendMessage(Methods.color(
						"&6Swarm&eEnchants&f >> &7You do not need to type anything other than /splitsouls in chat."));
				return true;
			}
			return true;
		}
		return true;
	}
}
