package me.BadBones69.CrazyEnchantments.API;

import java.util.HashMap;
import java.util.List;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;

public enum CEnchantments {

	// ----------------Boots---------------- \\
	GEARS("Gears", EnchantmentType.BOOTS), WINGS("Wings", EnchantmentType.BOOTS), ROCKET("Rocket",
			EnchantmentType.BOOTS), SPRINGS("Springs",
					EnchantmentType.BOOTS), ANTIGRAVITY("AntiGravity", EnchantmentType.BOOTS),
	// ----------------Bows---------------- \\
	BOOM("Boom", EnchantmentType.BOW), PULL("Pull", EnchantmentType.BOW), VENOM("Venom", EnchantmentType.BOW), DOCTOR(
			"Doctor", EnchantmentType.BOW), PIERCING("Piercing", EnchantmentType.BOW), ICEFREEZE("IceFreeze",
					EnchantmentType.BOW), LIGHTNING("Lightning",
							EnchantmentType.BOW), MULTIARROW("MultiArrow", EnchantmentType.BOW),
	// ----------------Helmets---------------- \\
	DRUNK("Drunk", EnchantmentType.HELMET), GLOWING("Glowing", EnchantmentType.HELMET), MERMAID("Mermaid",
			EnchantmentType.HELMET), IMPLANTS("Implants", EnchantmentType.HELMET), INSOMNIA("Insomnia",
					EnchantmentType.HELMET), COMMANDER("Commander",
							EnchantmentType.HELMET), ENDERSHIFT("EnderShift", EnchantmentType.HELMET),
	// ----------------Swords---------------- \\
	TRAP("Trap", EnchantmentType.SWORD), RAGE("Rage", EnchantmentType.SWORD), VIPER("Viper",
			EnchantmentType.SWORD), SNARE("Snare", EnchantmentType.SWORD), SLOWMO("SlowMo",
					EnchantmentType.SWORD), WITHER("Wither", EnchantmentType.SWORD), VAMPIRE("Vampire",
							EnchantmentType.SWORD), EXECUTE("Execute", EnchantmentType.SWORD), FASTTURN("FastTurn",
									EnchantmentType.SWORD), DISARMER("Disarmer", EnchantmentType.SWORD), HEADLESS(
											"Headless", EnchantmentType.SWORD), PARALYZE("Paralyze",
													EnchantmentType.SWORD), BLINDNESS("Blindness",
															EnchantmentType.SWORD), LIFESTEAL("LifeSteal",
																	EnchantmentType.SWORD), CONFUSION("Confusion",
																			EnchantmentType.SWORD), NUTRITION(
																					"Nutrition",
																					EnchantmentType.SWORD), SKILLSWIPE(
																							"SkillSwipe",
																							EnchantmentType.SWORD), OBLITERATE(
																									"Obliterate",
																									EnchantmentType.SWORD), INQUISITIVE(
																											"Inquisitive",
																											EnchantmentType.SWORD), LIGHTWEIGHT(
																													"LightWeight",
																													EnchantmentType.SWORD), DEMONFORGED(
																															"DemonForged",
																															EnchantmentType.SWORD), DOUBLEDAMAGE(
																																	"DoubleDamage",
																																	EnchantmentType.SWORD), DIVINEIMMOMATION(
																																			"DivineImmolation",
																																			EnchantmentType.SWORD),
	// ----------------Armor---------------- \\
	TANK("Tank", EnchantmentType.ARMOR), HULK("Hulk", EnchantmentType.ARMOR), VALOR("Valor",
			EnchantmentType.ARMOR), LUCKY("Lucky", EnchantmentType.ARMOR), NINJA("Ninja", EnchantmentType.ARMOR), ANGEL(
					"Angel", EnchantmentType.ARMOR), TAMER("Tamer", EnchantmentType.ARMOR), VOODOO("Voodoo",
							EnchantmentType.ARMOR), MOLTEN("Molten", EnchantmentType.ARMOR), SAVIOR("Savior",
									EnchantmentType.ARMOR), CACTUS("Cactus", EnchantmentType.ARMOR), FREEZE("Freeze",
											EnchantmentType.ARMOR), RECOVER("Recover", EnchantmentType.ARMOR), NURSERY(
													"Nursery", EnchantmentType.ARMOR), RADIANT("Radiant",
															EnchantmentType.ARMOR), FORTIFY("Fortify",
																	EnchantmentType.ARMOR), SPIRITS("Spirits",
																			EnchantmentType.ARMOR), PARADOX("Paradox",
																					EnchantmentType.ARMOR), OVERLOAD(
																							"Overload",
																							EnchantmentType.ARMOR), BLIZZARD(
																									"Blizzard",
																									EnchantmentType.ARMOR), ACIDRAIN(
																											"AcidRain",
																											EnchantmentType.ARMOR), HARDENED(
																													"Hardened",
																													EnchantmentType.ARMOR), ARMOURED(
																															"Armoured",
																															EnchantmentType.ARMOR), IMMORTAL(
																																	"Immortal",
																																	EnchantmentType.ARMOR), SANDSTORM(
																																			"SandStorm",
																																			EnchantmentType.ARMOR), SMOKEBOMB(
																																					"SmokeBomb",
																																					EnchantmentType.ARMOR), PAINGIVER(
																																							"PainGiver",
																																							EnchantmentType.ARMOR), SHOCKWAVE(
																																									"Shockwave",
																																									EnchantmentType.ARMOR), GUARDIANS(
																																											"Guardians",
																																											EnchantmentType.ARMOR), INTIMIDATE(
																																													"Intimidate",
																																													EnchantmentType.ARMOR), BURNSHIELD(
																																															"BurnShield",
																																															EnchantmentType.ARMOR), LEADERSHIP(
																																																	"Leadership",
																																																	EnchantmentType.ARMOR), UNDEADRUSE(
																																																			"UndeadRuse",
																																																			EnchantmentType.ARMOR), SPIRITLINK(
																																																					"SpiritLink",
																																																					EnchantmentType.ARMOR), INFESTATION(
																																																							"Infestation",
																																																							EnchantmentType.ARMOR), STORMCALLER(
																																																									"StormCaller",
																																																									EnchantmentType.ARMOR), ENLIGHTENED(
																																																											"Enlightened",
																																																											EnchantmentType.ARMOR), SELFDESTRUCT(
																																																													"SelfDestruct",
																																																													EnchantmentType.ARMOR), DEATHBRINGER(
																																																															"DeathBringer",
																																																															EnchantmentType.ARMOR), NATURESWRATH(
																																																																	"NaturesWrath",
																																																																	EnchantmentType.ARMOR),
	// ----------------Axes---------------- \\
	REKT("Rekt", EnchantmentType.AXE), PYRO("Pyro", EnchantmentType.AXE), DIZZY("Dizzy", EnchantmentType.AXE), BLEED(
			"Bleed",
			EnchantmentType.AXE), CURSED("Cursed", EnchantmentType.AXE), FEEDME("FeedMe", EnchantmentType.AXE), CLEAVE(
					"Cleave", EnchantmentType.AXE), BERSERK("Berserk", EnchantmentType.AXE), BLESSED("Blessed",
							EnchantmentType.AXE), DECAPITATION("Decapitation", EnchantmentType.AXE),
	// ----------------PickAxes---------------- \\
	BLAST("Blast", EnchantmentType.PICKAXE), FURNACE("Furnace", EnchantmentType.PICKAXE), AUTOSMELT("AutoSmelt",
			EnchantmentType.PICKAXE), EXPERIENCE("Experience", EnchantmentType.PICKAXE),
	// ----------------Tools---------------- \\
	HASTE("Haste", EnchantmentType.TOOL), TELEPATHY("Telepathy", EnchantmentType.TOOL), OXYGENATE("Oxygenate",
			EnchantmentType.TOOL),
	// ----------------All---------------- \\
	HELLFORGED("HellForged", EnchantmentType.ALL);

