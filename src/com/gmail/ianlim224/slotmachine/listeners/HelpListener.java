package com.gmail.ianlim224.slotmachine.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.handlers.HelpGui;

public class HelpListener implements Listener {
	private final HelpGui gui;

	public HelpListener(SlotMachine plugin) {
		this.gui = new HelpGui(plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory() == null) {
			return;
		}
		
		if(event.getView().getTopInventory().equals(gui.getGui())) {
			event.setCancelled(true);
		}
	}
}
