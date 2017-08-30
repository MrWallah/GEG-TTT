package ch.mrwallah.ttt.utils;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemCreator {

	public static ItemStack CreateItem(Material m, int Anzahl, int SubID, String Displayname, String Lore){
		
		ItemStack i = new ItemStack(m, Anzahl, (short) SubID);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(Displayname);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(Lore);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
		
	}
	
	public static ItemStack CreateExtItem(Material m, int Anzahl, int SubID, String Displayname, String Lore, String Lore2, String Lore3, String Lore4){
		
		ItemStack i = new ItemStack(m, Anzahl, (short) SubID);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(Displayname);
		ArrayList<String> lore = new ArrayList<>();
		lore.add(Lore);
		lore.add(Lore2);
		lore.add(Lore3);
		lore.add(Lore4);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
		
	}
	
	public static ItemStack CreateLArmor(Material m, int Anzahl, Color c){
		
		 ItemStack lchest = new ItemStack(m, Anzahl);
         LeatherArmorMeta lch = (LeatherArmorMeta)lchest.getItemMeta();
         lch.setColor(c);
         lchest.setItemMeta(lch);
         return lchest;
         
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack CreateHead(int Anzahl, String name){
		
		ItemStack skull = new ItemStack(397, 1, (short) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(name);
		skull.setItemMeta(meta);
		return skull;
        
	}
	
	
}
