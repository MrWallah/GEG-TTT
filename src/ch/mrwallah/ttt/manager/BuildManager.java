package ch.mrwallah.ttt.manager;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildManager implements Listener{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE){
		e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if (e.getBlock().getType() != Material.BEACON && e.getBlock().getType() != Material.REDSTONE_LAMP_OFF) {
			if (e.getPlayer().getGameMode() != GameMode.CREATIVE){
				e.setCancelled(true);
			}
		}
		
	}
	
}
