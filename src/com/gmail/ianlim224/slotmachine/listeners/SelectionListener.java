package com.gmail.ianlim224.slotmachine.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.data.ItemGrabber;
import com.gmail.ianlim224.slotmachine.data.Messages;
import com.gmail.ianlim224.slotmachine.data.MoneyCounter;
import com.gmail.ianlim224.slotmachine.handlers.SelectionGui;
import com.gmail.ianlim224.slotmachine.handlers.SelectionManager;
import com.gmail.ianlim224.slotmachine.holder.SelectionHolder;
import com.gmail.ianlim224.slotmachine.sounds.Sounds;

public class SelectionListener implements Listener {
	private SlotMachine plugin;
	private ItemGrabber grabber;

	public SelectionListener(SlotMachine plugin) {
		this.plugin = plugin;
		grabber = ItemGrabber.getInstance(plugin);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();

		if (item == null)
			return;

		if (!(event.getWhoClicked() instanceof Player))
			return;

		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getHolder() instanceof SelectionHolder) {
			event.setCancelled(true);
			SelectionGui gui = new SelectionManager(plugin).getSelectionGui(player);
			MoneyCounter counter = gui.getMoneyCounter();

			if (item.equals(grabber.getAddMoney())) {
				if (counter.getMoney() + MoneyCounter.BUY_PRICE > MoneyCounter.MAX_BET)
					return;
				counter.addMoneyByPrice();
				gui.resetPaper();
				return;
			}

			if (item.equals(grabber.getRemoveMoney())) {
				if (counter.getMoney() - MoneyCounter.BUY_PRICE < 0)
					return;
				counter.removeMoneyByPrice();
				gui.resetPaper();
				return;
			}

			if (item.equals(grabber.getCancelButton())) {
				player.closeInventory();
				return;
			}
			
			if(item.getType() == Material.PAPER) {
				double balance = SlotMachine.getBalance(player);
				if (balance < counter.getMoney()) {
					player.closeInventory();
					Sounds.playSoundCancel(player);
					player.sendMessage(SlotMachine.formatChatColor(Messages.PREFIX + Messages.not_enough_money));
					return;
				} else {
					gui.addToRoulette();
					return;
				}
			}
		}
	}
}
