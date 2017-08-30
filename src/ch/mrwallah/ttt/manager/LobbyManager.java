package ch.mrwallah.ttt.manager;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import ch.mrwallah.ttt.game.GSending;
import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.utils.Data;


public class LobbyManager implements Listener {
	
		@EventHandler
		public void onInvClick(InventoryClickEvent e){
			if (Main.gs == GameState.LOBBY){
				
				e.setCancelled(true);
			}
		}
		
	
		@EventHandler
		public void onDamage(EntityDamageEvent e){
			if (Main.gs == GameState.LOBBY){
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
		public void onClick(PlayerInteractEvent e){
			if (Main.gs == GameState.LOBBY){
			e.setCancelled(true);
			
			Player p = e.getPlayer();
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) ){
			if (p.getItemInHand().getItemMeta().getDisplayName() == "§3Pässe"){
				e.setCancelled(true);
				p.sendMessage(Data.Prefix + "§3Da wir noch in der Beta sind, sind die Pässe noch nicht verfügbar!");
				}
			}
			
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) ){
				if (p.getItemInHand().getItemMeta().getDisplayName() == "§2Zurück zur LOBBY"){
					GSending.toServer(p, "Lobby-1");
					}
				}
			}
		}
		
		@EventHandler
		public void onDrop(PlayerDropItemEvent e){
			if (Main.gs == GameState.LOBBY){
				e.setCancelled(true);
			}
		}
}

