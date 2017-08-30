package ch.mrwallah.ttt.main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ch.mrwallah.ttt.game.JoinListener;
import ch.mrwallah.ttt.game.MainListener;
import ch.mrwallah.ttt.game.TraitorTester;
import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.manager.BuildManager;
import ch.mrwallah.ttt.manager.ChestManager;
import ch.mrwallah.ttt.manager.KillManager;
import ch.mrwallah.ttt.manager.Leichen;
import ch.mrwallah.ttt.manager.LobbyManager;
import ch.mrwallah.ttt.manager.PlayerManager;
import ch.mrwallah.ttt.manager.Scoardboard;
import ch.mrwallah.ttt.manager.Shop;
import ch.mrwallah.ttt.manager.SetupManager;
import ch.mrwallah.ttt.manager.StartCMD;
import ch.mrwallah.ttt.manager.VoteManager;
import ch.mrwallah.ttt.manager.forcemapCMD;
import ch.mrwallah.ttt.mysql.MySQL;
import ch.mrwallah.ttt.utils.CoinsAPI;
import ch.mrwallah.ttt.utils.Data;


public class Main extends JavaPlugin {
	
	public static GameState gs;
	public static int cd;
	
	@SuppressWarnings("deprecation")
	public void onEnable(){
		gs = GameState.LOBBY;
		MySQL.connect();
		CoinsAPI.createTable();
		registerEvents();
		registerCMD();
		VoteManager.randomMaps();
		for (Player all : Bukkit.getOnlinePlayers()){
			Scoardboard.updateScoreboard(all);
		}
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		if (!Data.cfg.contains("Maps")){
			Data.cfg.set("Maps", 0);
			try {
				Data.cfg.save(Data.file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!Data.maincfg.contains("Lobby.MinSpieler")){
			Data.maincfg.set("Lobby.MinSpieler", 8);
			try {
				Data.maincfg.save(Data.fileConfig);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (MainListener.isRunning == false){
			if (Bukkit.getOnlinePlayers().size() > MainListener.pls){
					MainListener.startCountdown();
				}
			}
		
		for(Player all : Bukkit.getOnlinePlayers()){
		    if (Data.cfg.getString("Lobby" + ".Welt") != null){
				double x = Data.cfg.getDouble("Lobby" + ".X");
				double y = Data.cfg.getDouble("Lobby" + ".Y");
				double z = Data.cfg.getDouble("Lobby" + ".Z");
				float yaw = (float) Data.cfg.getDouble("Lobby" + ".Yaw");
				float pitch = (float) Data.cfg.getDouble("Lobby" + ".Pitch");
				World w = Bukkit.getWorld(Data.cfg.getString("Lobby" + ".Welt"));
				
				Location loc = new Location(w, x, y, z, yaw, pitch);
				
				all.teleport(loc);
			}
		}
		
		System.out.println("TTT-Plugin erfolgreich gestartet - by MrWallah");
		
		cd = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				if (gs == GameState.LOBBY){
				if (Bukkit.getOnlinePlayers().size() < Data.minplayers){
					Bukkit.broadcastMessage(Data.Prefix + "§6Warte auf Spieler....");
					}
				} else {
					Bukkit.getScheduler().cancelTask(cd);
				}
				
			}
		}, 20, 400);
		
	}
	
	
	public void onDisable() {
		
		System.out.println("TTT-Plugin erfolgreich beendet - by MrWallah");
			
	}
	
	public void registerEvents(){
		
		PluginManager pm =  Bukkit.getPluginManager();
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new LobbyManager(), this);
		pm.registerEvents(new BuildManager(), this);
		pm.registerEvents(new Leichen(), this);
		pm.registerEvents(new KillManager(), this);
		pm.registerEvents(new TraitorTester(this), this);
		pm.registerEvents(new SetupManager(), this);
		pm.registerEvents(new MainListener(this), this);
		pm.registerEvents(new Shop(), this);
		pm.registerEvents(new ChestManager(), this);
		pm.registerEvents(new PlayerManager(), this);
		pm.registerEvents(new VoteManager(), this);
	}
	
	public void registerCMD(){
		
		this.getCommand("ttt").setExecutor(new SetupManager());
		this.getCommand("start").setExecutor(new StartCMD());
		this.getCommand("forcemap").setExecutor(new forcemapCMD());
		this.getCommand("shop").setExecutor(new Shop());

	}
	

	
}
