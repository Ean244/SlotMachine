package com.gmail.ianlim224.slotmachine.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.ianlim224.slotmachine.holder.RouletteHolder;

public class RouletteListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getHolder() instanceof RouletteHolder) {
			event.setCancelled(true);
		}
	}
}
