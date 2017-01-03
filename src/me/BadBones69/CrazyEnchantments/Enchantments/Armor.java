package me.BadBones69.CrazyEnchantments.Enchantments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.Events.AngelUseEvent;
import me.BadBones69.CrazyEnchantments.API.Events.ArmorEquipEvent;
import me.BadBones69.CrazyEnchantments.API.Events.AuraActiveEvent;
import me.BadBones69.CrazyEnchantments.API.Events.EnchantmentUseEvent;
import me.BadBones69.CrazyEnchantments.API.Events.HellForgedUseEvent;
import me.BadBones69.CrazyEnchantments.multiSupport.Support;

public class Armor implements Listener {
	private static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");

	public static HashMap<Player, Integer> getLuck() {
		return luck;
	}

	private ArrayList<Player> fall = new ArrayList<Player>();
	private HashMap<Player, HashMap<CEnchantments, Calendar>> timer = new HashMap<Player, HashMap<CEnchantments, Calendar>>();
	private int time = Integer.MAX_VALUE;
	private HashMap<Player, Boolean> cooldown = Main.getCooldown();
	private HashMap<Player, Integer> burn = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> melt = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> luck = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> task = new HashMap<Player, Integer>();
	private static ArrayList<LivingEntity> allies = new ArrayList<LivingEntity>();

