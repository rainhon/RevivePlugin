package com.github.rainhon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeathGUI implements Listener {
    Inventory GUIInventory;
    DeadPlayer deadPlayer;

    private ItemMeta timesUpItemMeta;
    private ItemMeta respawnItemMeta;
    private ItemMeta useItemItemMeta;

    private ItemStack timesUpItemStack;
    private ItemStack respawnItemStack;
    private ItemStack useItemItemStack;

    private int timesUpSlot;
    private int respawnSlot;
    private int useItemSlot;

    private final List<String> timesUpLore = Arrays.asList(ChatColor.YELLOW +"請等待<time-left>秒後可使用");
//    private List<String> respawnLore = Arrays.asList(ChatColor.YELLOW +"馬上回到重生點復活");
//    private List<String> useItemLore = Arrays.asList(ChatColor.YELLOW +"消耗背包中的一個復活物品");

    public DeathGUI(DeadPlayer deadPlayer, RevivePlugin revivePlugin){
        this.deadPlayer = deadPlayer;

        timesUpSlot = revivePlugin.getConfig().getInt("revive-times-up-slot");
        respawnSlot = revivePlugin.getConfig().getInt("revive-respawn-slot");
        useItemSlot = revivePlugin.getConfig().getInt("revive-use-item-slot");

        GUIInventory = Bukkit.createInventory(null, 9, "請選擇復活方式");
        timesUpItemStack = createItemStack(Material.ENDER_PEARL, "原地復活", ChatColor.YELLOW +"請等待復活倒計時");
        respawnItemStack = createItemStack(Material.GRASS, "回到重生點", ChatColor.YELLOW +"馬上回到重生點復活");
        useItemItemStack = createItemStack(Material.GHAST_TEAR, "使用物品復活", ChatColor.YELLOW +"消耗背包中的一個復活物品", ChatColor.YELLOW + "馬上原地復活");

        timesUpItemMeta = timesUpItemStack.getItemMeta();
        respawnItemMeta = respawnItemStack.getItemMeta();
        useItemItemMeta = useItemItemStack.getItemMeta();

        GUIInventory.setItem(timesUpSlot, timesUpItemStack);
        GUIInventory.setItem(respawnSlot, respawnItemStack);
        GUIInventory.setItem(useItemSlot, useItemItemStack);
    }

    public Inventory getGUIInventory(){
        return GUIInventory;
    }

    public void openDeathGUI(Player player){
        player.openInventory(GUIInventory);
    }

    ItemStack createItemStack(Material material, String displayName, String... lore){
        final ItemStack itemStack = new ItemStack(material, 1);
        final ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void flushGUIItemStack(){
        List<String> newLore = new ArrayList<>();
        if(deadPlayer.getCountDown() >= 0){
            for (String s : timesUpLore) {
                newLore.add(s.replaceAll("<time-left>", String.valueOf(deadPlayer.getCountDown())));
            }
        }else{
            newLore.add("可以直接原地復活");
        }

        timesUpItemMeta.setLore(newLore);

        timesUpItemStack.setItemMeta(timesUpItemMeta);

        GUIInventory.setItem(timesUpSlot, timesUpItemStack);
    }
}
