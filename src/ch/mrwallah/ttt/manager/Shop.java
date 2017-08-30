package ch.mrwallah.ttt.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.ItemCreator;

public class Shop implements CommandExecutor, Listener {

	public static HashMap<Player, Integer> tpunkte = new HashMap<>();
	public static HashMap<Player, Integer> dpunkte = new HashMap<>();


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("shop")) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (Main.gs == GameState.INGAME) {
					if (TeamManager.detectives.contains(p)) {
						openDShop(p);
					} else if (TeamManager.traitors.contains(p)) {
						openTShop(p);
					} else {
						p.sendMessage(Data.Prefix + "§cNur Traitors oder Detectives können diesen Befehl nutzen!");
					}
				} else {
					p.sendMessage(Data.Prefix + "§cDieser Befehl kann nur in der Kampfphase genutzt werden");
				}
			} else {
				sender.sendMessage(Data.Prefix + "§cDu musst ein Spieler sein!");
			}
		}
		return false;
	}
	
	

	
	public static void openDShop(Player p) {
		Inventory dinv = Bukkit.createInventory(null, 9*2, "§6Shop §7| §6Punkte: " + dpunkte.get(p));
		
		for (int i = 0; i != 9*2; i++) {
			
			dinv.setItem(i, ItemCreator.CreateItem(Material.STAINED_GLASS_PANE, 1, 15, " ", " "));			
									
		}
		
		dinv.setItem(3, ItemCreator.CreateExtItem(Material.NETHER_STAR, 1, 0, "§3Heal-Pack", "§fHeilt per Rechtsklick", " ", "§2Klicken um zu kaufen", "§6Kosten:§2 2 Punkte"));
		dinv.setItem(4, ItemCreator.CreateExtItem(Material.SNOW_BALL, 1, 0, "§dDNA-Scanner", "§fZeigt dir die aktuellen Koordinaten vom Killer", " ", "§2Klicken um zu kaufen", "§6Kosten:§2 5 Punkte"));
		dinv.setItem(5, ItemCreator.CreateExtItem(Material.BLAZE_ROD, 1, 0, "§2Super-Identifizierer", "§fIdentifiziert den Killer der Leiche", " ", "§2Klicken um zu kaufen", "§6Kosten:§2 6 Punkte"));
		dinv.setItem(12, ItemCreator.CreateExtItem(Material.BEACON, 1, 0, "§aHealing-Station", "§fHelit alle Innocents/Detective, welche auf den Beacon drücken", " ", "§2Klicken um zu kaufen", "§6Kosten:§2 4 Punkte"));
		dinv.setItem(13, ItemCreator.CreateExtItem(Material.REDSTONE_LAMP_OFF, 1, 0, "§9Traitor-Detector", "§fSchlägt Alarm falls sich ein Traitor in der Nähe befindet", " ", "§2Klicken um zu kaufen", "§6Kosten:§2 3 Punkte"));
		dinv.setItem(14, ItemCreator.CreateExtItem(Material.ENDER_PEARL, 1, 0, "§cSwapper", "§fTauscht die Position mit einem zufälligen Spieler", " ", "§2Klicken um zu kaufen", "§6Kosten:§2 4 Punkte"));

		p.openInventory(dinv);
		
	}
	
	public void openTShop(Player p) {

		Inventory tinv = Bukkit.createInventory(null, 9*2, "§6Shop §7| §6Punkte: " + tpunkte.get(p));
		
		for (int i = 0; i != 9*2; i++) {
			
			tinv.setItem(i, ItemCreator.CreateItem(Material.STAINED_GLASS_PANE, 1, 15, " ", " "));			
									
		}
		
		ItemStack hp = new ItemStack(Material.NETHER_STAR, 1);
		ItemMeta imhp = hp.getItemMeta();
		ArrayList<String> lhp = new ArrayList<>();
		lhp.add("§6Kosten:§2 2 Punkte" + System.lineSeparator() + "§2Klicken um zu kaufen");
		imhp.setLore(lhp);
		imhp.setDisplayName("§3Heal-Pack");
		hp.setItemMeta(imhp);
		
		ItemStack it = new ItemStack(Material.NAME_TAG, 1);
		ItemMeta imit = it.getItemMeta();
		ArrayList<String> lit = new ArrayList<>();
		lit.add("§6Kosten:§2 5 Punkte" + System.lineSeparator() + "§2Klicken um zu kaufen");
		imit.setLore(lit);
		imit.setDisplayName("§aInnocent-Ticket");
		it.setItemMeta(imit);
		
		ItemStack cb = new ItemStack(Material.MONSTER_EGG, 1, (short) 50); 	
		ItemMeta imcb = cb.getItemMeta();
		ArrayList<String> lcb = new ArrayList<>();
		lcb.add("§6Kosten:§2 3 Punkte" + System.lineSeparator() + "§2Klicken um zu kaufen");
		imcb.setLore(lcb);
		imcb.setDisplayName("§2Creeper-Ball");
		cb.setItemMeta(imcb);
		
		ItemStack rg = new ItemStack(Material.FIREWORK_CHARGE, 1);
		ItemMeta imrg = rg.getItemMeta();
		ArrayList<String> lrg = new ArrayList<>();
		lrg.add("§6Kosten:§2 4 Punkte" + System.lineSeparator() + "§2Klicken um zu kaufen");
		imrg.setLore(lrg);
		imrg.setDisplayName("§8Rauch-Granate");
		rg.setItemMeta(imrg);
		
		ItemStack ts = new ItemStack(Material.EYE_OF_ENDER, 1);
		ItemMeta imts = ts.getItemMeta();
		ArrayList<String> lts = new ArrayList<>();
		lts.add("§6Kosten:§2 5 Punkte" + System.lineSeparator() + "§2Klicken um zu kaufen");
		imts.setLore(lts);
		imts.setDisplayName("§9Name-Hider");
		ts.setItemMeta(imts);
		
		ItemStack sp = new ItemStack(Material.ENDER_PEARL, 1);
		ItemMeta imsp = sp.getItemMeta();
		ArrayList<String> lsp = new ArrayList<>();
		lsp.add("§6Kosten:§2 4 Punkte" + System.lineSeparator() + "§2Klicken um zu kaufen");
		imsp.setLore(lsp);
		imsp.setDisplayName("§cSwapper");
		sp.setItemMeta(imsp);
		
		tinv.setItem(3, hp);
		tinv.setItem(4, it);
		tinv.setItem(5, cb);
		tinv.setItem(12, rg);
		tinv.setItem(13, ts);
		tinv.setItem(14, ts);

		p.openInventory(tinv);
		
	}
	
	
	@EventHandler
	public void onCClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().equalsIgnoreCase("§6Shop §7| §6Punkte: " + dpunkte.get(p))) {
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§3Heal-Pack")) {
				e.setCancelled(true);
				if (dpunkte.get(p) > 1) {
					dpunkte.put(p, dpunkte.get(p) - 2);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.NETHER_STAR, 1, 0, "§3Heal-Pack", "§fHeilt per Rechtsklick"));
					p.closeInventory();
				} else {
					p.sendMessage(Data.ShopPrefix + "§cDu hast nicht genung Punkte!");
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dDNA-Scanner")) {
				e.setCancelled(true);
				if (dpunkte.get(p) > 4) {
					dpunkte.put(p, dpunkte.get(p) - 5);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.SNOW_BALL, 1, 0, "§dDNA-Scanner", "§fZeigt die Kooridnaten des Killers"));
					p.closeInventory();
				} else {
					p.sendMessage(Data.ShopPrefix + "§cDu hast nicht genung Punkte!");
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Super-Identifizierer")) {
				e.setCancelled(true);
				if (dpunkte.get(p) > 5) {
					dpunkte.put(p, dpunkte.get(p) - 6);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.BLAZE_ROD, 1, 0, "§2Super-Identifizierer", "§fIdentifiziert den Killer der Leiche"));
					p.closeInventory();
				} else {
					p.sendMessage(Data.ShopPrefix + "§cDu hast nicht genung Punkte!");
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aHealing-Station")) {
				e.setCancelled(true);
				if (dpunkte.get(p) > 3) {
					dpunkte.put(p, dpunkte.get(p) - 4);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.BEACON, 1, 0, "§aHealing-Station", "§fHelit alle Innocents/Detective, welche auf den Beacon drücken"));
					p.closeInventory();
				} else {
					p.sendMessage(Data.ShopPrefix + "§cDu hast nicht genung Punkte!");
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§9Traitor-Detector")) {
				e.setCancelled(true);
				if (dpunkte.get(p) > 2) {
					dpunkte.put(p, dpunkte.get(p) - 3);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.REDSTONE_LAMP_OFF, 1, 0, "§9Traitor-Detector", "§fSchlägt Alarm falls sich ein Traitor in der Nähe befindet"));
					p.closeInventory();
				} else {
					p.sendMessage(Data.ShopPrefix + "§cDu hast nicht genung Punkte!");
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cSwapper")) {
				e.setCancelled(true);
				if (dpunkte.get(p) > 3) {
					dpunkte.put(p, dpunkte.get(p) - 4);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.ENDER_PEARL, 1, 0, "§cSwapper", "§fTauscht die Position mit einem zufälligen Spieler"));
					p.closeInventory();
				} else {
					p.sendMessage(Data.ShopPrefix + "§cDu hast nicht genung Punkte!");
				}
			}
		}
		
	}
	
	@EventHandler
	public void onInvClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (Main.gs == GameState.INGAME){
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (p.getItemInHand().getType() == Material.STICK) e.setCancelled(true);
				else if (p.getItemInHand().getItemMeta().getDisplayName() == "§3Heal-Pack"){
					e.setCancelled(true);
					p.getItemInHand().setType(null);
					p.setHealth(p.getMaxHealth());
				} else if (p.getItemInHand().getItemMeta().getDisplayName() == "§dDNA-Scanner") {
					
				}
			}
		}
	}
}
