package xyz.iscarter.simplebounty.utils;

import com.google.gson.Gson;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.Kill;

import java.io.*;
import java.util.*;

public class KillsStorageUtils {

    private static ArrayList<Kill> kills = new ArrayList<>();

    public static Kill addKill(String uuid) {

        Kill currentKills = null;

        for(Kill kill : kills) {
            if(kill.getPlayerUUID().equals(uuid)) {
                currentKills = kill;
                break;
            }
        }

        if(currentKills == null) {

            int amount = 0;
            Kill kill = new Kill(uuid, ++amount);
            kills.add(kill);

            return kill;
        } else {
            int amount = currentKills.getKills();
            deleteKills(uuid);
            Kill kill = new Kill(uuid, ++amount);
            kills.add(kill);

            return kill;
        }
    }

    public static Kill setKill(String uuid, int amount) {

        deleteKills(uuid);

        Kill kill = new Kill(uuid, amount);
        kills.add(kill);
        return kill;

    }

    public static Kill subtractKill(String uuid) {

        Kill currentKills = null;

        for(Kill kill : kills) {
            if(kill.getPlayerUUID().equals(uuid)) {
                currentKills = kill;
                break;
            }
        }

        if(currentKills == null) {

            int amount = 0;
            Kill kill = new Kill(uuid, 0);
            kills.add(kill);

            return kill;
        } else {
            int amount = currentKills.getKills();

            if(amount != 0) {

                double percentage = SimpleBounty.getPlugin().getConfig().getInt("kill_point_percent");

                double percentage2 = percentage / 100;

                amount = (int) Math.floor(amount - (amount * percentage2));

                deleteKills(uuid);
                Kill kill = new Kill(uuid, amount);
                kills.add(kill);


                return kill;
            }
            return null;
        }
    }

    public static ArrayList<Kill> getAllKills() {
        return kills;
    }

    public static Kill getKills(String uuid) {
        for(Kill kill : kills) {
            if(kill.getPlayerUUID().equals(uuid)) {
                return kill;
            }
        }
        return new Kill(uuid, 0);
    }

    public static void deleteKills(String uuid) {
        //linear search
        for(Kill kill : kills) {
            if(kill.getPlayerUUID().equals(uuid)) {
                kills.remove(kill);
                break;
            }
        }
    }

    public static void saveKills() throws IOException {

        Gson gson = new Gson();
        File file =  new File(SimpleBounty.getPlugin().getDataFolder().getAbsolutePath() + "/kills.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(kills, writer);
        writer.flush();
        writer.close();

        if(SimpleBounty.getPlugin().getConfig().getBoolean("display_save_messages")) {
            SimpleBounty.getPlugin().getLogger().info("Kills Saved");
        }

    }

    public static Timer timer;

    private static int minuetsBetweenSaves = SimpleBounty.getPlugin().getConfig().getInt("time_between_saves");
    public static void saveAutomatically() {
        timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                try {
                    saveKills();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, (60*minuetsBetweenSaves)*1000);
    }

    public static void loadKills() throws FileNotFoundException {
        Gson gson = new Gson();
        File file =  new File(SimpleBounty.getPlugin().getDataFolder().getAbsolutePath() + "/kills.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            Kill[] n = gson.fromJson(reader, Kill[].class);
            kills = new ArrayList<>(Arrays.asList(n));
        }
    }

    public static ArrayList<Kill> sort() {

        kills.sort(Comparator.comparing(Kill::getKills));

        return kills;

    }
}
