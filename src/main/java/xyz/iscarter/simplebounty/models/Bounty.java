package xyz.iscarter.simplebounty.models;

public class Bounty {
    private String playerUUID;
    private double amount;

    private String playerName;

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Bounty(String playerUUID, double amount, String playerName) {
        this.playerUUID = playerUUID;
        this.amount = amount;
        this.playerName = playerName;
    }
}
