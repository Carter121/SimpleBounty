package xyz.iscarter.simplebounty;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.iscarter.simplebounty.commands.kill.KillpointsCommand;
import xyz.iscarter.simplebounty.events.onPlayerJoinEvent;
import xyz.iscarter.simplebounty.events.onPlayerKillEvent;
import xyz.iscarter.simplebounty.commands.bounty.BountyCommand;
import xyz.iscarter.simplebounty.commands.bounty.BountyTabCompletion;
import xyz.iscarter.simplebounty.utils.BountiesStorageUtils;
import xyz.iscarter.simplebounty.utils.KillsStorageUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class SimpleBounty extends JavaPlugin {

    private static SimpleBounty plugin;

    public static SimpleBounty getPlugin() {
        return plugin;
    }

    private static Economy econ = null;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Check if an economy plugin is installed
        if (!setupEconomy() ) {
            getLogger().info(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;

        // Clear player cooldowns
        BountiesStorageUtils.clearPlayerCooldown();

        getCommand("bounty").setExecutor(new BountyCommand());
        getCommand("bounty").setTabCompleter(new BountyTabCompletion());

        getCommand("killpoint").setExecutor(new KillpointsCommand());

        getServer().getPluginManager().registerEvents(new onPlayerKillEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoinEvent(), this);


        try {
            BountiesStorageUtils.loadBounties();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BountiesStorageUtils.saveAutomatically();

        try {
            KillsStorageUtils.loadKills();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        KillsStorageUtils.saveAutomatically();

    }

    @Override
    public void onDisable() {
        try {
            BountiesStorageUtils.saveBounties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BountiesStorageUtils.timer.cancel();

        try {
            KillsStorageUtils.saveKills();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        KillsStorageUtils.timer.cancel();

        // Clear player cooldowns
        BountiesStorageUtils.clearPlayerCooldown();
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

    public static Economy getEconomy() {
        return econ;
    }
}
