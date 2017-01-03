package me.BadBones69.CrazyEnchantments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.BadBones69.CrazyEnchantments.API.FuckFireworks;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_10_R1;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_7_R4;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_8_R1;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_8_R2;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_8_R3;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_9_R1;
import me.BadBones69.CrazyEnchantments.multiSupport.nms.NMS_v1_9_R2;

public class Methods {
	private static Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CrazyEnchantments");

	public static String getConfigString(String path) {
		String msg = Main.settings.getConfig().getString(path);
		return msg;
	}

	public static String getMessageString(String path) {
		String msg = Main.settings.getMsg().getString(path);
		return msg;
	}

	public static String getSoulTracker(String path) {
		String msg = Main.settings.getSoulTracker().getString(path);
		return msg;
	}

	public static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static String removePower(String msg) {
		msg = msg.replace(" IV", "");
		msg = msg.replace(" IX", "");
		msg = msg.replace(" III", "");
		msg = msg.replace(" II", "");
		msg = msg.replace(" I", "");
		msg = msg.replace(" VIII", "");
		msg = msg.replace(" VII", "");
		msg = msg.replace(" VI", "");
		msg = msg.replace(" V", "");
		msg = msg.replace(" X", "");
		return msg;
	}

	public static boolean hasASkyBlock() {
		if (Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock") != null) {
			return true;
		}
		return false;
	}

	public static Integer transint(ItemStack item) {
		for (int i = 1; i <= 20; ++i) {
			if (Methods.removeColor(item.getItemMeta().getDisplayName()).contains("[" + i + "]")) {
				return i;
			} else {
				continue;
			}
		}
		return null;
	}

	public static void sendMultiMessage(Player player, List<String> messages) {
		for (String msg : messages) {
			player.sendMessage(color(msg));
		}
	}

