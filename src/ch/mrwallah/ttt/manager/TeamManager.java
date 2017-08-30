package ch.mrwallah.ttt.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import ch.mrwallah.ttt.utils.Data;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;

public class TeamManager {


	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Player> detectives = new ArrayList<Player>();
	public static ArrayList<Player> traitors = new ArrayList<Player>();
	public static ArrayList<Player> innocents = new ArrayList<Player>();
	
	public static int counter = 0;

	
	public static void setTeam(){
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			ArrayList<Player> players = new ArrayList<>();
			players.add(all);
			Collections.shuffle(players);
			for(int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				if (detectives.size() < 2) {
					detectives.add(p);
					p.sendMessage(Data.Prefix + "§6Du bist ein §1Detective§6! Finde und töte durch geschicktes Beobachten alle Traitors");
					p.sendMessage(Data.Prefix + "§6Mit /shop öffnest du den Shop");
					Shop.dpunkte.put(p, 0);
					Inventories.getDetectivItems(p);
				} else if (traitors.size() < 3) {
					colorNameT(p);
					traitors.add(p);
					p.sendMessage(Data.Prefix + "§6Du bist ein §4Traitor§6! Schalte möglichst unauffälig alle Detectives und Innocents aus");
					p.sendMessage(Data.Prefix + "§6Mit /shop öffnest du den Shop");
					Shop.tpunkte.put(p, 0);
				} else {
					innocents.add(p);
					p.sendMessage(Data.Prefix + "§6Du bist ein §2Innocent§6! Finde und töte durch geschicktes Beobachten alle Traitors");
				} 
			}
			
	}
		
		
		
		
		
		for (Player all : Bukkit.getOnlinePlayers()){
			Scoardboard.sendScoreboardIG(all);
		}
		
	}
	
	public static void colorNameT(Player p){
	
		String realName = p.getName();
		EntityPlayer ep = ((CraftPlayer) p).getHandle();
		ep.displayName = ChatColor.RED + realName;
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(ep);
		try{
			Field field = packet.getClass().getDeclaredField("b");
			field.setAccessible(true);
			Object gameProfile = field.get(packet);
			Field name = gameProfile.getClass().getDeclaredField("name");
			name.setAccessible(true);
			name.set(gameProfile, ep);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		for(Player p2 : Bukkit.getOnlinePlayers()){
			if(p2 != p){
				if (traitors.contains(p2)) {
					((CraftPlayer)p2).getHandle().playerConnection.sendPacket(packet);
				}
			}
		}
	
	}
	
	public static void colorNameD(Player p){
		
		String realName = p.getName();
		EntityPlayer ep = ((CraftPlayer) p).getHandle();
		ep.displayName = ChatColor.DARK_BLUE + realName;
		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(ep);
		try{
			Field field = packet.getClass().getDeclaredField("b");
			field.setAccessible(true);
			Object gameProfile = field.get(packet);
			Field name = gameProfile.getClass().getDeclaredField("name");
			name.setAccessible(true);
			name.set(gameProfile, ep);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		for(Player p2 : Bukkit.getOnlinePlayers()){
			if(p2 != p){
				((CraftPlayer)p2).getHandle().playerConnection.sendPacket(packet);
			}
		}
	
	}
	
}
