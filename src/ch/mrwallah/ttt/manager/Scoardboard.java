package ch.mrwallah.ttt.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import ch.mrwallah.ttt.game.MainListener;
import ch.mrwallah.ttt.game.Schutzzeit;
import ch.mrwallah.ttt.utils.Data;


public class Scoardboard {

	public static void sendScoreboard(Player p){
		
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard board = sm.getNewScoreboard();
		Objective obj = board.registerNewObjective("aaa", "bbb");
		
		obj.setDisplayName("§8» §4TTT §8«");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	
		obj.getScore("   ").setScore(9);
		obj.getScore("§6Map:").setScore(8);
		if (MainListener.isRunning == true){
			if (MainListener.cdz > 10){
				obj.getScore("§f  Voting..." ).setScore(7);
			} else {
				obj.getScore("§f  " + Data.cfg.getString("Maps." + TeleportManager.map) + " " ).setScore(7);
			}
		} else {
			obj.getScore("§f  Warte auf Voting..." ).setScore(7);
		}
		obj.getScore("          ").setScore(6);
		obj.getScore("§6Karma:").setScore(5);
		obj.getScore("§f  In der Beta nicht verfügbar!").setScore(4);
		obj.getScore("        ").setScore(3);
		obj.getScore("§6Bugs hier melden:").setScore(2);
		obj.getScore("§f  www.germanelitegaming.de").setScore(1);
		obj.getScore("                  ").setScore(0);
		

		p.setScoreboard(board);
	}
	

	public static void updateScoreboard(Player p) {
			sendScoreboard(p);
		}
	
	public static void sendScoreboardSZ(Player p){
		
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard board = sm.getNewScoreboard();
		Objective obj = board.registerNewObjective("aaa", "bbb");
		
		obj.setDisplayName("§8» §4TTT §8«");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	
		obj.getScore("   ").setScore(6);
		obj.getScore("§6Map:").setScore(5);
		obj.getScore("§f  " + Data.cfg.getString("Maps." + TeleportManager.map) + "   " ).setScore(4);
		obj.getScore("          ").setScore(3);
		obj.getScore("§6Rolle:").setScore(2);
		obj.getScore("§f  Bekannt in: 00:" + Schutzzeit.scdz).setScore(1);
		obj.getScore("        ").setScore(0);

		p.setScoreboard(board);
	}
	

	public static void updateScoreboardSZ(Player p) {
			sendScoreboardSZ(p);
		}
	
	public static void sendScoreboardIG(Player p){
		
		ScoreboardManager sm = Bukkit.getScoreboardManager();
		Scoreboard board = sm.getNewScoreboard();
		Objective obj = board.registerNewObjective("aaa", "bbb");
		
		obj.setDisplayName("§8» §4TTT §8«");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
	
		obj.getScore("   ").setScore(6);
		obj.getScore("§6Map:").setScore(5);
		obj.getScore("§f  " + Data.cfg.getString("Maps." + TeleportManager.map) + "       ").setScore(4);
		obj.getScore("          ").setScore(3);
		obj.getScore("§6Rolle:").setScore(2);
		if (TeamManager.traitors.contains(p)){
			obj.getScore("§f  §6Traitor").setScore(1);
			obj.getScore("        ").setScore(0);
			obj.getScore("§6Traitor-Punkte:").setScore(-1);
			obj.getScore("§f  " + Shop.tpunkte.get(p).intValue()).setScore(-2);
			obj.getScore("                  ").setScore(-3);
		} else if (TeamManager.detectives.contains(p)){
			obj.getScore("§f  §1Detective").setScore(1);
			obj.getScore("        ").setScore(0);
			obj.getScore("§6Detective-Punkte:").setScore(-1);
			obj.getScore("§f  " + Shop.dpunkte.get(p).intValue()).setScore(-2);
			obj.getScore("                  ").setScore(-3);
		} else if (TeamManager.innocents.contains(p)) {
			obj.getScore("§f  §2Innocent").setScore(1);
			obj.getScore("        ").setScore(0);
		} else if (!TeamManager.players.contains(p)){
			obj.getScore("§f  §7Spectator").setScore(1);
			obj.getScore("        ").setScore(0);
		}
		
		

		p.setScoreboard(board);
	}
	

	public static void updateScoreboardIG(Player p) {
			sendScoreboardIG(p);
		}
	
	
	
}
