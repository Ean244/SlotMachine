package com.gmail.ianlim224.slotmachine.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class SelectionManager {
	private static Map<Player, SelectionGui> guis = new HashMap<>();
	private final SlotMachine plugin;

	public SelectionManager(SlotMachine plugin) {
		this.plugin = plugin;
	}

	public void addSelectionGui(Player player) {
		guis.put(player, new SelectionGui(player, plugin));
	}
	
	public void addAndOpenSelectionGui(Player player) {
		addSelectionGui(player);
		guis.get(player).openGui();
	}
	
	public SelectionGui getSelectionGui(Player player) {
		return guis.get(player);
	}

	public void removeSelectionGui(Player player) {
		guis.remove(player);
	}
	
	public boolean hasPlayer(Player player) {
		return guis.containsKey(player);
	}
}
