package me.yukun.CrazyEncPlus.Soul;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.BadBones69.CrazyEnchantments.Methods;

public class SoulAdd implements Listener {
	HashMap<Player, Player> nokill = new HashMap<Player, Player>();
	HashMap<Player, Integer> delay = new HashMap<Player, Integer>();
	HashMap<Player, Integer> trackerSlot = new HashMap<Player, Integer>();
	HashMap<Player, Integer> currentSouls = new HashMap<Player, Integer>();
	HashMap<Player, String> Colour = new HashMap<Player, String>();
	ArrayList<String> colours = new ArrayList<String>();

	@EventHandler
	private void playerDeathEvent(final PlayerDeathEvent e) {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");
		colours.add("&f&l");
		colours.add("&a&l");
		colours.add("&b&l");
		colours.add("&e&l");
		colours.add("&6&l");
		if (e.getEntity().getKiller() instanceof Player) {
			Player killer = (Player) e.getEntity().getKiller();
			final Player killed = (Player) e.getEntity();
			if (Methods.getItemInHand(killer) != null) {
				ItemStack weapon = Methods.getItemInHand(killer);
				if (weapon.hasItemMeta()) {
					ItemMeta weaponMeta = weapon.getItemMeta();
					if (weaponMeta.hasLore()) {
						for (String line : weaponMeta.getLore()) {
							if (Methods.removeColor(line).contains("Souls Harvested:")) {
								if (nokill.get(killed) != null && nokill.get(killed) == killer) {
									killer.sendMessage(
											(Object) ChatColor.RED + "You cannot kill " + killed.getDisplayName()
													+ ChatColor.RED + " for souls until 5 minutes have passed!");
								} else {
									currentSouls.put(killer, Methods.getArgument("%souls%", line,
											Methods.getSoulTracker("SoulTrackers.Simple.ApplyLore")));
									trackerSlot.put(killer, Methods.getItemSlot(killer, weapon));
									nokill.put(killed, killer);
									Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										public void run() {
											nokill.remove(killed);
										}
									}, 300 * 20);
									for (String colour : colours) {
										if (line.equalsIgnoreCase(
												Methods.color(colour + "Souls Harvested: " + currentSouls.get(killer)))) {
											Colour.put(killer, colour);
											ArrayList<String> newlore = (ArrayList<String>) weaponMeta.getLore();
											newlore.remove(0);
											newlore.add(0, Methods.color(Colour.get(killer) + "Souls Harvested: "
													+ (currentSouls.get(killer) + 1)));
											weaponMeta.setLore(newlore);
											weapon.setItemMeta(weaponMeta);
											Methods.setItemInHand(killer, weapon);
											trackerSlot.remove(killer);
											currentSouls.remove(killer);
											delay.remove(killer);
											Colour.remove(killer);
										} else {
											continue;
										}
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
	}

}