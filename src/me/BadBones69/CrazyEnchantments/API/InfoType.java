package me.BadBones69.CrazyEnchantments.API;

public enum InfoType {
	
	HELMETS("Helmets"), BOOTS("Boots"), ARMOR("Armor"),
	SWORD("Sword"), AXE("Axe"), BOW("Bow"), PICKAXE("PickAxe"), 
	TOOL("Tool"), MISC("Misc");
	
	private String Name;
	
	private InfoType(String name){
		Name = name;
	}
	
	public String getName(){
		return Name;
	}
	
	public static InfoType[] getTypes(){
		return values();
	}	
}