	@EventHandler
	public void onEquip(ArmorEquipEvent e) {
		Player player = e.getPlayer();
		ItemStack NewItem = e.getNewArmorPiece();
		ItemStack OldItem = e.getOldArmorPiece();
		if (Main.CE.hasEnchantments(NewItem)) {
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.BURNSHIELD)) {
				if (CEnchantments.BURNSHIELD.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.BURNSHIELD, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, 0));
						burn.put(player, Main.CE.getPower(NewItem, CEnchantments.BURNSHIELD));
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.LUCKY)) {
				if (CEnchantments.LUCKY.isEnabled()) {
					luck.put(player, Main.CE.getPower(NewItem, CEnchantments.LUCKY));
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.MOLTEN)) {
				if (CEnchantments.MOLTEN.isEnabled()) {
					melt.put(player, Main.CE.getPower(NewItem, CEnchantments.MOLTEN));
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.HULK)) {
				if (CEnchantments.HULK.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.HULK, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time,
								Main.CE.getPower(NewItem, CEnchantments.HULK) - 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time,
								Main.CE.getPower(NewItem, CEnchantments.HULK) - 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time,
								Main.CE.getPower(NewItem, CEnchantments.HULK)));
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.OVERLOAD)) {
				if (CEnchantments.OVERLOAD.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.OVERLOAD, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, time,
								Main.CE.getPower(NewItem, CEnchantments.OVERLOAD) - 1));
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.NINJA)) {
				if (CEnchantments.NINJA.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.NINJA, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, time,
								Main.CE.getPower(NewItem, CEnchantments.NINJA) - 1));
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time,
								Main.CE.getPower(NewItem, CEnchantments.NINJA) - 1));
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.INSOMNIA)) {
				if (CEnchantments.INSOMNIA.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.INSOMNIA, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 1));
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.ARMOURED)) {
				if (CEnchantments.ARMOURED.isEnabled()) {
					if (player.getHealth() <= 4) {
						int power = Main.CE.getPower(NewItem, CEnchantments.ARMOURED);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, power - 1));
					}
				}
			}
		}
		if (Main.CE.hasEnchantments(OldItem)) {
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.HULK)) {
				if (CEnchantments.HULK.isEnabled()) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					player.removePotionEffect(PotionEffectType.SLOW);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.VALOR)) {
				if (CEnchantments.VALOR.isEnabled()) {
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.OVERLOAD)) {
				if (CEnchantments.OVERLOAD.isEnabled()) {
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.NINJA)) {
				if (CEnchantments.NINJA.isEnabled()) {
					player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
					player.removePotionEffect(PotionEffectType.SPEED);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.INSOMNIA)) {
				if (CEnchantments.INSOMNIA.isEnabled()) {
					player.removePotionEffect(PotionEffectType.CONFUSION);
					player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
					player.removePotionEffect(PotionEffectType.SLOW);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.ARMOURED)) {
				if (CEnchantments.ARMOURED.isEnabled()) {
					player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (Support.isFriendly(e.getDamager(), e.getEntity()))
			return;
		if (!Support.allowsPVP(e.getEntity().getLocation()))
			return;
		if (!Support.allowsPVP(e.getDamager().getLocation()))
			return;
		if (e.getDamager() instanceof LivingEntity) {
			if (e.getEntity() instanceof Player) {
				final Player player = (Player) e.getEntity();
				final LivingEntity damager = (LivingEntity) e.getDamager();
				for (ItemStack armor : player.getEquipment().getArmorContents()) {
					if (Main.CE.hasEnchantments(armor)) {
						if (Main.CE.hasEnchantment(armor, CEnchantments.ROCKET)) {
							if (CEnchantments.ROCKET.isEnabled()) {
								if (player.getHealth() <= 8) {
									if (Methods.randomPicker((8 - Main.CE.getPower(armor, CEnchantments.ROCKET)))) {
										EnchantmentUseEvent event = new EnchantmentUseEvent(player,
												CEnchantments.ROCKET, armor);
										Bukkit.getPluginManager().callEvent(event);
										if (!event.isCancelled()) {
											Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
													new Runnable() {
														public void run() {
															Vector v = player.getLocation().toVector()
																	.subtract(damager.getLocation().toVector())
																	.normalize().setY(10);
															player.setVelocity(v);
															player.setHealth(player.getMaxHealth());
														}
													}, 1);
											player.getWorld().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE,
													1);
											fall.add(player);
											Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
													new Runnable() {
														public void run() {
															fall.remove(player);
														}
													}, 15 * 20);
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.VALOR)) {
							if (CEnchantments.VALOR.isEnabled()) {
								if (Methods.randomPicker((10 - Main.CE.getPower(armor, CEnchantments.VALOR)))) {
									player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20,
											Main.CE.getPower(armor, CEnchantments.VALOR) - 1));
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.ARMOURED)) {
							if (CEnchantments.ARMOURED.isEnabled()) {
								if (player.getHealth() > 4) {
									player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.SHOCKWAVE)) {
							if (CEnchantments.SHOCKWAVE.isEnabled()) {
								if (Methods.randomPicker(30 - Main.CE.getPower(armor, CEnchantments.SHOCKWAVE))) {
									Location loc = e.getEntity().getLocation();
									double radius = 3;
									List<Entity> near = loc.getWorld().getEntities();
									for (Entity e1 : near) {
										if (e1.getLocation().distance(loc) <= radius) {
											if (e1 instanceof LivingEntity) {
												if (e1 instanceof Player) {
													if (Support.isFriendly(damager, e1)) {
														return;
													} else {
														Bukkit.getServer().getScheduler()
																.scheduleSyncDelayedTask(plugin, new Runnable() {

																	public void run() {
																		Vector v = player.getLocation().toVector()
																				.subtract(damager.getLocation()
																						.toVector())
																				.normalize().setY(1);
																		player.setVelocity(v);
																	}
																}, 1);
													}
												}
											}
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.ENLIGHTENED)) {
							if (CEnchantments.ENLIGHTENED.isEnabled()) {
								if (Methods.randomPicker(10)) {

									EnchantmentUseEvent event = new EnchantmentUseEvent(player,
											CEnchantments.ENLIGHTENED, armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										double heal = Main.CE.getPower(armor, CEnchantments.ENLIGHTENED);
										if (player.getHealth() + heal < player.getMaxHealth()) {
											player.setHealth(player.getHealth() + heal);
										}
										if (player.getHealth() + heal >= player.getMaxHealth()) {
											player.setHealth(player.getMaxHealth());
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.FORTIFY)) {
							if (CEnchantments.FORTIFY.isEnabled()) {
								if (Methods.randomPicker(12)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.FORTIFY,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5 * 20,
												Main.CE.getPower(armor, CEnchantments.FORTIFY)));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.FREEZE)) {
							if (CEnchantments.FREEZE.isEnabled()) {
								if (Methods.randomPicker(10)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.FREEZE,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20,
												1 + Main.CE.getPower(armor, CEnchantments.FREEZE)));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.MOLTEN)) {
							if (CEnchantments.MOLTEN.isEnabled()) {
								if (Methods.randomPicker(12)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.MOLTEN,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.setFireTicks((Main.CE.getPower(armor, CEnchantments.MOLTEN) * 2) * 20);
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.PAINGIVER)) {
							if (CEnchantments.PAINGIVER.isEnabled()) {
								if (Methods.randomPicker(10)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.PAINGIVER,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 3 * 20,
												Main.CE.getPower(armor, CEnchantments.PAINGIVER)));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.SAVIOR)) {
							if (CEnchantments.SAVIOR.isEnabled()) {
								if (Methods.randomPicker((9 - Main.CE.getPower(armor, CEnchantments.SAVIOR)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.SAVIOR,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										e.setDamage(e.getDamage() / 2);
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.SMOKEBOMB)) {
							if (CEnchantments.SMOKEBOMB.isEnabled()) {
								if (Methods.randomPicker((11 - Main.CE.getPower(armor, CEnchantments.SMOKEBOMB)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.SMOKEBOMB,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 1));
										damager.addPotionEffect(
												new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 0));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.VOODOO)) {
							if (CEnchantments.VOODOO.isEnabled()) {
								if (Methods.randomPicker(7)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.VOODOO,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5 * 20,
												Main.CE.getPower(armor, CEnchantments.VOODOO) - 1));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.INSOMNIA)) {
							if (CEnchantments.INSOMNIA.isEnabled()) {
								if (Methods.randomPicker(3)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.INSOMNIA,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										e.setDamage((e.getDamage() * 2));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.CACTUS)) {
							if (CEnchantments.CACTUS.isEnabled()) {
								if (Methods.randomPicker(4)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.CACTUS,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.damage(Main.CE.getPower(armor, CEnchantments.CACTUS));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(armor, CEnchantments.STORMCALLER)) {
							if (CEnchantments.STORMCALLER.isEnabled()) {
								if (Methods.randomPicker((12 - Main.CE.getPower(armor, CEnchantments.STORMCALLER)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player,
											CEnchantments.STORMCALLER, armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										Location loc = damager.getLocation();
										loc.getWorld().strikeLightningEffect(loc);
										for (LivingEntity en : Methods.getNearbyEntities(loc, 2D, damager)) {
											if (Support.allowsPVP(en.getLocation())) {
												if (!Support.isFriendly(player, en) && en != damager) {
													en.damage(5D, damager);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (damager instanceof Player) {
					for (ItemStack armor : damager.getEquipment().getArmorContents()) {
						if (Main.CE.hasEnchantments(armor)) {
							if (Main.CE.hasEnchantment(armor, CEnchantments.LEADERSHIP)) {
								if (CEnchantments.LEADERSHIP.isEnabled()) {
									if (Methods.randomPicker(12)) {
										if (Methods.hasFactions() || Methods.hasFeudal()) {
											int radius = 4 + Main.CE.getPower(armor, CEnchantments.LEADERSHIP);
											int players = 0;
											for (Entity en : damager.getNearbyEntities(radius, radius, radius)) {
												if (en instanceof Player) {
													Player o = (Player) en;
													if (Support.isFriendly(damager, o)) {
														players++;
													}
												}
											}
											if (players > 0) {
												EnchantmentUseEvent event = new EnchantmentUseEvent((Player) damager,
														CEnchantments.LEADERSHIP, armor);
												Bukkit.getPluginManager().callEvent(event);
												if (!event.isCancelled()) {
													e.setDamage(e.getDamage() + (players / 2));
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
	public void onAura(AuraActiveEvent e) {
		Player player = e.getPlayer();
		Player other = e.getOther();
		CEnchantments enchant = e.getEnchantment();
		int power = e.getPower();
		if (Support.allowsPVP(other.getLocation())) {
			if (!Support.isFriendly(player, other)) {
				Calendar cal = Calendar.getInstance();
				HashMap<CEnchantments, Calendar> eff = new HashMap<CEnchantments, Calendar>();
				if (timer.containsKey(other)) {
					eff = timer.get(other);
				}
				switch (enchant) {
				case BLIZZARD:
					if (CEnchantments.BLIZZARD.isEnabled()) {
						other.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, power - 1));
					}
					break;
				case INTIMIDATE:
					if (CEnchantments.INTIMIDATE.isEnabled()) {
						other.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3 * 20, power - 1));
					}
					break;
				case ACIDRAIN:
					if (CEnchantments.ACIDRAIN.isEnabled()) {
						if (!timer.containsKey(other)
								|| (timer.containsKey(other) && !timer.get(other).containsKey(enchant))
								|| (timer.containsKey(other) && timer.get(other).containsKey(enchant)
										&& cal.after(timer.get(other).get(enchant)))) {
							if (Methods.randomPicker(45)) {
								other.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 1));
								int time = 35 - (power * 5);
								// time > 0 ? time : 0
								// Means if "time" is less then 0 put the time
								// as "time" other wise set it to 0.
								Calendar c = cal;
								c.add(Calendar.SECOND, time > 0 ? time : 5);
								eff.put(enchant, c);
							}
						}
					}
					break;
				case SANDSTORM:
					if (CEnchantments.SANDSTORM.isEnabled()) {
						if (!timer.containsKey(other)
								|| (timer.containsKey(other) && !timer.get(other).containsKey(enchant))
								|| (timer.containsKey(other) && timer.get(other).containsKey(enchant)
										&& cal.after(timer.get(other).get(enchant)))) {
							if (Methods.randomPicker(38)) {
								other.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 * 20, 0));
								int time = 35 - (power * 5);
								// time > 0 ? time : 0
								// Means if "time" is less then 0 put the time
								// as "time" other wise set it to 0.
								Calendar c = cal;
								c.add(Calendar.SECOND, time > 0 ? time : 5);
								eff.put(enchant, c);
							}
						}
					}
					break;
				case RADIANT:
					if (CEnchantments.RADIANT.isEnabled()) {
						if (!timer.containsKey(other)
								|| (timer.containsKey(other) && !timer.get(other).containsKey(enchant))
								|| (timer.containsKey(other) && timer.get(other).containsKey(enchant)
										&& cal.after(timer.get(other).get(enchant)))) {
							if (Methods.randomPicker(25)) {
								other.setFireTicks(5 * 20);
								int time = 20 - (power * 5);
								// time > 0 ? time : 0
								// Means if "time" is less then 0 put the time
								// as "time" other wise set it to 0.
								Calendar c = cal;
								c.add(Calendar.SECOND, time > 0 ? time : 0);
								eff.put(enchant, c);
							}
						}
					}
					break;
				default:
					break;
				}
				timer.put(other, eff);
			}
		}
	}

	@EventHandler
	public void onMovement(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		int X = e.getFrom().getBlockX();
		int Y = e.getFrom().getBlockY();
		int Z = e.getFrom().getBlockZ();
		int x = e.getTo().getBlockX();
		int y = e.getTo().getBlockY();
		int z = e.getTo().getBlockZ();
		if (x != X || y != Y | z != Z) {
			for (ItemStack armor : player.getEquipment().getArmorContents()) {
				if (Main.CE.hasEnchantments(armor)) {
					if (Main.CE.hasEnchantment(armor, CEnchantments.NURSERY)) {
						if (CEnchantments.NURSERY.isEnabled()) {
							int heal = 1;
							if (Methods.randomPicker((25 - Main.CE.getPower(armor, CEnchantments.NURSERY)))) {
								if (player.getMaxHealth() > player.getHealth()) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.NURSERY,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										if (player.getHealth() + heal <= player.getMaxHealth()) {
											player.setHealth(player.getHealth() + heal);
										}
										if (player.getHealth() + heal >= player.getMaxHealth()) {
											player.setHealth(player.getMaxHealth());
										}
									}
								}
							}
						}
					}
					if (Main.CE.hasEnchantment(armor, CEnchantments.IMPLANTS)) {
						if (CEnchantments.IMPLANTS.isEnabled()) {
							int food = 1;
							if (Methods.randomPicker((25 - Main.CE.getPower(armor, CEnchantments.IMPLANTS)))) {
								if (player.getFoodLevel() < 20) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.IMPLANTS,
											armor);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										if (player.getFoodLevel() + food <= 20) {
											player.setFoodLevel(player.getFoodLevel() + food);
										}
										if (player.getFoodLevel() + food >= 20) {
											player.setFoodLevel(20);
										}
									}
								}
							}
						}
					}
					if (Main.CE.hasEnchantment(armor, CEnchantments.ANGEL)) {
						if (CEnchantments.ANGEL.isEnabled()) {
							if (Methods.hasFactions()) {
								int radius = 4 + Main.CE.getPower(armor, CEnchantments.ANGEL);
								for (Entity en : player.getNearbyEntities(radius, radius, radius)) {
									if (en instanceof Player) {
										Player o = (Player) en;
										if (Support.isFriendly(player, o)) {
											AngelUseEvent event = new AngelUseEvent(player, armor);
											Bukkit.getPluginManager().callEvent(event);
											if (!event.isCancelled()) {
												o.addPotionEffect(
														new PotionEffect(PotionEffectType.REGENERATION, 3 * 20, 0));
											}
										}
									}
								}
							}
						}
					}
					if (Main.CE.hasEnchantment(armor, CEnchantments.HELLFORGED)) {
						if (CEnchantments.HELLFORGED.isEnabled()) {
							if (armor.getDurability() > 0) {
								if (Methods.randomPicker(15)) {
									int dur = armor.getDurability() - Main.CE.getPower(armor, CEnchantments.HELLFORGED);
									if (armor.getDurability() > 0) {
										HellForgedUseEvent event = new HellForgedUseEvent(player, armor);
										Bukkit.getPluginManager().callEvent(event);
										if (!event.isCancelled()) {
											if (dur > 0) {
												armor.setDurability((short) dur);
											} else {
												armor.setDurability((short) 0);
											}
										}
									}
								}
							}
						}
					}
					if (Main.CE.hasEnchantment(armor, CEnchantments.ARMOURED)) {
						if (CEnchantments.ARMOURED.isEnabled()) {
							if (player.getHealth() <= 4) {
								int power = Main.CE.getPower(armor, CEnchantments.ARMOURED);
								player.addPotionEffect(
										new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, power - 1));
							}
						}
					}
				}
			}
			for (ItemStack item : player.getInventory().getContents()) {
				if (Main.CE.hasEnchantments(item)) {
					if (Main.CE.hasEnchantment(item, CEnchantments.HELLFORGED)) {
						if (CEnchantments.HELLFORGED.isEnabled()) {
							if (item.getDurability() > 0) {
								if (Methods.randomPicker(12)) {
									int dur = item.getDurability() - Main.CE.getPower(item, CEnchantments.HELLFORGED);
									if (item.getDurability() > 0) {
										HellForgedUseEvent event = new HellForgedUseEvent(player, item);
										Bukkit.getPluginManager().callEvent(event);
										if (!event.isCancelled()) {
											if (dur > 0) {
												item.setDurability((short) dur);
											} else {
												item.setDurability((short) 0);
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
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		Player killer = player.getKiller();
		if (!Support.allowsPVP(player.getLocation()))
			return;
		for (ItemStack item : player.getEquipment().getArmorContents()) {
			if (Main.CE.hasEnchantments(item)) {
				if (Main.CE.hasEnchantment(item, CEnchantments.SELFDESTRUCT)) {
					if (CEnchantments.SELFDESTRUCT.isEnabled()) {
						EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.SELFDESTRUCT, item);
						Bukkit.getPluginManager().callEvent(event);
						if (!event.isCancelled()) {
							Location loc = e.getEntity().getLocation();
							loc.getWorld().createExplosion(loc, Main.CE.getPower(item, CEnchantments.SELFDESTRUCT));
						}
					}
				}
			}
		}
		if (killer instanceof Player) {
			for (ItemStack item : killer.getEquipment().getArmorContents()) {
				if (Main.CE.hasEnchantments(item)) {
					if (Main.CE.hasEnchantment(item, CEnchantments.RECOVER)) {
						if (CEnchantments.RECOVER.isEnabled()) {
							EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.RECOVER, item);
							Bukkit.getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 2));
								killer.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 1));
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerFallDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.FALL) {
				if (fall.contains((Player) e.getEntity())) {
					e.setCancelled(true);
				}
			}
		}
	}

	private void spawnIG(Player player, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final IronGolem ig = (IronGolem) player.getWorld().spawn(player.getLocation(), (Class) IronGolem.class);
			ig.setCanPickupItems(false);
			ig.setHealth(80.0);
			ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 2));
			ig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 2));
			ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 2));
			ig.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player.getName() + "'s &e&lGuardian"));
			ig.setTarget((LivingEntity) damager);
			Support.noStack(ig);
			allies.add(ig);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					ig.remove();
				}
			}, 1200);
			return;
		}
	}

	@EventHandler
	private void allyDamageOwnerEvent(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player owner = (Player) e.getEntity();
			if (!(e.getDamager() instanceof Player)) {
				if (allies.contains(e.getDamager())) {
					if (Methods.removeColor(e.getDamager().getCustomName()).contains(owner.getName())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	private void playerEvent(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		if (!(damager instanceof Player) || !(entity instanceof Player) || event.isCancelled()) {
			return;
		}
		Player player = (Player) event.getEntity();
		if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().hasItemMeta()
				|| !player.getInventory().getChestplate().getItemMeta().hasLore()) {
			return;
		}
		Random random = new Random();
		int randomChance = 100000;
		int randomNumber = random.nextInt(randomChance);
		for (ItemStack item : player.getEquipment().getArmorContents()) {
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasLore()) {
					if (Main.CE.hasEnchantment(item, CEnchantments.GUARDIANS)) {
						if (CEnchantments.GUARDIANS.isEnabled()) {
							if (Main.CE.hasEnchantment(item, CEnchantments.LUCKY)) {
								if ((randomNumber + 100 * Main.CE.getPower(item, CEnchantments.LUCKY)
										+ 1000 * Main.CE.getPower(item, CEnchantments.GUARDIANS)) >= 99900) {
									spawnIG(player, event);
									return;
								}
							}
							if ((randomNumber + 1000 * Main.CE.getPower(item, CEnchantments.GUARDIANS)) >= 99900) {
								spawnIG(player, event);
								return;
							}
						}
					}
				}
			}
		}
	}

	private void spawnBlaze(Player player1, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player1.getWorld(), (player1.getLocation().getX() + 5),
					(player1.getLocation().getY() + 3), (player1.getLocation().getZ() + 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Blaze bz = (Blaze) player1.getWorld().spawn(location, (Class) Blaze.class);
			bz.setCanPickupItems(false);
			bz.setMaxHealth(30);
			bz.setHealth(30);
			bz.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 2));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 2));
			bz.setCustomName(Methods.color("&b&l" + player1.getName() + "'s &a&lSpirit"));
			bz.setTarget((LivingEntity) damager);
			Support.noStack(bz);
			allies.add(bz);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					bz.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnBlaze1(Player player1, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player1.getWorld(), (player1.getLocation().getX() - 5),
					(player1.getLocation().getY() + 3), (player1.getLocation().getZ() + 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Blaze bz = (Blaze) player1.getWorld().spawn(location, (Class) Blaze.class);
			bz.setCanPickupItems(false);
			bz.setMaxHealth(30);
			bz.setHealth(30);
			bz.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 2));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 2));
			bz.setCustomName(Methods.color("&b&l" + player1.getName() + "'s &a&lSpirit"));
			bz.setTarget((LivingEntity) damager);
			Support.noStack(bz);
			allies.add(bz);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					bz.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnBlaze2(Player player1, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player1.getWorld(), (player1.getLocation().getX() + 5),
					(player1.getLocation().getY() + 3), (player1.getLocation().getZ() - 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Blaze bz = (Blaze) player1.getWorld().spawn(location, (Class) Blaze.class);
			bz.setCanPickupItems(false);
			bz.setMaxHealth(30);
			bz.setHealth(30);
			bz.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 2));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 2));
			bz.setCustomName(Methods.color("&b&l" + player1.getName() + "'s &a&lSpirit"));
			bz.setTarget((LivingEntity) damager);
			Support.noStack(bz);
			allies.add(bz);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					bz.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnBlaze3(Player player1, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player1.getWorld(), (player1.getLocation().getX() - 5),
					(player1.getLocation().getY() + 3), (player1.getLocation().getZ() - 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Blaze bz = (Blaze) player1.getWorld().spawn(location, (Class) Blaze.class);
			bz.setCanPickupItems(false);
			bz.setMaxHealth(30);
			bz.setHealth(30);
			bz.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 2));
			bz.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 2));
			bz.setCustomName(Methods.color("&b&l" + player1.getName() + "'s &a&lSpirit"));
			bz.setTarget((LivingEntity) damager);
			Support.noStack(bz);
			allies.add(bz);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					bz.remove();
				}
			}, 1200);
			return;
		}
	}

	@EventHandler
	private void playerEvent1(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		if (!(damager instanceof Player) || !(entity instanceof Player) || event.isCancelled()) {
			return;
		}
		Player player = (Player) event.getEntity();
		if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().hasItemMeta()
				|| !player.getInventory().getChestplate().getItemMeta().hasLore()) {
			return;
		}
		Random random = new Random();
		int randomChance = 100000;
		int randomNumber = random.nextInt(randomChance);
		for (ItemStack item : player.getEquipment().getArmorContents()) {
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasLore()) {
					if (Main.CE.hasEnchantment(item, CEnchantments.SPIRITS)) {
						if (CEnchantments.SPIRITS.isEnabled()) {
							if (Main.CE.hasEnchantment(item, CEnchantments.LUCKY)) {
								if (CEnchantments.LUCKY.isEnabled()) {
									if ((randomNumber + 100 * Main.CE.getPower(item, CEnchantments.LUCKY)
											+ 1000 * Main.CE.getPower(item, CEnchantments.SPIRITS)) >= 99900) {
										this.spawnBlaze(player, event);
										this.spawnBlaze1(player, event);
										this.spawnBlaze2(player, event);
										this.spawnBlaze3(player, event);
										return;
									}
								}
							}
							if ((randomNumber + 1000 * Main.CE.getPower(item, CEnchantments.SPIRITS)) >= 99900) {
								this.spawnBlaze(player, event);
								this.spawnBlaze1(player, event);
								this.spawnBlaze2(player, event);
								this.spawnBlaze3(player, event);
								return;
							}
						}
					}
				}
			}
		}
	}

	private void spawnZombie(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX() - 5),
					(player2.getLocation().getY()), (player2.getLocation().getZ() - 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie1(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX() + 5),
					(player2.getLocation().getY()), (player2.getLocation().getZ() + 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie2(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX() - 5),
					(player2.getLocation().getY()), (player2.getLocation().getZ() + 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie3(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX() + 5),
					(player2.getLocation().getY()), (player2.getLocation().getZ() - 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie4(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX()),
					(player2.getLocation().getY()), (player2.getLocation().getZ() + 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie5(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX() + 5),
					(player2.getLocation().getY()), (player2.getLocation().getZ()));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie6(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX()),
					(player2.getLocation().getY()), (player2.getLocation().getZ() - 5));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	private void spawnZombie7(Player player2, EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Location location = new Location(player2.getWorld(), (player2.getLocation().getX() - 5),
					(player2.getLocation().getY()), (player2.getLocation().getZ()));
			@SuppressWarnings({ "unchecked", "rawtypes" })
			final Zombie zb = (Zombie) player2.getWorld().spawn(location, (Class) Zombie.class);
			zb.setCanPickupItems(false);
			zb.setMaxHealth(30);
			zb.setHealth(30);
			zb.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
			zb.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
			zb.setCustomName(ChatColor.translateAlternateColorCodes((char) '&',
					(String) "&b&l" + player2.getName() + "'s &b&lUndead Army"));
			zb.setTarget((LivingEntity) damager);
			Support.noStack(zb);
			allies.add(zb);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					zb.remove();
				}
			}, 1200);
			return;
		}
	}

	@EventHandler
	private void playerEvent2(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		if (!(damager instanceof Player) || !(entity instanceof Player) || event.isCancelled()) {
			return;
		}
		final Player player = (Player) event.getEntity();
		if (player.getInventory().getChestplate() == null || !player.getInventory().getChestplate().hasItemMeta()
				|| !player.getInventory().getChestplate().getItemMeta().hasLore()) {
			return;
		}
		Random random = new Random();
		int randomChance = 100000;
		int randomNumber = random.nextInt(randomChance);
		for (ItemStack item : player.getEquipment().getArmorContents()) {
			if (cooldown.get(player) != null && cooldown.get(player) == true) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().hasLore()) {
						if (player.getHealth() <= 4) {
							if (Main.CE.hasEnchantment(item, CEnchantments.UNDEADRUSE)) {
								if (CEnchantments.UNDEADRUSE.isEnabled()) {
									if (Main.CE.hasEnchantment(item, CEnchantments.LUCKY)) {
										if (CEnchantments.LUCKY.isEnabled()) {
											if ((randomNumber + 100 * Main.CE.getPower(item, CEnchantments.LUCKY) + 1000
													* Main.CE.getPower(item, CEnchantments.UNDEADRUSE)) >= 99900) {
												cooldown.put(player, false);
												player.addPotionEffect(
														new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 2));
												this.spawnZombie(player, event);
												this.spawnZombie1(player, event);
												this.spawnZombie2(player, event);
												this.spawnZombie3(player, event);
												this.spawnZombie4(player, event);
												this.spawnZombie5(player, event);
												this.spawnZombie6(player, event);
												this.spawnZombie7(player, event);
												Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
													@Override
													public void run() {
														cooldown.put(player, true);
													}
												}, 6000);
												return;
											}
										}
									}
									if ((randomNumber
											+ 1000 * Main.CE.getPower(item, CEnchantments.UNDEADRUSE)) >= 99900) {
										player.addPotionEffect(
												new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 2));
										cooldown.put(player, false);
										this.spawnZombie(player, event);
										this.spawnZombie1(player, event);
										this.spawnZombie2(player, event);
										this.spawnZombie3(player, event);
										this.spawnZombie4(player, event);
										this.spawnZombie5(player, event);
										this.spawnZombie6(player, event);
										this.spawnZombie7(player, event);
										Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

											@Override
											public void run() {
												cooldown.put(player, true);
											}

										}, 6000);
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

	@EventHandler
	private void allyDeathEvent(EntityDeathEvent e) {
		LivingEntity en = e.getEntity();
		if (allies.contains(en)) {
			allies.remove(en);
		}
	}

	public static void removeAllies() {
		for (LivingEntity ally : allies) {
			ally.remove();
		}
	}

	@EventHandler
	private void armorDamageEvent(PlayerItemDamageEvent event) {
		Random random = new Random();
		int randomChance = 100;
		int randomNumber = random.nextInt(randomChance);
		ItemStack item = event.getItem();
		if (event.getPlayer() instanceof Player) {
			Player player = event.getPlayer();
			if (item.hasItemMeta()) {
				if (item.getItemMeta().hasLore()) {
					if (Main.CE.hasEnchantment(item, CEnchantments.HARDENED)) {
						if (CEnchantments.HARDENED.isEnabled()) {
							if (!event.isCancelled()) {
								if (burn.get(player) != null) {
									randomNumber = randomNumber + (3 * burn.get(player));
								}
								if (luck.get(player) != null) {
									randomNumber = randomNumber + (3 * luck.get(player));
								}
								if (melt.get(player) != null) {
									randomNumber = randomNumber + (3 * melt.get(player));
								}
								if ((randomNumber) >= 70) {
									event.setCancelled(true);
									return;
								}
							}
						}
					}
				}
			}
		}

	}

	@EventHandler
	private void deathbringerEvent(EntityDamageByEntityEvent event) {
		Entity entity = event.getDamager();
		Entity entity2 = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (entity2 instanceof Player) {
				for (ItemStack item : player.getInventory().getArmorContents()) {
					if (item.hasItemMeta()) {
						if (item.getItemMeta().hasLore()) {
							if (Main.CE.hasEnchantment(item, CEnchantments.DEATHBRINGER)) {
								if (CEnchantments.DEATHBRINGER.isEnabled()) {
									if (Methods.randomPicker((25 - (Main.CE.getPower(item, CEnchantments.DEATHBRINGER)
											+ luck.get(player))))) {
										event.setDamage((event.getDamage() * 2));
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
	private void spiritLinkEvent(EntityDamageByEntityEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			final Player damaged = (Player) entity;
			for (ItemStack item : damaged.getInventory().getArmorContents()) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().hasLore()) {
						if (Main.CE.hasEnchantment(item, CEnchantments.TANK)) {
							if (CEnchantments.TANK.isEnabled()) {
								if (Methods.randomPicker(20 - Main.CE.getPower(item, CEnchantments.TANK))) {
									final ItemStack helmet = damaged.getInventory().getHelmet();
									if (helmet != null) {
										if (helmet.hasItemMeta()) {
											if (helmet.getItemMeta().hasLore()) {
												if (Main.CE.hasEnchantment(helmet, CEnchantments.DRUNK)) {
													damaged.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
													damaged.addPotionEffect(
															new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20,
																	Main.CE.getPower(item, CEnchantments.TANK) - 1));
													damaged.addPotionEffect(new PotionEffect(
															PotionEffectType.DAMAGE_RESISTANCE, 10 * 20,
															Main.CE.getPower(item, CEnchantments.TANK) - 1));
													damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
															10 * 20, Main.CE.getPower(item, CEnchantments.TANK) - 1));
													task.put(damaged, Bukkit.getScheduler()
															.scheduleSyncDelayedTask(plugin, new Runnable() {
																@Override
																public void run() {
																	damaged.addPotionEffect(new PotionEffect(
																			PotionEffectType.INCREASE_DAMAGE, time,
																			Main.CE.getPower(helmet,
																					CEnchantments.DRUNK) - 2));
																}
															}, 10 * 20));
												}
												if (!Main.CE.hasEnchantment(helmet, CEnchantments.DRUNK)) {
													damaged.addPotionEffect(
															new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20,
																	Main.CE.getPower(item, CEnchantments.TANK) - 2));
													damaged.addPotionEffect(new PotionEffect(
															PotionEffectType.DAMAGE_RESISTANCE, 10 * 20,
															Main.CE.getPower(item, CEnchantments.TANK) - 1));
													damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
															10 * 20, Main.CE.getPower(item, CEnchantments.TANK) - 1));
												}
											}
										}
									}
									damaged.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
											10 * 20, Main.CE.getPower(item, CEnchantments.TANK) - 1));
									damaged.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20,
											Main.CE.getPower(item, CEnchantments.TANK) - 1));
									damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20,
											Main.CE.getPower(item, CEnchantments.TANK) - 1));
								}
							}
						}
						if (Main.CE.hasEnchantment(item, CEnchantments.SPIRITLINK)) {
							if (CEnchantments.SPIRITLINK.isEnabled()) {
								Location loc = damaged.getLocation();
								double damage = event.getDamage();
								double radius = 3;
								List<Entity> near = loc.getWorld().getEntities();
								for (Entity e1 : near) {
									if (e1.getLocation().distance(loc) <= radius) {
										if (e1 instanceof LivingEntity) {
											if (e1 instanceof Player) {
												if (Support.isFriendly(damaged, e1)) {
													Player pnear = (Player) e1;
													if ((pnear.getHealth() + damage / (7 - Main.CE.getPower(item,
															CEnchantments.SPIRITLINK))) <= (pnear.getMaxHealth())) {
														pnear.setHealth(pnear.getHealth() + (damage / 2));
													}
													if ((pnear.getHealth() + damage / (7 - Main.CE.getPower(item,
															CEnchantments.SPIRITLINK))) >= (pnear.getMaxHealth())) {
														pnear.setHealth(pnear.getMaxHealth());
													}
												} else {
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