package com.gmail.ianlim224.slotmachine.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class MySQLManager {
	private static MySQLManager instance;
	private SlotMachine plugin;
	private MySQL sql;
	private boolean isEnabled;

	private MySQLManager(SlotMachine plugin) {
		this.plugin = plugin;
		isEnabled = plugin.getConfig().getConfigurationSection("mysql").getBoolean("allow_mysql");
	}

	public static MySQLManager getInstance(SlotMachine plugin) {
		if (instance != null) {
			return instance;
		} else {
			instance = new MySQLManager(plugin);
		}
		return instance;
	}

	public void init() {
		if (!plugin.getConfig().getConfigurationSection("mysql").getBoolean("allow_mysql")) {
			return;
		}
		
		plugin.getLogger().info("Connecting to MySQL...");
		try {
			sql = new MySQL(plugin);
		} catch (Exception e) {
			plugin.getLogger().info("Cannot connect to mysql, disabling it in announcement...");
			plugin.getConfig().getConfigurationSection("mysql").set("allow_mysql", false);
			plugin.saveConfig();
			sql.setConnected(false);
			return;
		}
		try {
			PreparedStatement createStatement = sql.getConnection()
					.prepareStatement("create table if not exists player_data(uuid char(36), wins int, loses int)");

			createStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (sql.isConnected()) {
			plugin.getLogger().info("MySQL successfully connected!");
		}
	}

	public void addPlayer(final Player p) {
		if (!isEnabled) {
			return;
		}
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					PreparedStatement s = sql.getConnection()
							.prepareStatement("select * from player_data where uuid = \"" + p.getUniqueId() + "\"");
					s.execute();
					ResultSet r = s.getResultSet();
					if (!r.isBeforeFirst()) {
						s = sql.getConnection().prepareStatement("insert into player_data (uuid,wins,loses) values (\""
								+ "" + p.getUniqueId() + "\", 0,0)");
						s.execute();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	public void addWins(final Player p) {
		if (!isEnabled) {
			return;
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					PreparedStatement s = sql.getConnection().prepareStatement(
							"update player_data set wins = wins + 1 where uuid = \"" + p.getUniqueId() + "\"");
					s.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	public void addLoses(final Player p) {
		if (!isEnabled) {
			return;
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					PreparedStatement s = sql.getConnection().prepareStatement(
							"update player_data set loses = loses + 1 where uuid = \"" + p.getUniqueId() + "\"");
					s.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	public void printWins(final Player req, final Player target) {
		if (!isEnabled) {
			return;
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					PreparedStatement s = sql.getConnection().prepareStatement(
							"select wins from player_data where uuid = \"" + target.getUniqueId() + "\"");
					s.execute();
					ResultSet set = s.getResultSet();
					while (set.next()) {
						req.sendMessage(SlotMachine.formatChatColor("&eWins: &6" + set.getInt("wins")));
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	public void printLoses(final Player req, final Player target) {
		if (!isEnabled) {
			return;
		}
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					PreparedStatement s = sql.getConnection().prepareStatement(
							"select loses from player_data where uuid = \"" + target.getUniqueId() + "\"");
					s.execute();
					ResultSet set = s.getResultSet();
					while (set.next()) {
						req.sendMessage(SlotMachine.formatChatColor("&eLoses: &6" + set.getInt("loses")));
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	public MySQL getSQL() {
		return sql;
	}
}