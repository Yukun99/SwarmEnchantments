package me.yukun.CrayEncPlus.DPitems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.CrazyEnchantments;

public class Transmog implements Listener {
	static CrazyEnchantments CE = CrazyEnchantments.getInstance();

	private HashMap<ItemStack, Boolean> transmogged = new HashMap<ItemStack, Boolean>();
	private HashMap<Player, Integer> I = new HashMap<Player, Integer>();
	private HashMap<ItemStack, String> dname = new HashMap<ItemStack, String>();
	private HashMap<ItemStack, Boolean> enchanted = new HashMap<ItemStack, Boolean>();
	Map<Enchantment, Integer> enchants = null;
	// private HashMap<Player, PlayerInventory> inven = new HashMap<Player,
	// PlayerInventory>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void inventoryClickEvent(InventoryClickEvent e) {
		Entity applier = e.getWhoClicked();
		if (!e.isCancelled()) {
			if (applier instanceof Player) {
				Player papplier = (Player) applier;
				// PlayerInventory pinventory = papplier.getInventory();
				// inven.put(papplier, pinventory);
				ItemStack item = e.getCurrentItem();
				ItemStack transmog = e.getCursor();
				String tname = "";
				if (item != null) {
					ItemMeta itemMeta = item.getItemMeta();
					ArrayList<String> lore1 = new ArrayList<String>();
					ArrayList<String> lore2 = new ArrayList<String>();
					ArrayList<String> lore3 = new ArrayList<String>();
					ArrayList<String> lore4 = new ArrayList<String>();
					ArrayList<String> lore5 = new ArrayList<String>();
					ArrayList<String> lore6 = new ArrayList<String>();
					ArrayList<String> lore7 = new ArrayList<String>();
					ArrayList<String> totallore = new ArrayList<String>();
					if (item.getType() != Material.AIR) {
						// papplier.sendMessage("1");
						if (transmog != null) {
							// papplier.sendMessage("2");
							if (item.getType().name().endsWith("SWORD") || item.getType().name().endsWith("AXE")
									|| item.getType().name().endsWith("HELMET")
									|| item.getType().name().endsWith("SHOVEL")
									|| item.getType().name().endsWith("CHESTPLATE")
									|| item.getType().name().endsWith("LEGGINGS")
									|| item.getType().name().endsWith("BOOTS") || item.getType().name().endsWith("BOW")
									|| item.getType().name().endsWith("PICKAXE")
									|| item.getType().name().endsWith("HOE")) {
								// papplier.sendMessage("3");
								if (item.hasItemMeta()) {
									// papplier.sendMessage("4");
									if (item.getItemMeta().hasLore()) {
										// papplier.sendMessage("5");
										if (item.getItemMeta().hasDisplayName()) {
											// papplier.sendMessage("6");
											if (Methods.transmogged(item) == true) {
												// papplier.sendMessage("7");
												// papplier.sendMessage(Api.transint(item)
												// + "");
												dname.put(item, item.getItemMeta().getDisplayName());
												I.put(papplier, Methods.transint(item));
												transmogged.put(item, true);
											}
											if (Methods.transmogged(item) == false) {
												// papplier.sendMessage("8");
												transmogged.put(item, false);
											}
										}
										if (!item.getItemMeta().hasDisplayName()) {
											// papplier.sendMessage("9");
											transmogged.put(item, false);
										}
										if (transmogged.get(item) != null) {
											// papplier.sendMessage("10");
											if (transmogged.get(item) == false) {
												// papplier.sendMessage("11");
												if (transmog.hasItemMeta()) {
													// papplier.sendMessage("12");
													if (transmog.getItemMeta().hasLore()) {
														// papplier.sendMessage("13");
														if (transmog.getItemMeta().hasDisplayName()) {
															if (transmog.getItemMeta().getDisplayName()
																	.contains(Methods.color("&e&lTransmog"))) {
																// papplier.sendMessage("14");
																for (String tlore : transmog.getItemMeta().getLore()) {
																	// papplier.sendMessage("15");
																	if (tlore.contains(Methods.color(
																			"&e&oPlace scroll on item to apply."))) {
																		// papplier.sendMessage("16");
																		if (e.getClickedInventory() instanceof PlayerInventory) {
																			// papplier.sendMessage("17");
																			if (CE.getItemEnchantments(item)
																					.size() > 0) {
																				Material material = item.getType();
																				ItemStack titem = new ItemStack(
																						material, 1);
																				ItemMeta titemMeta = titem
																						.getItemMeta();
																				if (item.getEnchantments() == null) {
																					enchanted.put(titem, false);
																				}
																				if (item.getEnchantments() != null) {
																					enchants = item.getEnchantments();
																				}
																				ArrayList<CEnchantments> enchantes = CE
																						.getEnchantments();
																				ArrayList<String> enchanters = new ArrayList<String>();
																				for (CEnchantments encs : enchantes) {
																					enchanters.add(Methods.removeColor(
																							encs.getCustomName()));
																				}
																				for (String ench : item.getItemMeta()
																						.getLore()) {
																					// papplier.sendMessage("29");
																					if (enchanters
																							.contains(Methods.removePower(
																									Methods.removeColor(
																											ench)))) {
																						// papplier.sendMessage("31");
																						for (CEnchantments enchant : CE
																								.getEnchantments()) {
																							// papplier.sendMessage("19");
																							if (ench.contains(enchant
																									.getCustomName())) {
																								String tier = CE
																										.getEnchantmentCategory(
																												enchant);
																								if (tier.contains(
																										"T1")) {
																									lore1.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T2")) {
																									lore2.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T3")) {
																									lore3.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T4")) {
																									lore4.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T5")) {
																									lore5.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T6")) {
																									lore6.add(0, Methods
																											.color(ench));
																									continue;
																								}
																							}
																						}
																					} else {
																						lore7.add(ench);
																						continue;
																					}
																				}
																				totallore.addAll(lore7);
																				totallore.addAll(lore6);
																				totallore.addAll(lore5);
																				totallore.addAll(lore4);
																				totallore.addAll(lore3);
																				totallore.addAll(lore2);
																				totallore.addAll(lore1);
																				int enchNumber = CE
																						.getItemEnchantments(item)
																						.size();
																				if (itemMeta.hasDisplayName()) {
																					tname = item.getItemMeta()
																							.getDisplayName()
																							+ " &d&l[&b&l&n"
																							+ enchNumber + "&d&l]";
																				}
																				if (!itemMeta.hasDisplayName()) {
																					tname = "&a" + item.getType().name()
																							+ " &d&l[&b&l&n"
																							+ enchNumber + "&d&l]";
																				}
																				e.setCancelled(true);
																				titemMeta.setLore(totallore);
																				titemMeta.setDisplayName(
																						Methods.color(tname));
																				titem.setItemMeta(titemMeta);
																				titem.addUnsafeEnchantments(enchants);
																				if (enchanted.get(titem) != null) {
																					if (enchanted.get(titem) == false) {
																						Methods.addGlow(titem);
																					}
																				}
																				e.setCursor(null);
																				papplier.getInventory()
																						.removeItem(item);
																				papplier.getInventory().addItem(
																						new ItemStack[] { titem });
																				for (int i2 = 1; i2 <= 10; ++i2) {
																					papplier.getWorld().playEffect(
																							papplier.getEyeLocation(),
																							Effect.SPELL, 1);
																				}
																				papplier.getWorld().playSound(
																						papplier.getLocation(),
																						Sound.LEVEL_UP, 1.0f, 1.0f);
																				enchants = null;
																				if (transmogged.get(item) != null) {
																					transmogged.remove(item);
																				}
																				if (dname.get(item) != null) {
																					dname.remove(item);
																				}
																				if (I.get(item) != null) {
																					I.remove(item);
																				}
																				return;
																			}
																		} else {
																			e.getWhoClicked().sendMessage(Methods.color(
																					"&6Swarm&eEnchants&f >> &cYou may only use Transmog scrolls in your own inventory!"));
																			return;
																		}
																	}
																}
															}
														}
													}
												}
											}
											if (transmogged.get(item) == true) {
												// papplier.sendMessage("21");
												String dname1 = dname.get(item).replace(
														Methods.color("&d&l[&b&l&n" + I.get(papplier) + "&d&l]"), "");
												if (transmog.hasItemMeta()) {
													// papplier.sendMessage("22");
													if (transmog.getItemMeta().hasLore()) {
														if (transmog.getItemMeta().hasDisplayName()) {
															// papplier.sendMessage("23");
															if (transmog.getItemMeta().getDisplayName()
																	.contains(Methods.color("&e&lTransmog"))) {
																// papplier.sendMessage("24");
																for (String tlore : transmog.getItemMeta().getLore()) {
																	// papplier.sendMessage("25");
																	if (tlore.contains(Methods.color(
																			"&e&oPlace scroll on item to apply."))) {
																		// papplier.sendMessage("26");
																		if (e.getClickedInventory() instanceof PlayerInventory) {
																			// papplier.sendMessage("27");
																			if (CE.getItemEnchantments(item)
																					.size() > 0) {
																				// papplier.sendMessage("28");
																				Material material = item.getType();
																				ItemStack titem = new ItemStack(
																						material, 1);
																				ItemMeta titemMeta = titem
																						.getItemMeta();
																				if (item.getEnchantments() == null) {
																					Methods.addGlow(titem);
																				}
																				if (item.getEnchantments() != null) {
																					enchants = item.getEnchantments();
																				}
																				ArrayList<CEnchantments> enchantes = CE
																						.getEnchantments();
																				ArrayList<String> enchanters = new ArrayList<String>();
																				for (CEnchantments encs : enchantes) {
																					enchanters.add(Methods.removeColor(
																							encs.getCustomName()));
																				}
																				for (String ench : item.getItemMeta()
																						.getLore()) {
																					// papplier.sendMessage("29");
																					if (enchanters
																							.contains(Methods.removePower(
																									Methods.removeColor(
																											ench)))) {
																						// papplier.sendMessage("31");
																						for (CEnchantments enchant : CE
																								.getEnchantments()) {
																							// papplier.sendMessage("19");
																							if (ench.contains(enchant
																									.getCustomName())) {
																								String tier = CE
																										.getEnchantmentCategory(
																												enchant);
																								if (tier.contains(
																										"T1")) {
																									lore1.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T2")) {
																									lore2.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T3")) {
																									lore3.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T4")) {
																									lore4.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T5")) {
																									lore5.add(0, Methods
																											.color(ench));
																									continue;
																								} else if (tier
																										.contains(
																												"T6")) {
																									lore6.add(0, Methods
																											.color(ench));
																									continue;
																								}
																							}
																						}
																					} else {
																						lore7.add(ench);
																						continue;
																					}
																				}
																				totallore.addAll(lore7);
																				totallore.addAll(lore6);
																				totallore.addAll(lore5);
																				totallore.addAll(lore4);
																				totallore.addAll(lore3);
																				totallore.addAll(lore2);
																				totallore.addAll(lore1);
																				int enchNumber = CE
																						.getItemEnchantments(item)
																						.size();
																				if (itemMeta.hasDisplayName()) {
																					tname = dname1 + " &d&l[&b&l&n"
																							+ enchNumber + "&d&l]";
																				}
																				e.setCancelled(true);
																				titemMeta.setLore(totallore);
																				titemMeta.setDisplayName(
																						Methods.color(tname));
																				titem.setItemMeta(titemMeta);
																				titem.addUnsafeEnchantments(enchants);
																				e.setCursor(null);
																				papplier.getInventory()
																						.removeItem(item);
																				papplier.getInventory().addItem(
																						new ItemStack[] { titem });
																				for (int i2 = 1; i2 <= 10; ++i2) {
																					papplier.getWorld().playEffect(
																							papplier.getEyeLocation(),
																							Effect.SPELL, 1);
																				}
																				papplier.getWorld().playSound(
																						papplier.getLocation(),
																						Sound.LEVEL_UP, 1.0f, 1.0f);
																				enchants = null;
																				if (transmogged.get(item) != null) {
																					transmogged.remove(item);
																				}
																				if (dname.get(item) != null) {
																					dname.remove(item);
																				}
																				if (I.get(item) != null) {
																					I.remove(item);
																				}
																				return;
																			}
																		} else {
																			e.getWhoClicked().sendMessage(Methods.color(
																					"&6Swarm&eEnchants&f >> &cYou may only use Transmog scrolls in your own inventory!"));
																			return;
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
