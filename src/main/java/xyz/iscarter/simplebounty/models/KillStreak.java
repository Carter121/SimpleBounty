package xyz.iscarter.simplebounty.models;

import java.util.UUID;

public class KillStreak {

    private int streak;
    private UUID playerUUID;
    private int maxStreak;

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(int maxStreak) {
        this.maxStreak = maxStreak;
    }

    public KillStreak(int streak, UUID playerUUID, int maxStreak) {
        this.streak = streak;
        this.playerUUID = playerUUID;
        this.maxStreak = maxStreak;
    }
}
