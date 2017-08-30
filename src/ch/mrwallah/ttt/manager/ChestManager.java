package ch.mrwallah.ttt.manager;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.ItemCreator;

public class ChestManager implements Listener {
	
	@EventHandler
	public void onChest(PlayerInteractEvent e){
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if (e.getClickedBlock().getType() == Material.CHEST){
				Player p = e.getPlayer();
				if (Main.gs != GameState.ENDING && Main.gs != GameState.LOBBY && Main.gs != GameState.SETUP){
				e.setCancelled(true);
				if (!p.getInventory().contains(Material.BOW) && !p.getInventory().contains(Material.STONE_SWORD) && !p.getInventory().contains(Material.WOOD_SWORD)){
					ItemStack ri = null;
					
					Random r = new Random();
					int random = r.nextInt(3);
					
					switch (random) {
					case 0:
						ri = ItemCreator.CreateItem(Material.STONE_SWORD, 1, 0, "§9Steinschwert", " ");
						break;
					case 1:
						ri = ItemCreator.CreateItem(Material.BOW, 1, 0, "§9Bogen", " ");
						break;
					case 2:
						ri = ItemCreator.CreateItem(Material.WOOD_SWORD, 1, 0, "§9Holzschwert", " ");
						break;
					}
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ri);
					if (ri.getType() == Material.BOW){
						p.getInventory().addItem(ItemCreator.CreateItem(Material.ARROW, 32, 0, "§9Pfeil", " "));
					}
				} else if (!p.getInventory().contains(Material.BOW) && !p.getInventory().contains(Material.STONE_SWORD)){
					ItemStack ri = null;
					
					Random r = new Random();
					int random = r.nextInt(2);
					
					switch (random) {
					case 0:
						ri = ItemCreator.CreateItem(Material.STONE_SWORD, 1, 0, "§9Steinschwert", " ");
						break;
					case 1:
						ri = ItemCreator.CreateItem(Material.BOW, 1, 0, "§9Bogen", " ");
						break;
					}
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ri);
					if (ri.getType() == Material.BOW){
						p.getInventory().addItem(ItemCreator.CreateItem(Material.ARROW, 32, 0, "§9Pfeil", " "));
					}
					} else if (!p.getInventory().contains(Material.BOW) && !p.getInventory().contains(Material.WOOD_SWORD)){
					ItemStack ri = null;

					Random r = new Random();
					int random = r.nextInt(2);
					
					switch (random) {
					case 0:
						ri = ItemCreator.CreateItem(Material.WOOD_SWORD, 1, 0, "§9Holzschwert", " ");
						break;
					case 1:
						ri = ItemCreator.CreateItem(Material.BOW, 1, 0, "§9Bogen", " ");
						break;
					}
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ri);
					if (ri.getType() == Material.BOW){
						p.getInventory().addItem(ItemCreator.CreateItem(Material.ARROW, 32, 0, "§9Pfeil", " "));
					}
				} else if (!p.getInventory().contains(Material.STONE_SWORD) && !p.getInventory().contains(Material.WOOD_SWORD)){
					ItemStack ri = null;

					Random r = new Random();
					int random = r.nextInt(2);
					
					switch (random) {
					case 0:
						ri = ItemCreator.CreateItem(Material.WOOD_SWORD, 1, 0, "§9Holzschwert", " ");
						break;
					case 1:
						ri = ItemCreator.CreateItem(Material.STONE_SWORD, 1, 0, "§9Steinschwert", " ");
						break;
					}
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ri);
				} else if (!p.getInventory().contains(Material.STONE_SWORD)){
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.STONE_SWORD, 1, 0, "§9Steinschwert", " "));
				} else if (!p.getInventory().contains(Material.WOOD_SWORD)){
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.WOOD_SWORD, 1, 0, "§9Holzschwert", " "));
				} else if (!p.getInventory().contains(Material.BOW)){
					Block b = e.getClickedBlock();
					b.setType(Material.AIR);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.ARROW, 32, 0, "§9Pfeil", " "));
					p.getInventory().addItem(ItemCreator.CreateItem(Material.BOW, 1, 0, "§9Bogen", " "));
				}
				}
			} else if (e.getClickedBlock().getType() == Material.ENDER_CHEST){
				if (Main.gs == GameState.INGAME){
					Player p = e.getPlayer();
					Block b = e.getClickedBlock();
					e.setCancelled(true);
					p.getInventory().addItem(ItemCreator.CreateItem(Material.IRON_SWORD, 1, 0, "§3Eisenschwert", " "));
					b.setType(Material.AIR);
					b.getWorld().playEffect(b.getLocation(), Effect.ENDER_SIGNAL, 5);
				} else if (Main.gs == GameState.SCHUTZZEIT){
					Player p = e.getPlayer();
					e.setCancelled(true);
					p.sendMessage(Data.Prefix + "§cDu kannst die Enderchest erst nach der Schutzzeit öffnen!");
				}
			}
		}
	}
	
	 @EventHandler
	 public void onArrowHit(ProjectileHitEvent e){
		  if(e.getEntity() instanceof Arrow){
		    Arrow arrow = (Arrow) e.getEntity();
		    arrow.remove();
		  }
		}
}
