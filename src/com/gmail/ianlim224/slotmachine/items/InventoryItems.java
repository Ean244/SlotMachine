package com.gmail.ianlim224.slotmachine.items;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public enum InventoryItems {
	//selection menu
	ADD_MONEY_NAME("selection_menu.add_money_name","&a&lAdd"),
	ADD_MONEY_LORE("selection_menu.add_money_lore","&7Increase bet amount by %amount%"),
	MINUS_MONEY_NAME("selection_menu.minus_money_name","&c&lMinus"),
	MINUS_MONEY_LORE("selection_menu.minus_money_lore","&7Decrease bet amount by %amount%"),
	CONFIRM_BET_NAME("selection_menu.confirm_bet_name","&e&l$%money%"),
	CONFIRM_BET_LORE("selection_menu.confirm_bet_lore","&7Click me to start the bet"),
	CONFIRM_BET_MATERIAL("selection_menu.confirm_bet_material","PAPER"),
	CANCEL_BET_NAME("selection_menu.cancel_bet_name" , "&c&lCancel"),
	CANCEL_BET_LORE("selection_menu.cancel_bet_lore" , "&7Cancel the bet"),
	CANCEL_BET_MATERIAL("selection_menu.cancel_bet_material" , "BARRIER"),
	//help menu
	HELP_INSTRUCTIONS_NAME("help_menu.help_instructions_name" , "&e&lInstructions"),
	HELP_INSTRUCTIONS_LORE_1("help_menu.help_instructions_lore_1" , "&7This page shows instructions for the slot machine game"),
	HELP_INSTRUCTIONS_LORE_2("help_menu.help_instructions_lore_2" , "&7Do &e/sm start&7 to start the game"),
	HELP_INSTRUCTIONS_MATERIAL("help_menu.help_instructions_material" , "BOOK_AND_QUILL"),
	HELP_INTRO_NAME("help_menu.help_intro_name", "&4&lIntro"),
	HELP_INTRO_LORE_1("help_menu.help_intro_lore_1", "&7Prizes are shown below"),
	HELP_INTRO_LORE_2("help_menu.help_intro_lore_2", "&7You win only if you get 3 in a row"),
	HELP_INTRO_MATERIAL("help_menu.help_intro_material", "SIGN"),
	HELP_COMMAND_NAME("help_menu.help_command_name","&6&lLIST OF COMMANDS"),
	HELP_COMMAND_LORE_1("help_menu.help_command_lore_1","&3/sm- &amain command"),
	HELP_COMMAND_LORE_2("help_menu.help_command_lore_2","&3/sm help- &aopens the help menu"),
	HELP_COMMAND_LORE_3("help_menu.help_command_lore_3","&3/sm start- &aopens the slot machine menu"),
	HELP_COMMAND_MATERIAL("help_menu.help_command_material","BOOK");
	
	private String path;
	private String value;
	private static FileConfiguration fc;
	
	private InventoryItems(String path, String val) {
		this.path = path;
		value = val;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getValue() {
		return value;
	}
	
	public static void setFc(FileConfiguration newFc) {
		fc = newFc;
	}
	
	public String getConfigValue() {
		return SlotMachine.formatChatColor(fc.getString(this.path));
	}
	
	public static FileConfiguration getFc() {
		return fc;
	}
}
