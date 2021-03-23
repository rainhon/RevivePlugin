package com.github.rainhon;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class CommandRevive implements CommandExecutor, TabCompleter {

    private RevivePlugin revivePlugin;

    public CommandRevive(RevivePlugin revivePlugin) {
        this.revivePlugin = revivePlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("revive.giveitem")){
            sender.sendMessage("沒有使用該命令權限");
            return true;
        }
        if(args.length < 1){
            sender.sendMessage("必须指定给与的玩家");
            return false;
        }
        int amount = 1;
        if(args.length > 1){
            try{
                amount = Integer.parseInt(args[1]);
            }catch (NumberFormatException ignore){}
        }
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null){
            sender.sendMessage("未找到該玩家");
            return false;
        }

        PlayerInventory inventory = player.getInventory();
        List<String> lore = revivePlugin.getConfig().getStringList("revive-item-lore");
        String displayName = revivePlugin.getConfig().getString("revive-item-displayName");
        inventory.addItem(ItemStackFactory.getReviveItem(amount, displayName, lore));
        sender.sendMessage("已添加");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            List<String> playerList = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()){
                playerList.add(player.getPlayerListName());
            }
            return playerList;
        }
        return null;
    }
}
