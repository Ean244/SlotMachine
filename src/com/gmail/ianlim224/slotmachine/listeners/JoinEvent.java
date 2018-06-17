package com.gmail.ianlim224.slotmachine.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.ianlim224.slotmachine.SlotMachine;
import com.gmail.ianlim224.slotmachine.mysql.MySQLManager;

public class JoinEvent implements Listener {

	private MySQLManager sql;

	public JoinEvent(SlotMachine plugin) {
		sql = MySQLManager.getInstance(plugin);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		sql.addPlayer(e.getPlayer());
	}
}
