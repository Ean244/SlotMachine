package com.gmail.ianlim224.slotmachine.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.handlers.RouletteManager;
import com.gmail.ianlim224.slotmachine.handlers.SelectionManager;

public class InventoryClose implements Listener {
	private SlotMachine plugin;

	public InventoryClose(SlotMachine plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClose(InventoryCloseEvent event) {
		if (!(event.getPlayer() instanceof Player))
			return;
		Player player = (Player) event.getPlayer();

		SelectionManager selectionManager = new SelectionManager(plugin);
		if (selectionManager.hasPlayer(player)) {
			selectionManager.removeSelectionGui(player);
		}

		RouletteManager rouletteManager = new RouletteManager(plugin);
		if (rouletteManager.hasPlayer(player)) {
			rouletteManager.removeRouletteGui(player);
		}
	}
}
