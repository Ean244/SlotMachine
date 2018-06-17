package com.gmail.ianlim224.slotmachine.handlers;

import org.bukkit.inventory.Inventory;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.data.ItemGrabber;
import com.gmail.ianlim224.slotmachine.sounds.Sounds;
import com.gmail.ianlim224.slotmachine.utils.GlassPane;
import com.gmail.ianlim224.slotmachine.utils.RandNumGenerator;

public class RouletteAnimator {
	private final RouletteGui gui;
	private final Inventory inv;
	private final SlotMachine plugin;
	private final ItemGrabber grabber;
	private final RandNumGenerator generator;
	
	public RouletteAnimator(RouletteGui gui, SlotMachine plugin) {
		this.gui = gui;
		this.inv = gui.getGui();
		this.plugin = plugin;
		this.generator = new RandNumGenerator();
		this.grabber = ItemGrabber.getInstance(plugin);
	}
	
	public void next() {
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, GlassPane.getRandom());
		}

		inv.setItem(11, grabber.getLeftArrow());
		inv.setItem(15, grabber.getRightArrow());

		for (int i = 12; i < 15; i++) {
			inv.setItem(i, grabber.getMaterials()[generator.next()]);
		}

		if (plugin.getConfig().getBoolean("UI_CLICK")) {
			Sounds.PlaySoundClick(gui.getPlayer());
		}

		if (plugin.getConfig().getBoolean("POP_SOUND")) {
			Sounds.playSoundPop(gui.getPlayer());
		}
	}
	
	public Inventory getInv() {
		return inv;
	}
	
	public RandNumGenerator getRandNumGen() {
		return generator;
	}
}
