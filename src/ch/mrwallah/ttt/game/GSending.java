package ch.mrwallah.ttt.game;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ch.mrwallah.ttt.manager.Leichen;
import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.Title;
import ch.mrwallah.ttt.utils.Windetector;
import ch.mrwallah.ttt.utils.WindetectorStates;

public class GSending {

	
	public static int cdz;
	public static int cd;

	@SuppressWarnings("deprecation")
	public static void restartServer() {
		
		if (Windetector.winner == WindetectorStates.INNOCENTS) {
			Title title = new Title("§2Die Innocents haben gewonnen!","§6Alle Traitors wurden eliminiert");
			title.setTitleColor(ChatColor.GREEN);
			title.setSubtitleColor(ChatColor.DARK_GREEN);

			title.broadcast();
		} else if (Windetector.winner == WindetectorStates.TRAITORS) {
			Title title = new Title("§4Die Traitors haben gewonnen!","§2Alle Innocents und Detectives wurden eliminiert");
			title.setTitleColor(ChatColor.RED);
			title.setSubtitleColor(ChatColor.DARK_RED);

			title.broadcast();
		}
		
		
		cdz = 16;
		
		cd = Bukkit.getScheduler().scheduleSyncRepeatingTask(MainListener.pl, new BukkitRunnable() {
						
			@Override
			public void run() {
				cdz--;
				if (cdz == 15){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6" + cdz + " §7Sekunden neu");
				}
				if (cdz == 10){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6" + cdz + " §7Sekunden neu");
				}
				if (cdz == 5){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6" + cdz + " §7Sekunden neu");
				}
				if (cdz == 4){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6" + cdz + " §7Sekunden neu");
				}
				if (cdz == 3){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6" + cdz + " §7Sekunden neu");
				}
				if (cdz == 2){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6" + cdz + " §7Sekunden neu");
				}
				if (cdz == 1){
					Bukkit.broadcastMessage(Data.Prefix + "§7Der Server startet in §6einer §7Sekunde neu");
					Bukkit.getScheduler().cancelTask(cd);
					for (Player all : Bukkit.getOnlinePlayers()) {
						toServer(all, "Lobby-1");
					}
					Bukkit.getServer().reload();
					Leichen.removeCorpse();
				}
				
			}
		}, 20, 20);
	}
	
	 public static void toServer(Player player, String targetServer) {
	        ByteArrayOutputStream b = new ByteArrayOutputStream();
	        DataOutputStream out = new DataOutputStream(b);
	        try{
	            out.writeUTF("Connect");
	            out.writeUTF(targetServer);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        player.sendPluginMessage(MainListener.pl, "BungeeCord", b.toByteArray());
	    }
	 	
}
