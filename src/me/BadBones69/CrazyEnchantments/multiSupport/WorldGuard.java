package me.BadBones69.CrazyEnchantments.multiSupport;

import org.bukkit.Location;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuard {
	public static boolean allowsPVP(Location loc){
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(loc.getWorld()).getApplicableRegions(loc);
		if (set.queryState(null, DefaultFlag.PVP)==StateFlag.State.DENY){
			return false;
		}
		return true;
	}
	public static boolean allowsBreak(Location loc){
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(loc.getWorld()).getApplicableRegions(loc);
		if (set.queryState(null, DefaultFlag.BLOCK_BREAK)==StateFlag.State.DENY || set.queryState(null, DefaultFlag.BUILD)==StateFlag.State.DENY){
			return false;
		}
		return true;
	}
	public static boolean allowsExplotions(Location loc){
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(loc.getWorld()).getApplicableRegions(loc);
		if (set.queryState(null, DefaultFlag.OTHER_EXPLOSION)==StateFlag.State.DENY || set.queryState(null, DefaultFlag.TNT)==StateFlag.State.DENY){
			return false;
		}
		return true;
	}
	public static boolean inRegion(String regionName, Location loc){
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(loc.getWorld()).getApplicableRegions(loc);
		for(ProtectedRegion region : set){
			if(regionName.equalsIgnoreCase(region.getId())){
				return true;
			}
		}
		return false;
	}
}