	public static boolean transmogged(ItemStack item) {
		for (int i = 1; i <= 20; ++i) {
			if (Methods.removeColor(item.getItemMeta().getDisplayName()).contains("[" + i + "]")) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	public static boolean containsItem(Player player, ItemStack item) {
		for (ItemStack items : player.getInventory().getContents()) {
			if (items != null) {
				if (item != null) {
					if (items.isSimilar(item)) {
						return true;
					} else {
						continue;
					}
				}
			}
		}
		return false;
	}

	public static Integer getItemSlot(Player player, ItemStack item) {
		if (Methods.containsItem(player, item)) {
			for (int i = 0; i < player.getInventory().getSize(); ++i) {
				ItemStack items = player.getInventory().getItem(i);
				if (items.isSimilar(item)) {
					return i;
				}
			}
		}
		return null;
	}

	public static ItemStack getItem(Player player, ItemStack item) {
		if (Methods.containsItem(player, item)) {
			for (ItemStack items : player.getInventory().getContents()) {
				if (items.isSimilar(item) && (items.getAmount() + item.getAmount()) <= 64) {
					return items;
				} else {
					continue;
				}
			}
		}
		return null;
	}

	public static String removeColor(String msg) {
		msg = ChatColor.stripColor(msg);
		return msg;
	}

	public static Integer getVersion() {
		String ver = Bukkit.getServer().getClass().getPackage().getName();
		ver = ver.substring(ver.lastIndexOf('.') + 1);
		ver = ver.replaceAll("_", "").replaceAll("R", "").replaceAll("v", "");
		return Integer.parseInt(ver);
	}

	public static Integer getArgument(String Argument, ItemStack item, String Msg) {
		String arg = "0";
		Msg = Methods.color(Msg).toLowerCase();
		String name = item.getItemMeta().getDisplayName().toLowerCase();
		if (Msg.contains(Argument.toLowerCase())) {
			String[] b = Msg.split(Argument.toLowerCase());
			if (b.length >= 1)
				arg = name.replace(b[0], "");
			if (b.length >= 2)
				arg = arg.replace(b[1], "");
		}
		if (Methods.isInt(arg)) {
			return Integer.parseInt(arg);
		}
		return null;
	}

	public static Integer getArgument(String Argument, String input, String Msg) {
		String arg = "0";
		Msg = Methods.removeColor(Methods.color(Msg)).toLowerCase();
		String name = Methods.removeColor(input).toLowerCase();
		if (Msg.contains(Argument.toLowerCase())) {
			String[] b = Msg.split(Argument.toLowerCase());
			if (b.length >= 1)
				arg = name.replace(b[0], "");
			if (b.length >= 2)
				arg = arg.replace(b[1], "");
		}
		if (Methods.isInt(arg)) {
			return Integer.parseInt(arg);
		}
		return null;
	}

	public static String getString(String Argument, ItemStack item, String Msg) {
		String arg = "0";
		Msg = Methods.color(Msg).toLowerCase();
		String name = item.getItemMeta().getDisplayName().toLowerCase();
		if (Msg.contains(Argument.toLowerCase())) {
			String[] b = Msg.split(Argument.toLowerCase());
			if (b.length >= 1)
				arg = name.replace(b[0], "");
			if (b.length >= 2)
				arg = arg.replace(b[1], "");
		}
		return arg;
	}

	public static ItemStack addGlow(ItemStack item) {
		if (getVersion() == 1101) {
			return NMS_v1_10_R1.addGlow(item);
		}
		if (getVersion() == 192) {
			return NMS_v1_9_R2.addGlow(item);
		}
		if (getVersion() == 191) {
			return NMS_v1_9_R1.addGlow(item);
		}
		if (getVersion() == 183) {
			return NMS_v1_8_R3.addGlow(item);
		}
		if (getVersion() == 182) {
			return NMS_v1_8_R2.addGlow(item);
		}
		if (getVersion() == 181) {
			return NMS_v1_8_R1.addGlow(item);
		}
		if (getVersion() == 174) {
			return NMS_v1_7_R4.addGlow(item);
		} else {
			Bukkit.getLogger().log(Level.SEVERE, "[Crazy Enchantments]>> Your server is too far out of date. "
					+ "Please update or remove this plugin to stop further Errors.");
			return item;
		}
	}

	public static ItemStack addGlow(ItemStack item, boolean toggle) {
		if (toggle) {
			if (getVersion() == 1101) {
				return NMS_v1_10_R1.addGlow(item);
			}
			if (getVersion() == 192) {
				return NMS_v1_9_R2.addGlow(item);
			}
			if (getVersion() == 191) {
				return NMS_v1_9_R1.addGlow(item);
			}
			if (getVersion() == 183) {
				return NMS_v1_8_R3.addGlow(item);
			}
			if (getVersion() == 182) {
				return NMS_v1_8_R2.addGlow(item);
			}
			if (getVersion() == 181) {
				return NMS_v1_8_R1.addGlow(item);
			}
			if (getVersion() == 174) {
				return NMS_v1_7_R4.addGlow(item);
			} else {
				Bukkit.getLogger().log(Level.SEVERE, "[Crazy Enchantments]>> Your server is too far out of date. "
						+ "Please update or remove this plugin to stop further Errors.");
				return item;
			}
		} else {
			return item;
		}
	}

	public static ItemStack getItemInHand(Player player) {
		if (getVersion() >= 191) {
			return player.getInventory().getItemInHand();
		} else {
			return player.getItemInHand();
		}
	}

	public static void setItemInHand(Player player, ItemStack item) {
		if (Methods.getVersion() >= 191) {
			player.getInventory().setItemInHand(item);
		} else {
			player.setItemInHand(item);
		}
	}

	public static String getPower(Integer i) {
		if (i <= 0)
			return "I";
		if (i == 1)
			return "I";
		if (i == 2)
			return "II";
		if (i == 3)
			return "III";
		if (i == 4)
			return "IV";
		if (i == 5)
			return "V";
		if (i == 6)
			return "VI";
		if (i == 7)
			return "VII";
		if (i == 8)
			return "VIII";
		if (i == 9)
			return "IX";
		if (i == 10)
			return "X";
		return i + "";
	}

	public static ArrayList<String> getPotions() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("ABSORPTION");
		list.add("BLINDNESS");
		list.add("CONFUSION");
		list.add("DAMAGE_RESISTANCE");
		list.add("FAST_DIGGING");
		list.add("FIRE_RESISTANCE");
		list.add("GLOWING");
		list.add("HARM");
		list.add("HEAL");
		list.add("HEALTH_BOOST");
		list.add("HUNGER");
		list.add("INCREASE_DAMAGE");
		list.add("INVISIBILITY");
		list.add("JUMP");
		list.add("LEVITATION");
		list.add("LUCK");
		list.add("NIGHT_VISION");
		list.add("POISON");
		list.add("REGENERATION");
		list.add("SATURATION");
		list.add("SLOW");
		list.add("SLOW_DIGGING");
		list.add("SPEED");
		list.add("UNLUCK");
		list.add("WATER_BREATHING");
		list.add("WEAKNESS");
		list.add("WITHER");
		return list;
	}

	public static boolean hasFactions() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Factions") != null) {
			return true;
		}
		return false;
	}

	public static boolean hasFeudal() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Feudal") != null) {
			return true;
		}
		return false;
	}

	public static ItemStack removeLore(ItemStack item, String i) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta m = item.getItemMeta();
		for (String l : item.getItemMeta().getLore()) {
			if (!l.equals(i)) {
				lore.add(l);
			}
		}
		m.setLore(lore);
		item.setItemMeta(m);
		return item;
	}

	public static ItemStack replaceLore(ItemStack item, String oldlore, String newlore) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta m = item.getItemMeta();
		for (String l : item.getItemMeta().getLore()) {
			if (l.equals(oldlore)) {
				lore.add(color(newlore));
			} else {
				lore.add(l);
			}
		}
		m.setLore(lore);
		item.setItemMeta(m);
		return item;
	}

	public static ItemStack makeItem(Material material, int amount, int type, String name) {
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(color(name));
		item.setItemMeta(m);
		return item;
	}

	public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore) {
		ArrayList<String> l = new ArrayList<String>();
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(color(name));
		for (String L : lore)
			l.add(color(L));
		m.setLore(l);
		item.setItemMeta(m);
		return item;
	}

	public static ItemStack makeItem(String type, int amount) {
		int ty = 0;
		if (type.contains(":")) {
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		return item;
	}

	public static ItemStack makeItem(String type, int amount, String name) {
		int ty = 0;
		if (type.contains(":")) {
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		ItemMeta me = item.getItemMeta();
		item.setItemMeta(me);
		return item;
	}

	public static ItemStack makeItem(String type, int amount, String name, List<String> lore) {
		ArrayList<String> l = new ArrayList<String>();
		int ty = 0;
		if (type.contains(":")) {
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		ItemMeta me = item.getItemMeta();
		me.setDisplayName(color(name));
		for (String L : lore)
			l.add(color(L));
		me.setLore(l);
		item.setItemMeta(me);
		return item;
	}

	public static ItemStack makeItem(String type, int amount, String name, List<String> lore,
			Map<Enchantment, Integer> enchants) {
		ArrayList<String> l = new ArrayList<String>();
		int ty = 0;
		if (type.contains(":")) {
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		ItemMeta me = item.getItemMeta();
		me.setDisplayName(color(name));
		for (String L : lore)
			l.add(color(L));
		me.setLore(l);
		item.setItemMeta(me);
		item.addUnsafeEnchantments(enchants);
		return item;
	}

	public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore,
			List<String> lore2) {
		ArrayList<String> l = new ArrayList<String>();
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(color(name));
		for (String L : lore)
			l.add(color(L));
		for (String L : lore2)
			l.add(color(L));
		m.setLore(l);
		item.setItemMeta(m);
		return item;
	}

	public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore,
			Map<Enchantment, Integer> enchants) {
		ItemStack item = new ItemStack(material, amount, (short) type);
		ItemMeta m = item.getItemMeta();
		m.setDisplayName(name);
		m.setLore(lore);
		item.setItemMeta(m);
		item.addUnsafeEnchantments(enchants);
		return item;
	}

	public static String percentPicker() {
		Random i = new Random();
		return Integer.toString(i.nextInt(100));
	}

	public static String getPrefix() {
		return color(Main.settings.getConfig().getString("Settings.Prefix"));
	}

	public static double getMoney(Player player) {
		try {
			return Main.econ.getBalance(player);
		} catch (Exception e) {
			Bukkit.getLogger().log(Level.WARNING,
					"[Crazy Enchantments]>> Couldn't find the money for " + player.getName() + ".");
			return 0D;
		}
	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static Player getPlayer(String name) {
		return Bukkit.getServer().getPlayer(name);
	}

	public static Location getLoc(Player player) {
		return player.getLocation();
	}

	public static void runCMD(Player player, String CMD) {
		player.performCommand(CMD);
	}

	public static boolean isOnline(String name, CommandSender p) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		p.sendMessage(getPrefix() + color(Main.settings.getMsg().getString("Messages.Not-Online")));
		return false;
	}

	public static boolean hasPermission(Player player, String perm, Boolean toggle) {
		if (!player.hasPermission("CrazyEnchantments." + perm)) {
			if (toggle) {
				player.sendMessage(getPrefix() + color(Main.settings.getMsg().getString("Messages.No-Perm")));
			}
			return false;
		}
		return true;
	}

	public static boolean hasPermission(CommandSender sender, String perm, Boolean toggle) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("CrazyEnchantments." + perm)) {
				if (toggle) {
					player.sendMessage(getPrefix() + color(Main.settings.getMsg().getString("Messages.No-Perm")));
				}
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public static void removeItem(ItemStack item, Player player) {
		if (item.getAmount() <= 1) {
			player.getInventory().removeItem(item);
		}
		if (item.getAmount() > 1) {
			ItemStack i = item;
			i.setAmount(item.getAmount() - 1);
		}
	}

	public static ItemStack removeItem(ItemStack item) {
		if (item.getAmount() <= 1) {
			return new ItemStack(Material.AIR);
		} else {
			ItemStack i = item;
			i.setAmount(item.getAmount() - 1);
			return i;
		}
	}

	public static String getInvName() {
		return color(Main.settings.getConfig().getString("Settings.InvName"));
	}

	public static int getXPLvl(Player player) {
		return player.getLevel();
	}

	public static void takeLvlXP(Player player, int amount) {
		player.setLevel(player.getLevel() - amount);
	}

	public static int getTotalExperience(Player player) {// https://www.spigotmc.org/threads/72804
		int experience = 0;
		int level = player.getLevel();
		if (level >= 0 && level <= 15) {
			experience = (int) Math.ceil(Math.pow(level, 2) + (6 * level));
			int requiredExperience = 2 * level + 7;
			double currentExp = Double.parseDouble(Float.toString(player.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		} else if (level > 15 && level <= 30) {
			experience = (int) Math.ceil((2.5 * Math.pow(level, 2) - (40.5 * level) + 360));
			int requiredExperience = 5 * level - 38;
			double currentExp = Double.parseDouble(Float.toString(player.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		} else {
			experience = (int) Math.ceil(((4.5 * Math.pow(level, 2) - (162.5 * level) + 2220)));
			int requiredExperience = 9 * level - 158;
			double currentExp = Double.parseDouble(Float.toString(player.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		}
	}

	public static void takeTotalXP(Player player, int amount) {
		if (getVersion() >= 181) {
			int total = getTotalExperience(player) - amount;
			player.setTotalExperience(0);
			player.setTotalExperience(total);
			player.setLevel(0);
			player.setExp(0);
			for (; total > player.getExpToLevel();) {
				total -= player.getExpToLevel();
				player.setLevel(player.getLevel() + 1);
			}
			float xp = (float) total / (float) player.getExpToLevel();
			player.setExp(xp);
		} else {
			player.giveExp(-amount);
		}
	}

	public static boolean isProtected(ItemStack i) {
		if (i.hasItemMeta()) {
			if (i.getItemMeta().hasLore()) {
				for (String lore : i.getItemMeta().getLore())
					if (lore.equals(color(Main.settings.getConfig().getString("Settings.WhiteScroll.ProtectedName")))) {
						return true;
					}
			}
		}
		return false;
	}

	public static ItemStack BlackScroll(int i) {
		String name = color(Main.settings.getConfig().getString("Settings.BlackScroll.Name"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Methods.color("&7Right Click for more Info."));
		String type = Main.settings.getConfig().getString("Settings.BlackScroll.Item");
		int ty = 0;
		if (type.contains(":")) {
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		return makeItem(m, i, ty, name, Main.settings.getConfig().getStringList("Settings.BlackScroll.Item-Lore"));
	}

	public static ItemStack addWhiteScroll(int amount) {
		return makeItem(Main.settings.getConfig().getString("Settings.WhiteScroll.Item"), amount,
				Main.settings.getConfig().getString("Settings.WhiteScroll.Name"),
				Main.settings.getConfig().getStringList("Settings.WhiteScroll.Item-Lore"));
	}

	public static ItemStack addLore(ItemStack item, String i) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta m = item.getItemMeta();
		if (item.getItemMeta().hasLore()) {
			lore.addAll(item.getItemMeta().getLore());
		}
		lore.add(color(i));
		if (lore.contains(color(Main.settings.getConfig().getString("Settings.WhiteScroll.ProtectedName")))) {
			lore.remove(color(Main.settings.getConfig().getString("Settings.WhiteScroll.ProtectedName")));
			lore.add(color(Main.settings.getConfig().getString("Settings.WhiteScroll.ProtectedName")));
		}
		if (lore.contains(color(Main.settings.getConfig().getString("Settings.ProtectionCrystal.Protected")))) {
			lore.remove(color(Main.settings.getConfig().getString("Settings.ProtectionCrystal.Protected")));
			lore.add(color(Main.settings.getConfig().getString("Settings.ProtectionCrystal.Protected")));
		}
		m.setLore(lore);
		item.setItemMeta(m);
		return item;
	}

	public static ItemStack removeProtected(ItemStack item) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemMeta m = item.getItemMeta();
		lore.addAll(m.getLore());
		lore.remove(color(Main.settings.getConfig().getString("Settings.WhiteScroll.ProtectedName")));
		m.setLore(lore);
		item.setItemMeta(m);
		return item;
	}

	public static void hasUpdate() {
		try {
			HttpURLConnection c = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
					.openConnection();
			c.setDoOutput(true);
			c.setRequestMethod("POST");
			c.getOutputStream()
					.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=16470")
							.getBytes("UTF-8"));
			String oldVersion = plugin.getDescription().getVersion();
			String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine()
					.replaceAll("[a-zA-Z ]", "");
			if (!newVersion.equals(oldVersion)) {
				Bukkit.getConsoleSender().sendMessage(Methods.getPrefix() + Methods.color(
						"&cYour server is running &7v" + oldVersion + "&c and the newest is &7v" + newVersion + "&c."));
			}
		} catch (Exception e) {
			return;
		}
	}

	public static void hasUpdate(Player player) {
		try {
			HttpURLConnection c = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
					.openConnection();
			c.setDoOutput(true);
			c.setRequestMethod("POST");
			c.getOutputStream()
					.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=16470")
							.getBytes("UTF-8"));
			String oldVersion = plugin.getDescription().getVersion();
			String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine()
					.replaceAll("[a-zA-Z ]", "");
			if (!newVersion.equals(oldVersion)) {
				player.sendMessage(Methods.getPrefix() + Methods.color(
						"&cYour server is running &7v" + oldVersion + "&c and the newest is &7v" + newVersion + "&c."));
			}
		} catch (Exception e) {
			return;
		}
	}

	public static int getEnchAmount(ItemStack item) {
		int amount = 0;
		amount += Main.CE.getItemEnchantments(item).size();
		amount += Main.CustomE.getItemEnchantments(item).size();
		if (Main.settings.getConfig().contains("Settings.EnchantmentOptions.IncludeVanillaEnchantments")) {
			if (Main.settings.getConfig().getBoolean("Settings.EnchantmentOptions.IncludeVanillaEnchantments")) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().hasEnchants()) {
						amount = +item.getItemMeta().getEnchants().size();
					}
				}
			}
		}
		return amount;
	}

	public static Integer getPercent(String Argument, ItemStack item, List<String> Msg) {
		List<String> lore = item.getItemMeta().getLore();
		String arg = "100";
		for (String oLine : Msg) {
			oLine = Methods.color(oLine).toLowerCase();
			if (oLine.contains(Argument.toLowerCase())) {
				String[] b = oLine.split(Argument.toLowerCase());
				for (String iline : lore) {
					if (b.length >= 1) {
						if (iline.toLowerCase().startsWith(b[0])) {
							arg = iline.toLowerCase().replace(b[0], "");
						}
					}
					if (b.length >= 2) {
						if (iline.toLowerCase().endsWith(b[1])) {
							arg = arg.toLowerCase().replace(b[1], "");
						}
					}
				}
			}
		}
		return Integer.parseInt(arg);
	}

	public static Boolean hasArgument(String Argument, List<String> Msg) {
		for (String l : Msg) {
			l = Methods.color(l).toLowerCase();
			if (l.contains(Argument.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean randomPicker(int max) {
		Random number = new Random();
		if (max <= 0) {
			return true;
		}
		int chance = 1 + number.nextInt(max);
		if (chance == 1) {
			return true;
		}
		return false;
	}

	public static boolean randomPicker(int min, int max) {
		if (max == min || max <= min || max <= 0) {
			return true;
		}
		Random number = new Random();
		int chance = 1 + number.nextInt(max);
		if (chance >= 1 && chance <= min) {
			return true;
		}
		return false;
	}

	public static Integer percentPick(int max, int min) {
		Random i = new Random();
		return min + i.nextInt(max - min);
	}

	public static boolean isInvFull(Player player) {
		if (player.getInventory().firstEmpty() == -1) {
			return true;
		}
		return false;
	}

	public static List<LivingEntity> getNearbyEntities(Location loc, double radius, Entity ent) {
		List<Entity> out = ent.getNearbyEntities(radius, radius, radius);
		List<LivingEntity> entities = new ArrayList<LivingEntity>();
		for (Entity en : out) {
			if (en instanceof LivingEntity) {
				entities.add((LivingEntity) en);
			}
		}
		return entities;
	}

	public static void fireWork(Location loc, ArrayList<Color> colors) {
		Firework fw = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta fm = fw.getFireworkMeta();
		fm.addEffects(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(colors).trail(false)
				.flicker(false).build());
		fm.setPower(0);
		fw.setFireworkMeta(fm);
		FuckFireworks.addFirework(fw);
		detonate(fw);
	}

	private static void detonate(final Firework f) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				f.detonate();
			}
		}, 2);
	}

	public static Color getColor(String color) {
		if (color.equalsIgnoreCase("AQUA"))
			return Color.AQUA;
		if (color.equalsIgnoreCase("BLACK"))
			return Color.BLACK;
		if (color.equalsIgnoreCase("BLUE"))
			return Color.BLUE;
		if (color.equalsIgnoreCase("FUCHSIA"))
			return Color.FUCHSIA;
		if (color.equalsIgnoreCase("GRAY"))
			return Color.GRAY;
		if (color.equalsIgnoreCase("GREEN"))
			return Color.GREEN;
		if (color.equalsIgnoreCase("LIME"))
			return Color.LIME;
		if (color.equalsIgnoreCase("MAROON"))
			return Color.MAROON;
		if (color.equalsIgnoreCase("NAVY"))
			return Color.NAVY;
		if (color.equalsIgnoreCase("OLIVE"))
			return Color.OLIVE;
		if (color.equalsIgnoreCase("ORANGE"))
			return Color.ORANGE;
		if (color.equalsIgnoreCase("PURPLE"))
			return Color.PURPLE;
		if (color.equalsIgnoreCase("RED"))
			return Color.RED;
		if (color.equalsIgnoreCase("SILVER"))
			return Color.SILVER;
		if (color.equalsIgnoreCase("TEAL"))
			return Color.TEAL;
		if (color.equalsIgnoreCase("WHITE"))
			return Color.WHITE;
		if (color.equalsIgnoreCase("YELLOW"))
			return Color.YELLOW;
		return Color.WHITE;
	}

	public static String getEnchantmentName(Enchantment en) {
		HashMap<String, String> enchants = new HashMap<String, String>();
		enchants.put("ARROW_DAMAGE", "Power");
		enchants.put("ARROW_FIRE", "Flame");
		enchants.put("ARROW_INFINITE", "Infinity");
		enchants.put("ARROW_KNOCKBACK", "Punch");
		enchants.put("DAMAGE_ALL", "Sharpness");
		enchants.put("DAMAGE_ARTHROPODS", "Bane_Of_Arthropods");
		enchants.put("DAMAGE_UNDEAD", "Smite");
		enchants.put("DEPTH_STRIDER", "Depth_Strider");
		enchants.put("DIG_SPEED", "Efficiency");
		enchants.put("DURABILITY", "Unbreaking");
		enchants.put("FIRE_ASPECT", "Fire_Aspect");
		enchants.put("KNOCKBACK", "KnockBack");
		enchants.put("LOOT_BONUS_BLOCKS", "Fortune");
		enchants.put("LOOT_BONUS_MOBS", "Looting");
		enchants.put("LUCK", "Luck_Of_The_Sea");
		enchants.put("LURE", "Lure");
		enchants.put("OXYGEN", "Respiration");
		enchants.put("PROTECTION_ENVIRONMENTAL", "Protection");
		enchants.put("PROTECTION_EXPLOSIONS", "Blast_Protection");
		enchants.put("PROTECTION_FALL", "Feather_Falling");
		enchants.put("PROTECTION_FIRE", "Fire_Protection");
		enchants.put("PROTECTION_PROJECTILE", "Projectile_Protection");
		enchants.put("SILK_TOUCH", "Silk_Touch");
		enchants.put("THORNS", "Thorns");
		enchants.put("WATER_WORKER", "Aqua_Affinity");
		enchants.put("BINDING_CURSE", "Curse_Of_Binding");
		enchants.put("MENDING", "Mending");
		enchants.put("FROST_WALKER", "Frost_Walker");
		enchants.put("VANISHING_CURSE", "Curse_Of_Vanishing");
		if (enchants.get(en.getName()) == null) {
			return "None Found";
		}
		return enchants.get(en.getName());
	}

	public static ItemStack makeItem(String type, int amount, String name, List<String> lore, Boolean glowing) {
		ArrayList<String> l = new ArrayList<String>();
		int ty = 0;
		if (type.contains(":")) {
			String[] b = type.split(":");
			type = b[0];
			ty = Integer.parseInt(b[1]);
		}
		Material m = Material.matchMaterial(type);
		ItemStack item = new ItemStack(m, amount, (short) ty);
		ItemMeta me = item.getItemMeta();
		me.setDisplayName(color(name));
		for (String L : lore)
			l.add(color(L));
		me.setLore(l);
		item.setItemMeta(me);
		if (glowing) {
			item = addGlow(item);
		}
		return item;
	}
}