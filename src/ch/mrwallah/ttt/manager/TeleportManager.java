package ch.mrwallah.ttt.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import ch.mrwallah.ttt.utils.Data;

public class TeleportManager {

	public static int map;
	
	static int votes1 = VoteManager.votes1;
	static int votes2 = VoteManager.votes2;
	static int votes3 = VoteManager.votes3;
	
	static int rndm1 = VoteManager.rndm1;
	static int rndm2 = VoteManager.rndm2;
	static int rndm3 = VoteManager.rndm3;
	
	
	public static void voting(){
		
		if (votes1 > votes2 && votes1 > votes3){
			map = rndm1;
		} else if (votes2 > votes1 && votes2 > votes3){
			map = rndm2;
		} else if (votes3 > votes1 && votes3 > votes1){
			map = rndm3;
		} else if (votes1 == votes2){
			map = rndm1;
		} else if (votes1 == votes3){
			map = rndm1;
		} else if (votes2 == votes3){
			map = rndm2;
		} else {
			map = rndm3;
		}
	}
	


	
	public static void teleportPlayers(Player p, int i){
		String MapName = Data.cfg.getString("Maps." + map);
		double x = Data.cfg.getDouble(MapName + "." + "Warps" + "." + i + ".X");
		double y = Data.cfg.getDouble(MapName + "." + "Warps" + "." + i + ".Y");
		double z = Data.cfg.getDouble(MapName + "." + "Warps" + "." + i + ".Z");
		float yaw = (float) Data.cfg.getDouble(MapName + "." + "Warps" + "." + i + ".Yaw");
		float pitch = (float) Data.cfg.getDouble(MapName + "." + "Warps" + "." + i + ".Pitch");
		World w = Bukkit.getWorld(Data.cfg.getString(MapName + "." + "Warps" + "." + i + ".Welt"));
		
		Data.clearInv(p);
		
		Location loc = new Location(w, x, y, z, yaw, pitch);
		
		p.teleport(loc);
		
	
		
		
	}

		
	
}
