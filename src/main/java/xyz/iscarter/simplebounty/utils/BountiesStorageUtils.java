package xyz.iscarter.simplebounty.utils;

import com.google.gson.Gson;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.plugin.Plugin;
import xyz.iscarter.simplebounty.SimpleBounty;
import xyz.iscarter.simplebounty.models.Bounty;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class BountiesStorageUtils {

    private static ArrayList<Bounty> bounties = new ArrayList<>();

    public static Bounty createBounty(String uuid, Double amount, String displayName) {

        Bounty bounty = new Bounty(uuid, amount, displayName);
        bounties.add(bounty);

        return bounty;
    }

    public static ArrayList<Bounty> getAllBounties() {
        return bounties;
    }

    public static Bounty getBounty(String uuid) {
        for(Bounty bounty : bounties) {
            if(bounty.getPlayerUUID().equals(uuid)) {
                return bounty;
            }
        }
        return null;
    }

    public static void deleteBounty(String displayName) {
        //linear search
        for(Bounty bounty : bounties) {
            if(bounty.getPlayerName().equals(displayName)) {
                bounties.remove(bounty);
                break;
            }
        }
    }

    public static void saveBounties() throws IOException {

        Gson gson = new Gson();
        File file =  new File(SimpleBounty.getPlugin().getDataFolder().getAbsolutePath() + "/bounties.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(bounties, writer);
        writer.flush();
        writer.close();

        SimpleBounty.getPlugin().getLogger().info("Bounties Saved");

    }

    public static Timer timer;
    private static int minuetsBetweenSaves = SimpleBounty.getPlugin().getConfig().getInt("time_between_saves");

    public static void saveAutomatically() {
        timer = new Timer();

        timer.schedule( new TimerTask() {
            public void run() {
                try {
                    saveBounties();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, (60*minuetsBetweenSaves)*1000);
    }

    public static void loadBounties() throws FileNotFoundException {
        Gson gson = new Gson();
        File file =  new File(SimpleBounty.getPlugin().getDataFolder().getAbsolutePath() + "/bounties.json");
        if(file.exists()) {
            Reader reader = new FileReader(file);
            Bounty[] n = gson.fromJson(reader, Bounty[].class);
            bounties = new ArrayList<>(Arrays.asList(n));
        }
    }
}
