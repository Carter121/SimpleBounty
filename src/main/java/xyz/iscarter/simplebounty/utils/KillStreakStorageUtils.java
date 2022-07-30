package xyz.iscarter.simplebounty.utils;

import com.google.gson.Gson;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.KillStreak;

import java.io.*;
import java.util.*;

public class KillStreakStorageUtils {

    private static ArrayList<KillStreak> streaks = new ArrayList<>();


    public static KillStreak addOneToStreak(UUID playerUUID) {

        for(KillStreak streak: streaks) {

            if(streak.getPlayerUUID() == playerUUID) {

                KillStreak oldStreak = streak;
                streaks.remove(oldStreak);

                int newAmount = oldStreak.getStreak();
                newAmount++;

                KillStreak newStreak;

                if(newAmount <= oldStreak.getMaxStreak()) {
                    newStreak = new KillStreak(newAmount, oldStreak.getPlayerUUID(), oldStreak.getMaxStreak());
                } else {
                    newStreak = new KillStreak(newAmount, oldStreak.getPlayerUUID(), newAmount);
                }


                streaks.add(newStreak);

                checkKillStreakRewards(playerUUID, newStreak);

                return newStreak;

            }

        }

        KillStreak newStreak = new KillStreak(1, playerUUID, 1);
        streaks.add(newStreak);

        checkKillStreakRewards(playerUUID, newStreak);

        return newStreak;

    }

    public static KillStreak setStreak(UUID playerUUID, int amount) {

        for(KillStreak streak: streaks) {

            if(streak.getPlayerUUID().toString().equalsIgnoreCase(playerUUID.toString())) {

                KillStreak oldStreak = streak;
                streaks.remove(oldStreak);

                KillStreak newStreak;

                if(amount <= oldStreak.getMaxStreak()) {
                    newStreak = new KillStreak(amount, oldStreak.getPlayerUUID(), oldStreak.getMaxStreak());
                } else {
                    newStreak = new KillStreak(amount, oldStreak.getPlayerUUID(), amount);
                }


                streaks.add(newStreak);

                checkKillStreakRewards(playerUUID, newStreak);

                return newStreak;

            }

        }

        KillStreak newStreak = new KillStreak(amount, playerUUID, amount);
        streaks.add(newStreak);

        checkKillStreakRewards(playerUUID, newStreak);

        return newStreak;

    }


    public static KillStreak subtractOneFromStreak(UUID playerUUID) {

        KillStreak currentStreak = null;

        for(KillStreak streak: streaks) {
            if(streak.getPlayerUUID() == playerUUID) {
                currentStreak = streak;
                break;
            }
        }

        if(currentStreak != null) {

            KillStreak newStreak = new KillStreak(currentStreak.getStreak() - 1, currentStreak.getPlayerUUID(), currentStreak.getMaxStreak());

            streaks.remove(currentStreak);
            streaks.add(newStreak);

            return newStreak;

        }

        return null;

    }


    public static KillStreak getStreak(UUID playerUUID) {

        KillStreak playerKillStreak = null;

        for(KillStreak streak: streaks) {
            if(streak.getPlayerUUID().toString().equals(playerUUID.toString())) {
                playerKillStreak = streak;
                break;
            }
        }

        return playerKillStreak;
    }


    public static void deleteStreak(UUID playerUUID) {
        for(KillStreak streak: streaks) {
            if(streak.getPlayerUUID().toString().equals(playerUUID.toString())) {
                streaks.remove(streak);
                break;
            }
        }
    }

    public static void resetStreak(UUID playerUUID) {
        for(KillStreak streak: streaks) {
            if(streak.getPlayerUUID().toString().equals(playerUUID.toString())) {
                int maxStreak = streak.getMaxStreak();
                streaks.remove(streak);
                KillStreak newStreak = new KillStreak(0, playerUUID, maxStreak);
                streaks.add(newStreak);
                break;
            }
        }
    }


    public static void saveStreaks() throws IOException {

        Gson gson = new Gson();
        File file =  new File(SimpleBounty.getPlugin().getDataFolder().getAbsolutePath() + "/streaks.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(streaks, writer);
        writer.flush();
        writer.close();

        if(SimpleBounty.getPlugin().getConfig().getBoolean("display_save_messages")) {
            SimpleBounty.getPlugin().getLogger().info("Streaks Saved");
        }

    }

    public static Timer timer;
    private static int minuetsBetweenSaves = SimpleBounty.getPlugin().getConfig().getInt("time_between_saves");

    public static void saveAutomatically() {
        timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                try {
                    saveStreaks();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, (60*minuetsBetweenSaves)*1000);
    }

    public static void loadStreaks() throws FileNotFoundException {
        Gson gson = new Gson();
        File file =  new File(SimpleBounty.getPlugin().getDataFolder().getAbsolutePath() + "/streaks.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            KillStreak[] n = gson.fromJson(reader, KillStreak[].class);
            streaks = new ArrayList<>(Arrays.asList(n));
        }
    }

    private static void checkKillStreakRewards(UUID playerUUID, KillStreak killStreak) {

        Player p = Bukkit.getPlayer(playerUUID);

        ConfigurationSection rewards = SimpleBounty.getPlugin().getConfig().getConfigurationSection("rewards");

        rewards.getKeys(false).forEach(key -> {

            int streakNum = SimpleBounty.getPlugin().getConfig().getInt("rewards." + key + ".streak_num");

            if(killStreak.getStreak() == streakNum) {
                List<String> commands = SimpleBounty.getPlugin().getConfig().getStringList("rewards." + key + ".commands");

                commands.forEach(command -> {

                    assert command != null;
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(p, command));

                });


            }

        });

    }

}
