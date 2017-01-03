package me.yukun.CrayEncPlus.RandomEffects;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.BadBones69.CrazyEnchantments.Methods;

public class DamageParticles implements Listener {
	@EventHandler
	private void entityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.isCancelled()
				|| !Methods.getConfigString("Settings.BloodDamageParticles").equalsIgnoreCase("on")
				|| event.getEntityType() == EntityType.PRIMED_TNT || event.getEntityType() == EntityType.DROPPED_ITEM
				|| event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
				|| !(event.getDamager() instanceof Player)) {
			return;
		}
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (event.getEntity() instanceof Player) {
			Player damager = (Player) event.getDamager();
			damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1.0f, 2.0f);
			Player player = (Player) event.getEntity();
			player.getWorld()
					.playEffect(new Location(player.getWorld(), player.getLocation().getX(),
							player.getLocation().getY(), player.getLocation().getZ()), Effect.STEP_SOUND,
							Material.REDSTONE_BLOCK);
			player.getWorld()
					.playEffect(
							new Location(player.getWorld(), player.getLocation().getX(),
									player.getLocation().getY() + 1.0, player.getLocation().getZ()),
							Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
		}
	}
}