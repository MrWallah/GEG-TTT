package ch.mrwallah.ttt.utils;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class Health {

	public static int playerHealth(Player kl) {
		return (int)StrictMath.ceil(damageable(kl).getHealth());
		
	}

	public static Damageable damageable(Player kl) {
		return (Damageable)kl;
	}
	
}
