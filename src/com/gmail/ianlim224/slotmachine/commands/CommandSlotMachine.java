package com.gmail.ianlim224.slotmachine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.handlers.HelpGui;
import com.gmail.ianlim224.slotmachine.handlers.SelectionManager;
import com.gmail.ianlim224.slotmachine.mysql.MySQLManager;

public class CommandSlotMachine implements CommandExecutor {

	private HelpGui handler;
	private MySQLManager sql;
	private SlotMachine plugin;

	public CommandSlotMachine(SlotMachine plugin) {
		handler = new HelpGui(plugin);
		sql = MySQLManager.getInstance(plugin);
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("slotmachine.use")) {
			sender.sendMessage(SlotMachine.formatChatColor("&cYou have insufficient permissions!"));
			return true;
		}

		if (args.length > 1) {
			return false;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(SlotMachine.formatChatColor("&cOnly players can execute that command!"));
			return true;
		}

		Player p = (Player) sender;

		if (args.length < 1) {
			handler.openHelp((Player) sender);
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			handler.openHelp((Player) sender);
			return true;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (!(sender.hasPermission("slotmachine.admin"))) {
				sender.sendMessage(SlotMachine.formatChatColor("&cYou have insufficient permissions!"));
				return true;
			}
			plugin.reload();
			return true;
		}

		if (plugin.getConfig().getConfigurationSection("mysql").getBoolean("allow_mysql")) {
			if (args.length == 1 && args[0].equalsIgnoreCase("stats")) {
				p.sendMessage(SlotMachine.formatChatColor("&bStats for " + p.getName() + ":"));
				sql.printWins(p, p);
				sql.printLoses(p, p);
				return true;
			}
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
			Player player = (Player) sender;
			SelectionManager manager = new SelectionManager(plugin);
			manager.addAndOpenSelectionGui(player);
			return true;
		} else {
			return false;
		}
	}
}
