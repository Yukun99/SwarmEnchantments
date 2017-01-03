package me.BadBones69.CrazyEnchantments.API;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.BadBones69.CrazyEnchantments.Methods;
import me.BadBones69.CrazyEnchantments.Main;

public class GKitzItem {
	
	private HashMap<CEnchantments, Integer> ceEnchantments;
	private HashMap<Enchantment, Integer> enchantments;
	private HashMap<String, Integer> cEnchantments;
	private ArrayList<String> lore;
	private String type;
	private String name;
	private Integer amount;
	
	/**
	 * Make an empty gkit item.
	 */
	public GKitzItem(){
		ceEnchantments = new HashMap<CEnchantments, Integer>();
		enchantments = new HashMap<Enchantment, Integer>();
		cEnchantments = new HashMap<String, Integer>();
		lore = new ArrayList<String>();
		type = "299";
		name = "";
		amount = 1;
	}
	
	/**
	 * 
	 * @param enchant Crazy Enchantment
	 * @param level Level of the enchantment
	 */
	public void addCEEnchantment(CEnchantments enchant, Integer level){
		ceEnchantments.put(enchant, level);
	}
	
	/**
	 * 
	 * @param enchant Crazy Enchantment
	 */
	public void removeCEEnchantment(CEnchantments enchant){
		ceEnchantments.remove(enchant);
	}
	
	/**
	 * 
	 * @param enchant Custom Enchantment
	 * @param level Level of the enchantment
	 */
	public void addCustomEnchantment(String enchant, Integer level){
		cEnchantments.put(enchant, level);
	}
	
	/**
	 * 
	 * @param enchant Custom Enchantment
	 */
	public void removeCustomEnchantment(String enchant){
		cEnchantments.remove(enchant);
	}
	
	/**
	 * 
	 * @param enchant Vanilla Enchantment
	 * @param level Level of the enchantment
	 */
	public void addEnchantment(Enchantment enchant, Integer level){
		enchantments.put(enchant, level);
	}
	
	/**
	 * 
	 * @param enchant Vanilla Enchantment
	 */
	public void removeEnchantment(Enchantment enchant){
		enchantments.remove(enchant);
	}
	
	/**
	 * 
	 * @param id Item's ID
	 */
	public void setItem(String id){
		type = id;
	}
	
	/**
	 * 
	 * @param amount Amount of items
	 */
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	/**
	 * 
	 * @param name Name of the item
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 * @param lore Lore of the item
	 */
	public void setLore(ArrayList<String> lore){
		this.lore = lore;
	}
	
	/**
	 * 
	 * @return Returns a fully finished item.
	 */
	public ItemStack build(){
		ItemStack item = Methods.makeItem(type, amount, name);
		for(CEnchantments en : ceEnchantments.keySet()){
			Main.CE.addEnchantment(item, en, ceEnchantments.get(en));
		}
		for(String en : cEnchantments.keySet()){
			if(Main.CustomE.isEnchantment(en)){
				Main.CustomE.addEnchantment(item, en, cEnchantments.get(en));
			}
		}
		for(Enchantment en : enchantments.keySet()){
			item.addUnsafeEnchantment(en, enchantments.get(en));
		}
		for(String l : lore){
			Methods.addLore(item, l);
		}
		return item;
	}
	
}