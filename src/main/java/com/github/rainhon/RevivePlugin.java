package com.github.rainhon;

import org.bukkit.plugin.java.JavaPlugin;

public class RevivePlugin extends JavaPlugin {
    private DeathManager deathManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        deathManager = new DeathManager(this);
    }

    @Override
    public void onDisable(){
        for(DeadPlayer deadPlayer : deathManager.getDeadPlayers().values()){
            deadPlayer.revive();
        }
    }

    public DeathManager getDeathManager(){
        return this.deathManager;
    }
}
