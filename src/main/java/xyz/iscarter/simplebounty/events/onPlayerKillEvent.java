package xyz.iscarter.simplebounty.events;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.Bounty;
import xyz.iscarter.simplebounty.models.Kill;
import xyz.iscarter.simplebounty.models.KillStreak;
import xyz.iscarter.simplebounty.utils.BountiesStorageUtils;
import xyz.iscarter.simplebounty.utils.KillStreakStorageUtils;
import xyz.iscarter.simplebounty.utils.KillsStorageUtils;
import xyz.iscarter.simplebounty.utils.SamePlayerKillStreakUtils;

import java.util.ArrayList;

public class onPlayerKillEvent implements Listener {

    boolean areTargetKillPointsZero = false;

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player p = e.getPlayer();

        Entity killer = p.getKiller();


        Bounty playerBounty = getPlayerBounty(p);


        KillStreakStorageUtils.resetStreak(p.getUniqueId());


        if(killer instanceof Player) {
            Player killerPlayer = ((Player) killer).getPlayer();
            KillsStorageUtils.addKill(killer.getUniqueId().toString());

            KillStreakStorageUtils.addOneToStreak(killerPlayer.getUniqueId());

            getKillerBounty(killerPlayer);

            SamePlayerKillStreakUtils.addKill(killer.getUniqueId().toString(), p.getUniqueId().toString());

            if(playerBounty != null) {
                Economy econ = SimpleBounty.getEconomy();

                econ.depositPlayer(killerPlayer, playerBounty.getAmount());
            }
        }


        if(getPlayerKills(p) != null) {
            KillsStorageUtils.subtractKill(p.getUniqueId().toString());
        } else {
            KillsStorageUtils.subtractKill(p.getUniqueId().toString());
        }


        createPlayerBounty(p);


    }

    private static Kill getPlayerKills(Player p) {
        Kill kills = KillsStorageUtils.getKills(p.getUniqueId().toString());

        if(kills != null) {
            return kills;
        }

        return null;
    }


    private static Bounty getPlayerBounty(Player p) {

        return BountiesStorageUtils.getBounty(p.getUniqueId().toString());

    }

    private static Bounty getKillerBounty(Player p) {

        BountiesStorageUtils.deleteBounty(p.getName());

        String playerUUID = p.getUniqueId().toString();
        int playerKillPoints = KillsStorageUtils.getKills(playerUUID).getKills();
        double amountPerKillPoint = SimpleBounty.getPlugin().getConfig().getDouble("amount_per_kill_point");
        double amount = playerKillPoints * amountPerKillPoint;

        Bounty tempBounty = new Bounty(playerUUID, amount, p.getName());

        BountiesStorageUtils.createBounty(tempBounty.getPlayerUUID(),tempBounty.getAmount(),tempBounty.getPlayerName());

        return tempBounty;
    }

    private static void createPlayerBounty(Player p) {
        BountiesStorageUtils.deleteBounty(p.getName());

        String playerUUID = p.getUniqueId().toString();
        int playerKillPoints = KillsStorageUtils.getKills(playerUUID).getKills();
        double amountPerKillPoint = SimpleBounty.getPlugin().getConfig().getDouble("amount_per_kill_point");
        double amount = playerKillPoints * amountPerKillPoint;


        Bounty tempBounty = new Bounty(playerUUID, amount, p.getName());

        BountiesStorageUtils.createBounty(tempBounty.getPlayerUUID(),tempBounty.getAmount(),tempBounty.getPlayerName());
    }
}
