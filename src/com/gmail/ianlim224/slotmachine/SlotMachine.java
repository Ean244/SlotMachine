package com.gmail.ianlim224.slotmachine;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.ianlim224.slotmachine.commands.CommandSlotMachine;
import com.gmail.ianlim224.slotmachine.config.ConfigManager;
import com.gmail.ianlim224.slotmachine.handlers.CommandAliasesManager;
import com.gmail.ianlim224.slotmachine.items.InventoryItems;
import com.gmail.ianlim224.slotmachine.listeners.HelpListener;
import com.gmail.ianlim224.slotmachine.listeners.InventoryClose;
import com.gmail.ianlim224.slotmachine.listeners.JoinEvent;
import com.gmail.ianlim224.slotmachine.listeners.PlayerQuit;
import com.gmail.ianlim224.slotmachine.listeners.RouletteListener;
import com.gmail.ianlim224.slotmachine.listeners.SelectionListener;
import com.gmail.ianlim224.slotmachine.mysql.MySQLManager;
import com.gmail.ianlim224.slotmachine.utils.UpdateChecker;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class SlotMachine extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	public static Economy econ = null;
	public static SlotMachine plugin = null;
	private File configf;
	private File msgf;
	private FileConfiguration configc;
	private FileConfiguration msgc;
	public static long time;
	public static String format;
	public static long pot = 0;
	private String prefix, error;
	private int time_per_draw;
	private UpdateChecker checker;
	private MySQLManager sql;
	private ConfigManager items = new ConfigManager(this, "", "items.yml");

	@Override
	public void onEnable() {
		plugin = this;
		createFiles();
		loadItems();
		checker = new UpdateChecker(this);
		if (getConfig().getBoolean("auto_update") && checker.isConnected()) {
			if (checker.hasUpdate()) {
				System.out.println("---------------------------------");
				getLogger().info("There is a new version: v" + checker.getLatestVersion());
				getLogger().info("You are running v" + getDescription().getVersion());
				getLogger().info(
						"Please download the new version at https://www.spigotmc.org/resources/slotmachine.43765/");
				System.out.println("---------------------------------");
			} else {
				System.out.println("---------------------------------");
				getLogger().info("You are up to date!");
				System.out.println("---------------------------------");
			}
		}
		prefix = SlotMachine.formatChatColor(msgc.getString("prefix"));
		error = SlotMachine.formatChatColor(msgc.getString("error_msg"));
		registerCommands();
		registerListener();
		time = System.currentTimeMillis() + time_per_draw * 60 * 1000;

		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			getLogger().info("Hooked into vault!");
		}

		sql = MySQLManager.getInstance(this);
		sql.init();
	}

	private void loadItems() {
		InventoryItems.setFc(items.getConfig());

		items.getConfig().options()
				.header("Slot machine items configuration file - this file will load the items in guis"
						+ "\nWaring: if any errors are detected, this file will automatically reset to default");

		for (InventoryItems msg : InventoryItems.values()) {
			items.getConfig().addDefault(msg.getPath(), msg.getValue());
		}
		items.getConfig().options().copyDefaults(true);
		items.saveConfig();
	}

	private void createFiles() {
		configf = new File(getDataFolder(), "announcement.yml");
		msgf = new File(getDataFolder(), "messages.yml");

		if (!configf.exists()) {
			configf.getParentFile().mkdirs();
			saveResource("announcement.yml", false);
		}
		if (!msgf.exists()) {
			msgf.getParentFile().mkdirs();
			saveResource("messages.yml", false);
		}

		configc = new YamlConfiguration();
		msgc = new YamlConfiguration();
		try {
			configc.load(configf);
			msgc.load(msgf);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void registerCommands() {
		this.getCommand("slotmachine").setExecutor(new CommandSlotMachine(this));
		this.getCommand("slotmachine").setUsage(SlotMachine.formatChatColor(prefix + error));
		CommandAliasesManager aliasesManager = new CommandAliasesManager(this);
		aliasesManager.setAdditionalAliases(this.getCommand("slotmachine"), getConfig().getStringList("aliases"));
	}

	private void registerListener() {
		getServer().getPluginManager().registerEvents(new InventoryClose(this), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getServer().getPluginManager().registerEvents(new SelectionListener(this), this);
		getServer().getPluginManager().registerEvents(new RouletteListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
		getServer().getPluginManager().registerEvents(new HelpListener(this), this);
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Economy getEcononomy() {
		return econ;
	}

	public static String formatChatColor(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static boolean payMoney(double d, OfflinePlayer paramOfflinePlayer) {
		EconomyResponse localEconomyResponse = econ.depositPlayer(paramOfflinePlayer, d);
		return localEconomyResponse.transactionSuccess();
	}

	public static boolean takeMoney(long paramInt, OfflinePlayer paramOfflinePlayer) {
		EconomyResponse localEconomyResponse = econ.withdrawPlayer(paramOfflinePlayer, paramInt);
		return localEconomyResponse.transactionSuccess();
	}

	public static double getBalance(OfflinePlayer paramOfflinePlayer) {
		return econ.getBalance(paramOfflinePlayer);
	}

	public static int getrandom(int min, int max) {
		int exp = min + (int) (Math.random() * (max - min + 1));
		return exp;
	}

	public FileConfiguration getMsgConfig() {
		return msgc;
	}

	public static String getformatTime() {
		int hours = (int) ((time - System.currentTimeMillis()) / 3600000);
		int minutes = (int) ((time - System.currentTimeMillis()) % 3600000 / 60000);
		int seconds = (int) ((time - System.currentTimeMillis()) % 3600000 % 60000 / 1000);

		if (hours < 1 && minutes < 1) {
			return seconds + " seconds";
		} else if (hours < 1) {
			return minutes + " minutes, " + seconds + " seconds";
		} else {
			return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
		}
	}

	public ConfigManager getItemsManager() {
		return items;
	}

	public void reload() {
		reloadConfig();
		saveConfig();
		items.reloadConfig();
		items.saveConfig();
	}

	public static SlotMachine getInstance() {
		return plugin;
	}
}
