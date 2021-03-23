package com.github.rainhon.listener;

import com.github.rainhon.DeadPlayer;
import com.github.rainhon.RevivePlugin;
import com.github.rainhon.event.PlayerPreDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PlayerPreDeathListener implements Listener {
    private final RevivePlugin revivePlugin;
    private final List<String> activeWorldList;

    public PlayerPreDeathListener(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
        activeWorldList = revivePlugin.getConfig().getStringList("active-world");
    }

    @EventHandler
    public void onPlayerPreDeath(PlayerPreDeathEvent event){
        Player player = event.getPlayer();
        if(activeWorldList.contains(player.getWorld().getName())){
            revivePlugin.getDeathManager().getDeadPlayers().put(player.getUniqueId(), new DeadPlayer(player, revivePlugin));
            revivePlugin.getDeathManager().runTask();
        }
    }
}
