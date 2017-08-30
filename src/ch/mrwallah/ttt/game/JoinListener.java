package ch.mrwallah.ttt.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.manager.Scoardboard;
import ch.mrwallah.ttt.utils.Data;

public class JoinListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if (Main.gs == GameState.LOBBY){
			Scoardboard.sendScoreboard(p);
			for (Player all : Bukkit.getOnlinePlayers()){
				if (Main.gs == GameState.LOBBY){
					Scoardboard.updateScoreboard(all);
				} else if (Main.gs == GameState.LOBBY){
					Scoardboard.updateScoreboardSZ(all);
				} else {
					Scoardboard.updateScoreboardIG(all);
				}
			}
			
			if (Data.cfg.getString("Lobby" + ".Welt") != null){
				double x = Data.cfg.getDouble("Lobby" + ".X");
				double y = Data.cfg.getDouble("Lobby" + ".Y");
				double z = Data.cfg.getDouble("Lobby" + ".Z");
				float yaw = (float) Data.cfg.getDouble("Lobby" + ".Yaw");
				float pitch = (float) Data.cfg.getDouble("Lobby" + ".Pitch");
				World w = Bukkit.getWorld(Data.cfg.getString("Lobby" + ".Welt"));
				
				Location loc = new Location(w, x, y, z, yaw, pitch);
				
				p.teleport(loc);
			}
			
		}
		
	}
					
}
