package ch.mrwallah.ttt.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.manager.Scoardboard;
import ch.mrwallah.ttt.manager.TeamManager;
import ch.mrwallah.ttt.utils.Data;

public class Schutzzeit {
	
	public static int scd;
	public static int scdz;
	
	public static void Schutz(){
			
			scdz = 31;	
	
			scd = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainListener.pl, new Runnable() {
				@Override
				public void run() {
					scdz--;
					for (Player all : Bukkit.getOnlinePlayers()){
						Scoardboard.sendScoreboardSZ(all);
					}
					if (scdz == 30){
						Bukkit.broadcastMessage(Data.Prefix + "§7Die Schutzzeit endet in §6" + scdz + " §7Sekunden!");
					}
					if (scdz == 20){
						Bukkit.broadcastMessage(Data.Prefix + "§7Die Schutzzeit endet in §6" + scdz + " §7Sekunden!");
					}
					if (scdz == 10){
						Bukkit.broadcastMessage(Data.Prefix + "§7Die Schutzzeit endet in §6" + scdz + " §7Sekunden!");
					}
					if (scdz < 6 && scdz > 1){
						Bukkit.broadcastMessage(Data.Prefix + "§7Die Schutzzeit endet in §6" + scdz + " §7Sekunden!");
					}
					if (scdz == 1){
						Bukkit.broadcastMessage(Data.Prefix + "§7Die Schutzzeit endet in §6einer §7Sekunde!");
						
						Bukkit.broadcastMessage(Data.Prefix + "§7Die Schutzzeiz ist beendet!");
						Main.gs = GameState.INGAME;
						Bukkit.getScheduler().cancelTask(scd);
						for(Player player : Bukkit.getOnlinePlayers()) {
						TeamManager.players.add(player);
						Scoardboard.sendScoreboardIG(player);
						}
						TeamManager.setTeam();
					}
				}
			}, 20, 20);
			
		}
	
}