	String Name;
	EnchantmentType Type;
	private static HashMap<CEnchantments, String> customNames = new HashMap<CEnchantments, String>();
	private static HashMap<CEnchantments, String> bookColors = new HashMap<CEnchantments, String>();
	private static HashMap<CEnchantments, String> enchantColors = new HashMap<CEnchantments, String>();
	private static HashMap<CEnchantments, Boolean> active = new HashMap<CEnchantments, Boolean>();
	private static HashMap<CEnchantments, List<String>> enchantDesc = new HashMap<CEnchantments, List<String>>();

	/**
	 * 
	 * @param name
	 *            Name of the enchantment.
	 * @param type
	 *            Type of items it goes on.
	 */
	private CEnchantments(String name, EnchantmentType type) {
		Name = name;
		Type = type;
	}

	/**
	 * Loads all the enchantments data.
	 */
	public static void load() {
		for (CEnchantments en : values()) {
			String name = en.getName();
			customNames.put(en, Main.settings.getEnchs().getString("Enchantments." + name + ".Name"));
			bookColors.put(en, Main.settings.getEnchs().getString("Enchantments." + name + ".BookColor"));
			enchantColors.put(en, Main.settings.getEnchs().getString("Enchantments." + name + ".Color"));
			active.put(en, Main.settings.getEnchs().getBoolean("Enchantments." + name + ".Enabled"));
			enchantDesc.put(en, Main.settings.getEnchs().getStringList("Enchantments." + name + ".Info.Description"));
		}
	}

	/**
	 * 
	 * @return The name of the enchantment.
	 */
	public String getName() {
		return Name;
	}

	/**
	 * 
	 * @return The custom name in the Enchantment.yml.
	 */
	public String getCustomName() {
		return customNames.get(this);
	}

	/**
	 *
	 * @return The description of the enchantment in the Enchantments.yml.
	 */
	public List<String> getDiscription() {
		return enchantDesc.get(this);
	}

	/**
	 * 
	 * @return Return the color that goes on the Enchantment Book.
	 */
	public String getBookColor() {
		return Methods.color(bookColors.get(this));
	}

	/**
	 * 
	 * @return Returns the color that goes on the Enchanted Item.
	 */
	public String getEnchantmentColor() {
		return Methods.color(enchantColors.get(this));
	}

	/**
	 * 
	 * @return The type the enchantment is.
	 */
	public EnchantmentType getType() {
		return Type;
	}

	/**
	 * 
	 * @return True if the enchantment is enabled and false if not.
	 */
	public Boolean isEnabled() {
		return active.get(this);
	}
}