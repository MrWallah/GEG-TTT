package ch.mrwallah.ttt.manager;

import java.io.IOException;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.ItemCreator;

public class SetupManager implements CommandExecutor, Listener{
	
	public String currentMapName;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("ttt")){
			if (sender instanceof Player){
				Player p = (Player)sender;
				if (p.hasPermission("ttt.setup")){
					if (args.length != 0){
						if (args[0].equalsIgnoreCase("setup")){
							if (args.length == 1){
								if (Main.gs == GameState.SETUP){
									Inventories.getLobbyItems(p);
									Main.gs = GameState.LOBBY;
									p.sendMessage(Data.Prefix + "§4Setup-Modus deaktiviert");
								} else if (Main.gs == GameState.LOBBY){
									Data.clearInv(p);
									p.setGameMode(GameMode.CREATIVE);
									p.sendMessage(Data.Prefix + "§2Setup-Modus aktiviert");
									Main.gs = GameState.SETUP;
								} else {
									p.sendMessage(Data.Prefix + "§cDas Spiel hat bereits angefangen! Du kannst nun nicht mehr in den Setup-Modus wechseln!");
								}
							}
						} else if (args[0].equalsIgnoreCase("removemap")){
							if (Main.gs == GameState.SETUP){
								if (args.length == 2){
									if (Data.configContainsMap(args[1].toString())){
										String MapName = args[1];
										int MapNr = Data.cfg.getInt(MapName + "MapNr");
										int maps = Data.cfg.getInt("Maps.Anzahl");
										int mnsmap = maps - 1;
										for (int i = 1; i < maps; i++) {
											if (i > mnsmap) {
												int im = i -1;
												String smap = Data.cfg.getString("Maps." + i);
												Data.cfg.set("Maps." + i, null);
												Data.cfg.set("Maps." + im, smap);
												Data.cfg.set(smap + ".MapNr", im);
											}
										}
										Data.cfg.set(MapName, null);
										Data.cfg.set("Maps." + MapNr, null);
										Data.cfg.set("Maps.Anzahl", mnsmap);
										try {
											Data.cfg.save(Data.file);
										} catch (IOException e) {
											e.printStackTrace();
										}
										p.sendMessage(Data.Prefix + "§2Es wurde erfolgreich die Map " + MapName + " mit der Nummer " + Data.cfg.getInt(MapName + "MapNr") + " entfernt!");
									} else {
										p.sendMessage(Data.Prefix + "§cEs existiert keine Map mit dem Namen " + args[1] + "!");
										p.sendMessage(Data.Prefix + "§cBekannte Maps: §f" + Data.keys.toString().replaceAll("\\[\\]", "") );
										Data.keys.clear();
									}
								} else {
								p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <removemap> <mapname>");
								}
							} else {
								p.sendMessage(Data.Prefix + "§cBitte benutze §6/ttt setup §cum den Setup-Modus zu aktivieren!");
							}
						} else if (args[0].equalsIgnoreCase("createmap")){
							if (Main.gs == GameState.SETUP){
								if (args.length == 3){
									if (p.getItemInHand().getType() != Material.AIR){
										if (!Data.isInt(args[1].toString())){
											if (!Data.configIGCContainsMap(args[1].toString())){
												int maps = Data.cfg.getInt("Maps.Anzahl");
												int nxtmap = maps + 1;
												String MapName = args[1];
												Data.cfg.set("Maps.Anzahl", nxtmap);
												Data.cfg.set("Maps." + nxtmap, MapName);
												Data.cfg.set(MapName + ".MapNr", nxtmap);
												Data.cfg.set(MapName + ".Item", p.getItemInHand().getTypeId());
												Data.cfg.set(MapName + ".Builder", args[2]);
												try {
												Data.cfg.save(Data.file);
												} catch (IOException e) {
													e.printStackTrace();
												}
												p.sendMessage(Data.Prefix + "§2Es wurde erfolgreich die Map " + MapName + " mit der Nummer " + nxtmap + " erstellt!");
											} else {
												p.sendMessage(Data.Prefix + "§cEs existiert bereits eine Map mit dem Namen " + args[1] + "!");
											}
										} else {
											p.sendMessage(Data.Prefix + "§cBitte gib einen Namen ein, nicht nur Zahlen.");
										}
									} else {
										p.sendMessage(Data.Prefix + "§cBitte halte ein Item in der Hand. Das Item wird später das Item für die Map im Voting sein.");
									}
								} else {
									p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <createmap> <mapname> <builder>");
								}
							} else {
									p.sendMessage(Data.Prefix + "§cBitte benutze §6/ttt setup §cum den Setup-Modus zu aktivieren!");
							}
						} else if (args[0].equalsIgnoreCase("setwarp")){			
							if (Main.gs == GameState.SETUP){
								if (args.length == 3){
									if (Data.isInt(args[2])){
										if (Data.configContainsMap(args[1].toString())){
											Location loc = p.getLocation();
											double x = loc.getX();
											double y = loc.getY();
											double z = loc.getZ();
											double yaw = loc.getYaw();
											String welt = loc.getWorld().getName();
											
											String MapName = args[1].toString();
											
											Data.cfg.set(MapName + "." + "Warps" + "." + args[2] + ".X", x);
											Data.cfg.set(MapName + "." + "Warps" + "." + args[2] + ".Y", y);
											Data.cfg.set(MapName + "." + "Warps" + "." + args[2] + ".Z", z);
											Data.cfg.set(MapName + "." + "Warps" + "." + args[2] + ".Yaw", yaw);
											Data.cfg.set(MapName + "." + "Warps" + "." + args[2] + ".Pitch", 0.0);
											Data.cfg.set(MapName + "." + "Warps" + "." + args[2] + ".Welt", welt);
											try {
												Data.cfg.save(Data.file);
											} catch (IOException e) {
												e.printStackTrace();
											}
											p.sendMessage(Data.Prefix + "§aWarp " + args[2] + " erfolgreich gesetzt!" );
										} else {
											p.sendMessage(Data.Prefix + "§cEs existiert keine Map mit dem Namen " + args[1] + "!");
											p.sendMessage(Data.Prefix + "§cBekannte Maps: §f" + Data.keys.toString().replaceAll("\\[\\]", "") );
											Data.keys.clear();
										}
									} else {
										p.sendMessage(Data.Prefix + "§cBitte gib bei der Warpnummer eine Zahl ein!");
									}
								} else {
									p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <setwarp> <mapname> <warpnummer>");
								}
							} else {
								p.sendMessage(Data.Prefix + "§cBitte benutze §6/ttt setup §cum den Setup-Modus zu aktivieren!");
							}
						} else if (args[0].equalsIgnoreCase("settester")){
							if (Main.gs == GameState.SETUP){	
								if (args.length == 3){
									if (Data.configContainsMap(args[1].toString())){
										int MapNr = Data.cfg.getInt(args[1] + "MapNr");
										String MapName = Data.cfg.getString("Maps." + MapNr);
										if (args[2].equalsIgnoreCase("inside")){
											Location loc = p.getLocation();
											double x = loc.getX();
											double y = loc.getY();
											double z = loc.getZ();
											double yaw = loc.getYaw();
											String welt = loc.getWorld().getName();
																												
											Data.cfg.set(MapName + ".Tester" + ".Inside" + ".X", x);
											Data.cfg.set(MapName + ".Tester" + ".Inside" + ".Y", y);
											Data.cfg.set(MapName + ".Tester" + ".Inside" + ".Z", z);
											Data.cfg.set(MapName + ".Tester" + ".Inside" + ".yaw", yaw);
											Data.cfg.set(MapName + ".Tester" + ".Inside" + ".Pitch", 0.0);
											Data.cfg.set(MapName + ".Tester" + ".Inside" + ".Welt", welt);
											try {
												Data.cfg.save(Data.file);
											} catch (IOException e) {
												e.printStackTrace();
											}
											p.sendMessage(Data.Prefix + "§aTester erfolgreich gesetzt! (inside)" );
										} else if (args[2].equalsIgnoreCase("outside")) {
											Location loc = p.getLocation();
											double x = loc.getX();
											double y = loc.getY();
											double z = loc.getZ();
											double yaw = loc.getYaw();
											String welt = loc.getWorld().getName();
											
											
											Data.cfg.set(MapName + ".Tester" + ".Outside" + ".X", x);
											Data.cfg.set(MapName + ".Tester" + ".Outside" + ".Y", y);
											Data.cfg.set(MapName + ".Tester" + ".Outside" + ".Z", z);
											Data.cfg.set(MapName + ".Tester" + ".Outside" + ".yaw", yaw);
											Data.cfg.set(MapName + ".Tester" + ".Outside" + ".Pitch", 0.0);
											Data.cfg.set(MapName + ".Tester" + ".Outside" + ".Welt", welt);
											try {
												Data.cfg.save(Data.file);
											} catch (IOException e) {
												e.printStackTrace();
											}
											p.sendMessage(Data.Prefix + "§aTester erfolgreich gesetzt! (outside)" );
										} else if (args[2].equalsIgnoreCase("setpiston")) {
											
											p.getInventory().addItem(ItemCreator.CreateItem(Material.DIAMOND_AXE, 1, 0, "§2TTT-Axt 1", "§fLinksklick auf Diamantblock"));
											p.getInventory().addItem(ItemCreator.CreateItem(Material.DIAMOND_AXE, 1, 0, "§2TTT-Axt 2", "§fLinksklick auf Diamantblock"));
											p.getInventory().addItem(ItemCreator.CreateItem(Material.DIAMOND_AXE, 1, 0, "§2TTT-Axt 3", "§fLinksklick auf Diamantblock"));
											
											currentMapName = MapName;
											
										} else if (args[2].equalsIgnoreCase("setbutton")) {
											
											p.getInventory().addItem(ItemCreator.CreateItem(Material.DIAMOND_AXE, 1, 0, "§2TTT-Axt Button", "§fLinksklick auf Tester-Button"));
											
											currentMapName = MapName;
											
										}
									} else {
										p.sendMessage(Data.Prefix + "§cEs existiert keine Map mit dem Namen " + args[1] + "!");
										p.sendMessage(Data.Prefix + "§cBekannte Maps: §f" + Data.keys.toString().replaceAll("\\[\\]", "") );
										Data.keys.clear();
									}									
								} else {
									p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <settester> <mapname> <inside/outside/setpiston/setbutton>");
								}
							} else {
								p.sendMessage(Data.Prefix + "§cBitte benutze §6/ttt setup §cum den Setup-Modus zu aktivieren!");
							}
						} else if (args[0].equalsIgnoreCase("setlobby")){			
							if (Main.gs == GameState.SETUP){
								if (args.length == 1){
									
									Location loc = p.getLocation();
									double x = loc.getX();
									double y = loc.getY();
									double z = loc.getZ();
									double yaw = loc.getYaw();
									String welt = loc.getWorld().getName();
									
									
									Data.cfg.set("Lobby" + ".X", x);
									Data.cfg.set("Lobby" +  ".Y", y);
									Data.cfg.set("Lobby" +  ".Z", z);
									Data.cfg.set("Lobby" +  ".Yaw", yaw);
									Data.cfg.set("Lobby" +  ".Pitch", 0.0);
									Data.cfg.set("Lobby" +  ".Welt", welt);
									try {
										Data.cfg.save(Data.file);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(Data.Prefix + "§aWartelobby erfolgreich gesetzt!" );
								} else {
									p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <setlobby>");
								}
							} else {
								p.sendMessage(Data.Prefix + "§cBitte benutze §6/ttt setup §cum den Setup-Modus zu aktivieren!");
							}
						} else if (args[0].equalsIgnoreCase("setspectator")){			
							if (Main.gs == GameState.SETUP){
								if (args.length == 1){
									if (Data.configContainsMap(args[1].toString())){
										Location loc = p.getLocation();
										double x = loc.getX();
										double y = loc.getY();
										double z = loc.getZ();
										double yaw = loc.getYaw();
										String welt = loc.getWorld().getName();
										
										String MapName = args[1].toString();
										
										Data.cfg.set(MapName + ".Spectator" + ".X", x);
										Data.cfg.set(MapName + ".Spectator" +  ".Y", y);
										Data.cfg.set(MapName + ".Spectator" +  ".Z", z);
										Data.cfg.set(MapName + ".Spectator" +  ".Yaw", yaw);
										Data.cfg.set(MapName + ".Spectator" +  ".Pitch", 0.0);
										Data.cfg.set(MapName + ".Spectator" +  ".Welt", welt);
										try {
											Data.cfg.save(Data.file);
										} catch (IOException e) {
											e.printStackTrace();
										}
										p.sendMessage(Data.Prefix + "§aSpectator-Spawn erfolgreich gesetzt!" );
									} else {
										p.sendMessage(Data.Prefix + "§cEs existiert keine Map mit dem Namen " + args[1] + "!");
										p.sendMessage(Data.Prefix + "§cBekannte Maps: §f" + Data.keys.toString().replaceAll("\\[\\]", "") );
										Data.keys.clear();
									}
									
								} else {
									p.sendMessage(Data.Prefix + "§cBitte benutze: §6/skywars <setspectator>");
								}
							} else {
								p.sendMessage(Data.Prefix + "§cBitte benutze §6/skywars setup §cum den Setup-Modus zu aktivieren!");
							}
						} else {
							p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <settester/setwarp/createmap/removemap/setlobby/setspectator>");
						}		
					} else {
						p.sendMessage(Data.Prefix + "§cBitte benutze: §6/ttt <settester/setwarp/createmap/removemap/setlobby/setspectator>");
					}
					} else {
					p.sendMessage(Data.NoPerm);
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onSetupAxe(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (Main.gs == GameState.SETUP) {
			if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (p.getItemInHand().getItemMeta().getDisplayName() == "§2TTT-Axt 1") {
					Block b = e.getClickedBlock();
					double x = b.getX();
					double y = b.getY();
					double z = b.getZ();

					Data.cfg.set(currentMapName + ".Tester" + ".Block1" + ".X", x);
					Data.cfg.set(currentMapName + ".Tester" + ".Block1" + ".Y", y);
					Data.cfg.set(currentMapName + ".Tester" + ".Block1" + ".Z", z);
					p.sendMessage(Data.Prefix + "§2Block 1 erfolgreich gesetzt");
				} else if (p.getItemInHand().getItemMeta().getDisplayName() == "§2TTT-Axt 2") {
					Block b = e.getClickedBlock();
					double x = b.getX();
					double y = b.getY();
					double z = b.getZ();

					Data.cfg.set(currentMapName + ".Tester" + ".Block2" + ".X", x);
					Data.cfg.set(currentMapName + ".Tester" + ".Block2" + ".Y", y);
					Data.cfg.set(currentMapName + ".Tester" + ".Block2" + ".Z", z);
					p.sendMessage(Data.Prefix + "§2Block 2 erfolgreich gesetzt");
				} else if (p.getItemInHand().getItemMeta().getDisplayName() == "§2TTT-Axt 3") {
					Block b = e.getClickedBlock();
					double x = b.getX();
					double y = b.getY();
					double z = b.getZ();

					Data.cfg.set(currentMapName + ".Tester" + ".Block3" + ".X", x);
					Data.cfg.set(currentMapName + ".Tester" + ".Block3" + ".Y", y);
					Data.cfg.set(currentMapName + ".Tester" + ".Block3" + ".Z", z);
					p.sendMessage(Data.Prefix + "§2Block 3 erfolgreich gesetzt");
				} else if (p.getItemInHand().getItemMeta().getDisplayName() == "§2TTT-Axt Button") {
					Block b = e.getClickedBlock();
					if (b.getType().equals(Material.STONE_BUTTON) || b.getType().equals(Material.WOOD_BUTTON)) {
						double x = b.getX();
						double y = b.getY();
						double z = b.getZ();

						Data.cfg.set(currentMapName + ".Tester" + ".Block2" + ".X", x);
						Data.cfg.set(currentMapName + ".Tester" + ".Block2" + ".Y", y);
						Data.cfg.set(currentMapName + ".Tester" + ".Block2" + ".Z", z);
						p.sendMessage(Data.Prefix + "§2Block 2 erfolgreich gesetzt");
					} else {
						p.sendMessage(Data.Prefix + "§cDer ausgeählte Block ist kein Button!");
					}
				}
			}
		}
	}
	
}
