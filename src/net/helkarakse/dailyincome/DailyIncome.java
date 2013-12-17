package net.helkarakse.dailyincome;

import java.util.logging.Logger;

import lib.PatPeter.SQLibrary.Database;
import lib.PatPeter.SQLibrary.SQLite;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DailyIncome extends JavaPlugin {
	private Database db;
	private Logger logger = Logger.getLogger("DailyIncome");
	private String tableName = "Players";
	private static Economy economy = null;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(
				new DailyIncomePlayerListener(this), this);
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

		// setup the economy
		if (!(getServer().getPluginManager().getPlugin("Vault") == null)) {
			RegisteredServiceProvider<Economy> provider = getServer()
					.getServicesManager().getRegistration(Economy.class);
			if (provider != null) {
				economy = (Economy) provider.getProvider();
			}
		} else {
			processError("Vault plugin not found, required for this to work.");
		}
	}

	@Override
	public void onDisable() {

	}

	private void processException(Exception exception) {
		logger.info(exception.getMessage());
		getPluginLoader().disablePlugin(this);
	}

	private void processError(String error) {
		logger.info(error);
		getPluginLoader().disablePlugin(this);
	}
}
