package ch.mrwallah.ttt.manager;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;


public class PlayerManager implements Listener {
	
		
	
		@EventHandler
		public void onDamage(EntityDamageEvent e){
			if (Main.gs == GameState.SCHUTZZEIT){
				if (e.getEntityType() == EntityType.PLAYER) {
					e.setCancelled(true);
					Player p = (Player) e.getEntity();
					p.setHealth(20);
				}	
			}
		}
	
		@EventHandler
		public void onFoodChange(FoodLevelChangeEvent e){
			e.setCancelled(true);
			Player p = (Player) e.getEntity();
			p.setFoodLevel(20);
			
		}
		
		@EventHandler
		public void onInv(InventoryOpenEvent e){
			Player p = (Player) e.getPlayer();
			if (Main.gs == GameState.INGAME || Main.gs == GameState.SCHUTZZEIT){
				if (e.getInventory() != p.getInventory() && !e.getInventory().getTitle().contains("Shop")){
					e.setCancelled(true);
				}
			}
			
		}
		
		@EventHandler
		public void onDrop(PlayerDropItemEvent e){
			Item drop = e.getItemDrop();
			if (drop.getItemStack().getType() != Material.IRON_SWORD && drop.getItemStack().getType() != Material.STONE_SWORD && drop.getItemStack().getType() != Material.WOOD_SWORD
					&& drop.getItemStack().getType() != Material.BOW && drop.getItemStack().getType() != Material.ARROW) {
				e.setCancelled(true);
			}
			
		}
}

