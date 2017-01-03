package me.yukun.CrayEncPlus.core;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.SettingsManager;

public class Givedp implements CommandExecutor {
	String SIname = "noitem";
	int amount = 0;

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player psender = (Player) sender;
			if (psender != null) {
				if (psender.isOnline()) {
					if (psender.hasPermission("CrayEncPlus.givedp")) {
						ItemStack item = new ItemStack(Material.ACACIA_DOOR, 1);
						ItemMeta itemMeta = item.getItemMeta();
						ArrayList<String> lore = new ArrayList<String>();
						int amount = 1;
						String type = null;
						String type2 = null;
						if (args.length == 0) {
							sender.sendMessage(
									Methods.color("&6Swarm&eEnchants&f >> &7") + "/givedp <player> <item> <amount>");
							return true;
						}
						if (args.length == 1) {
							Player player = psender;
							if (player.isOnline()) {
								if (amount != 0) {
									if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("simplesoultracker")
											|| args[0].equalsIgnoreCase("simplest")) {
										item = new ItemStack(Material.PAPER, amount);
										type = "SoulTrackers.Simple.";
										SIname = "&fSimple Soul Tracker(s)";
										// psender.sendMessage("test1");
									} else if (args[0].equalsIgnoreCase("2")
											|| args[0].equalsIgnoreCase("uniquesoultracker")
											|| args[0].equalsIgnoreCase("uniquest")) {
										item = new ItemStack(Material.PAPER, amount);
										type = "SoulTrackers.Unique.";
										SIname = "&aUnique Soul Tracker(s)";
										// psender.sendMessage("test2");
									} else if (args[0].equalsIgnoreCase("3")
											|| args[0].equalsIgnoreCase("elitesoultracker")
											|| args[0].equalsIgnoreCase("elitest")) {
										item = new ItemStack(Material.PAPER, amount);
										type = "SoulTrackers.Elite.";
										SIname = "&bRare Soul Tracker(s)";
										// psender.sendMessage("test3");
									} else if (args[0].equalsIgnoreCase("4")
											|| args[0].equalsIgnoreCase("ultimatesoultracker")
											|| args[0].equalsIgnoreCase("ultimatest")) {
										item = new ItemStack(Material.PAPER, amount);
										type = "SoulTrackers.Ultimate.";
										SIname = "&eUltimate Soul Tracker(s)";
										// psender.sendMessage("test4");
									} else if (args[0].equalsIgnoreCase("5")
											|| args[0].equalsIgnoreCase("legendarysoultracker")
											|| args[0].equalsIgnoreCase("legendaryst")) {
										item = new ItemStack(Material.PAPER, amount);
										type = "SoulTrackers.Legendary.";
										SIname = "&6Legendary Soul Tracker(s)";
										// psender.sendMessage("test5");
									} else if (args[0].equalsIgnoreCase("6") || args[0].equalsIgnoreCase("soulgem")
											|| args[0].equalsIgnoreCase("sgem")) {
										item = new ItemStack(Material.EMERALD, amount);
										type = "SoulGemOptions.";
										SIname = "&aSoul Gem(s)";
										// psender.sendMessage("test6");
									} else if (args[0].equalsIgnoreCase("7") || args[0].equalsIgnoreCase("transmog")
											|| args[0].equalsIgnoreCase("transmogscroll")) {
										item = new ItemStack(Material.PAPER, amount);
										type = "TransmogOptions.";
										SIname = "&eTransmog Scroll(s)";
										// psender.sendMessage("test7");
									} else if (args[0].equalsIgnoreCase("8")
											|| args[0].equalsIgnoreCase("rankquestitem1")
											|| args[0].equalsIgnoreCase("rqitem1")) {
										item = new ItemStack(Material.MAGMA_CREAM, amount);
										type = "RankQuestOptions.";
										type2 = "SoldierBee.";
										SIname = "&eRank Quest Item(s)";
										// psender.sendMessage("test7");
									} else if (args[0].equalsIgnoreCase("9")
											|| args[0].equalsIgnoreCase("rankquestitem2")
											|| args[0].equalsIgnoreCase("rqitem2")) {
										item = new ItemStack(Material.MAGMA_CREAM, amount);
										type = "RankQuestOptions.";
										type2 = "Wasp.";
										SIname = "&eRank Quest Item(s)";
										// psender.sendMessage("test7");
									} else if (args[0].equalsIgnoreCase("10")
											|| args[0].equalsIgnoreCase("rankquestitem2")
											|| args[0].equalsIgnoreCase("rqitem2")) {
										item = new ItemStack(Material.MAGMA_CREAM, amount);
										type = "RankQuestOptions.";
										type2 = "QueenBee.";
										SIname = "&eRank Quest Item(s)";
										// psender.sendMessage("test7");
									} else {
										psender.sendMessage(Methods.color("&6Swarm&eEnchants &f>> &cInvalid item!"));
										return true;
									}
									// psender.sendMessage(type);
									if (item != null) {
										if (item.getType() != Material.ACACIA_DOOR) {
											if (type.startsWith("SoulTrackers.")) {
												String dname = Methods.color(SettingsManager.getInstance().getSoulTracker()
														.getString(type + "Name"));
												itemMeta.setDisplayName(dname);
												lore.add(Methods.color(SettingsManager.getInstance().getSoulTracker()
														.getString(String.valueOf(type) + "Lore1")));
												lore.add(Methods.color(SettingsManager.getInstance().getSoulTracker()
														.getString(String.valueOf(type) + "Lore2")));
												itemMeta.setLore(lore);
												// psender.sendMessage(type);
												// psender.sendMessage(dname);
												// for (String line : lore)
												// {
												// psender.sendMessage(line);
												// }
											} else if (type == "SoulGemOptions.") {
												String dname = Methods.color(SettingsManager.getInstance().getSoulTracker()
														.getString(type + "Name").replace("%souls%", "10"));
												itemMeta.setDisplayName(dname);
												for (String line : Main.settings.getSoulTracker()
														.getStringList("SoulGemOptions.Lore")) {
													lore.add(Methods.color(line));
													// psender.sendMessage(line);
												}
												itemMeta.setLore(lore);
												// psender.sendMessage(type);
												// psender.sendMessage(dname);
											} else if (type == "TransmogOptions.") {
												String dname = Methods.color(SettingsManager.getInstance().getSoulTracker()
														.getString(type + "Name"));
												itemMeta.setDisplayName(dname);
												for (String line : Main.settings.getSoulTracker()
														.getStringList("TransmogOptions.Lore")) {
													lore.add(Methods.color(line));
													// psender.sendMessage(Api.color(line));
												}
												itemMeta.setLore(lore);
												// psender.sendMessage(type);
												// psender.sendMessage(Api.color(dname));
											} else if (type == "RankQuestOptions.") {
												String dname = Methods.color(SettingsManager.getInstance().getSoulTracker()
														.getString(type + "Name").replace("%rank%",
																Main.settings.getSoulTracker().getString(type + type2 + "Rank")));
												itemMeta.setDisplayName(Methods.color(dname));
												for (String line : Main.settings.getSoulTracker()
														.getStringList("RankQuestOptions.Lore")) {
													String line2 = line.replace("%rank%",
														Main.settings.getSoulTracker().getString(type + type2 + "Rank"));
													String line3 = line2.replace("%rankvoucher%",
															Main.settings.getSoulTracker().getString(type + type2 + "Rank") + " &bVoucher");
													lore.add(Methods.color(line3));
													psender.sendMessage(Methods.color(line3));
												}
												itemMeta.setLore(lore);
												// psender.sendMessage(type);
												// psender.sendMessage(Api.color(dname));
											} else {
												return true;
											}
										}
									}
									// psender.sendMessage(itemMeta.getDisplayName());
									if (player != null && player.isOnline() && type != null) {
										// psender.sendMessage("giveitemtest1");
										if (player.getInventory().firstEmpty() != -1) {
											// psender.sendMessage("giveitemtest2");
											if (itemMeta.getDisplayName() != null) {
												// psender.sendMessage("giveitemtest3");
												if (itemMeta.getLore() != null) {
													// psender.sendMessage("giveitemtest4");
													item.setAmount(amount);
													item.setItemMeta(itemMeta);
													player.getInventory().addItem(new ItemStack[] { item });
													player.updateInventory();
													player.sendMessage(
															Methods.color("&6Swarm&eEnchants&f >> &7You have just received "
																	+ amount + " " + SIname));
													// psender.sendMessage(player.getName());
													return true;
												}
											}
										}
									}
									// psender.sendMessage(player.getName());
									if (player.getInventory().firstEmpty() == -1) {
										for (int i = 0; i <= player.getInventory().getSize(); ++i) {
											ItemStack item2 = player.getInventory().getItem(i);
											if (item2.isSimilar(item)) {
												int amount1 = item2.getAmount();
												if ((amount1 + amount) <= 64) {
													item.setAmount(amount1 + amount);
													item.setItemMeta(itemMeta);
													player.getInventory().removeItem(item2);
													player.getInventory().addItem(item);
													player.updateInventory();
													player.sendMessage(
															Methods.color("&6Swarm&eEnchants&f >> &7You have just received "
																	+ amount + SIname + "(s)"));
													return true;
												}
												return true;
											}
											if (!item2.isSimilar(item) || (item2.getAmount() + amount) >= 64) {
												player.sendMessage(Methods.color(
														"&6Swarm&eEnchants&f >> &cInventory full, item deleted!"));
												return true;
											}
											if (!item2.isSimilar(item)) {
												player.sendMessage(Methods.color(
														"&6Swarm&eEnchants&f >> &cInventory full, item deleted!"));
												return true;
											}
										}
									}
									// psender.sendMessage(item.getItemMeta().getDisplayName()
									// + item.getAmount());
								}
								return true;
							}
							return true;
						}
						if (args.length == 2 || args.length == 3) {
							if (args.length == 2) {
								amount = 1;
							}
							if (args.length == 3) {
								if (args[2] != null) {
									if (Methods.isInt(args[2])) {
										if (Integer.parseInt(args[2]) > 0) {
											amount = Integer.parseInt(args[2]);
										}
									}
								}
							}
							if (Bukkit.getPlayer(args[0]) != null) {
								Player player = Bukkit.getPlayer(args[0]);
								if (player.isOnline()) {
									if (amount != 0) {
										if (args[1].equalsIgnoreCase("1")
												|| args[1].equalsIgnoreCase("simplesoultracker")
												|| args[1].equalsIgnoreCase("simplest")) {
											item = new ItemStack(Material.PAPER, amount);
											type = "SoulTrackers.Simple.";
											SIname = "&fSimple Soul Tracker(s)";
											// psender.sendMessage("test1");
										} else if (args[1].equalsIgnoreCase("2")
												|| args[1].equalsIgnoreCase("uniquesoultracker")
												|| args[1].equalsIgnoreCase("uniquest")) {
											item = new ItemStack(Material.PAPER, amount);
											type = "SoulTrackers.Unique.";
											SIname = "&aUnique Soul Tracker(s)";
											// psender.sendMessage("test2");
										} else if (args[1].equalsIgnoreCase("3")
												|| args[1].equalsIgnoreCase("elitesoultracker")
												|| args[1].equalsIgnoreCase("elitest")) {
											item = new ItemStack(Material.PAPER, amount);
											type = "SoulTrackers.Elite.";
											SIname = "&bRare Soul Tracker(s)";
											// psender.sendMessage("test3");
										} else if (args[1].equalsIgnoreCase("4")
												|| args[1].equalsIgnoreCase("ultimatesoultracker")
												|| args[1].equalsIgnoreCase("ultimatest")) {
											item = new ItemStack(Material.PAPER, amount);
											type = "SoulTrackers.Ultimate.";
											SIname = "&eUltimate Soul Tracker(s)";
											// psender.sendMessage("test4");
										} else if (args[1].equalsIgnoreCase("5")
												|| args[1].equalsIgnoreCase("legendarysoultracker")
												|| args[1].equalsIgnoreCase("legendaryst")) {
											item = new ItemStack(Material.PAPER, amount);
											type = "SoulTrackers.Legendary.";
											SIname = "&6Legendary Soul Tracker(s)";
											// psender.sendMessage("test5");
										} else if (args[1].equalsIgnoreCase("6") || args[1].equalsIgnoreCase("soulgem")
												|| args[1].equalsIgnoreCase("sgem")) {
											item = new ItemStack(Material.EMERALD, amount);
											type = "SoulGemOptions.";
											SIname = "&aSoul Gem(s)";
											// psender.sendMessage("test6");
										} else if (args[1].equalsIgnoreCase("7") || args[1].equalsIgnoreCase("")) {
											item = new ItemStack(Material.PAPER, amount);
											type = "TransmogOptions.";
											SIname = "&eTransmog Scroll(s)";
											// psender.sendMessage("test7");
										} else if (args[0].equalsIgnoreCase("8")
												|| args[0].equalsIgnoreCase("rankquestitem1")
												|| args[0].equalsIgnoreCase("rqitem1")) {
											item = new ItemStack(Material.MAGMA_CREAM, amount);
											type = "RankQuestOptions.";
											type2 = "SoldierBee.";
											SIname = "&eRank Quest Item(s)";
											// psender.sendMessage("test7");
										} else if (args[0].equalsIgnoreCase("9")
												|| args[0].equalsIgnoreCase("rankquestitem2")
												|| args[0].equalsIgnoreCase("rqitem2")) {
											item = new ItemStack(Material.MAGMA_CREAM, amount);
											type = "RankQuestOptions.";
											type2 = "Wasp.";
											SIname = "&eRank Quest Item(s)";
											// psender.sendMessage("test7");
										} else if (args[0].equalsIgnoreCase("10")
												|| args[0].equalsIgnoreCase("rankquestitem2")
												|| args[0].equalsIgnoreCase("rqitem2")) {
											item = new ItemStack(Material.MAGMA_CREAM, amount);
											type = "RankQuestOptions.";
											type2 = "QueenBee.";
											SIname = "&eRank Quest Item(s)";
											// psender.sendMessage("test7");
										} else {
											psender.sendMessage(Methods.color("&6Swarm&eEnchants&f >> &cInvalid item!"));
											return true;
										}
										// psender.sendMessage(type);
										if (item != null) {
											if (item.getType() != Material.ACACIA_DOOR) {
												if (type.startsWith("SoulTrackers.")) {
													String dname = Methods.color(SettingsManager.getInstance()
															.getSoulTracker().getString(type + "Name"));
													itemMeta.setDisplayName(dname);
													lore.add(Methods.color(SettingsManager.getInstance().getSoulTracker()
															.getString(String.valueOf(type) + "Lore1")));
													lore.add(Methods.color(SettingsManager.getInstance().getSoulTracker()
															.getString(String.valueOf(type) + "Lore2")));
													itemMeta.setLore(lore);
													// psender.sendMessage(type);
													// psender.sendMessage(dname);
													// for (String line : lore)
													// {
													// psender.sendMessage(line);
													// }
												} else if (type == "SoulGemOptions.") {
													String dname = Methods
															.color(SettingsManager.getInstance().getSoulTracker()
																	.getString(type + "Name").replace("%souls%", "10"));
													itemMeta.setDisplayName(dname);
													for (String line : Main.settings.getSoulTracker()
															.getStringList("SoulGemOptions.Lore")) {
														lore.add(Methods.color(line));
														// psender.sendMessage(line);
													}
													itemMeta.setLore(lore);
													// psender.sendMessage(type);
													// psender.sendMessage(dname);
												} else if (type == "TransmogOptions.") {
													String dname = Methods.color(SettingsManager.getInstance()
															.getSoulTracker().getString(type + "Name"));
													itemMeta.setDisplayName(dname);
													for (String line : Main.settings.getSoulTracker()
															.getStringList("TransmogOptions.Lore")) {
														lore.add(Methods.color(line));
														// psender.sendMessage(Api.color(line));
													}
													itemMeta.setLore(lore);
													// psender.sendMessage(type);
													// psender.sendMessage(Api.color(dname));
												} else if (type == "RankQuestOptions.") {
													String dname = Methods.color(SettingsManager.getInstance().getSoulTracker()
															.getString(type + "Name"));
													dname.replace("%rank%",
															Main.settings.getSoulTracker().getString(type + type2 + "Rank"));
													itemMeta.setDisplayName(Methods.color(dname));
													for (String line : Main.settings.getSoulTracker()
															.getStringList("RankQuestOptions.Lore")) {
														line.replace("%rank%",
															Main.settings.getSoulTracker().getString(type + type2 + "Rank"));
														line.replace("%rankvoucher%",
																Main.settings.getSoulTracker().getString(type + type2 + "Rank") + "&bVoucher");
														lore.add(Methods.color(line));
														// psender.sendMessage(Api.color(line));
													}
													itemMeta.setLore(lore);
													// psender.sendMessage(type);
													// psender.sendMessage(Api.color(dname));
												} else {
													return true;
												}
											}
										}
										// psender.sendMessage(itemMeta.getDisplayName());
										if (player != null && player.isOnline() && type != null) {
											// psender.sendMessage("giveitemtest1");
											if (player.getInventory().firstEmpty() != -1) {
												// psender.sendMessage("giveitemtest2");
												if (itemMeta.getDisplayName() != null) {
													// psender.sendMessage("giveitemtest3");
													if (itemMeta.getLore() != null) {
														// psender.sendMessage("giveitemtest4");
														item.setAmount(amount);
														item.setItemMeta(itemMeta);
														player.getInventory().addItem(new ItemStack[] { item });
														player.updateInventory();
														player.sendMessage(Methods
																.color("&6Swarm&eEnchants&f >> &7You have just received "
																		+ amount + " " + SIname));
														// psender.sendMessage(player.getName());
														return true;
													}
												}
											}
										}
										// psender.sendMessage(player.getName());
										if (player.getInventory().firstEmpty() == -1) {
											for (int i = 0; i <= player.getInventory().getSize(); ++i) {
												ItemStack item2 = player.getInventory().getItem(i);
												if (item2.isSimilar(item)) {
													int amount1 = item2.getAmount();
													if ((amount1 + amount) <= 64) {
														item.setAmount(amount1 + amount);
														item.setItemMeta(itemMeta);
														player.getInventory().removeItem(item2);
														player.getInventory().addItem(item);
														player.updateInventory();
														player.sendMessage(Methods
																.color("&6Swarm&eEnchants&f >> &7You have just received "
																		+ amount + SIname + "(s)"));
														return true;
													}
													return true;
												}
												if (!item2.isSimilar(item) || (item2.getAmount() + amount) >= 64) {
													player.sendMessage(Methods.color(
															"&6Swarm&eEnchants&f >> &cInventory full, item deleted!"));
													return true;
												}
												if (!item2.isSimilar(item)) {
													player.sendMessage(Methods.color(
															"&6Swarm&eEnchants&f >> &cInventory full, item deleted!"));
													return true;
												}
											}
										}
										// psender.sendMessage(item.getItemMeta().getDisplayName()
										// + item.getAmount());
									}
									return true;
								}
							} else if (Bukkit.getPlayer(args[0]) == null || !Bukkit.getPlayer(args[0]).isOnline()
									|| type == null) {
								sender.sendMessage(Methods.color("&6Swarm&eEnchants&f >> &7 Either player " + args[0]
										+ " is offline or doesn't exist,  or you entered an invalid item!"));
								return true;
							}
						}
					}
				}
			}
		}
		if (!(sender instanceof Player)) {
			ItemStack item = new ItemStack(Material.ACACIA_DOOR, 1);
			ItemMeta itemMeta = item.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			int amount = 1;
			String type = null;
			String type2 = null;
			if (args.length == 0) {
				sender.sendMessage(Methods.color("&6Swarm&eEnchants&f >> &7") + "/givedp <player> <item> <amount>");
				return true;
			}
			if (args.length == 1) {
				if (Methods.isInt(args[0])) {
					if (Integer.parseInt(args[0]) <= 7) {
						sender.sendMessage(
								Methods.color("&6Swarm&eEnchants&f >> &7You need to specify who to give the item to!"));
						return true;
					}
					if (Integer.parseInt(args[0]) > 7) {
						sender.sendMessage(Methods.color("6Swarm&eEnchants&f >> &cInvalid item!"));
					}
				}
				if (!Methods.isInt(args[0])) {
					sender.sendMessage(Methods.color("6Swarm&eEnchants&f >> &cInvalid item!"));
				}
			}
			if (args.length == 2 || args.length == 3) {
				if (args.length == 2) {
					amount = 1;
				}
				if (args.length == 3) {
					if (args[2] != null) {
						if (Methods.isInt(args[2])) {
							amount = Integer.parseInt(args[2]);
						}
					}
				}
				if (Bukkit.getPlayer(args[0]) != null) {
					Player player = Bukkit.getPlayer(args[0]);
					if (player.isOnline()) {
						if (amount != 0) {
							sender.sendMessage(Methods.color("&6Swarm&eEnchants&f >> &7" + "The item has been sent to "
									+ player.getDisplayName() + "!"));
							if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("simplesoultracker")
									|| args[1].equalsIgnoreCase("simplest")) {
								item = new ItemStack(Material.PAPER, amount);
								type = "SoulTrackers.Simple.";
								SIname = "&fSimple Soul Tracker(s)";
								// debug line
								// sender.sendMessage("Hi you suck");
							} else if (args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("uniquesoultracker")
									|| args[1].equalsIgnoreCase("uniquest")) {
								item = new ItemStack(Material.PAPER, amount);
								type = "SoulTrackers.Unique.";
								SIname = "&aUnique Soul Tracker(s)";
							} else if (args[1].equalsIgnoreCase("3") || args[1].equalsIgnoreCase("elitesoultracker")
									|| args[1].equalsIgnoreCase("elitest")) {
								item = new ItemStack(Material.PAPER, amount);
								type = "SoulTrackers.Elite.";
								SIname = "&bRare Soul Tracker(s)";
							} else if (args[1].equalsIgnoreCase("4") || args[1].equalsIgnoreCase("ultimatesoultracker")
									|| args[1].equalsIgnoreCase("ultimatest")) {
								item = new ItemStack(Material.PAPER, amount);
								type = "SoulTrackers.Ultimate.";
								SIname = "&eUltimate Soul Tracker(s)";
							} else if (args[1].equalsIgnoreCase("5") || args[1].equalsIgnoreCase("legendarysoultracker")
									|| args[1].equalsIgnoreCase("legendaryst")) {
								item = new ItemStack(Material.PAPER, amount);
								type = "SoulTrackers.Legendary.";
								SIname = "&6Legendary Soul Tracker(s)";
							} else if (args[1].equalsIgnoreCase("6") || args[1].equalsIgnoreCase("soulgem")
									|| args[1].equalsIgnoreCase("sgem")) {
								item = new ItemStack(Material.EMERALD, amount);
								type = "SoulGemOptions.";
								SIname = "&aSoul Gem(s)";
							} else if (args[1].equalsIgnoreCase("7") || args[1].equalsIgnoreCase("")) {
								item = new ItemStack(Material.PAPER, amount);
								type = "TransmogOptions.";
								SIname = "&eTransmog Scroll(s)";
							} else if (args[0].equalsIgnoreCase("8") || args[0].equalsIgnoreCase("rankquestitem1")
									|| args[0].equalsIgnoreCase("rqitem1")) {
								item = new ItemStack(Material.MAGMA_CREAM, amount);
								type = "RankQuestOptions.";
								type2 = "SoldierBee.";
								SIname = "&eRank Quest Item(s)";
								// psender.sendMessage("test7");
							} else if (args[0].equalsIgnoreCase("9") || args[0].equalsIgnoreCase("rankquestitem2")
									|| args[0].equalsIgnoreCase("rqitem2")) {
								item = new ItemStack(Material.MAGMA_CREAM, amount);
								type = "RankQuestOptions.";
								type2 = "Wasp.";
								SIname = "&eRank Quest Item(s)";
								// psender.sendMessage("test7");
							} else if (args[0].equalsIgnoreCase("10") || args[0].equalsIgnoreCase("rankquestitem2")
									|| args[0].equalsIgnoreCase("rqitem2")) {
								item = new ItemStack(Material.MAGMA_CREAM, amount);
								type = "RankQuestOptions.";
								type2 = "QueenBee.";
								SIname = "&eRank Quest Item(s)";
								// psender.sendMessage("test7");
							} else {
								sender.sendMessage("&6Swarm&eEnchants&f >> &cInvalid item!");
								return true;
							}
							if (item != null) {
								if (item.getType() != Material.ACACIA_DOOR) {
									if (type.startsWith("SoulTrackers.")) {
										itemMeta.setDisplayName(Methods.color(SettingsManager.getInstance().getSoulTracker()
												.getString(type + "Name")));
										lore.add(Methods.color(SettingsManager.getInstance().getSoulTracker()
												.getString(String.valueOf(type) + "Lore1")));
										lore.add(Methods.color(SettingsManager.getInstance().getSoulTracker()
												.getString(String.valueOf(type) + "Lore2")));
										itemMeta.setLore(lore);
									} else if (type == "SoulGemOptions.") {
										itemMeta.setDisplayName(Methods.color(SettingsManager.getInstance().getSoulTracker()
												.getString(type + "Name").replace("%souls%", "10")));
										for (String line : Main.settings.getSoulTracker()
												.getStringList("SoulGemOptions.Lore")) {
											lore.add(Methods.color(line));
										}
										itemMeta.setLore(lore);
									} else if (type == "TransmogOptions.") {
										itemMeta.setDisplayName(
												Methods.color(Main.settings.getSoulTracker().getString(type + "Name")));
										for (String line : Main.settings.getSoulTracker()
												.getStringList(type + "Lore")) {
											lore.add(Methods.color(line));
										}
										itemMeta.setLore(lore);
									} else if (type == "RankQuestOptions.") {
										String dname = Methods.color(SettingsManager.getInstance().getSoulTracker()
												.getString(type + "Name"));
										dname.replace("%rank%",
												Main.settings.getSoulTracker().getString(type + type2 + "Rank"));
										itemMeta.setDisplayName(Methods.color(dname));
										for (String line : Main.settings.getSoulTracker()
												.getStringList("RankQuestOptions.Lore")) {
											line.replace("%rank%",
												Main.settings.getSoulTracker().getString(type + type2 + "Rank"));
											line.replace("%rankvoucher%",
													Main.settings.getSoulTracker().getString(type + type2 + "Rank") + "&bVoucher");
											lore.add(Methods.color(line));
											// psender.sendMessage(Api.color(line));
										}
										itemMeta.setLore(lore);
										// psender.sendMessage(type);
										// psender.sendMessage(Api.color(dname));
									} else {
										return true;
									}
								}
							}
							if (player != null && player.isOnline() && type != null) {
								if (player.getInventory().firstEmpty() > 0) {
									item.setAmount(amount);
									item.setItemMeta(itemMeta);
									player.getInventory().addItem(item);
									player.updateInventory();
									player.sendMessage(
											Methods.color("&6Swarm&eEnchants&f >> &7You have just received a " + SIname));
									return true;
								}
							}
							if (player.getInventory().firstEmpty() < 0) {
								for (int i = 1; i <= 36; ++i) {
									ItemStack item2 = player.getInventory().getItem(i);
									if (item2.isSimilar(item)) {
										int amount1 = item2.getAmount();
										if ((amount1 + amount) <= 64) {
											item.setAmount(amount1 + amount);
											item.setItemMeta(itemMeta);
											player.getInventory().removeItem(item2);
											player.getInventory().addItem(item);
											player.updateInventory();
											player.sendMessage(Methods.color(
													"&6Swarm&eEnchants&f >> &7You have just received a " + SIname));
											return true;
										}
										return true;
									}
									if (!item2.isSimilar(item) || (item2.getAmount() + amount) >= 64) {
										player.sendMessage(
												Methods.color("&6Swarm&eEnchants&f >> &cInventory full, item deleted!"));
										return true;
									}
									if (!item2.isSimilar(item)) {
										player.sendMessage(
												Methods.color("&6Swarm&eEnchants&f >> &cInventory full, item deleted!"));
										return true;
									}
								}
							}
						}
						return true;
					}
				} else if (Bukkit.getPlayer(args[0]) == null || !Bukkit.getPlayer(args[0]).isOnline() || type == null) {
					sender.sendMessage(Methods.color("&6Swarm&eEnchants&f >> &7 Either player " + args[0]
							+ " is offline or doesn't exist,  or you entered an invalid item!"));
					return true;
				}
			}
		}
		return true;
	}

}
