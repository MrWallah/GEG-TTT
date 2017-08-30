package ch.mrwallah.ttt.manager;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.ItemCreator;



public class VoteManager implements Listener{
	
	public static int rndm1;
	public static int rndm2;
	public static int rndm3;

	public static ArrayList<Player> map1 = new ArrayList<>();
	public static ArrayList<Player> map2 = new ArrayList<>();	
	public static ArrayList<Player> map3 = new ArrayList<>();	

	public static int votes1;
	public static int votes2;
	public static int votes3;

	
	public static void randomMaps(){	
		if (Data.cfg.getInt("Maps.Anzahl") > 2){
		Random randomGenerator = new Random();
		int randomInt1 = randomGenerator.nextInt(Data.cfg.getInt("Maps.Anzahl")) + 1;
		rndm1 =  randomInt1;
		int randomInt2 = randomGenerator.nextInt(Data.cfg.getInt("Maps.Anzahl")) + 1;
		while(randomInt1 == randomInt2) {
		 randomInt2 = randomGenerator.nextInt(Data.cfg.getInt("Maps.Anzahl")) + 1;
		}
		rndm2 = randomInt2;
		int randomInt3 = randomGenerator.nextInt(Data.cfg.getInt("Maps.Anzahl")) + 1;
		while(randomInt3 == randomInt2 || randomInt3 == randomInt1) {
		 randomInt3 = randomGenerator.nextInt(Data.cfg.getInt("Maps.Anzahl")) + 1;
		}
		rndm3 = randomInt3;
		} else {
			System.out.println(Data.Prefix + "§cError: Setup Maps!");
		}
		

	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if (e.getPlayer().getItemInHand().getType() == (Material.PAPER)){
				if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == "§6Map Voting"){
					e.setCancelled(true);
					Player p = e.getPlayer();
					Inventory inv = Bukkit.createInventory(null, 9, "§6Map Voting");
					
					for (int i = 0; i != 9; i++) {
						
						inv.setItem(i, ItemCreator.CreateItem(Material.STAINED_GLASS_PANE, 1, 15, " ", " "));			
												
					}
					
					String MapName1 = Data.cfg.getString("Maps." + rndm1);

					
					ItemStack map1 = new ItemStack(Data.cfg.getInt(MapName1 + "." + "Item"), 1);
					ItemMeta immap1 = map1.getItemMeta();
					ArrayList<String> lmap1 = new ArrayList<>();
					lmap1.add("§6Votes: " + votes1);
					immap1.setLore(lmap1);
					immap1.setDisplayName("§f" + MapName1);
					map1.setItemMeta(immap1);
					
					String MapName2 = Data.cfg.getString("Maps." + rndm2);
					
					ItemStack map2 = new ItemStack(Data.cfg.getInt(MapName2 + "." + "Item"), 1);
					ItemMeta immap2 = map2.getItemMeta();
					ArrayList<String> lmap2 = new ArrayList<>();
					lmap2.add("§6Votes: " + votes2);
					immap2.setLore(lmap2);
					immap2.setDisplayName("§f" + MapName2);
					map2.setItemMeta(immap2);
					
					String MapName3 = Data.cfg.getString("Maps." + rndm3);
					
					ItemStack map3 = new ItemStack(Data.cfg.getInt(MapName3 + "." + "Item"), 1);
					ItemMeta immap3 = map3.getItemMeta();
					ArrayList<String> lmap3 = new ArrayList<>();
					lmap3.add("§6Votes: " + votes3);
					immap3.setLore(lmap3);
					immap3.setDisplayName("§f" + MapName3);
					map3.setItemMeta(immap3);
					
					inv.setItem(2, map1);
					
					inv.setItem(4, map2);

					inv.setItem(6, map3);

					
					p.openInventory(inv);
				}
			}
		}
	}
	
	@EventHandler
	public void invClick(InventoryClickEvent e){
		if (e.getInventory().getTitle().equalsIgnoreCase("§6Map Voting")){
			Player p = (Player) e.getWhoClicked();
			if (e.getSlot() == 2){
					if (!map1.contains(p)){
						map1.add(p);
						votes1 = votes1 + 1;
						for (Player all : Bukkit.getOnlinePlayers()){
							all.updateInventory();
							Scoardboard.updateScoreboard(all);
						}
						p.playSound(p.getLocation(), Sound.ENDERDRAGON_HIT, 1, 1);
						p.closeInventory();
						if (map2.contains(p)){
							map2.remove(p);
							votes2 = votes2 -1;
							p.sendMessage(Data.Prefix + "§2Du hast deine Stimme geändert");
						} else if (map3.contains(p)){
							map3.remove(p);
							votes3 = votes3 -1;
							p.sendMessage(Data.Prefix + "§2Du hast deine Stimme geändert");
						}
					} else {
						p.sendMessage(Data.Prefix + "§cDu hast bereits für diese Map gestimmt!");
					}
			} else if (e.getSlot() == 4){		
				if (!map2.contains(p)){
					map2.add(p);
					votes2 = votes2 + 1;
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_HIT, 1, 1);
					p.closeInventory();
					for (Player all : Bukkit.getOnlinePlayers()){
						all.updateInventory();
						Scoardboard.updateScoreboard(all);
					}
					if (map1.contains(p)){
						map1.remove(p);
						votes1 = votes1 -1;
						p.sendMessage(Data.Prefix + "§2Du hast deine Stimme geändert");
					} else if (map3.contains(p)){
						map3.remove(p);
						votes3 = votes3 -1;
						p.sendMessage(Data.Prefix + "§2Du hast deine Stimme geändert");
					}
				} else {
					p.sendMessage(Data.Prefix + "§cDu hast bereits für diese Map gestimmt!");
				}
			} else if (e.getSlot() == 6){
				if (!map3.contains(p)){
					map3.add(p);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_HIT, 1, 1);
					votes3 = votes3 + 1;
					for (Player all : Bukkit.getOnlinePlayers()){
						all.updateInventory();
						Scoardboard.updateScoreboard(all);
					}
					p.closeInventory();
					if (map2.contains(p)){
						map2.remove(p);
						votes2 = votes2 -1;
						p.sendMessage(Data.Prefix + "§2Du hast deine Stimme geändert");
					} else if (map1.contains(p)){
						map1.remove(p);
						votes1 = votes1 -1;
						p.sendMessage(Data.Prefix + "§2Du hast deine Stimme geändert");
					}
				} else {
					p.sendMessage(Data.Prefix + "§cDu hast bereits für diese Map gestimmt!");
				}
			} 
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		if (map1.contains(p)){
			map1.remove(p);
			votes1 = votes1 - 1;
		} else if (map2.contains(p)){
			map2.remove(p);
			votes2 = votes2 - 1;
		} else if (map3.contains(p)){
			map3.remove(p);
			votes3 = votes3 - 1;
		} 
	}

}
