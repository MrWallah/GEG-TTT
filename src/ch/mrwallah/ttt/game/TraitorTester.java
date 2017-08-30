package ch.mrwallah.ttt.game;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import ch.mrwallah.ttt.manager.TeamManager;
import ch.mrwallah.ttt.manager.TeleportManager;
import ch.mrwallah.ttt.utils.Data;

public class TraitorTester implements Listener{
	
	public TraitorTester(ch.mrwallah.ttt.main.Main Main){
		this.pl = Main;
	}
	
	private ch.mrwallah.ttt.main.Main pl;
	
	private boolean used = false;
	private boolean incd = false;
	public static ArrayList<Player> player = new ArrayList<>();
	public static ArrayList<Player> intester = new ArrayList<>();
	public static ArrayList<Player> innoticket = new ArrayList<>();
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		Player p = e.getPlayer();
			if (e.getClickedBlock().getType() == Material.STONE_BUTTON){
				Block b = e.getClickedBlock();
				String MapName = Data.cfg.getString("Maps." + TeleportManager.map);
				
				double bx = Data.cfg.getDouble(MapName + "." + "Tester.Inside.X");
				double by = Data.cfg.getDouble(MapName + "." + "Tester.Inside.Y");
				double bz = Data.cfg.getDouble(MapName + "." + "Tester.Inside.Z");
				String bwelt = Data.cfg.getString(MapName + "." + "Tester.Inside.Welt");
				Location buttonloc = new Location(Bukkit.getWorld(bwelt), bx, by, bz);
				
				if (b.getLocation() == buttonloc) {
					if (used == false){

						if (incd == false){

							
						double x = Data.cfg.getDouble(MapName + "." + "Tester.Inside.X");
						double y = Data.cfg.getDouble(MapName + "." + "Tester.Inside.Y");
						double z = Data.cfg.getDouble(MapName + "." + "Tester.Inside.Z");
						float yaw = (float) Data.cfg.getDouble(MapName + "." + "Tester.Inside.yaw");
						float pitch = (float) Data.cfg.getDouble(MapName + "." + "Tester.Inside.pitch");
						String welt = Data.cfg.getString(MapName + "." + "Tester.Inside.Welt");
						
						
						Location loc = new Location(Bukkit.getWorld(welt), x, y, z, yaw, pitch);
						
						for (Player all : Bukkit.getOnlinePlayers()) {
							intester.remove(p);
							if (intester.contains(all)) {
								double ox = Data.cfg.getDouble(MapName + "." + "Tester.Outside.X");
								double oy = Data.cfg.getDouble(MapName + "." + "Tester.Outside.Y");
								double oz = Data.cfg.getDouble(MapName + "." + "Tester.Outside.Z");
								float oyaw = (float) Data.cfg.getDouble(MapName + "." + "Tester.Outside.yaw");
								float opitch = (float) Data.cfg.getDouble(MapName + "." + "Tester.Outside.pitch");
								String owelt = Data.cfg.getString(MapName + "." + "Tester.Outside.Welt");
								Location oloc = new Location(Bukkit.getWorld(owelt), ox, oy, oz, oyaw, opitch);
								all.teleport(oloc);
							}
						}
						
						p.teleport(loc);
						player.add(p);
						used = true;
						Bukkit.broadcastMessage(Data.Prefix + "§7" + p.getName() + " hat den Traitor-Tester betreten!");
						Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
							
							@Override
							public void run() {
								used = false;
								incd = true;
								removeCD();
								if (TeamManager.traitors.contains(p)){
									p.sendMessage(Data.Prefix + "§cDu wurdest als §4Traitor §cerkannt!");
									p.getWorld().playEffect(p.getLocation(), Effect.INSTANT_SPELL, 5);
								} else {
									p.getWorld().playEffect(p.getLocation(), Effect.HAPPY_VILLAGER, 5);
									p.sendMessage(Data.Prefix + "§cDu wurdest als §4Traitor §cerkannt!");
								}
							}
						}, 100);
					} else {
						p.sendMessage(Data.Prefix + "§cDer Tester ist noch im Cooldown!");
					}
					} else {
						p.sendMessage(Data.Prefix + "§cDer Tester wird bereits genutzt!");
					}
				}
				
		} else {
			return;
		}
	}
	
	public void removeCD(){
		Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			
			@Override
			public void run() {
				incd = false;											
			}
		}, 100);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.IRON_BLOCK){
			if (p.getLocation().subtract(0, 2, 0).getBlock().getType() == Material.DIAMOND_BLOCK){
				intester.add(p);
			} else {
				if (intester.contains(p)) {
					intester.remove(p);
				}
			}
		} else if (p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.GLASS){
			if (p.getLocation().subtract(0, 2, 0).getBlock().getType() == Material.PISTON_BASE){
				intester.add(p);
			} else {
				if (intester.contains(p)) {
					intester.remove(p);
				}
			}
		} else {
			if (intester.contains(p)) {
				intester.remove(p);	
			}
		}
	}
	
}
