package me.BadBones69.CrazyEnchantments.multiSupport;

import org.bukkit.Location;

public class GriefProtection {
	
	public static boolean allowsPVP(Location loc){
		
		return true;
	}
	
	public static boolean allowsBreak(Location loc){
		
		return true;
	}
	
	public static boolean allowsExplotions(Location loc){
		
		return true;
	}
	
	public static boolean inRegion(String regionName, Location loc){
		
		return false;
	}
	
}