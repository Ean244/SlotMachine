package com.gmail.ianlim224.slotmachine.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gmail.ianlim224.slotmachine.SlotMachine;

public class UpdateChecker {
	private SlotMachine plugin;
	private String version;
	
	public UpdateChecker(SlotMachine plugin) {
		this.plugin = plugin;
		this.version = getLatestVersion();
	}
	
	
	public String getLatestVersion() {
		try {
			final int resource = 43765;
            HttpURLConnection con = (HttpURLConnection) new URL(
                    "http://www.spigotmc.org/api/general.php").openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.getOutputStream()
                    .write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + resource)
                            .getBytes("UTF-8"));
            String version = new BufferedReader(new InputStreamReader(
                    con.getInputStream())).readLine();
            if (version.length() <= 7) {
                return version;
            }
        } catch (Exception ex) {
        	System.out.println("---------------------------------");
            plugin.getLogger().info("Failed to check for a update on spigot.");
            System.out.println("---------------------------------");
        }
		return null;
	}
	
	public boolean isConnected() {
		return version != null;
	}
	
	public boolean hasUpdate() {
		return !(version.equals(plugin.getDescription().getVersion()));
	}
	
}
