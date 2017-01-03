package me.yukun.CrazyEncPlus.Soul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.Events.ArmorEquipEvent;
import me.BadBones69.CrazyEnchantments.Enchantments.Armor;
import me.BadBones69.CrazyEnchantments.multiSupport.Support;

public class SoulMode implements Listener {
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("CrazyEnchantments");
	private HashMap<Player, Integer> particles = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> souls = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> slot = new HashMap<Player, Integer>();
	private static HashMap<Player, Integer> ptimer = new HashMap<Player, Integer>();
	private static HashMap<Player, Integer> pcounter = new HashMap<Player, Integer>();
	private ArrayList<Player> soul = new ArrayList<Player>();
	private ArrayList<Player> immortal = new ArrayList<Player>();
	private ArrayList<Player> paradox = new ArrayList<Player>();
	private HashMap<Player, Integer> immortall = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> paradoxl = new HashMap<Player, Integer>();
	private static ArrayList<Player> jump = new ArrayList<Player>();

	@EventHandler
	public void playerJumpEvent(PlayerMoveEvent e) {
		if (e.getTo().getY() > e.getFrom().getY()) {
			if (jump.contains(e.getPlayer())) {
				e.setCancelled(true);
			}
		}
	}

	public static void strikeLightning() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (ptimer.keySet() != null) {
					for (Player players : ptimer.keySet()) {
						players.getWorld().strikeLightning(players.getLocation());
						players.getWorld().strikeLightningEffect(players.getLocation());
						Random random = new Random();
						int rand = random.nextInt(10);
						if (rand > 7) {
							ptimer.remove(players);
							jump.remove(players);
							if (pcounter.get(players) != null) {
								Bukkit.getScheduler().cancelTask(pcounter.get(players));
								pcounter.remove(players);
							}
							if (players.hasPotionEffect(PotionEffectType.SLOW)) {
								players.removePotionEffect(PotionEffectType.SLOW);
							}
							if (players.hasPotionEffect(PotionEffectType.JUMP)) {
								players.removePotionEffect(PotionEffectType.JUMP);
								if (players.getInventory().getBoots() != null) {
									if (Main.CE.hasEnchantment(players.getInventory().getBoots(),
											CEnchantments.SPRINGS)) {
										players.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
												Integer.MAX_VALUE, Main.CE.getPower(players.getInventory().getBoots(),
														CEnchantments.SPRINGS)));
									}
								}
							}
						}
						continue;
					}
				}
			}
		}, 20, 20);
	}

	@EventHandler
	private void playerMoveGemEvent(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (soul.contains(player)) {
			if (e.getCurrentItem() != null) {
				if (Methods.getItemSlot(player, e.getCurrentItem()) != null) {
					if (Methods.getItemSlot(player, e.getCurrentItem()) == slot.get(player)) {
						e.setCancelled(true);
						player.sendMessage(Methods.color(
								"&6Swarm&eEnchants&f >>&c You may not move the gem in your inventory when you are in soul mode!"));
					}
				}
			}
		}
	}

	@EventHandler
	private void playerDropGemEvent(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (soul.contains(player)) {
			if (e.getItemDrop().getItemStack() != null) {
				if (e.getItemDrop().getItemStack().getType() == Material.EMERALD) {
					ItemStack gem = e.getItemDrop().getItemStack();
					if (gem.hasItemMeta()) {
						ItemMeta gemMeta = gem.getItemMeta();
						if (gemMeta.hasDisplayName()) {
							if (gemMeta.hasLore()) {
								String dname = gem.getItemMeta().getDisplayName();
								if (Methods.getArgument("%souls%", dname,
										Methods.getSoulTracker("SoulGemOptions.Name")) != null) {
									ArrayList<String> clore = new ArrayList<String>();
									for (String line : Main.settings.getSoulTracker()
											.getStringList("SoulGemOptions.Lore")) {
										clore.add(Methods.color(line));
									}
									if (clore.equals(gem.getItemMeta().getLore())) {
										e.setCancelled(true);
										player.sendMessage(Methods.color(
												"&6Swarm&eEnchants&f >>&c You may not drop the gem when you are in soul mode!"));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	private void playerDeathEvent(PlayerDeathEvent e) {
		Player player = e.getEntity();
		if (soul.contains(e.getEntity())) {
			soul.remove(player);
			slot.remove(player);
			Bukkit.getScheduler().cancelTask(particles.get(player));
			particles.remove(player);
		}
		if (ptimer.get(player) != null) {
			ptimer.remove(player);
			if (pcounter.get(player) != null) {
				Bukkit.getScheduler().cancelTask(pcounter.get(player));
				pcounter.remove(player);
			}
		}
	}

	@EventHandler
	private void playerLeaveEvent(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (soul.contains(e.getPlayer())) {
			soul.remove(player);
			slot.remove(player);
			Bukkit.getScheduler().cancelTask(particles.get(player));
			particles.remove(player);
		}
		if (ptimer.get(player) != null) {
			ptimer.remove(player);
			if (pcounter.get(player) != null) {
				Bukkit.getScheduler().cancelTask(pcounter.get(player));
				pcounter.remove(player);
			}
		}
	}

	@EventHandler
	private void soulEnchantsEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player damager = (Player) e.getDamager();
			Entity dmged = e.getEntity();
			if (e.getEntity() instanceof Player) {
				Player damaged = (Player) e.getEntity();
				if (soul.contains(damaged)) {
					if (paradox.contains(damaged)) {
						double radius = 20;
						Location loc = damager.getLocation();
						List<Player> near = loc.getWorld().getPlayers();
						for (Player e1 : near) {
							if (e1.getLocation().distance(loc) <= radius) {
								if (!Support.isFriendly(damaged, e1) || e1 == damaged) {
									continue;
								} else {
									int integer = (int) e.getDamage() / (6 - paradoxl.get(damaged));
									if (integer > 0) {
										if ((e1.getHealth() + integer) <= e1.getMaxHealth()) {
											e1.setHealth(e1.getHealth() + integer);
										} else {
											e1.setHealth(e1.getMaxHealth());
										}
									}
								}
							}
						}
					}
					for (ItemStack armour : damaged.getInventory().getArmorContents()) {
						if (armour.hasItemMeta()) {
							ItemMeta armourMeta = armour.getItemMeta();
							if (armourMeta.hasLore()) {
								if (Main.CE.hasEnchantments(armour)) {
									if (Main.CE.hasEnchantment(armour, CEnchantments.NATURESWRATH)) {
										if (Armor.getLuck().get(damaged) != null) {
											Random random = new Random();
											int randomChance = 90000;
											int randomNumber = random.nextInt(randomChance);
											if ((randomNumber + 100 * Armor.getLuck().get(damaged) + 1000
													* Main.CE.getPower(armour, CEnchantments.NATURESWRATH)) >= 89110) {
												int gemsouls = Methods.getArgument("%souls%",
														damaged.getInventory().getItem(slot.get(damaged)),
														Methods.getSoulTracker("SoulGemOptions.Name"));
												if (gemsouls >= 20) {
													gemsouls = gemsouls - 20;
													ItemStack gem = damaged.getInventory().getItem(slot.get(damaged));
													ItemMeta gemMeta = gem.getItemMeta();
													gemMeta.setDisplayName(
															Methods.color(Methods.getSoulTracker("SoulGemOptions.Name")
																	.replace("%souls%", gemsouls + "")));
													gem.setItemMeta(gemMeta);
													damaged.getInventory().setItem(slot.get(damaged), gem);
													Location loc = damager.getLocation();
													double radius = 3;
													List<Entity> near = loc.getWorld().getEntities();
													for (Entity e1 : near) {
														if (e1.getLocation().distance(loc) <= radius) {
															if (e1 instanceof LivingEntity) {
																if (e1 instanceof Player) {
																	Player p2 = (Player) e1;
																	if (Support.isFriendly(damaged, p2) || p2 == damaged) {
																		continue;
																	} else {
																		if (e.getEntity() instanceof Player) {
																			final Player p1 = (Player) e1;
																			if (p1 == damager) {
																				p1.getWorld().playEffect(
																						p1.getLocation(), Effect.SMOKE,
																						1, 5);
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.CONFUSION,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.POISON,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.WITHER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.HUNGER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.SLOW, 10 * 20,
																						100));
																				jump.remove(p1);
																				jump.add(p1);
																				ptimer.remove(p1);
																				ptimer.put(p1, 10);
																				if (pcounter.containsKey(p1)) {
																					Bukkit.getScheduler().cancelTask(pcounter.get(p1));
																					pcounter.remove(p1);
																				}
																				pcounter.put(p1, Bukkit.getScheduler()
																						.scheduleSyncRepeatingTask(
																								plugin, new Runnable() {
																									@Override
																									public void run() {
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) > 0) {
																											ptimer.put(
																													p1,
																													ptimer.get(
																															p1)
																															- 1);
																										}
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) == 0) {
																											jump.remove(
																													p1);
																											ptimer.remove(
																													p1);
																											Bukkit.getScheduler()
																													.cancelTask(
																															pcounter.get(
																																	p1));
																											pcounter.remove(
																													p1);
																										}
																									}
																								}, 20, 20));
																				continue;
																			} else {
																				p1.getWorld().playEffect(
																						p1.getLocation(), Effect.SMOKE,
																						1, 5);
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.CONFUSION,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.POISON,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.WITHER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.HUNGER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.SLOW, 10 * 20,
																						100));
																				jump.remove(p1);
																				jump.add(p1);
																				ptimer.remove(p1);
																				ptimer.put(p1, 10);
																				if (pcounter.containsKey(p1)) {
																					Bukkit.getScheduler().cancelTask(pcounter.get(p1));
																					pcounter.remove(p1);
																				}
																				pcounter.put(p1, Bukkit.getScheduler()
																						.scheduleSyncRepeatingTask(
																								plugin, new Runnable() {
																									@Override
																									public void run() {
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) > 0) {
																											ptimer.put(
																													p1,
																													ptimer.get(
																															p1)
																															- 1);
																										}
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) == 0) {
																											jump.remove(
																													p1);
																											ptimer.remove(
																													p1);
																											Bukkit.getScheduler()
																													.cancelTask(
																															pcounter.get(
																																	p1));
																											pcounter.remove(
																													p1);
																										}
																									}
																								}, 20, 20));
																				continue;
																			}
																		}
																	}
																}
															}
														}
													}
													break;
												}
												break;
											}
										} else {
											Random random = new Random();
											int randomChance = 90000;
											int randomNumber = random.nextInt(randomChance);
											if ((randomNumber + 1000
													* Main.CE.getPower(armour, CEnchantments.NATURESWRATH)) >= 89110) {
												Location loc = damager.getLocation();
												double radius = 3;
												List<Entity> near = loc.getWorld().getEntities();
												int gemsouls = Methods.getArgument("%souls%",
														damaged.getInventory().getItem(slot.get(damaged)),
														Methods.getSoulTracker("SoulGemOptions.Name"));
												if (gemsouls >= 20) {
													gemsouls = gemsouls - 20;
													ItemStack gem = damaged.getInventory().getItem(slot.get(damaged));
													ItemMeta gemMeta = gem.getItemMeta();
													gemMeta.setDisplayName(
															Methods.color(Methods.getSoulTracker("SoulGemOptions.Name")
																	.replace("%souls%", gemsouls + "")));
													gem.setItemMeta(gemMeta);
													damaged.getInventory().setItem(slot.get(damaged), gem);
													for (Entity e1 : near) {
														if (e1.getLocation().distance(loc) <= radius) {
															if (e1 instanceof LivingEntity) {
																if (e1 instanceof Player) {
																	Player p2 = (Player) e1;
																	if (Support.isFriendly(damaged, p2) || p2 == damaged) {
																		continue;
																	} else {
																		if (e.getEntity() instanceof Player) {
																			final Player p1 = (Player) e1;
																			if (p1 == damager) {
																				p1.getWorld().playEffect(
																						p1.getLocation(), Effect.SMOKE,
																						1, 5);
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.CONFUSION,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.POISON,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.WITHER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.HUNGER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 20,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.SLOW, 10 * 20,
																						100));
																				jump.remove(p1);
																				jump.add(p1);
																				ptimer.remove(p1);
																				ptimer.put(p1, 10);
																				if (pcounter.containsKey(p1)) {
																					Bukkit.getScheduler().cancelTask(pcounter.get(p1));
																					pcounter.remove(p1);
																				}
																				pcounter.put(p1, Bukkit.getScheduler()
																						.scheduleSyncRepeatingTask(
																								plugin, new Runnable() {
																									@Override
																									public void run() {
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) > 0) {
																											ptimer.put(
																													p1,
																													ptimer.get(
																															p1)
																															- 1);
																										}
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) == 0) {
																											jump.remove(
																													p1);
																											ptimer.remove(
																													p1);
																											Bukkit.getScheduler()
																													.cancelTask(
																															pcounter.get(
																																	p1));
																											pcounter.remove(
																													p1);
																										}
																									}
																								}, 20, 20));
																				continue;
																			} else {
																				p1.getWorld().playEffect(
																						p1.getLocation(), Effect.SMOKE,
																						1, 5);
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.CONFUSION,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.POISON,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.WITHER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.HUNGER,
																						(Main.CE.getPower(armour,
																								CEnchantments.NATURESWRATH))
																								* 10,
																						2));
																				p1.addPotionEffect(new PotionEffect(
																						PotionEffectType.SLOW, 10 * 20,
																						100));
																				jump.remove(p1);
																				jump.add(p1);
																				ptimer.remove(p1);
																				ptimer.put(p1, 10);
																				if (pcounter.containsKey(p1)) {
																					Bukkit.getScheduler().cancelTask(pcounter.get(p1));
																					pcounter.remove(p1);
																				}
																				pcounter.put(p1, Bukkit.getScheduler()
																						.scheduleSyncRepeatingTask(
																								plugin, new Runnable() {
																									@Override
																									public void run() {
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) > 0) {
																											ptimer.put(
																													p1,
																													ptimer.get(
																															p1)
																															- 1);
																										}
																										if (ptimer
																												.get(p1) != null
																												&& ptimer
																														.get(p1) == 0) {
																											jump.remove(
																													p1);
																											ptimer.remove(
																													p1);
																											Bukkit.getScheduler()
																													.cancelTask(
																															pcounter.get(
																																	p1));
																											pcounter.remove(
																													p1);
																										}
																									}
																								}, 20, 20));
																				continue;
																			}
																		}
																		continue;
																	}
																}
																continue;
															}
															continue;
														}
														continue;
													}
													break;
												}
												break;
											}
											continue;
										}
										continue;
									}
									continue;
								}
								continue;
							}
							continue;
						}
						continue;
					}
				}
			}
			if (soul.contains(damager)) {
				if (Methods.getItemInHand(damager) != null) {
					ItemStack weapon = Methods.getItemInHand(damager);
					if (weapon.hasItemMeta()) {
						if (weapon.getItemMeta().hasLore()) {
							if (Main.CE.hasEnchantments(weapon)) {
								if (Main.CE.hasEnchantment(weapon, CEnchantments.DIVINEIMMOMATION)) {
									Location loc = dmged.getLocation();
									double radius = 3;
									List<Entity> near = loc.getWorld().getEntities();
									for (Entity e1 : near) {
										if (e1.getLocation().distance(loc) <= radius) {
											if (e1 instanceof LivingEntity) {
												LivingEntity e2 = (LivingEntity) e1;
												if (e1 instanceof Player) {
													Player p2 = (Player) e1;
													if (Support.isFriendly(damager, p2) || p2 == damager) {
														continue;
													} else {
														if (e.getEntity() instanceof Player) {
															Player p1 = (Player) e1;
															Player damaged = (Player) e.getEntity();
															if (p1 == damaged) {
																p1.getWorld().playEffect(p1.getLocation(), Effect.FLAME,
																		1, 5);
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.CONFUSION,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.POISON,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.WITHER,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.SLOW_DIGGING,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.setFireTicks((Main.CE.getPower(weapon,
																		CEnchantments.DIVINEIMMOMATION)) * 20);
																continue;
															} else {
																p1.getWorld().playEffect(p1.getLocation(), Effect.FLAME,
																		1, 5);
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.CONFUSION,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.POISON,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.WITHER,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.addPotionEffect(new PotionEffect(
																		PotionEffectType.SLOW_DIGGING,
																		(Main.CE.getPower(weapon,
																				CEnchantments.DIVINEIMMOMATION)) * 20,
																		2));
																p1.setFireTicks((Main.CE.getPower(weapon,
																		CEnchantments.DIVINEIMMOMATION)) * 20);
																continue;
															}
														}
													}
												} else {
													e2.getWorld().playEffect(e2.getLocation(), Effect.FLAME, 1, 5);
													e2.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
															(Main.CE.getPower(weapon, CEnchantments.DIVINEIMMOMATION))
																	* 20,
															2));
													e2.addPotionEffect(new PotionEffect(PotionEffectType.POISON,
															(Main.CE.getPower(weapon, CEnchantments.DIVINEIMMOMATION))
																	* 20,
															2));
													e2.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,
															(Main.CE.getPower(weapon, CEnchantments.DIVINEIMMOMATION))
																	* 20,
															2));
													e2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,
															(Main.CE.getPower(weapon, CEnchantments.DIVINEIMMOMATION))
																	* 20,
															2));
													e2.setFireTicks(
															(Main.CE.getPower(weapon, CEnchantments.DIVINEIMMOMATION))
																	* 20);
													continue;
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

	@EventHandler
	public void onEquip(ArmorEquipEvent e) {
		Player player = e.getPlayer();
		ItemStack NewItem = e.getNewArmorPiece();
		ItemStack OldItem = e.getOldArmorPiece();
		if (Main.CE.hasEnchantments(NewItem)) {
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.IMMORTAL)) {
				if (CEnchantments.IMMORTAL.isEnabled()) {
					immortal.add(player);
					immortall.put(player, Main.CE.getPower(NewItem, CEnchantments.IMMORTAL));
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.PARADOX)) {
				if (CEnchantments.PARADOX.isEnabled()) {
					paradox.add(player);
					paradoxl.put(player, Main.CE.getPower(NewItem, CEnchantments.PARADOX));
				}
			}
		}
		if (Main.CE.hasEnchantments(OldItem)) {
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.IMMORTAL)) {
				if (CEnchantments.IMMORTAL.isEnabled()) {
					immortal.remove(player);
					immortall.remove(player);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.PARADOX)) {
				if (CEnchantments.PARADOX.isEnabled()) {
					paradox.remove(player);
					paradoxl.remove(player);
				}
			}
		}
	}

	@EventHandler
	private void immortalEvent(PlayerItemDamageEvent e) {
		if (e.getPlayer() != null) {
			Player player = e.getPlayer();
			if (immortal.contains(player)) {
				if (soul.contains(player)) {
					e.setCancelled(true);
					int gemsouls = Methods.getArgument("%souls%", player.getInventory().getItem(slot.get(player)),
							Methods.getSoulTracker("SoulGemOptions.Name"));
					if (gemsouls >= ((5 - immortall.get(player)) * e.getDamage())) {
						gemsouls = gemsouls - ((5 - immortall.get(player)) * e.getDamage());
						ItemStack gem = player.getInventory().getItem(slot.get(player));
						ItemMeta gemMeta = gem.getItemMeta();
						gemMeta.setDisplayName(
								Methods.color(Methods.getSoulTracker("SoulGemOptions.Name").replace("%souls%", gemsouls + "")));
						gem.setItemMeta(gemMeta);
						player.getInventory().setItem(slot.get(player), gem);
					}
				}
			}
		}
	}

	@EventHandler
	private void playerInteractEvent(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		if (Methods.getItemInHand(player) != null) {
			ItemStack gem = Methods.getItemInHand(player);
			if (gem.hasItemMeta()) {
				if (gem.getType() == Material.EMERALD) {
					if (gem.getItemMeta().hasDisplayName()) {
						if (gem.getItemMeta().hasLore()) {
							String dname = gem.getItemMeta().getDisplayName();
							if (Methods.getArgument("%souls%", dname, Methods.getSoulTracker("SoulGemOptions.Name")) != null) {
								ArrayList<String> clore = new ArrayList<String>();
								for (String line : Main.settings.getSoulTracker()
										.getStringList("SoulGemOptions.Lore")) {
									clore.add(Methods.color(line));
								}
								if (clore.equals(gem.getItemMeta().getLore())) {
									if (gem.getAmount() == 1) {
										if (soul.contains(player)) {
											soul.remove(player);
											slot.remove(player);
											Bukkit.getScheduler().cancelTask(particles.get(player));
											particles.remove(player);
											Bukkit.getScheduler().cancelTask(souls.get(player));
											souls.remove(player);
											player.sendMessage(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulMode.Messages.Deactivate1")));
											player.sendMessage(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulMode.Messages.Deactivate2")));
										} else {
											soul.add(player);
											slot.put(player, player.getInventory().getHeldItemSlot());
											player.sendMessage(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulMode.Messages.Activate1")));
											player.sendMessage(Methods.color(Main.settings.getSoulTracker()
													.getString("SoulMode.Messages.Activate2")));
											particles.put(player, Bukkit.getScheduler()
													.scheduleSyncRepeatingTask(plugin, new Runnable() {

														@Override
														public void run() {
															if (soul.contains(player)) {
																player.getWorld().playEffect(player.getLocation(),
																		Effect.FLYING_GLYPH, 1, 10);
																player.getWorld().playEffect(player.getLocation(),
																		Effect.FLYING_GLYPH, 1, 10);
																player.getWorld().playEffect(player.getLocation(),
																		Effect.FLYING_GLYPH, 1, 10);
																player.getWorld().playEffect(player.getLocation(),
																		Effect.FLYING_GLYPH, 1, 10);
																for (ItemStack item : player.getInventory()
																		.getContents()) {
																	if (Main.CE.hasEnchantments(item)) {
																		if (Main.CE.hasEnchantment(item,
																				CEnchantments.DIVINEIMMOMATION)) {
																			player.getWorld().playEffect(
																					player.getLocation(),
																					Effect.MAGIC_CRIT, 1, 10);
																			player.getWorld().playEffect(
																					player.getLocation(),
																					Effect.MAGIC_CRIT, 1, 10);
																			player.getWorld().playEffect(
																					player.getLocation(),
																					Effect.MAGIC_CRIT, 1, 10);
																			player.getWorld().playEffect(
																					player.getLocation(),
																					Effect.MAGIC_CRIT, 1, 10);
																		}
																	}
																}
															}
														}
													}, 0, 1));
											souls.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
													new Runnable() {

														@Override
														public void run() {
															if (soul.contains(player)) {
																int gemsouls = Methods.getArgument("%souls%",
																		player.getInventory().getItem(slot.get(player)),
																		Methods.getSoulTracker("SoulGemOptions.Name"));
																player.sendMessage(Methods.color(
																		"&e&l** SOULS: &n" + gemsouls + "&r&e&l **"));
																for (ItemStack item : player.getInventory()
																		.getContents()) {
																	if (Main.CE.hasEnchantments(item)) {
																		if (Main.CE.hasEnchantment(item,
																				CEnchantments.DIVINEIMMOMATION)) {
																			if (gemsouls >= 5) {
																				gemsouls = gemsouls - 5;
																				ItemStack gem = player.getInventory()
																						.getItem(slot.get(player));
																				ItemMeta gemMeta = gem.getItemMeta();
																				gemMeta.setDisplayName(Methods.color(Methods
																						.getSoulTracker(
																								"SoulGemOptions.Name")
																						.replace("%souls%",
																								gemsouls + "")));
																				gem.setItemMeta(gemMeta);
																				player.getInventory()
																						.setItem(slot.get(player), gem);
																				player.getWorld().playSound(
																						player.getLocation(),
																						Sound.PORTAL, 1.0f, 2.0f);
																			}
																		}
																	}
																}
																if (soul.contains(player)) {
																	if (paradox.contains(player)) {
																		int gemsoul = Methods.getArgument("%souls%",
																				player.getInventory()
																						.getItem(slot.get(player)),
																				Methods.getSoulTracker(
																						"SoulGemOptions.Name"));
																		if (gemsoul >= 5) {
																			gemsoul = gemsoul - 5;
																			ItemStack gem = player.getInventory()
																					.getItem(slot.get(player));
																			ItemMeta gemMeta = gem.getItemMeta();
																			gemMeta.setDisplayName(Methods.color(Methods
																					.getSoulTracker(
																							"SoulGemOptions.Name")
																					.replace("%souls%", gemsoul + "")));
																			gem.setItemMeta(gemMeta);
																			player.getInventory()
																					.setItem(slot.get(player), gem);
																			player.getWorld().playSound(
																					player.getLocation(), Sound.PORTAL,
																					1.0f, 2.0f);
																		}
																	}
																}
															}
														}
													}, 40, 40));
										}
									} else {
										player.sendMessage(Methods.color(
												"&6Swarm&eEnchants&f >>&c Please unstack your soul gem to use it!"));
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