package com.github.rainhon.listener;

import com.github.rainhon.RevivePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetListener implements Listener {
    private final RevivePlugin revivePlugin;

    public EntityTargetListener(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
    }

    @EventHandler
    public void onEntityTargetPlayer(EntityTargetLivingEntityEvent event){
        if(!(event.getTarget() instanceof Player)){
            return;
        }

        if(! revivePlugin.getDeathManager().getDeadPlayers().isEmpty()){
            if(revivePlugin.getDeathManager().getDeadPlayers().containsKey(event.getTarget().getUniqueId())){
                event.setCancelled(true);
            }
        }
    }
}
