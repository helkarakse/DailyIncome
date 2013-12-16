package net.helkarakse.dailyincome;

import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;

import org.bukkit.plugin.java.JavaPlugin;

public final class DailyIncome extends JavaPlugin {
	private Database db;
	private Logger logger = Logger.getLogger("DailyIncome");
	private String tableName = "Players";

	@Override
	public void onEnable() {
		// create the database if needed
		db = new SQLite(logger, "[DailyIncome] ", getDataFolder()
				.getAbsolutePath(), "DailyIncome", ".sqlite");

		try {
			db.open();

			if (!db.isTable(tableName)) {
				try {
					db.query("CREATE TABLE "
							+ tableName
							+ " (id INTEGER PRIMARY KEY, name TEXT, timestamp INTEGER);");
				} catch (Exception exception) {
					processException(exception);
				}
			}
		} catch (Exception exception) {
			processException(exception);
		}
	}

	@Override
	public void onDisable() {

	}

	private void processException(Exception exception) {
		logger.info(exception.getMessage());
		getPluginLoader().disablePlugin(this);
	}
}
