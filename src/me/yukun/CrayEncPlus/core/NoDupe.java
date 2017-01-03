package me.yukun.CrayEncPlus.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import me.BadBones69.CrazyEnchantments.Methods;

public class NoDupe implements Listener {
	static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().contains("/pv")) {
			final Player player = e.getPlayer();
			final String command = e.getMessage().replace("/", "");
			Double locx = player.getLocation().getX();
			Double locy = player.getLocation().getY();
			Double locz = player.getLocation().getZ();
			Location loc = new Location(player.getWorld(), 0, 0, 0);
			loc.setX(locx);
			loc.setY(locy + 0.1);
			loc.setZ(locz);
			for (int i = 1; i <= 30; ++i) {
				if (e.getMessage().contains(i + "")) {
					player.teleport(loc);
					e.setCancelled(true);
					player.sendMessage(Methods.color("&6Swarm&eAntiDupe &f>> &7Please wait while we process your command!"));
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
							new Runnable() {
								public void run() {
									player.performCommand(command);
								}
							}, 1 * 5);
				}
			}
		}
	}
}
