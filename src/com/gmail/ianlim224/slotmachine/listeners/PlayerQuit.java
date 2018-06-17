package com.gmail.ianlim224.slotmachine.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.handlers.RouletteManager;
import com.gmail.ianlim224.slotmachine.handlers.SelectionManager;

public class PlayerQuit implements Listener {
	private SlotMachine plugin;
	
	public PlayerQuit(SlotMachine plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		SelectionManager selectionManager = new SelectionManager(plugin);
		if(selectionManager.hasPlayer(player)) {
			selectionManager.removeSelectionGui(event.getPlayer());
		}
		
		RouletteManager rouletteManager = new RouletteManager(plugin);
		if(rouletteManager.hasPlayer(player)) {
			rouletteManager.removeRouletteGui(player);
		}
	}
}
