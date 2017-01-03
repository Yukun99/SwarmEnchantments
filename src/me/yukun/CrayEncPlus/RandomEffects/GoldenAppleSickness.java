package me.yukun.CrayEncPlus.RandomEffects;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;
import me.BadBones69.CrazyEnchantments.API.CEnchantments;
import me.BadBones69.CrazyEnchantments.API.CrazyEnchantments;

public class GoldenAppleSickness implements Listener {
	static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");
	CrazyEnchantments CE = CrazyEnchantments.getInstance();
	ArrayList<Player> Active = new ArrayList<Player>();

	@SuppressWarnings("static-access")
	public GoldenAppleSickness(Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	private void playerConsumeEvent1(PlayerItemConsumeEvent event) {
		final Player player = event.getPlayer();
		if (player.getInventory().getItemInHand().getType().name().endsWith("GOLDEN_APPLE")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					player.removePotionEffect(PotionEffectType.REGENERATION);
					player.removePotionEffect(PotionEffectType.ABSORPTION);
					player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2));
					player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 3));
				}
			}, 1);
		}
	}

	@EventHandler
	private void playerConsumeEvent(PlayerItemConsumeEvent event) {
		if (Main.settings.getConfig().getString("Settings.GoldenAppleSickness.Enabled").equalsIgnoreCase("false")) {
			return;
		}
		Player player = event.getPlayer();
		if (Main.settings.getConfig().getString("Settings.GoldenAppleSickness.Enabled").equalsIgnoreCase("true")) {
			if (player.getInventory().getItemInHand().getType().name().endsWith("GOLDEN_APPLE")) {
				ItemStack item = player.getInventory().getHelmet();
				if (item != null) {
					if (item.hasItemMeta()) {
						if (item.getItemMeta().hasLore()) {
							if (Methods.randomPicker(5)) {
								if (CE.hasEnchantment(item, CEnchantments.INSOMNIA)) {
									if (CEnchantments.INSOMNIA.isEnabled()) {
										event.getPlayer().sendMessage(
												Methods.color(Main.settings.getMsg().getString("Messages.WarningMessage")));
										Active.add(player);
										Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
											@Override
											public void run() {
												for (Player aplayer : Active) {
													aplayer.getPlayer().sendMessage(Methods.color(Main.settings.getMsg()
															.getString("Messages.SicknessMessage")));
													aplayer.removePotionEffect(PotionEffectType.POISON);
													aplayer.addPotionEffect(
															new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
													aplayer.addPotionEffect(
															new PotionEffect(PotionEffectType.POISON, 80, 1));
													Active.remove(aplayer);
													return;
												}
											}
										}, 5 * 20);
									}
								}
								if (!CE.hasEnchantment(item, CEnchantments.INSOMNIA)) {
									event.getPlayer().sendMessage(
											Methods.color(Main.settings.getMsg().getString("Messages.WarningMessage")));
									Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										@Override
										public void run() {
											for (Player aplayer : Active) {
												aplayer.getPlayer().sendMessage(Methods.color(
														Main.settings.getMsg().getString("Messages.SicknessMessage")));
												aplayer.removePotionEffect(PotionEffectType.POISON);
												aplayer.addPotionEffect(
														new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
												aplayer.addPotionEffect(
														new PotionEffect(PotionEffectType.POISON, 80, 1));
												Active.remove(aplayer);
												return;
											}
										}
									}, 5 * 20);
								}
							}
						}
					}
				}
			} else {
				return;
			}
		}
	}

}
