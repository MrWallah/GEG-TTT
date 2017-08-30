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

public class forcemapCMD implements CommandExecutor{

	public static boolean used;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("forcemap")){
			Player p = (Player) sender;
			if (p.hasPermission("minigames.forcemap")){
				if (Main.gs == GameState.LOBBY){
				if (args.length == 1){
					if (used == false){
						if (MainListener.cdz > 11) {
							if (!Data.isInt(args[0])){
								if (Data.configContainsMap(args[0].toString())){
									TeleportManager.map = Data.cfg.getInt(args[0] + ".MapNr");
									for (Player all : Bukkit.getOnlinePlayers()){
										Inventories.getLobbyItems(all);
									}
								} else {
									p.sendMessage(Data.Prefix + "§cEs existiert keine Map mit dem Namen " + args[0] + "!");
								}
							} else {
								p.sendMessage(Data.Prefix + "§cEs existiert keine Map mit dem Namen " + args[0] + "!");
							}
						} else {
							p.sendMessage(Data.Prefix + "§cDu kannst nun keine Map mehr setzten");
						}

					} else {
						p.sendMessage(Data.Prefix + "§cEs wurde bereits eine Map gewählt!");
					}
				} else {
					p.sendMessage(Data.Prefix + "§cBitte benutze: §6/forcemap <map>");
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
