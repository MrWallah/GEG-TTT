package ch.mrwallah.ttt.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.manager.Inventories;
import ch.mrwallah.ttt.manager.Scoardboard;
import ch.mrwallah.ttt.manager.StartCMD;
import ch.mrwallah.ttt.manager.TeamManager;
import ch.mrwallah.ttt.manager.TeleportManager;
import ch.mrwallah.ttt.manager.forcemapCMD;
import ch.mrwallah.ttt.utils.Data;

public class MainListener implements Listener{
	
	public static HashMap<UUID, Integer> players = new HashMap<>();

	public MainListener(ch.mrwallah.ttt.main.Main Main){
		pl = Main;
	}
	
	public static ch.mrwallah.ttt.main.Main pl;

	public static boolean isRunning;
	public static int cd;
	public static int cdz;
	public static int pls = Data.minplayers - 1;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if (Main.gs == GameState.LOBBY){
			Player p = e.getPlayer();
			Inventories.getLobbyItems(p);
			e.setJoinMessage(Data.Prefix + p.getName()+ " §7hat das Spiel betreten §7(§6" + Bukkit.getOnlinePlayers().size() +"§7/§6" + Bukkit.getMaxPlayers() + "§7)");
			if (isRunning == false){
			if (Bukkit.getOnlinePlayers().size() > pls){
				startCountdown();
				}
			}
			} else {
				e.setJoinMessage(null);
			}
					
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		if (Main.gs == GameState.LOBBY){
			Player p = e.getPlayer();
			int pls = Bukkit.getOnlinePlayers().size() - 1;
			e.setQuitMessage(Data.Prefix + p.getName()+ " §7hat das Spiel verlassen §7(§6" + pls +"§7/§6" + Bukkit.getMaxPlayers() + "§7)");
			if (Bukkit.getOnlinePlayers().size() == Data.minplayers){
			if (isRunning == true){
				Bukkit.getScheduler().cancelTask(cd);
				cdz = 61;
				isRunning = false;
				StartCMD.used = false;
				for (Player all : Bukkit.getOnlinePlayers()){
					Inventories.getLobbyItems(all);
					Scoardboard.updateScoreboard(all);
				}
			}
		}
			
		} else {
			e.setQuitMessage(null);
		}
				
	}
	
	public static void startCountdown() {
		cdz = 61;
		
		Bukkit.broadcastMessage(Data.Prefix + "§6Der Countdown hat begonnen!");
		isRunning = true;
		for (Player all : Bukkit.getOnlinePlayers()){
			Inventories.getLobbyItems(all);
		}
		cd = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
				@Override
				public void run() {
					if (Main.gs == GameState.LOBBY){
						if (isRunning == true){
							cdz--;
						} else {
							Bukkit.broadcastMessage(Data.Prefix + "§c ERROR: ML1"); //1
							Bukkit.getScheduler().cancelTask(cd);
						}
					} else {
						Bukkit.broadcastMessage(Data.Prefix + "§c ERROR: ML2"); //2
						Bukkit.getScheduler().cancelTask(cd);
					}
					for (Player all : Bukkit.getOnlinePlayers()){
						all.updateInventory();
						Scoardboard.updateScoreboard(all);
						all.setLevel(cdz);
					}
					if (cdz == 60){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
					}
					if (cdz == 50){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
					}
					if (cdz == 40){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
					}
					if (cdz == 30){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
					}
					if (cdz == 20){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
					}

					if (cdz == 11){
						if (forcemapCMD.used == false){
							TeleportManager.voting();
						}
					}
					if (cdz == 10){
						Bukkit.broadcastMessage(Data.Prefix + "§6Das Voting wurde beendet!");
						Bukkit.broadcastMessage(Data.Prefix + "§6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
						Bukkit.broadcastMessage(Data.Prefix + "        ");
						Bukkit.broadcastMessage(Data.Prefix + "§6  Map: §f" + Data.cfg.getString("Maps." + TeleportManager.map));
						Bukkit.broadcastMessage(Data.Prefix + "        ");
						Bukkit.broadcastMessage(Data.Prefix + "§6▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
						for (Player all : Bukkit.getOnlinePlayers()){
							all.playSound(all.getLocation(), Sound.ANVIL_LAND, 1, 1);
							Scoardboard.updateScoreboard(all);
							Inventories.getLobbyItems(all);
						}

					}

					if (cdz < 6 && cdz > 1){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6" + cdz + " §7Sekunden!");
					}
					if (cdz == 1){
						Bukkit.broadcastMessage(Data.Prefix + "§7Das Spiel startet in §6einer §7Sekunde!");
						for (Player all : Bukkit.getOnlinePlayers()) {
							TeamManager.players.add(all);							
							ArrayList<Player> players = new ArrayList<>();
							players.add(all);
							Collections.shuffle(players);
							TeamManager.setTeam();
					}
						Main.gs = GameState.SCHUTZZEIT;
						Schutzzeit.Schutz();
						Bukkit.getScheduler().cancelTask(cd);
						for (Player all : Bukkit.getOnlinePlayers()){
							Scoardboard.sendScoreboardSZ(all);
						}
					}

				}
				
			
			}, 20, 20);
		
	}
	
	
}
