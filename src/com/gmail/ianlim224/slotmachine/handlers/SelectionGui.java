package com.gmail.ianlim224.slotmachine.handlers;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.data.ItemGrabber;
import com.gmail.ianlim224.slotmachine.data.Messages;
import com.gmail.ianlim224.slotmachine.data.MoneyCounter;
import com.gmail.ianlim224.slotmachine.holder.SelectionHolder;
import com.gmail.ianlim224.slotmachine.items.InventoryItems;
import com.gmail.ianlim224.slotmachine.utils.ItemBuilder;

public class SelectionGui {
	private final Player player;
	private final Inventory gui;
	private final MoneyCounter counter;
	private final ItemGrabber grabber;
	private final SlotMachine plugin;

	public static final DecimalFormat DECI_FORMAT = new DecimalFormat("#,###,##0.00");

	public SelectionGui(Player player, SlotMachine plugin) {
		this.player = player;
		this.gui = Bukkit.createInventory(new SelectionHolder(), 27,
				SlotMachine.formatChatColor(plugin.getConfig().getString("select_menu_name")));
		this.grabber = ItemGrabber.getInstance(plugin);
		this.counter = new MoneyCounter();
		this.plugin = plugin;
		loadGui();
	}

	public void loadGui() {
		this.gui.setItem(11, grabber.getRemoveMoney());
		this.gui.setItem(15, grabber.getAddMoney());
		this.gui.setItem(13, grabber.getPaperButton());
		this.gui.setItem(22, grabber.getCancelButton());
	}

	public void openGui() {
		this.player.openInventory(gui);
	}

	public void resetPaper() {
		ItemStack paper = new ItemBuilder(Material.valueOf(InventoryItems.CONFIRM_BET_MATERIAL.getConfigValue()))
				.setName(InventoryItems.CONFIRM_BET_NAME.getConfigValue().replaceAll("%money%",
						Long.toString(counter.getMoney())))
				.setLore(InventoryItems.CONFIRM_BET_LORE.getConfigValue()).toItemStack();
		gui.setItem(13, paper);
	}

	public void addToRoulette() {
		player.closeInventory();
		SlotMachine.takeMoney(counter.getMoney(), player);
		player.sendMessage(SlotMachine.formatChatColor(
				Messages.PREFIX + (Messages.msg_money_taken.replaceAll("%money%", Long.toString(counter.getMoney()))
						.replaceAll("%balance%", DECI_FORMAT.format(SlotMachine.getBalance(player))))));
		RouletteManager manager = new RouletteManager(plugin);
		manager.addAndOpenRouletteGui(player, counter.getMoney());
	}

	public Player getPlayer() {
		return player;
	}

	public Inventory getGui() {
		return gui;
	}

	public MoneyCounter getMoneyCounter() {
		return counter;
	}
}
