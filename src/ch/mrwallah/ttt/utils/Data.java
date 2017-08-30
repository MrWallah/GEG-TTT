package ch.mrwallah.ttt.utils;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Data {
	
	public static String Prefix = "§8[§4TTT§8] ";
	public static String WarnPrefix = "§8[§4WARNUNG§8] ";
	public static String ShopPrefix = "§8[§4Shop§8] ";
	public static String Coinsprefix = "§f[§6Coins§f]§r ";
	public static String NoPerm = Prefix + "§cDeine Rechte reichen nicht aus, um diesen Befehl auszuführen";
	public static File file = new File ("plugins//TTT//spawns.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	public static File fileConfig = new File ("plugins//TTT//config.yml");
	public static YamlConfiguration maincfg = YamlConfiguration.loadConfiguration(fileConfig);
	public static int minplayers = maincfg.getInt("Lobby.MinSpieler");
	
	public static void clearInv(Player p){
		p.setHealth(20);
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setLevel(0);;
	}
	
	public static ArrayList<String> keys = new ArrayList<String>();

	public static boolean configContainsMap(String arg){
		keys.clear();
		 boolean boo = false;
	        keys.addAll(Data.cfg.getKeys(false));
	        keys.remove("Maps");
	        for(int i = 0; i < keys.size(); i++){
	            if(keys.get(i).equals(arg)){
	                boo = true;
	            } 
	        }
	        if(boo){
	            return true;
	        } else {
	        return false;
	        }
		}
	
	public static boolean configIGCContainsMap(String arg){
		keys.clear();
		 boolean boo = false;
	        ArrayList<String> keys = new ArrayList<String>();
	        keys.addAll(Data.cfg.getKeys(false));
	        for(int i = 0; i < keys.size(); i++){
	            if(keys.get(i).equalsIgnoreCase(arg)){
	                boo = true;
	            }
	        }
	        if(boo){
	            return true;
	        } else {
	        return false;
	        }
		}
	
	
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	
	
}
