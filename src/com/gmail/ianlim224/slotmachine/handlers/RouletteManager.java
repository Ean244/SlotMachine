package com.gmail.ianlim224.slotmachine.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class RouletteManager {
	private static Map<Player, RouletteGui> guis = new HashMap<>();
	private final SlotMachine plugin;
	
	public RouletteManager(SlotMachine plugin) {
		this.plugin = plugin;
	}
	
	public void addRouletteGui(Player player, double amount) {
		guis.put(player, new RouletteGui(amount, player, plugin));
	}
	
	public void addAndOpenRouletteGui(Player player, double amount) {
		addRouletteGui(player, amount);
		player.openInventory(guis.get(player).getGui());
	}
	
	public void removeRouletteGui(Player player) {
		guis.remove(player);
	}
	
	public RouletteGui getRoulette(Player player) {
		return guis.get(player);
	}
	
	public boolean hasPlayer(Player player) {
		return guis.containsKey(player);
	}
	
}
