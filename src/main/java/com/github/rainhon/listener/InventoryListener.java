package com.github.rainhon.listener;

import com.github.rainhon.DeadPlayer;
import com.github.rainhon.RevivePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class InventoryListener implements Listener {
    private final RevivePlugin revivePlugin;
    private int timesUpSlot;
    private int respawnSlot;
    private int useItemSlot;

    public InventoryListener(RevivePlugin revivePlugin){
        this.revivePlugin = revivePlugin;
        timesUpSlot = revivePlugin.getConfig().getInt("revive-times-up-slot");
        respawnSlot = revivePlugin.getConfig().getInt("revive-respawn-slot");
        useItemSlot = revivePlugin.getConfig().getInt("revive-use-item-slot");
    }

    @EventHandler
    public void onInventoryClicked(InventoryClickEvent event){
        for(DeadPlayer deadPlayer: revivePlugin.getDeathManager().getDeadPlayers().values()){
            if(event.getInventory() == deadPlayer.getDeathGUIInventory()){
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();
                if(event.getSlot() == respawnSlot){
                    player.teleport(player.getWorld().getSpawnLocation().add(new Vector(0, 1, 0)));
                    deadPlayer.revive();
                }else if(event.getSlot() == useItemSlot){
                    ItemStack reviveItem = null;
                    boolean hasFound = false;
                    for(ItemStack itemStack : player.getInventory().getContents()){
                        if(itemStack != null && itemStack.getItemMeta() != null && itemStack.getItemMeta().getLore() != null){
                            for(String lore : itemStack.getItemMeta().getLore()){
                                if(lore.contains("REVIVE_ITEM")){
                                    reviveItem = itemStack;
                                    hasFound = true;
                                    break;
                                }
                            }
                            if(hasFound){
                                break;
                            }
                        }
                    }
                    if(hasFound){
                        int newAmount = reviveItem.getAmount()-1;
                        reviveItem.setAmount(newAmount);
                        deadPlayer.revive();
                    }else{
                        player.sendMessage("沒有復活物品");
                    }
                }else if(event.getSlot() == timesUpSlot){
                    if(deadPlayer.isTimesUp()){
                        deadPlayer.revive();
                    }else{
                        player.sendMessage("請等待倒計時結束");
                    }
                }
                player.closeInventory();
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        for(DeadPlayer deadPlayer: revivePlugin.getDeathManager().getDeadPlayers().values()){
            if(deadPlayer.getDeathGUIInventory() == event.getInventory()){
                event.setCancelled(true);
                return;
            }
        }
    }
}
