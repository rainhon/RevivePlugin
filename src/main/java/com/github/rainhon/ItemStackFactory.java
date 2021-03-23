package com.github.rainhon;

import com.github.rainhon.util.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class ItemStackFactory {
    public static ItemStack getReviveItem(int amount, String displayName, List<String> lore){
        Material material = Material.GHAST_TEAR;
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta;
        if(itemStack.hasItemMeta()){
            meta = itemStack.getItemMeta();
        }else{
            meta = Bukkit.getItemFactory().getItemMeta(material);
        }
        for(int i=0; i < lore.size(); i++){
            lore.set(i, lore.get(i).replace("&", String.valueOf(ChatColor.COLOR_CHAR)));
        }
        lore.add("REVIVE_ITEM");
        meta.setLore(lore);
        meta.setDisplayName(displayName.replace("&", String.valueOf(ChatColor.COLOR_CHAR)));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
