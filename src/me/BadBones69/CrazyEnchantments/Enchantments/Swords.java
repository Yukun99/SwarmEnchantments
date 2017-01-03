package me.BadBones69.CrazyEnchantments.Enchantments;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.Events.DisarmerUseEvent;
import me.BadBones69.CrazyEnchantments.API.Events.EnchantmentUseEvent;
import me.BadBones69.CrazyEnchantments.multiSupport.Support;

public class Swords implements Listener {
	private static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");
	private int time = Integer.MAX_VALUE;

	private HashMap<Player, Double> multi = new HashMap<Player, Double>();
	private HashMap<Player, Integer> num = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> reset = new HashMap<Player, Integer>();
	private HashMap<Player, Calendar> timer = new HashMap<Player, Calendar>();
	private static HashMap<Player, ArrayList<LivingEntity>> mobs = new HashMap<Player, ArrayList<LivingEntity>>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if (Support.isFriendly(e.getDamager(), e.getEntity()))
			return;
		if (e.getEntity() instanceof LivingEntity) {
			if (e.getDamager() instanceof Player) {
				final Player damager = (Player) e.getDamager();
				LivingEntity en = (LivingEntity) e.getEntity();
				ItemStack It = Methods.getItemInHand(damager);
				if (!e.getEntity().isDead()) {
					if (Main.CE.hasEnchantments(It)) {
						if (Main.CE.hasEnchantment(It, CEnchantments.DISARMER)) {
							if (CEnchantments.DISARMER.isEnabled()) {
								if (e.getEntity() instanceof Player) {
									Player player = (Player) e.getEntity();
									int slot = Methods.percentPick(4, 1);
									if (Methods.randomPicker((20 - Main.CE.getPower(It, CEnchantments.DISARMER)))) {
										if (slot == 1) {
											if (player.getEquipment().getHelmet() != null) {
												ItemStack item = player.getEquipment().getHelmet();
												DisarmerUseEvent event = new DisarmerUseEvent(player, damager, item);
												Bukkit.getPluginManager().callEvent(event);
												if (!event.isCancelled()) {
													player.getEquipment().setHelmet(null);
													player.getInventory().addItem(item);
												}
											}
										}
										if (slot == 2) {
											if (player.getEquipment().getChestplate() != null) {
												ItemStack item = player.getEquipment().getChestplate();
												DisarmerUseEvent event = new DisarmerUseEvent(player, damager, item);
												Bukkit.getPluginManager().callEvent(event);
												if (!event.isCancelled()) {
													player.getEquipment().setChestplate(null);
													player.getInventory().addItem(item);
												}
											}
										}
										if (slot == 3) {
											if (player.getEquipment().getLeggings() != null) {
												ItemStack item = player.getEquipment().getLeggings();
												DisarmerUseEvent event = new DisarmerUseEvent(player, damager, item);
												Bukkit.getPluginManager().callEvent(event);
												if (!event.isCancelled()) {
													player.getEquipment().setLeggings(null);
													player.getInventory().addItem(item);
												}
											}
										}
										if (slot == 4) {
											if (player.getEquipment().getBoots() != null) {
												ItemStack item = player.getEquipment().getBoots();
												DisarmerUseEvent event = new DisarmerUseEvent(player, damager, item);
												Bukkit.getPluginManager().callEvent(event);
												if (!event.isCancelled()) {
													player.getEquipment().setBoots(null);
													player.getInventory().addItem(item);
												}
											}
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.RAGE)) {
							if (CEnchantments.RAGE.isEnabled()) {
								int Cap = 2;
								EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.RAGE, It);
								Bukkit.getPluginManager().callEvent(event);
								if (!event.isCancelled()) {
									if (multi.containsKey(damager)) {
										if (reset.get(damager) != null) {
											Bukkit.getScheduler().cancelTask(reset.get(damager));
										}
										if (multi.get(damager) <= Cap)
											multi.put(damager, multi.get(damager)
													+ (Main.CE.getPower(It, CEnchantments.RAGE) * 0.01));
										if (multi.get(damager).intValue() == num.get(damager)) {
											damager.sendMessage(Methods.color(
													"&3You are now doing &a" + num.get(damager) + "x &3Damage."));
											num.put(damager, num.get(damager) + 1);
										}
										e.setDamage(e.getDamage() * multi.get(damager));
									}
									if (!multi.containsKey(damager)) {
										multi.put(damager, 1.0);
										num.put(damager, 2);
										damager.sendMessage(Methods.color("&aYour Rage is Building."));
									}
									reset.put(damager,
											Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												@Override
												public void run() {
													multi.remove(damager);
													damager.sendMessage(Methods.color("&cYour Rage has Cooled Down."));
												}
											}, 4 * 20));
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.SKILLSWIPE)) {
							if (CEnchantments.SKILLSWIPE.isEnabled()) {
								if (en instanceof Player) {
									Player player = (Player) en;
									int amount = 4 + Main.CE.getPower(It, CEnchantments.SKILLSWIPE);
									if (player.getTotalExperience() > 1) {
										EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
												CEnchantments.SKILLSWIPE, It);
										Bukkit.getPluginManager().callEvent(event);
										if (!event.isCancelled()) {
											if (player.getTotalExperience() >= amount) {
												Methods.takeTotalXP(player, amount);
												Methods.takeTotalXP(damager, -amount);
												return;
											}
											if (player.getTotalExperience() < amount) {
												player.setTotalExperience(0);
												Methods.takeTotalXP(damager, -amount);
												return;
											}
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.LIFESTEAL)) {
							if (CEnchantments.LIFESTEAL.isEnabled()) {
								int steal = Main.CE.getPower(It, CEnchantments.LIFESTEAL);
								if (Methods.randomPicker(5)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.LIFESTEAL, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										if (damager.getHealth() + steal < damager.getMaxHealth()) {
											damager.setHealth(damager.getHealth() + steal);
										}
										if (damager.getHealth() + steal >= damager.getMaxHealth()) {
											damager.setHealth(damager.getMaxHealth());
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.NUTRITION)) {
							if (CEnchantments.NUTRITION.isEnabled()) {
								if (Methods.randomPicker(8)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.NUTRITION, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										if (damager.getSaturation()
												+ (2 * Main.CE.getPower(It, CEnchantments.NUTRITION)) <= 20) {
											damager.setSaturation(damager.getSaturation()
													+ (2 * Main.CE.getPower(It, CEnchantments.NUTRITION)));
											return;
										}
										if (damager.getSaturation()
												+ (2 * Main.CE.getPower(It, CEnchantments.NUTRITION)) >= 20) {
											damager.setSaturation(20);
											return;
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.VAMPIRE)) {
							if (CEnchantments.VAMPIRE.isEnabled()) {
								if (Methods.randomPicker(20 - Main.CE.getPower(It, CEnchantments.VAMPIRE))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.VAMPIRE,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										if (damager.getHealth() + e.getDamage() / 2 < damager.getMaxHealth()) {
											damager.setHealth(damager.getHealth() + e.getDamage() / 2);
										}
										if (damager.getHealth() + e.getDamage() / 2 >= damager.getMaxHealth()) {
											damager.setHealth(damager.getMaxHealth());
										}
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.BLINDNESS)) {
							if (CEnchantments.BLINDNESS.isEnabled()) {
								if (Methods.randomPicker(20 - Main.CE.getPower(It, CEnchantments.BLINDNESS))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.BLINDNESS, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20,
												Main.CE.getPower(It, CEnchantments.BLINDNESS) - 1));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.CONFUSION)) {
							if (CEnchantments.CONFUSION.isEnabled()) {
								if (Methods.randomPicker(7)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.CONFUSION, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
												5 + (Main.CE.getPower(It, CEnchantments.CONFUSION)) * 20, 0));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.DOUBLEDAMAGE)) {
							if (CEnchantments.DOUBLEDAMAGE.isEnabled()) {
								if (Methods.randomPicker((20 - Main.CE.getPower(It, CEnchantments.DOUBLEDAMAGE)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.DOUBLEDAMAGE, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										e.setDamage((e.getDamage() * 2));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.EXECUTE)) {
							if (CEnchantments.EXECUTE.isEnabled()) {
								if (en.getHealth() <= 2) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.EXECUTE,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,
												3 + (Main.CE.getPower(It, CEnchantments.EXECUTE)) * 20, 3));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.FASTTURN)) {
							if (CEnchantments.FASTTURN.isEnabled()) {
								if (Methods.randomPicker((20 - Main.CE.getPower(It, CEnchantments.FASTTURN)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.FASTTURN,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										e.setDamage(e.getDamage() + (e.getDamage() / 3));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.LIGHTWEIGHT)) {
							if (CEnchantments.LIGHTWEIGHT.isEnabled()) {
								if (Methods.randomPicker(8)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.LIGHTWEIGHT, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										damager.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 5 * 20,
												Main.CE.getPower(It, CEnchantments.LIGHTWEIGHT) - 1));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.OBLITERATE)) {
							if (CEnchantments.OBLITERATE.isEnabled()) {
								if (Methods.randomPicker(11 - Main.CE.getPower(It, CEnchantments.OBLITERATE))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager,
											CEnchantments.OBLITERATE, It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										e.getEntity().setVelocity(
												damager.getLocation().getDirection().multiply(2).setY(1.25));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.PARALYZE)) {
							if (CEnchantments.PARALYZE.isEnabled()) {
								if (Methods.randomPicker(13 - Main.CE.getPower(It, CEnchantments.PARALYZE))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.PARALYZE,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 2));
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 3 * 20, 2));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.SLOWMO)) {
							if (CEnchantments.SLOWMO.isEnabled()) {
								if (Methods.randomPicker(20 - Main.CE.getPower(It, CEnchantments.SLOWMO))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.SLOWMO,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20,
												Main.CE.getPower(It, CEnchantments.SLOWMO)));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.SNARE)) {
							if (CEnchantments.SNARE.isEnabled()) {
								if (Methods.randomPicker(11 - (Main.CE.getPower(It, CEnchantments.SNARE)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.SNARE,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 0));
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 3 * 20, 0));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.TRAP)) {
							if (CEnchantments.TRAP.isEnabled()) {
								if (Methods.randomPicker(11 - (Main.CE.getPower(It, CEnchantments.TRAP)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.TRAP,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3 * 20, 2));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.VIPER)) {
							if (CEnchantments.VIPER.isEnabled()) {
								if (Methods.randomPicker(10)) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.VIPER,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 5 * 20,
												Main.CE.getPower(It, CEnchantments.VIPER)));
									}
								}
							}
						}
						if (Main.CE.hasEnchantment(It, CEnchantments.WITHER)) {
							if (CEnchantments.WITHER.isEnabled()) {
								if (Methods.randomPicker(11 - (Main.CE.getPower(It, CEnchantments.WITHER)))) {
									EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.WITHER,
											It);
									Bukkit.getPluginManager().callEvent(event);
									if (!event.isCancelled()) {
										en.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 2 * 20, 2));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDamage(PlayerDeathEvent e) {
		if (Support.isFriendly(e.getEntity().getKiller(), e.getEntity()))
			return;
		if (!Support.allowsPVP(e.getEntity().getLocation()))
			return;
		if (e.getEntity().getKiller() instanceof Player) {
			Player damager = (Player) e.getEntity().getKiller();
			Player player = e.getEntity();
			ItemStack item = Methods.getItemInHand(damager);
			if (Main.CE.hasEnchantments(item)) {
				if (Main.CE.hasEnchantment(item, CEnchantments.HEADLESS)) {
					if (CEnchantments.HEADLESS.isEnabled()) {
						int power = Main.CE.getPower(item, CEnchantments.HEADLESS);
						if (Methods.randomPicker(11 - power)) {
							EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.HEADLESS, item);
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
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDamage(EntityDeathEvent e) {
		if (Support.isFriendly(e.getEntity().getKiller(), e.getEntity()))
			return;
		if (e.getEntity().getKiller() instanceof Player) {
			Player damager = (Player) e.getEntity().getKiller();
			ItemStack item = Methods.getItemInHand(damager);
			if (Main.CE.hasEnchantments(item)) {
				if (Main.CE.hasEnchantment(item, CEnchantments.INQUISITIVE)) {
					if (CEnchantments.INQUISITIVE.isEnabled()) {
						if (Methods.randomPicker(3)) {
							EnchantmentUseEvent event = new EnchantmentUseEvent(damager, CEnchantments.INQUISITIVE,
									item);
							Bukkit.getPluginManager().callEvent(event);
							if (!event.isCancelled()) {
								e.setDroppedExp(
										e.getDroppedExp() * (Main.CE.getPower(item, CEnchantments.INQUISITIVE) + 2));
							}
						}
					}
				}
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
			ItemStack item = player.getItemInHand();
			if (item != null) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().hasLore()) {
						if (Main.CE.hasEnchantment(item, CEnchantments.DEMONFORGED)) {
							if (CEnchantments.DEMONFORGED.isEnabled()) {
								if (item.getDurability() > 0) {
									if (item.getType().name().endsWith("SWORD") || item.getType().name().endsWith("AXE")
											|| item.getType().name().endsWith("BOW")) {
										if (Methods.randomPicker(12)) {
											int dur = item.getDurability()
													- Main.CE.getPower(item, CEnchantments.DEMONFORGED);
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
	public void onAllyTarget(EntityTargetEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			for (Player player : mobs.keySet()) {
				if (Methods.removeColor(e.getEntity().getName()).contains(player.getName())) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onAllySpawn(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
			Player player = (Player) e.getEntity();
			LivingEntity en = (LivingEntity) e.getDamager();
			if (!Support.isFriendly(player, en)) {
				if (Support.allowsPVP(player.getLocation()) && Support.allowsPVP(en.getLocation())) {
					if (!mobs.containsKey(player)) {
						for (ItemStack item : player.getEquipment().getArmorContents()) {
							if (Main.CE.hasEnchantments(item)) {
								if (!timer.containsKey(player) || (timer.containsKey(player)
										&& Calendar.getInstance().after(timer.get(player)))) {
									if (player.getHealth() <= 6) {
										if (Methods.randomPicker(10)) {
											if (Main.CE.hasEnchantment(item, CEnchantments.TAMER)) {
												if (!mobs.containsKey(player)) {
													int power = Main.CE.getPower(item, CEnchantments.TAMER);
													spawnAllies(player, en, EntityType.WOLF, power);
												}
											}
											if (en instanceof Player) {
												if (Main.CE.hasEnchantment(item, CEnchantments.INFESTATION)) {
													if (!mobs.containsKey(player)) {
														int power = Main.CE.getPower(item, CEnchantments.INFESTATION);
														spawnAllies(player, en, EntityType.ENDERMITE, power * 3);
														spawnAllies(player, en, EntityType.SILVERFISH, power * 3);
													}
												}
											} else {
												continue;
											}
										}
									}
								}
							}
						}
					} else {
						attackEnemy(player, en);
					}
				}
			}
		}
		if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			LivingEntity en = (LivingEntity) e.getEntity();
			if (mobs.containsKey(player)) {
				if (mobs.get(player).contains(en)) {
					e.setCancelled(true);
					return;
				}
			}
			if (!Support.isFriendly(player, en)) {
				if (Support.allowsPVP(player.getLocation()) && Support.allowsPVP(en.getLocation())) {
					if (!mobs.containsKey(player)) {
						for (ItemStack item : player.getEquipment().getArmorContents()) {
							if (Main.CE.hasEnchantments(item)) {
								if (!timer.containsKey(player) || (timer.containsKey(player)
										&& Calendar.getInstance().after(timer.get(player)))) {
									if (Methods.randomPicker(10)) {
										if (Main.CE.hasEnchantment(item, CEnchantments.TAMER)) {
											if (!mobs.containsKey(player)) {
												int power = Main.CE.getPower(item, CEnchantments.TAMER);
												spawnAllies(player, en, EntityType.WOLF, power);
											}
										}
										if (en instanceof Player) {
											if (Main.CE.hasEnchantment(item, CEnchantments.INFESTATION)) {
												if (!mobs.containsKey(player)) {
													int power = Main.CE.getPower(item, CEnchantments.INFESTATION);
													spawnAllies(player, en, EntityType.ENDERMITE, power * 3);
													spawnAllies(player, en, EntityType.SILVERFISH, power * 3);
												}
											}
										} else {
											continue;
										}
									}
								}
							}
						}
					} else {
						attackEnemy(player, en);
					}
				}
			}
		}
	}

	@EventHandler
	public void onAllyDespawn(ChunkUnloadEvent e) {
		if (e.getChunk().getEntities().length > 0) {
			for (Entity en : e.getChunk().getEntities()) {
				if (en instanceof LivingEntity) {
					LivingEntity En = (LivingEntity) en;
					for (Player player : mobs.keySet()) {
						if (mobs.get(player).contains(En)) {
							mobs.get(player).remove(En);
							En.remove();
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (mobs.containsKey(player)) {
			for (LivingEntity en : mobs.get(player)) {
				en.remove();
			}
			mobs.remove(player);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		for (Player player : mobs.keySet()) {
			if (player == e.getEntity()) {
				removeAlly(player);
			}
		}
	}

	@EventHandler
	public void onAllyDeath(EntityDeathEvent e) {
		for (Player player : mobs.keySet()) {
			if (mobs.get(player).contains(e.getEntity())) {
				e.setDroppedExp(0);
				e.getDrops().clear();
			}
		}
	}

	private void spawnAllies(final Player player, LivingEntity enemy, EntityType mob, Integer amount) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 5);
		timer.put(player, cal);
		for (int i = 0; i < amount; i++) {
			LivingEntity en = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), mob);
			switch (mob) {
			case WOLF:
				en.setMaxHealth(16);
				en.setHealth(16);
				en.setCanPickupItems(false);
				en.setCustomName(Methods.color("&f&l" + player.getName() + "'s " + "Bitch"));
				en.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
				en.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 1));
				en.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 1));
				en.setCustomNameVisible(true);
				Support.noStack(en);
				break;
			case ENDERMITE:
				en.setMaxHealth(10);
				en.setHealth(10);
				en.setCanPickupItems(false);
				en.setCustomName(Methods.color("&6&l" + player.getName() + "'s " + "Dark Ant"));
				en.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
				en.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 3));
				en.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 3));
				en.setCustomNameVisible(true);
				Support.noStack(en);
				break;
			case SILVERFISH:
				en.setMaxHealth(10);
				en.setHealth(10);
				en.setCanPickupItems(false);
				en.setCustomName(Methods.color("&6&l" + player.getName() + "'s " + "Ant"));
				en.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, 1));
				en.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, 3));
				en.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, 3));
				en.setCustomNameVisible(true);
				Support.noStack(en);
				break;
			default:
				break;
			}
			if (!mobs.containsKey(player)) {
				ArrayList<LivingEntity> E = new ArrayList<LivingEntity>();
				E.add(en);
				mobs.put(player, E);
			} else {
				mobs.get(player).add(en);
			}
		}
		attackEnemy(player, enemy);
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (mobs.get(player) != null) {
					if (mobs.containsKey(player)) {
						for (LivingEntity en : mobs.get(player)) {
							en.remove();
						}
						mobs.remove(player);
					}
				}
			}
		}, 1 * 60 * 20);
	}

	public static HashMap<Player, ArrayList<LivingEntity>> getAllies() {
		return mobs;
	}

	private void attackEnemy(Player player, LivingEntity enemy) {
		for (LivingEntity ally : mobs.get(player)) {
			switch (ally.getType()) {
			case WOLF:
				Wolf wolf = (Wolf) ally;
				wolf.setTarget(enemy);
				break;
			case ENDERMITE:
				Endermite mite = (Endermite) ally;
				mite.setTarget(enemy);
				break;
			case SILVERFISH:
				Silverfish sfish = (Silverfish) ally;
				sfish.setTarget(enemy);
				break;
			default:
				break;
			}
		}
	}

	public static void removeAllies() {
		for (Player player : mobs.keySet()) {
			for (LivingEntity ally : mobs.get(player)) {
				ally.remove();
			}
		}
	}

	public static void removeAlly(Player player) {
		for (LivingEntity ally : mobs.get(player)) {
			ally.remove();
		}
	}
}