package com.gmail.ianlim224.slotmachine.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.utils.ItemBuilder;

public class HelpMaterial {
	private ItemStack material;
	
	public HelpMaterial(int i, SlotMachine plugin) {
		if(i > 3) {
			return;
		}
		
		ConfigurationSection multiSection = plugin.getConfig().getConfigurationSection("multiplier");
		
		material = new ItemBuilder(new ItemStack(Material
				.valueOf(plugin.getConfig().getConfigurationSection("material").getString("material" + i))))
				.setName(SlotMachine.formatChatColor("&d&lPrize : x" + multiSection.getInt("multiplier" + i)))
				.setLore(SlotMachine.formatChatColor("&7You win " + multiSection.getInt("multiplier"+ i) + " times the bet"))
				.toItemStack();
	}
	
	public ItemStack getMaterial() {
		return material;
	}
}
