package ch.mrwallah.ttt.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import ch.mrwallah.ttt.game.MainListener;
import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.ItemCreator;

public class Inventories implements Listener{

	public static void getLobbyItems(Player p){
		
		Data.clearInv(p);
		if (MainListener.isRunning == true){
			if (MainListener.cdz > 10){
				if (forcemapCMD.used == false){
					p.getInventory().setItem(0, ItemCreator.CreateItem(Material.PAPER, 1, 0, "§6Map Voting", "§2Vote für eine Map!"));
					p.getInventory().setItem(1, ItemCreator.CreateItem(Material.INK_SACK , 1, 8, "§3Pässe", "§2Comming Soon"));
					p.getInventory().setItem(8, ItemCreator.CreateItem(Material.SLIME_BALL, 1, 0, "§2Zurück zur LOBBY", " "));
				} else {
					p.getInventory().setItem(0, ItemCreator.CreateItem(Material.INK_SACK , 1, 8, "§3Pässe", "§2Comming Soon"));
					p.getInventory().setItem(8, ItemCreator.CreateItem(Material.SLIME_BALL, 1, 0, "§2Zurück zur LOBBY", " "));
				}
			} else {
				p.getInventory().setItem(0, ItemCreator.CreateItem(Material.INK_SACK , 1, 8, "§3Pässe", "§2Comming Soon"));
				p.getInventory().setItem(8, ItemCreator.CreateItem(Material.SLIME_BALL, 1, 0, "§2Zurück zur LOBBY", " "));
			}
		} else {
			p.getInventory().setItem(0, ItemCreator.CreateItem(Material.INK_SACK , 1, 8, "§3Pässe", "§2Comming Soon"));
			p.getInventory().setItem(8, ItemCreator.CreateItem(Material.SLIME_BALL, 1, 0, "§2Zurück zur LOBBY", " "));
		}
		
		p.setHealth(20);
		p.setFoodLevel(20);
		
	}
	
	public static void getDetectivItems(Player p) {
		p.getInventory().setItem(8, ItemCreator.CreateItem(Material.STICK, 1, 0, "§1Detective Stick", "§fKlicke auf eine Leichen um sie zu identifizieren"));
	}
	
	
}
