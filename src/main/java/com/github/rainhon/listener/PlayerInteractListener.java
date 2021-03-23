package com.github.rainhon.listener;

import com.github.rainhon.DeadPlayer;
import com.github.rainhon.RevivePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private final RevivePlugin revivePlugin;

    public PlayerInteractListener(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(revivePlugin.getDeathManager().getDeadPlayers().containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            DeadPlayer deadPlayer = revivePlugin.getDeathManager().getDeadPlayers().get(event.getPlayer().getUniqueId());
            if(!deadPlayer.isDeathGUIOpened()){
                deadPlayer.getDeathGUI().openDeathGUI(deadPlayer.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if(revivePlugin.getDeathManager().getDeadPlayers().containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            DeadPlayer deadPlayer = revivePlugin.getDeathManager().getDeadPlayers().get(event.getPlayer().getUniqueId());
            if(!deadPlayer.isDeathGUIOpened()){
                deadPlayer.getDeathGUI().openDeathGUI(deadPlayer.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event){
        if(revivePlugin.getDeathManager().getDeadPlayers().containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            DeadPlayer deadPlayer = revivePlugin.getDeathManager().getDeadPlayers().get(event.getPlayer().getUniqueId());
            if(!deadPlayer.isDeathGUIOpened()){
                deadPlayer.getDeathGUI().openDeathGUI(deadPlayer.getPlayer());
            }
        }
    }

    //玩家攻击其他生物
    @EventHandler
    public void onPlayerAttackOthers(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && revivePlugin.getDeathManager().getDeadPlayers().containsKey(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
        }
    }

}
