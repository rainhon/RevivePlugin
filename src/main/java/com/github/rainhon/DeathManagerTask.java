package com.github.rainhon;

import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class DeathManagerTask extends BukkitRunnable {

    private final RevivePlugin revivePlugin;

    public DeathManagerTask(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
    }

    @Override
    public void run(){
        if(revivePlugin.getDeathManager().getDeadPlayers().isEmpty()){
            System.out.println("==========");
            this.cancel();
            System.out.println("cancel task");
            System.out.println("==========");
        }
        HashMap<UUID, DeadPlayer> deadPlayers = revivePlugin.getDeathManager().getDeadPlayers();
        for(DeadPlayer deadPlayer: deadPlayers.values()){
            deadPlayer.reduceCountDown(1);
        }
    }
}
