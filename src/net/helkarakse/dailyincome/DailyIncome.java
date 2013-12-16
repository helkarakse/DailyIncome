package net.helkarakse.dailyincome;

import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.plugin.java.JavaPlugin;

public final class DailyIncome extends JavaPlugin {
	private Database db;
	private Logger logger = Logger.getLogger("DailyIncome");

	@Override
	public void onEnable() {
		db = new SQLite(logger, "[DailyIncome] ", getDataFolder()
				.getAbsolutePath(), "DailyIncome", ".sqlite");

		try {
			db.open();
		} catch (Exception e) {
			logger.info(e.getMessage());
			getPluginLoader().disablePlugin(this);
		}
	}

	@Override
	public void onDisable() {
		
	}
}
