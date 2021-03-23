package com.github.rainhon.util;

import org.bukkit.ChatColor;

public class CommonUtil {
    public static String buildInvisibleLore(String s){
        StringBuilder stringBuilder = new StringBuilder();
        for(char c : s.toCharArray()){
            stringBuilder.append(ChatColor.COLOR_CHAR).append(c);
        }
        return stringBuilder.toString();
    }

    public static String readInvisibleLore(String s){
        return s.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "");
    }
}
