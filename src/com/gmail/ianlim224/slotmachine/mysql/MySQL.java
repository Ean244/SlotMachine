package com.gmail.ianlim224.slotmachine.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.configuration.ConfigurationSection;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class MySQL {
	private boolean connected;
	private Connection connection;
	private String ip;
	private String database;
	private String user;
	private String password;

	public MySQL(SlotMachine plugin) throws ClassNotFoundException, SQLException {
		ConfigurationSection sql = plugin.getConfig().getConfigurationSection("mysql");
		this.ip = sql.getString("ip");
		this.database = sql.getString("database_name");
		this.user = sql.getString("username");
		this.password = sql.getString("password");
		Class.forName("com.mysql.jdbc.Driver");

		this.connection = DriverManager
				.getConnection("jdbc:mysql://" + ip + "/" + database + "?user=" + user + "&password=" + password);
		this.connected = true;
	}

	public String getServerIP() {
		return this.ip;
	}

	public String getDatabase() {
		return this.database;
	}

	public String getUsername() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public boolean isConnected() {
		return this.connected;
	}
	
	public void setConnected(boolean b) {
		this.connected = b;
	}
}
