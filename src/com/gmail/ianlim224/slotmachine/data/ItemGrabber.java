package com.gmail.ianlim224.slotmachine.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.items.InventoryItems;
import com.gmail.ianlim224.slotmachine.utils.GlassPane;
import com.gmail.ianlim224.slotmachine.utils.ItemBuilder;

public class ItemGrabber {
	private ItemStack addMoney;
	private ItemStack removeMoney;
	private ItemStack paperButton;
	private ItemStack cancelButton;
	private ItemStack leftArrow;
	private ItemStack rightArrow;
	private ItemStack materialOne;
	private ItemStack materialTwo;
	private ItemStack materialThree;
	private ItemStack[] materials;
	private ItemStack helpIntro;
	private ItemStack helpInstructions;
	private ItemStack helpMatOne;
	private ItemStack helpMatTwo;
	private ItemStack helpMatThree;
	private long buyPrice;
	private ItemStack helpCmd;
	
	private static ItemGrabber inst;
	
	public static ItemGrabber getInstance(SlotMachine plugin) {
		if(inst != null) {
			return inst;
		} else {
			inst = new ItemGrabber(plugin);
			return inst;
		}
	}

	private ItemGrabber(SlotMachine plugin) {
		try {
		FileConfiguration config;
		config = plugin.getConfig();

		ConfigurationSection materialSection = config.getConfigurationSection("material");

		HelpMaterial mat1 = new HelpMaterial(1, plugin);
		HelpMaterial mat2 = new HelpMaterial(2, plugin);
		HelpMaterial mat3 = new HelpMaterial(3, plugin);

		buyPrice = config.getLong("bet_price");
		setAddMoney(new ItemBuilder(GlassPane.GREEN.toItemStack())
				.setName(InventoryItems.ADD_MONEY_NAME.getConfigValue())
				.setLore(InventoryItems.ADD_MONEY_LORE.getConfigValue().replaceAll("%amount%", Long.toString(buyPrice)))
				.toItemStack());
		setRemoveMoney(new ItemBuilder(GlassPane.RED.toItemStack())
				.setName(InventoryItems.MINUS_MONEY_NAME.getConfigValue()).setLore(InventoryItems.MINUS_MONEY_LORE
						.getConfigValue().replaceAll("%amount%", Long.toString(buyPrice)))
				.toItemStack());
		setPaperButton(
				new ItemBuilder(new ItemStack(Material.valueOf(InventoryItems.CONFIRM_BET_MATERIAL.getConfigValue())))
						.setName(InventoryItems.CONFIRM_BET_NAME.getConfigValue().replaceAll("%money%",
								Long.toString(buyPrice)))
						.setLore(InventoryItems.CONFIRM_BET_LORE.getConfigValue()).toItemStack());
		setCancelButton(
				new ItemBuilder(new ItemStack(Material.valueOf(InventoryItems.CANCEL_BET_MATERIAL.getConfigValue())))
						.setName(InventoryItems.CANCEL_BET_NAME.getConfigValue())
						.setLore(InventoryItems.CANCEL_BET_LORE.getConfigValue()).toItemStack());
		setLeftArrow(new ItemBuilder(new ItemStack(Material.ARROW)).setName(SlotMachine.formatChatColor("&1&l--->"))
				.toItemStack());
		setRightArrow(new ItemBuilder(new ItemStack(Material.ARROW)).setName(SlotMachine.formatChatColor("&1&l<---"))
				.toItemStack());
		setMaterialOne(new ItemBuilder(new ItemStack(Material.valueOf(materialSection.getString("material1"))))
				.setName("").toItemStack());
		setMaterialTwo(new ItemBuilder(new ItemStack(Material.valueOf(materialSection.getString("material2"))))
				.setName("").toItemStack());
		setMaterialThree(new ItemBuilder(new ItemStack(Material.valueOf(materialSection.getString("material3"))))
				.setName("").toItemStack());
		setMaterials(new ItemStack[] { getMaterialOne(), getMaterialTwo(), getMaterialThree() });
		setHelpInstructions(new ItemBuilder(
				new ItemStack(Material.valueOf(InventoryItems.HELP_INSTRUCTIONS_MATERIAL.getConfigValue())))
						.setName(InventoryItems.HELP_INSTRUCTIONS_NAME.getConfigValue())
						.addLoreLine(InventoryItems.HELP_INSTRUCTIONS_LORE_1.getConfigValue())
						.addLoreLine(InventoryItems.HELP_INSTRUCTIONS_LORE_2.getConfigValue()).toItemStack());
		setHelpIntro(new ItemBuilder(new ItemStack(Material.valueOf(InventoryItems.HELP_INTRO_MATERIAL.getConfigValue()))).setName(InventoryItems.HELP_INTRO_NAME.getConfigValue())
				.addLoreLine(InventoryItems.HELP_INTRO_LORE_1.getConfigValue())
				.addLoreLine(InventoryItems.HELP_INTRO_LORE_2.getConfigValue())
				.toItemStack());
		setHelpMatOne(mat1.getMaterial());
		setHelpMatTwo(mat2.getMaterial());
		setHelpMatThree(mat3.getMaterial());
		setHelpCmd(new ItemBuilder(new ItemStack(Material.valueOf(InventoryItems.HELP_COMMAND_MATERIAL.getConfigValue()))).setName(InventoryItems.HELP_COMMAND_NAME.getConfigValue())
				.addLoreLine(InventoryItems.HELP_COMMAND_LORE_1.getConfigValue())
				.addLoreLine(InventoryItems.HELP_COMMAND_LORE_2.getConfigValue())
				.addLoreLine(InventoryItems.HELP_COMMAND_LORE_3.getConfigValue())
				.toItemStack());
		} catch(Exception e) {
			System.out.println("---------------------------------");
			plugin.getLogger().warning("An error occured when loading items.yml, resetting to default...");
			plugin.getLogger().warning("Please make sure you input correct material type");
			System.out.println("---------------------------------");
			for(InventoryItems i: InventoryItems.values()) {
				InventoryItems.getFc().set(i.getPath(), i.getValue());
			}
			plugin.getItemsManager().saveConfig();
		}
	}

