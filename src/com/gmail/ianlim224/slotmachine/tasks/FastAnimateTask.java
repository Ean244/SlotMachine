package com.gmail.ianlim224.slotmachine.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.handlers.RouletteGui;

public class FastAnimateTask extends BukkitRunnable {
	private final RouletteGui gui;
	private final long startTime;
	private final long endTime;
	private final SlotMachine plugin;

	public FastAnimateTask(RouletteGui gui, long period, SlotMachine plugin) {
		this.gui = gui;
		this.startTime = System.currentTimeMillis();
		this.endTime = startTime + period;
		this.plugin = plugin;
	}

	@Override
	public void run() {
		gui.getAnimator().next();

		if (System.currentTimeMillis() >= endTime) {
			this.cancel();
			new SlowAnimateTask(gui, 4500, plugin).runTaskTimer(plugin, 5, 8);
		}
	}
}
