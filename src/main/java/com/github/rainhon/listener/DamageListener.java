package com.github.rainhon.listener;

import com.github.rainhon.DeathManager;
import com.github.rainhon.RevivePlugin;
import com.github.rainhon.event.PlayerPreDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    RevivePlugin revivePlugin;

    public DamageListener(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event){
        if(! (event.getEntity() instanceof Player)){
            return;
        }

        Player player = (Player) event.getEntity();

        //处于死亡状态不受伤害
        if(!revivePlugin.getDeathManager().getDeadPlayers().isEmpty()){
            if(revivePlugin.getDeathManager().getDeadPlayers().containsKey(player.getUniqueId())){
                event.setCancelled(true);
            }
        }

        //受到致命伤
        if(player.getHealth() <= event.getFinalDamage()){
            PlayerPreDeathEvent playerPreDeathEvent = new PlayerPreDeathEvent(player);
            Bukkit.getPluginManager().callEvent(playerPreDeathEvent);
            event.setCancelled(true);
        }
    }
}
