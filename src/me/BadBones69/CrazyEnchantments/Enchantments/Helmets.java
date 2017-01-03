package me.BadBones69.CrazyEnchantments.Enchantments;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.Events.ArmorEquipEvent;
import me.BadBones69.CrazyEnchantments.API.Events.EnchantmentUseEvent;
import me.BadBones69.CrazyEnchantments.multiSupport.Support;

public class Helmets implements Listener {
	private int time = Integer.MAX_VALUE;
	private HashMap<Player, Boolean> cooldown = Main.getCooldown1();
	static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");

	@SuppressWarnings("static-access")
	public Helmets(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEquip(ArmorEquipEvent e) {
		Player player = e.getPlayer();
		ItemStack NewItem = e.getNewArmorPiece();
		ItemStack OldItem = e.getOldArmorPiece();
		if (Main.CE.hasEnchantments(NewItem)) {
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.DRUNK)) {
				if (CEnchantments.DRUNK.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.DRUNK, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						int power = Main.CE.getPower(NewItem, CEnchantments.DRUNK);
						if (power == 1) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, power - 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, power - 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, power - 2));
						}
						if (power == 2) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, power - 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, power - 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, power - 3));
						}
						if (power == 3) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, power - 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, power - 1));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, power - 2));
						}
						if (power == 4) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, time, power - 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, power - 2));
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, power - 3));
						}
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.GLOWING)) {
				if (CEnchantments.GLOWING.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.GLOWING, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, time,
								Main.CE.getPower(NewItem, CEnchantments.GLOWING) - 1));
					}
				}
			}
			if (Main.CE.hasEnchantment(NewItem, CEnchantments.MERMAID)) {
				if (CEnchantments.MERMAID.isEnabled()) {
					EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.MERMAID, NewItem);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, time,
								Main.CE.getPower(NewItem, CEnchantments.MERMAID) - 1));
					}
				}
			}
		}
		if (Main.CE.hasEnchantments(OldItem)) {
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.GLOWING)) {
				if (CEnchantments.GLOWING.isEnabled()) {
					player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.DRUNK)) {
				if (CEnchantments.DRUNK.isEnabled()) {
					player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
					player.removePotionEffect(PotionEffectType.SLOW);
				}
			}
			if (Main.CE.hasEnchantment(OldItem, CEnchantments.MERMAID)) {
				if (CEnchantments.MERMAID.isEnabled()) {
					player.removePotionEffect(PotionEffectType.WATER_BREATHING);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onMovment(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		for (ItemStack armor : player.getEquipment().getArmorContents()) {
			if (Main.CE.hasEnchantments(armor)) {
				if (Main.CE.hasEnchantment(armor, CEnchantments.COMMANDER)) {
					if (CEnchantments.COMMANDER.isEnabled()) {
						if (Methods.hasFactions()) {
							int radius = 4 + Main.CE.getPower(armor, CEnchantments.COMMANDER);
							ArrayList<Player> players = new ArrayList<Player>();
							for (Entity en : player.getNearbyEntities(radius, radius, radius)) {
								if (en instanceof Player) {
									Player o = (Player) en;
									if (Support.isFriendly(player, o)) {
										players.add(o);
									}
								}
							}
							if (players.size() > 0) {
								EnchantmentUseEvent event = new EnchantmentUseEvent(player, CEnchantments.COMMANDER,
										armor);
								Bukkit.getPluginManager().callEvent(event);
								if (!event.isCancelled()) {
									for (Player P : players) {
										P.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 3 * 20, 1));
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
	public void enderShiftEvent(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player) {
			final Player damaged = (Player) entity;
			if (damaged.getHealth() <= 4) {
				if (cooldown.get(damaged) != null && cooldown.get(damaged) == false) {
					ItemStack helmet = damaged.getInventory().getHelmet();
					if (helmet != null) {
						if (helmet.hasItemMeta()) {
							if (helmet.getItemMeta().hasLore()) {
								if (Main.CE.hasEnchantment(helmet, CEnchantments.ENDERSHIFT)) {
									final ItemStack boots = damaged.getInventory().getBoots();
									if (boots != null) {
										if (boots.hasItemMeta()) {
											if (boots.getItemMeta().hasLore()) {
												if (Main.CE.hasEnchantment(boots, CEnchantments.GEARS)) {
													damaged.setHealth(damaged.getHealth() + 4);
													damaged.removePotionEffect(PotionEffectType.SPEED);
													damaged.addPotionEffect(
															new PotionEffect(PotionEffectType.SPEED, 10 * 20,
																	Main.CE.getPower(helmet, CEnchantments.ENDERSHIFT) + 2));
													cooldown.put(damaged, true);
													Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
															new Runnable() {
																@Override
																public void run() {
																	damaged.removePotionEffect(PotionEffectType.SPEED);
																	damaged.addPotionEffect(new PotionEffect(
																			PotionEffectType.SPEED, time,
																			Main.CE.getPower(boots, CEnchantments.GEARS)
																					- 1));
																}
															}, 10 * 20);
													Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
															new Runnable() {
																@Override
																public void run() {
																	cooldown.put(damaged, false);
																}
															}, 120 * 20);
												}
												if (!Main.CE.hasEnchantment(boots, CEnchantments.GEARS)) {
													damaged.setHealth(damaged.getHealth() + 4);
													damaged.addPotionEffect(
															new PotionEffect(PotionEffectType.SPEED, 10 * 20,
																	Main.CE.getPower(helmet, CEnchantments.ENDERSHIFT) + 2));
													cooldown.put(damaged, true);
													Bukkit.getScheduler().scheduleSyncDelayedTask(plugin,
															new Runnable() {
																@Override
																public void run() {
																	cooldown.put(damaged, false);
																}
															}, 120 * 20);
												}
											}
											damaged.setHealth(damaged.getHealth() + 4);
											damaged.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20,
													Main.CE.getPower(helmet, CEnchantments.ENDERSHIFT) + 2));
											cooldown.put(damaged, true);
											Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
												@Override
												public void run() {
													cooldown.put(damaged, false);
												}
											}, 120 * 20);
										}
										damaged.setHealth(damaged.getHealth() + 4);
										damaged.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20,
												Main.CE.getPower(helmet, CEnchantments.ENDERSHIFT) + 2));
										cooldown.put(damaged, true);
										Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											@Override
											public void run() {
												cooldown.put(damaged, false);
											}
										}, 120 * 20);
									}
									damaged.setHealth(damaged.getHealth() + 4);
									damaged.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20,
											Main.CE.getPower(helmet, CEnchantments.ENDERSHIFT) + 2));
									cooldown.put(damaged, true);
									Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										@Override
										public void run() {
											cooldown.put(damaged, false);
										}
									}, 120 * 20);
								}
							}
						}
					}
				}
			}
		}
	}
}