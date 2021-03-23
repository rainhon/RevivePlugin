package com.github.rainhon;

import com.github.rainhon.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class DeathManager {
    private final RevivePlugin revivePlugin;
    private HashMap<UUID, DeadPlayer> deadPlayers = new HashMap<>();
    private BukkitTask deathTask;

    public DeathManager(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
        Bukkit.getServer().getPluginManager().registerEvents(new DamageListener(revivePlugin), revivePlugin);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerPreDeathListener(revivePlugin), revivePlugin);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityTargetListener(revivePlugin), revivePlugin);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractListener(revivePlugin), revivePlugin);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryListener(revivePlugin), revivePlugin);

        CommandRevive commandRevive = new CommandRevive(revivePlugin);

        revivePlugin.getCommand("revive").setExecutor(commandRevive);
        revivePlugin.getCommand("revive").setTabCompleter(commandRevive);
    }

    public HashMap<UUID, DeadPlayer> getDeadPlayers(){
        return this.deadPlayers;
    }

    public void runTask(){
        if(this.deathTask == null || this.deathTask.isCancelled() ){
            Bukkit.getScheduler().runTaskTimer(revivePlugin, bukkitTask -> {
                this.deathTask = bukkitTask;
                if(deadPlayers.isEmpty()){
                    bukkitTask.cancel();
                }
                for(DeadPlayer deadPlayer: deadPlayers.values()){
                    deadPlayer.reduceCountDown(1);
                }
            }, 0, 20);
        }
    }

}