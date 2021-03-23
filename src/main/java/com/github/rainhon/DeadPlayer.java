package com.github.rainhon;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class DeadPlayer {
    private final Player player;
    private final RevivePlugin revivePlugin;
    private HashMap<UUID, Player> hideToPlayers = new HashMap<>();
    private final Hologram deathHolo;
    private int countDown;
    private boolean isDeathGUIOpened;
    private List<String> messageList;
    private DeathGUI deathGUI;
    private boolean isTimesUp;


    public DeadPlayer(Player player, RevivePlugin revivePlugin){
        this.player = player;
        this.revivePlugin = revivePlugin;

        player.setGameMode(GameMode.SPECTATOR);//短暂更换游戏模式消除仇恨
        Listener playerMoveListener = new Listener() {
            @EventHandler
            public void onPlayerMove(PlayerMoveEvent event){
                if(event.getPlayer() == player){
                    event.setCancelled(true);
                }
            }
        };
        Bukkit.getPluginManager().registerEvents(playerMoveListener, revivePlugin);
        //10tick后将游戏模式变回冒险模式
        Bukkit.getScheduler().runTaskLater(revivePlugin, ()->{
            //往上TP一格，避免陷入地下
            player.teleport(player.getLocation().add(new Vector(0, 1, 0)));
            player.setGameMode(GameMode.ADVENTURE);
            PlayerMoveEvent.getHandlerList().unregister(playerMoveListener);
        }, 10);

        deathGUI = new DeathGUI(this, revivePlugin);

        player.setHealth(20);
        messageList = revivePlugin.getConfig().getStringList("floating-message");
        countDown = revivePlugin.getConfig().getInt("revive-countdown");
        hideToOnlinePlayer();
        deathHolo = HologramsAPI.createHologram(revivePlugin, player.getLocation().add(new Vector(0, 3.0, 0)));
        for(String s : messageList){
            deathHolo.appendTextLine(replaceCountdown(s));
        }
    }

    private void hideToOnlinePlayer(){
        Bukkit.getServer().getOnlinePlayers().forEach(otherPlayer -> {
            otherPlayer.hidePlayer(this.revivePlugin, this.player);
            hideToPlayers.put(otherPlayer.getUniqueId(), otherPlayer);
        });
    }

    public void reduceCountDown(int i){
        this.countDown -= i;
        if(countDown <= 0){
            isTimesUp = true;
        }
        flushDeathInfo();
        deathGUI.flushGUIItemStack();
    }

    public int getCountDown(){
        return this.countDown;
    }

    public Player getPlayer(){
        return this.player;
    }

    private void showToAll(){
        for(UUID uuid: hideToPlayers.keySet()){
            if(Bukkit.getPlayer(uuid) != null){
                Bukkit.getPlayer(uuid).showPlayer(revivePlugin, this.player);
            }
        }
    }

    private void flushDeathInfo(){
        deathHolo.clearLines();
        if(isTimesUp){
            deathHolo.appendTextLine("原地復活: 可使用");
        }else{
            for(String s : messageList){
                deathHolo.appendTextLine(replaceCountdown(s));
            }
        }
        deathHolo.teleport(player.getLocation().add(0.0, 4.0, 0.0));
    }

    public void revive(){
        player.sendMessage("您復活了");
        player.setGameMode(GameMode.SURVIVAL);
        showToAll();
        deathHolo.delete();
        revivePlugin.getDeathManager().getDeadPlayers().remove(player.getUniqueId());
    }

    public boolean isDeathGUIOpened() {
        return isDeathGUIOpened;
    }

    public void setDeathGUIOpened(boolean deathGUIOpened) {
        isDeathGUIOpened = deathGUIOpened;
    }

    private String replaceCountdown(String s){
        s = s.replaceAll("&", String.valueOf(ChatColor.COLOR_CHAR));
        return s.replaceAll("<left-time>", String.valueOf(countDown));
    }

    public Inventory getDeathGUIInventory(){
        return deathGUI.getGUIInventory();
    }

    public DeathGUI getDeathGUI(){
        return deathGUI;
    }

    public boolean isTimesUp(){
        return isTimesUp;
    }
}
