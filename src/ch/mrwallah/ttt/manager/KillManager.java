package ch.mrwallah.ttt.manager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import ch.mrwallah.ttt.game.MainListener;
import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.RemovePlayer;
import ch.mrwallah.ttt.utils.Windetector;

public class KillManager implements Listener{
	
	public static HashMap<Player, Integer> warnings = new HashMap<>();
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.setDroppedExp(0);
		e.setKeepLevel(false);
		e.getDrops().clear();
		if (Main.gs == GameState.INGAME) {
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if (TeamManager.players.contains(p)) {
					if (e.getEntity().getKiller() instanceof Player) {
						Player k = e.getEntity().getKiller();
						if (TeamManager.traitors.contains(k)) {
							if (TeamManager.traitors.contains(p)) {
								RemovePlayer.removePlayer(p);
								k.sendMessage(Data.Prefix + "§cDu hast §4" + p.getDisplayName() + " §cgetötet. TÖTE KEINE TRAITORS!");
								warnings.put(k, warnings.get(k) + 1);
								k.sendMessage(Data.WarnPrefix + "§cVerwarnung §4" + warnings.get(k) + " §cvon §43" );
								if (warnings.get(k) == 3) {
									k.kickPlayer("§cRandom-Killing ist verboten!");
								} else {
									k.playSound(k.getLocation(), Sound.COW_HURT, 2, 2);
									if (Shop.tpunkte.get(k) > 0) {
										Shop.tpunkte.put(k, Shop.tpunkte.get(p) -1);
										k.sendMessage(Data.Prefix + "§c -1 Traitor-Punkt");
									}
								}
								for (Player all : Bukkit.getOnlinePlayers()) {
									Scoardboard.updateScoreboardIG(all);
								}
							} else if (TeamManager.detectives.contains(p)) {
								RemovePlayer.removePlayer(p);
								p.sendMessage(Data.Prefix + "§cDu hast §4" + p.getDisplayName() + " §cgetötet.");
								Shop.tpunkte.put(k, Shop.tpunkte.get(p) +2);
								for (Player all : Bukkit.getOnlinePlayers()) {
									Scoardboard.updateScoreboardIG(all);
								}
							} else if (TeamManager.innocents.contains(p)) {
								RemovePlayer.removePlayer(p);
								p.sendMessage(Data.Prefix + "§cDu hast §4" + p.getDisplayName() + " §cgetötet.");
								Shop.tpunkte.put(k, Shop.tpunkte.get(p) +1);
								for (Player all : Bukkit.getOnlinePlayers()) {
									Scoardboard.updateScoreboardIG(all);
								}
							}
						} else if (TeamManager.detectives.contains(k)) {
							if (TeamManager.detectives.contains(p)) {
								RemovePlayer.removePlayer(p);
								k.sendMessage(Data.Prefix + "§cDu hast §4" + p.getDisplayName() + " §cgetötet. TÖTE KEINE DETECTIVES!");
								warnings.put(k, warnings.get(k) + 1);
								k.sendMessage(Data.WarnPrefix + "§cVerwarnung §4" + warnings.get(k) + " §cvon §43" );
								if (warnings.get(k) == 3) {
									k.kickPlayer("§cRandom-Killing ist verboten!");
								} else {
									k.playSound(k.getLocation(), Sound.COW_HURT, 2, 2);
									if (Shop.dpunkte.get(k) > 0) {
										Shop.dpunkte.put(k, Shop.dpunkte.get(p) -1);
										k.sendMessage(Data.Prefix + "§c -1 Detective-Punkt");
									}
								}
								for (Player all : Bukkit.getOnlinePlayers()) {
									Scoardboard.updateScoreboardIG(all);
								}
							} else if (TeamManager.traitors.contains(p)) {
								RemovePlayer.removePlayer(p);
								p.sendMessage(Data.Prefix + "§cDu hast §4" + p.getDisplayName() + " §cgetötet.");
								Shop.tpunkte.put(k, Shop.tpunkte.get(k) +2);
								for (Player all : Bukkit.getOnlinePlayers()) {
									Scoardboard.updateScoreboardIG(all);
								}
							} else if (TeamManager.innocents.contains(p)) {
								RemovePlayer.removePlayer(p);
								k.sendMessage(Data.Prefix + "§cDu hast §4" + p.getDisplayName() + " §cgetötet. Töte keine Innovents!");
								warnings.put(k, warnings.get(k) + 1);
								k.sendMessage(Data.WarnPrefix + "§cVerwarnung §4" + warnings.get(k) + " §cvon §43" );
								if (warnings.get(k) == 3) {
									k.kickPlayer("§cRandom-Killing ist verboten!");
								} else {
									k.playSound(k.getLocation(), Sound.COW_HURT, 2, 2);
									if (Shop.dpunkte.get(k) > 0) {
										Shop.dpunkte.put(k, Shop.dpunkte.get(p) -1);
										k.sendMessage(Data.Prefix + "§c -1 Detective-Punkt");
									}
								}
								for (Player all : Bukkit.getOnlinePlayers()) {
									Scoardboard.updateScoreboardIG(all);
								}
							}
						}
						Leichen.summonCorpse(p.getLocation(), p.getWorld(), p, k);
						Windetector.windetection();
						} else {
						Windetector.windetection();
					}
					p.sendMessage(Data.Prefix + "§cDu bist gestorben!");
					Leichen.summonCorpse(p.getLocation(), p.getWorld(), p, p);
					TeamManager.players.remove(p);
				} 
			}
		}
		
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (Main.gs == GameState.INGAME) {
			Leichen.summonCorpse(p.getLocation(), p.getWorld(), p, p);
			Bukkit.getScheduler().scheduleAsyncDelayedTask(MainListener.pl, new Runnable() {
				
				@Override
				public void run() {
					Windetector.windetection();
					
				}
			}, 5);
		}
	}
	
	
}
