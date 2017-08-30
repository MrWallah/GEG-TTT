package ch.mrwallah.ttt.utils;

import ch.mrwallah.ttt.game.GSending;
import ch.mrwallah.ttt.gamestates.GameState;
import ch.mrwallah.ttt.main.Main;
import ch.mrwallah.ttt.manager.TeamManager;

public class Windetector {

	public static WindetectorStates winner;
	
	public static void windetection() {
		if (TeamManager.traitors.size() < 2) {
			winner = WindetectorStates.INNOCENTS;
			Main.gs = GameState.ENDING;
			GSending.restartServer();
		} else if (TeamManager.innocents.size() < 2 && TeamManager.detectives.size() < 2) {
			winner = WindetectorStates.TRAITORS;
			Main.gs = GameState.ENDING;
			GSending.restartServer();
		} else {
			winner = WindetectorStates.NOTEAM;
		}
	}
	
}
