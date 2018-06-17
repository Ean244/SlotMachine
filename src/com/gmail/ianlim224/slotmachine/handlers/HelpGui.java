package com.gmail.ianlim224.slotmachine.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.data.ItemGrabber;
import com.gmail.ianlim224.slotmachine.utils.GlassPane;

public class HelpGui {
	
	private ItemGrabber grabber;
	
	public HelpGui(SlotMachine plugin) {
		grabber = ItemGrabber.getInstance(plugin);
	}
	
	public static Inventory help = Bukkit.createInventory(null, 54, SlotMachine.formatChatColor(SlotMachine.plugin.getConfig().getString("help_menu_name")));
	
	private void init() {
		for(int i = 0 ; i < 54 ; i++){
			HelpGui.help.setItem(i, GlassPane.ORANGE.toItemStack());
		}
		help.setItem(11, grabber.getHelpInstructions());
		help.setItem(13, grabber.getHelpIntro());
		help.setItem(15, grabber.getHelpCmd());
		fill(27,30,grabber.getHelpMatOne());
		fill(39,42,grabber.getHelpMatTwo());
		fill(51,54,grabber.getHelpMatThree());
		
	}
	
	public void openHelp(Player p) {
		init();
		p.openInventory(help);
	}
	
	private static void fill(int start, int end, ItemStack its) {
		for(int i = start ; i < end; i++) {
			help.setItem(i, its);
		}
	}
	
	public Inventory getGui() {
		return help;
	}
}
