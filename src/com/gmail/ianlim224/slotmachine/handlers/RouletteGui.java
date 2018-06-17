package com.gmail.ianlim224.slotmachine.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.holder.RouletteHolder;
import com.gmail.ianlim224.slotmachine.tasks.FastAnimateTask;

public class RouletteGui {
	private final Inventory gui;
	private final Player player;
	private final SlotMachine plugin;
	private final BukkitTask task;
	private final RouletteAnimator animator;
	private final double amount;
	
	public RouletteGui(double amount, Player player, SlotMachine plugin) {
		this.player = player;
		this.plugin = plugin;
		this.amount = amount;
		this.gui = Bukkit.createInventory(new RouletteHolder(), 27,
				SlotMachine.formatChatColor(plugin.getConfig().getString("slotmachine_menu_name")));
		this.task = createTask();
		this.animator = new RouletteAnimator(this, plugin);
	}

	public BukkitTask createTask() {
		return new FastAnimateTask(this, 3500, plugin).runTaskTimer(plugin, 0, 5);
	}

	public void openGui() {
		player.openInventory(gui);
	}

	public Inventory getGui() {
		return gui;
	}

	public Player getPlayer() {
		return player;
	}

	public BukkitTask getTask() {
		return task;
	}

	public RouletteAnimator getAnimator() {
		return animator;
	}
	
	public double getAmount() {
		return amount;
	}
}
