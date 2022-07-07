package xyz.iscarter.simplebounty.models;

public class SamePlayerKillStreak {

    private int killsInTime;
    private String playerUUID;
    private String targetUUID;

    public SamePlayerKillStreak(int killsInTime, String playerUUID, String targetUUID) {
        this.killsInTime = killsInTime;
        this.playerUUID = playerUUID;
        this.targetUUID = targetUUID;
    }

    public int getKillsInTime() {
        return killsInTime;
    }

    public void setKillsInTime(int killsInTime) {
        this.killsInTime = killsInTime;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getTargetUUID() {
        return targetUUID;
    }

    public void setTargetUUID(String targetUUID) {
        this.targetUUID = targetUUID;
    }
}
