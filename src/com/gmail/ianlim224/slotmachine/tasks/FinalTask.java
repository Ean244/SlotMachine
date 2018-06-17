package com.gmail.ianlim224.slotmachine.tasks;

import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.data.ItemGrabber;
import com.gmail.ianlim224.slotmachine.data.Messages;
import com.gmail.ianlim224.slotmachine.handlers.RouletteGui;
import com.gmail.ianlim224.slotmachine.mysql.MySQLManager;
import com.gmail.ianlim224.slotmachine.sounds.Sounds;

public class FinalTask extends BukkitRunnable {
	private final RouletteGui gui;
	private final Inventory inv;
	private final SlotMachine plugin;
	private final ItemGrabber grabber;
	private final MySQLManager sql;

	public static final double WINNING_PROBABILITY;
	public static final double MATERIAL_ONE_PROB;
	public static final double MATERIAL_TWO_PROB;
	public static final double MATERIAL_THREE_PROB;
	private final static Random random = new Random();

	static {
		FileConfiguration config = SlotMachine.getInstance().getConfig();
		if (config.getDouble("item-probability.material1") + config.getDouble("item-probability.material2")
				+ config.getDouble("item-probability.material3") != 1) {
			Bukkit.getLogger().log(Level.WARNING, "Material probablilties should add up to be 1!");
			SlotMachine.getInstance().getConfig().set("item-probability.material1", 0.2);
			SlotMachine.getInstance().getConfig().set("item-probability.material2", 0.3);
			SlotMachine.getInstance().getConfig().set("item-probability.material3", 0.5);
		}

		if (config.getDouble("winning-probability") > 1) {
			Bukkit.getLogger().log(Level.WARNING, "Winning probablilty should not be greater than 1!");
			config.set("winning-probability", 0.5);
		}

		WINNING_PROBABILITY = config.getDouble("winning-probability");
		MATERIAL_ONE_PROB = config.getDouble("item-probability.material1");
		MATERIAL_TWO_PROB = config.getDouble("item-probability.material2");
		MATERIAL_THREE_PROB = config.getDouble("item-probability.material3");
	}

	public FinalTask(RouletteGui gui, SlotMachine plugin) {
		this.gui = gui;
		this.inv = gui.getGui();
		this.plugin = plugin;
		this.grabber = ItemGrabber.getInstance(plugin);
		this.sql = MySQLManager.getInstance(plugin);
	}

	@Override
	public void run() {
		gui.getAnimator().next();

		if (computeWin()) {
			int prizeNum = computePrize();
			for (int i = 12; i < 15; i++) {
				inv.setItem(i, grabber.getMaterials()[prizeNum - 1]);
			}
			int multiplier = plugin.getConfig().getInt("multiplier.multiplier" + prizeNum);
			gui.getPlayer().sendMessage(SlotMachine.formatChatColor(Messages.PREFIX
					+ Messages.win.replaceAll("%money%", Double.toString(multiplier * gui.getAmount()))));
			Sounds.playSoundWin(gui.getPlayer());
			SlotMachine.payMoney(gui.getAmount() * multiplier, gui.getPlayer());
			sql.addWins(gui.getPlayer());
		} else {
			if(isThreeInRow()) {
				gui.getAnimator().next();
			}
			gui.getPlayer().sendMessage(Messages.PREFIX + SlotMachine.formatChatColor(Messages.lose));
			Sounds.playSoundCancel(gui.getPlayer());
			sql.addLoses(gui.getPlayer());
		}
		this.cancel();
		new BukkitRunnable() {
			@Override
			public void run() {
				gui.getPlayer().closeInventory();
			}
		}.runTaskLater(plugin, 30);
	}

	private boolean computeWin() {
		return (random.nextDouble() <= WINNING_PROBABILITY);
	}

	private int computePrize() {
		double mat2 = MATERIAL_ONE_PROB + MATERIAL_TWO_PROB;

		double rand = random.nextDouble();
		if (rand <= MATERIAL_ONE_PROB) {
			return 1;
		} else if (rand <= mat2) {
			return 2;
		} else {
			return 3;
		}
	}
	
	private boolean isThreeInRow() {
		return (this.inv.getItem(12).equals(inv.getItem(13)) && this.inv.getItem(13).equals(this.inv.getItem(14)));
	}
}