	public ItemStack getAddMoney() {
		return addMoney;
	}

	public void setAddMoney(ItemStack addMoney) {
		this.addMoney = addMoney;
	}

	public ItemStack getRemoveMoney() {
		return removeMoney;
	}

	public void setRemoveMoney(ItemStack removeMoney) {
		this.removeMoney = removeMoney;
	}

	public ItemStack getPaperButton() {
		return paperButton;
	}

	public void setPaperButton(ItemStack paperButton) {
		this.paperButton = paperButton;
	}

	public ItemStack getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(ItemStack cancelButton) {
		this.cancelButton = cancelButton;
	}

	public ItemStack getLeftArrow() {
		return leftArrow;
	}

	public void setLeftArrow(ItemStack leftArrow) {
		this.leftArrow = leftArrow;
	}

	public ItemStack getRightArrow() {
		return rightArrow;
	}

	public void setRightArrow(ItemStack rightArrow) {
		this.rightArrow = rightArrow;
	}

	public ItemStack getMaterialOne() {
		return materialOne;
	}

	public void setMaterialOne(ItemStack materialOne) {
		this.materialOne = materialOne;
	}

	public ItemStack getMaterialTwo() {
		return materialTwo;
	}

	public void setMaterialTwo(ItemStack materialTwo) {
		this.materialTwo = materialTwo;
	}

	public ItemStack getMaterialThree() {
		return materialThree;
	}

	public void setMaterialThree(ItemStack materialThree) {
		this.materialThree = materialThree;
	}

	public ItemStack[] getMaterials() {
		return materials;
	}

	public void setMaterials(ItemStack[] materials) {
		this.materials = materials;
	}

	public ItemStack getHelpIntro() {
		return helpIntro;
	}

	public void setHelpIntro(ItemStack helpIntro) {
		this.helpIntro = helpIntro;
	}

	public ItemStack getHelpInstructions() {
		return helpInstructions;
	}

	public void setHelpInstructions(ItemStack helpInstructions) {
		this.helpInstructions = helpInstructions;
	}

	public ItemStack getHelpMatOne() {
		return helpMatOne;
	}

	public void setHelpMatOne(ItemStack helpMatOne) {
		this.helpMatOne = helpMatOne;
	}

	public ItemStack getHelpMatTwo() {
		return helpMatTwo;
	}

	public void setHelpMatTwo(ItemStack helpMatTwo) {
		this.helpMatTwo = helpMatTwo;
	}

	public ItemStack getHelpMatThree() {
		return helpMatThree;
	}

	public void setHelpMatThree(ItemStack helpMatThree) {
		this.helpMatThree = helpMatThree;
	}
	
	public ItemStack getHelpCmd() {
		return helpCmd;
	}

	public void setHelpCmd(ItemStack helpCmd) {
		this.helpCmd = helpCmd;
	}
}