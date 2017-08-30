package ch.mrwallah.ttt.utils;

import org.bukkit.entity.Player;

import ch.mrwallah.ttt.manager.TeamManager;

public class RemovePlayer {

	public static void removePlayer(Player p) {
		
		if (TeamManager.players.contains(p)) {
			TeamManager.players.remove(p);
			if (TeamManager.innocents.contains(p)) {
				TeamManager.traitors.remove(p);
				TeamManager.players.remove(p);
			} else if (TeamManager.innocents.contains(p)) {
				TeamManager.innocents.remove(p);
				TeamManager.players.remove(p);
			} else if (TeamManager.detectives.contains(p)){
				TeamManager.detectives.remove(p);
				TeamManager.players.remove(p);
			}
		}
		
		
	}
	
	
}
