package com.gmail.ianlim224.slotmachine.data;

import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class Messages {
	private String prefix;
	
	public Messages() {
		prefix = SlotMachine.formatChatColor(msgf.getString("prefix"));
	}
	
	private static FileConfiguration msgf = SlotMachine.plugin.getMsgConfig();
	
	public static final String PREFIX = SlotMachine.formatChatColor(msgf.getString("prefix"));
	
	public final static String error = msgf.getString("error_msg");

	public final static String win = msgf.getString("win_msg");

	public final static String not_enough_money = msgf.getString("not_enough_money");

	public final static String lose = msgf.getString("lose_msg");
	public final static String msg_money_taken = msgf.getString("msg_money_taken");

	public String getPrefix() {
		return prefix;
	}
	
}
