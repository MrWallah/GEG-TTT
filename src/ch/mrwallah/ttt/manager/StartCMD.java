package ch.mrwallah.ttt.manager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.mrwallah.ttt.game.MainListener;
import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.utils.Data;

public class StartCMD implements CommandExecutor{

	public static boolean used;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("start")){
			Player p = (Player) sender;
			if (p.hasPermission("minigames.start")){
				if (Main.gs == GameState.LOBBY){
				if (args.length == 0){
					if (Bukkit.getOnlinePlayers().size() > MainListener.pls){
						if (MainListener.cdz > 11) {
							if (used == false){

								MainListener.cdz = 12;
								used = true;
								
								Bukkit.broadcastMessage(Data.Prefix + "§2Der Countdown wurde verkürzt!");
								
							} else {
								p.sendMessage(Data.Prefix + "§cDer Countdown wurde bereits verkürzt!");
							}
						} else {
							p.sendMessage(Data.Prefix + "§cDas Spiel startet bereits in " + MainListener.cdz + " Sekunde(n)");
						}
					} else {
						p.sendMessage(Data.Prefix + "§cEs sind nicht genügen Spieler online um die Runde zu starten!");
					}
				} else {
					p.sendMessage(Data.Prefix + "§cBitte benutze: §6/start");
				}
				} else {
					p.sendMessage(Data.Prefix + "§cDu kannst diesen Befehl nur während der Wartezeit ausführen!");
				}
			} else {
				p.sendMessage(Data.NoPerm);
			}
		}
		return false;
	}

}
