package com.gmail.ianlim224.slotmachine.sounds;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {
	public static void playSoundCancel(Player player) {
		try {
		    player.playSound(player.getLocation(), Sound.valueOf("BLOCK_ANVIL_PLACE"), 1, 0);
		} catch(IllegalArgumentException e) {
			player.playSound(player.getLocation(), Sound.valueOf("ANVIL_LAND"), 1, -12);
		}
	}
	
	public static void playSoundWin(Player player) {
		try {
		    player.playSound(player.getLocation(), Sound.valueOf("ENTITY_PLAYER_LEVELUP"), 1, 0);
		} catch(IllegalArgumentException e) {
			player.playSound(player.getLocation(), Sound.valueOf("LEVEL_UP"), 1, 0);
		    return;
		}
	}
	
	public static void PlaySoundClick(Player player) {
		try {
			player.playSound(player.getLocation(), Sound.valueOf("UI_BUTTON_CLICK"), 1, 2);
		} catch (IllegalArgumentException e) {
			player.playSound(player.getLocation(), Sound.valueOf("CLICK"), 1, 2);
		}
	}
	
	public static void playSoundPop(Player player) {
		try {
			player.playSound(player.getLocation(), Sound.valueOf("ENTITY_ITEM_PICKUP"), 1, 2);
		} catch (IllegalArgumentException e) {
			player.playSound(player.getLocation(), Sound.valueOf("ITEM_PICKUP"), 1, 2);
		}
	}
}
