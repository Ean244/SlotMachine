package com.gmail.ianlim224.slotmachine.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public enum GlassPane {
	ORANGE(1), MAGNETA(2), LIGHT_BLUE(3), YELLOW(4), LIME(5),
	PINK(6), CYAN(9), PURPLE(10), BLUE(11), GREEN(13), RED(14);
	
	private short s;
	
	private GlassPane(int i) {
		this.s = (short) i;
	}
	
	public ItemStack toItemStack() {
		return new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) s))
				.setName(" ").toItemStack();
	}
	
	public static ItemStack getRandom() {
		return GlassPane.values()[SlotMachine.getrandom(0, GlassPane.values().length - 1)].toItemStack();
	}
}
