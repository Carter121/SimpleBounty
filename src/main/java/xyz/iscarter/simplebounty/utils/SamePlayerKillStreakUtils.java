package xyz.iscarter.simplebounty.utils;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.Kill;
import xyz.iscarter.simplebounty.models.SamePlayerKillStreak;

import java.io.*;
import java.util.*;

public class SamePlayerKillStreakUtils {

    private static ArrayList<SamePlayerKillStreak> samePlayerKillStreaks = new ArrayList<>();

    // Adds a kill to the players "streak" on a certain player
    public static SamePlayerKillStreak addKill(String playerUUID, String targetUUID) {

        SamePlayerKillStreak currentSamePlayerKillStreak = null;

        for(SamePlayerKillStreak samePlayerKillStreak : samePlayerKillStreaks) {
            if(samePlayerKillStreak.getPlayerUUID().equals(playerUUID) && samePlayerKillStreak.getTargetUUID().equals(targetUUID)) {
                currentSamePlayerKillStreak = samePlayerKillStreak;
                break;
            }
        }

        SamePlayerKillStreak newSamePlayerKillStreak = null;

        if(currentSamePlayerKillStreak == null) {

            int amount = 1;
            newSamePlayerKillStreak = new SamePlayerKillStreak(amount, playerUUID, targetUUID);
            samePlayerKillStreaks.add(newSamePlayerKillStreak);


        } else {

            int amount = currentSamePlayerKillStreak.getKillsInTime();
            deleteKill(playerUUID, targetUUID);
            newSamePlayerKillStreak = new SamePlayerKillStreak(++amount, playerUUID, targetUUID);
            samePlayerKillStreaks.add(newSamePlayerKillStreak);

            banPlayer(playerUUID, newSamePlayerKillStreak);

        }

        // Subtract one kill after time period is up
        Timer timer1;

        timer1 = new Timer();

        long timeRange = SimpleBounty.getPlugin().getConfig().getLong("ban_time_range");

        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                subtractKill(playerUUID, targetUUID);
            }
        }, timeRange * 60000);


        return newSamePlayerKillStreak;

    }

    public static SamePlayerKillStreak subtractKill(String playerUUID, String targetUUID) {

        SamePlayerKillStreak currentSamePlayerKillStreak = null;

        for(SamePlayerKillStreak samePlayerKillStreak : samePlayerKillStreaks) {
            if(samePlayerKillStreak.getPlayerUUID().equals(playerUUID) && samePlayerKillStreak.getTargetUUID().equals(targetUUID)) {
                currentSamePlayerKillStreak = samePlayerKillStreak;

                samePlayerKillStreaks.remove(currentSamePlayerKillStreak);

                break;
            }
        }

        int oldKills = currentSamePlayerKillStreak.getKillsInTime();
        oldKills--;

        SamePlayerKillStreak newSamePlayerKillStreak = new SamePlayerKillStreak(oldKills, currentSamePlayerKillStreak.getPlayerUUID(), currentSamePlayerKillStreak.getTargetUUID());

        samePlayerKillStreaks.add(newSamePlayerKillStreak);

        return newSamePlayerKillStreak;
    }


    // Deletes a kill streak
    public static SamePlayerKillStreak deleteKill(String playerUUID, String targetUUID) {

        SamePlayerKillStreak currentSamePlayerKillStreak = null;

        for(SamePlayerKillStreak samePlayerKillStreak : samePlayerKillStreaks) {
            if(samePlayerKillStreak.getPlayerUUID().equals(playerUUID) && samePlayerKillStreak.getTargetUUID().equals(targetUUID)) {
                samePlayerKillStreaks.remove(samePlayerKillStreak);
                return samePlayerKillStreak;
            }
        }

        return null;

    }

    public static SamePlayerKillStreak getKill(String playerUUID, String targetUUID) {

        SamePlayerKillStreak currentKill = null;

        for(SamePlayerKillStreak kill: samePlayerKillStreaks) {
            if(kill.getPlayerUUID().equals(playerUUID) && kill.getTargetUUID().equals(targetUUID)) {
                return kill;
            }
        }

        return null;

    }

    // Ban player if they kill someone the max times
    private static void banPlayer(String playerUUID, SamePlayerKillStreak samePlayerKillStreak) {

        int maxKills = SimpleBounty.getPlugin().getConfig().getInt("kill_amount");

        if(samePlayerKillStreak.getKillsInTime() > maxKills) {

            Player player = Bukkit.getPlayer(UUID.fromString(playerUUID));

            String banTime = SimpleBounty.getPlugin().getConfig().getString("player_ban_time");

            String banReason = SimpleBounty.getPlugin().getConfig().getString("ban_reason");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + player.getName() + " " + banTime + " " + banReason);

        }

    }
}
