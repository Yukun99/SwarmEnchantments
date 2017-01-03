package me.BadBones69.CrazyEnchantments.Enchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.Events.EnchantmentUseEvent;
import me.BadBones69.CrazyEnchantments.multiSupport.Support;

public class Axes implements Listener {
	static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");

	@SuppressWarnings("static-access")
	public Axes(Plugin plugin) {
		this.plugin = plugin;
	}

	private HashMap<Player, Integer> timer = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> tbleed = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> dbleed = new HashMap<Player, Integer>();
	private HashMap<Player, Boolean> bleed = Main.getBleed();
	private HashMap<Player, Player> damagers = new HashMap<Player, Player>();

	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent e) {
		bleed.put(e.getPlayer(), false);
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (!Support.allowsPVP(e.getEntity().getLocation()))
			return;
		if (!Support.allowsPVP(e.getDamager().getLocation()))
			return;
		if (Support.isFriendly(e.getDamager(), e.getEntity()))
			return;
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity en = (LivingEntity) e.getEntity();
			if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
				final Player damager = (Player) e.getDamager();
				ItemStack item = Methods.getItemInHand(damager);
				final Player damaged = (Player) e.getEntity();
				if (!e.getEntity().isDead()) {
					if (Main.CE.hasEnchantments(item)) {
						if (Main.CE.hasEnchantment(item, CEnchantments.BLEED)) {
							if (!Main.CE.hasEnchantment(item, CEnchantments.CLEAVE)) {
								if (bleed.get(damaged) != null && bleed.get(damaged) == false) {
									bleed.put(damaged, true);
									dbleed.put(damaged, Main.CE.getPower(item, CEnchantments.BLEED));
									timer.put(damaged, 8);
									damagers.put(damaged, damager);
									tbleed.put(damaged,
											Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
												@Override
												public void run() {
													if (bleed.get(damaged) != null && bleed.get(damaged) == true) {
														if (timer.get(damaged) >= 1) {
															timer.put(damaged, timer.get(damaged) - 1);
															damaged.damage(dbleed.get(damaged), damagers.get(damaged));
														}
														if (timer.get(damaged) == 0) {
															timer.remove(damaged);
															damaged.damage(dbleed.get(damaged), damagers.get(damaged));
															bleed.put(damaged, false);
															dbleed.remove(damaged);
															damagers.remove(damaged);
															Bukkit.getScheduler().cancelTask(tbleed.get(damaged));
															tbleed.remove(damaged);
														}
													}
												}
											}, 0, 1 * 20));
								}
							}
							if (Main.CE.hasEnchantment(item, CEnchantments.CLEAVE)) {
								if (bleed.get(damaged) != true) {
									bleed.put(damaged, true);
									dbleed.put(damaged, Main.CE.getPower(item, CEnchantments.BLEED));
									timer.put(damaged, 8);
									damagers.put(damaged, damager);
									tbleed.put(damaged,
											Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
												@Override
												public void run() {
													if (bleed.get(damaged) != null && bleed.get(damaged) == true) {
														if (timer.get(damaged) != null) {
															if (timer.get(damaged) != null && timer.get(damaged) >= 1) {
																timer.put(damaged, timer.get(damaged) - 1);
																damaged.damage(dbleed.get(damaged),
																		damagers.get(damaged));
															}
															if (timer.get(damaged) != null && timer.get(damaged) == 0) {
																timer.remove(damaged);
																damaged.damage(dbleed.get(damaged),
																		damagers.get(damaged));
																bleed.put(damaged, false);
																dbleed.remove(damaged);
																damagers.remove(damaged);
																Bukkit.getScheduler().cancelTask(tbleed.get(damaged));
																tbleed.remove(damaged);
															}
														}
													}
												}
											}, 0, 1 * 20));
								}
								Random random = new Random();
								int randomChance = 100;
								int randomNumber = random.nextInt(randomChance);
								if (randomNumber + 4 * Main.CE.getPower(item, CEnchantments.CLEAVE) >= 100) {
									Location loc = e.getEntity().getLocation();
									double radius = 3;
									List<Entity> near = loc.getWorld().getEntities();
									for (Entity e1 : near) {
										if (e1.getLocation().distance(loc) <= radius) {
											if (e1 instanceof LivingEntity) {
												if (e1 instanceof Player) {
													if (Support.isFriendly(damager, e1) || damager == e1 || damaged == e1) {
														continue;
													}
													if (!Support.isFriendly(damager, e1) && damager != e1
															&& damaged != e1) {
														final Player player = (Player) e1;
														if (bleed.get(player) != true) {
															bleed.put(player, true);
															dbleed.put(player, Main.CE.getPower(item, CEnchantments.BLEED));
															timer.put(player, 8);
															damagers.put(player, damager);
															tbleed.put(player, Bukkit.getScheduler()
																	.scheduleSyncRepeatingTask(plugin, new Runnable() {
																		@Override
																		public void run() {
																			if (bleed.get(player) != null
																					&& bleed.get(player) == true) {
																				if (timer.get(player) >= 1) {
																					timer.put(player,
																							timer.get(player) - 1);
																					player.damage(dbleed.get(player),
																							damagers.get(player));
																				}
																				if (timer.get(player) == 0) {
																					timer.remove(player);
																					player.damage(dbleed.get(player),
																							damagers.get(player));
																					bleed.put(player, false);
																					dbleed.remove(player);
																					damagers.remove(player);
																					Bukkit.getScheduler().cancelTask(
																							tbleed.get(damaged));
																					tbleed.remove(player);
																				}
																			}
																		}
																	}, 0, 1 * 20));
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if (!Main.CE.hasEnchantment(item, CEnchantments.BLEED)) {
							if (Main.CE.hasEnchantment(item, CEnchantments.CLEAVE)) {
								if (Armor.getLuck().get(damaged) != null) {
									if (Methods.randomPicker(4 * Armor.getLuck().get(damaged)
											+ 4 * Main.CE.getPower(item, CEnchantments.CLEAVE))) {
										Location loc = e.getEntity().getLocation();
										double damage = e.getDamage();
										double radius = 3;
										List<Entity> near = loc.getWorld().getEntities();
										for (Entity e1 : near) {
											if (e1.getLocation().distance(loc) <= radius) {
												if (e1 instanceof LivingEntity) {
													if (e1 instanceof Player) {
														if (Support.isFriendly(damager, e1) || damager == e1) {
															continue;
														} else {
															Player pnear = (Player) e1;
															pnear.damage(damage, damagers.get(pnear));
														}
													}
												}
											}
										}
									} else {
										if (Methods.randomPicker(+4 * Main.CE.getPower(item, CEnchantments.CLEAVE))) {
											Location loc = e.getEntity().getLocation();
											double damage = e.getDamage();
											double radius = 3;
											List<Entity> near = loc.getWorld().getEntities();
											for (Entity e1 : near) {
												if (e1.getLocation().distance(loc) <= radius) {
													if (e1 instanceof LivingEntity) {
														if (e1 instanceof Player) {
															if (Support.isFriendly(damager, e1) || damaged == e1
																	|| damager == e1) {
																continue;
															} else {
																Player pnear = (Player) e1;
																pnear.damage(damage, damagers.get(pnear));
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
						if (Main.CE.hasEnchantment(item, CEnchantments.PYRO)) {
							if (CEnchantments.PYRO.isEnabled()) {
								if (Methods.randomPicker(10)) {
									en.setFireTicks((Main.CE.getPower(item, CEnchantments.PYRO) * 2) * 20);
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.BERSERK)) {
							if (CEnchantments.BERSERK.isEnabled()) {
								if (Methods.randomPicker(12)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.BERSERK,
											item);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,
												(Main.CE.getPower(item, CEnchantments.BERSERK) + 5) * 20, 1));
										damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
												(Main.CE.getPower(item, CEnchantments.BERSERK) + 5) * 20, 0));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.BLESSED)) {
							if (CEnchantments.BLESSED.isEnabled()) {
								if (Methods.randomPicker((12 - Main.CE.getPower(item, CEnchantments.BLESSED)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.BLESSED,
											item);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										removeBadPotions(damager);
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.FEEDME)) {
							if (CEnchantments.FEEDME.isEnabled()) {
								int food = 2 * Main.CE.getPower(item, CEnchantments.FEEDME);
								if (Methods.randomPicker(10)) {
									if (damager.getFoodLevel() < 20) {
										EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
												CEnchantments.FEEDME, item);
										Bukkit.getPluginManager().callEvent(event);
										if (!event.isCancelled()) {
											if (damager.getFoodLevel() + food < 20) {
												damager.setFoodLevel((int) (damager.getSaturation() + food));
											}
											if (damager.getFoodLevel() + food > 20) {
												damager.setFoodLevel(20);
											}
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.REKT)) {
							if (CEnchantments.REKT.isEnabled()) {
								double damage = e.getDamage() * 2;
								if (Methods.randomPicker((20 - Main.CE.getPower(item, CEnchantments.REKT)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.REKT,
											item);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										e.setDamage(damage);
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.CURSED)) {
							if (CEnchantments.CURSED.isEnabled()) {
								if (Methods.randomPicker(10)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.CURSED,
											item);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,
												(Main.CE.getPower(item, CEnchantments.CURSED) + 9) * 20, 1));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.DIZZY)) {
							if (CEnchantments.DIZZY.isEnabled()) {
								if (Methods.randomPicker(10)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.DIZZY,
											item);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
												(Main.CE.getPower(item, CEnchantments.DIZZY) + 9) * 20, 0));
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
	public void onPlayerDamage(PlayerDeathEvent e) {
		Player killed = e.getEntity();
		if (!Support.allowsPVP(e.getEntity().getLocation()))
			return;
		if (e.getEntity().getKiller() instanceof Player) {
			Player damager = (Player) e.getEntity().getKiller();
			Player player = e.getEntity();
			ItemStack item = Methods.getItemInHand(damager);
			if (Main.CE.hasEnchantments(item)) {
				if (Main.CE.hasEnchantment(item, CEnchantments.DECAPITATION)) {
					if (CEnchantments.DECAPITATION.isEnabled()) {
						int power = Main.CE.getPower(item, CEnchantments.DECAPITATION);
						if (Methods.randomPicker(11 - power)) {
							EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.DECAPITATION,
									item);
							Bukkit.getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								ItemStack head = Methods.makeItem("397:3", 1);
								SkullMeta m = (SkullMeta) head.getItemMeta();
								m.setOwner(player.getName());
								head.setItemMeta(m);
								e.getDrops().add(head);
							}
						}
					}
				}
			}
		}
		if (timer.get(killed) != null) {
			timer.remove(killed);
		}
		if (dbleed.get(killed) != null) {
			dbleed.remove(killed);
		}
		if (damagers.get(killed) != null) {
			damagers.remove(killed);
		}
		if (bleed.get(killed) != null && bleed.get(killed) == true) {
			bleed.put(killed, false);
		}
		if (tbleed.get(killed) != null) {
			Bukkit.getScheduler().cancelTask(tbleed.get(killed));
			tbleed.remove(killed);
		}
	}

	public void onPlayerLeave(PlayerQuitEvent e) {
		Player killed = e.getPlayer();
		if (timer.get(killed) != null) {
			timer.remove(killed);
		}
		if (dbleed.get(killed) != null) {
			dbleed.remove(killed);
		}
		if (damagers.get(killed) != null) {
			damagers.remove(killed);
		}
		if (bleed.get(killed) != null && bleed.get(killed) == true) {
			bleed.remove(killed);
		}
		if (tbleed.get(killed) != null) {
			Bukkit.getScheduler().cancelTask(tbleed.get(killed));
			tbleed.remove(killed);
		}
	}

	void removeBadPotions(Player player) {
		ArrayList<PotionEffectType> bad = new ArrayList<PotionEffectType>();
		bad.add(PotionEffectType.BLINDNESS);
		bad.add(PotionEffectType.CONFUSION);
		bad.add(PotionEffectType.HUNGER);
		bad.add(PotionEffectType.POISON);
		bad.add(PotionEffectType.SLOW);
		bad.add(PotionEffectType.SLOW_DIGGING);
		bad.add(PotionEffectType.WEAKNESS);
		bad.add(PotionEffectType.WITHER);
		for (PotionEffectType p : bad) {
			if (player.hasPotionEffect(p)) {
				player.removePotionEffect(p);
			}
		}
	}
}