package xyz.iscarter.simplebounty.models;

public class Kill {

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    private String playerUUID;
    private int kills;

    public Kill(String playerUUID, int kills) {
        this.playerUUID = playerUUID;
        this.kills = kills;
    }
}
