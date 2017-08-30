package ch.mrwallah.ttt.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import ch.mrwallah.ttt.utils.Data;
import ch.mrwallah.ttt.utils.ItemCreator;

public class Leichen implements Listener{
	
	public static HashMap<ArmorStand, Player> killer = new HashMap<>();
	public static HashMap<ArmorStand, Player> dp = new HashMap<>();
	public static HashMap<ArmorStand, Boolean> c = new HashMap<>();
	public static ArrayList<ArmorStand> all = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void summonCorpse(Location loc, World world, Player p, Player k) {
		

		
		ArmorStand a = (ArmorStand) world.spawnCreature(loc, EntityType.ARMOR_STAND);
		a.setArms(true);
		a.setBasePlate(false);
		a.setBoots(ItemCreator.CreateLArmor(Material.LEATHER_BOOTS, 1, Color.GRAY));
		a.setLeggings(ItemCreator.CreateLArmor(Material.LEATHER_LEGGINGS, 1, Color.GRAY));
		a.setChestplate(ItemCreator.CreateLArmor(Material.LEATHER_CHESTPLATE, 1, Color.GRAY));
		a.setHelmet(ItemCreator.CreateHead(1, "MHF_Question"));
		a.setGravity(true);
		a.setRemoveWhenFarAway(false);
		a.setCustomNameVisible(true);
		a.setCustomName("§fUNBEKANNT");
		killer.put(a, k);
		dp.put(a, p);
		c.put(a, false);
		all.add(a);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
			Player p = e.getPlayer();
			if (p.getGameMode() != GameMode.CREATIVE) {
				e.setCancelled(true);
			}
			if (TeamManager.detectives.contains(p)) {
				if (p.getItemInHand().getType() == Material.STICK) {
					ArmorStand a = (ArmorStand) e.getRightClicked();
					if (c.containsKey(a)) {
						if (c.get(a) == false) {
							c.put(a, true);
							if (TeamManager.detectives.contains(dp.get(a))) {
								Bukkit.broadcastMessage(Data.Prefix + "§6Die Leiche von " + dp.get(a).getName() + " wurde gefunden! Er war ein §1Detective§6!");
								a.setHelmet(ItemCreator.CreateHead(1, dp.get(a).getName()));
								a.setChestplate(ItemCreator.CreateLArmor(Material.LEATHER_CHESTPLATE, 1, Color.BLUE));
								a.setCustomName(dp.get(a).getName());
							} else if (TeamManager.traitors.contains(dp.get(a))) {
								Bukkit.broadcastMessage(Data.Prefix + "§6Die Leiche von " + dp.get(a).getName() + " wurde gefunden! Er war ein §4Traitor§6!");
								a.setHelmet(ItemCreator.CreateHead(1, dp.get(a).getName()));
								a.setChestplate(ItemCreator.CreateLArmor(Material.LEATHER_CHESTPLATE, 1, Color.RED));
								a.setCustomName(dp.get(a).getName());
							} if (TeamManager.innocents.contains(dp.get(a))) {
								Bukkit.broadcastMessage(Data.Prefix + "§6Die Leiche von " + dp.get(a).getName() + " wurde gefunden! Er war ein §2Innocent§6!");
								a.setHelmet(ItemCreator.CreateHead(1, dp.get(a).getName()));
								a.setChestplate(ItemCreator.CreateLArmor(Material.LEATHER_CHESTPLATE, 1, Color.LIME));
								a.setCustomName(dp.get(a).getName());
							} 
						} else {
							p.sendMessage(Data.Prefix + "§cDie Leiche wurde bereits Identifiziert");
						}
					}					
				}
			}
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof ArmorStand) {
			e.setCancelled(true);
		}
	}
	
	public static void removeCorpse() {
		for (ArmorStand a : all) {
			a.remove();
		}
	}
	
}
