package me.rukes.sgclear;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener{
    
    
    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(this, this);
        System.out.println("[SGAddon] Successfully enabled!");
    }
    
    @Override
    public void onDisable(){
        System.out.println("[SGAddon] Successfully disabled!");
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length == 0){
            sender.sendMessage("§cYou must specify a player's name.");
        }else{
            Player arg = Bukkit.getPlayer(args[0]);
            if (arg != null) {
                sender.sendMessage("§3"+arg.getName()+" §bis in §3"+arg.getLocation().getWorld().getName()+"§b.");
            }else{
                sender.sendMessage("§cNo such player found.");
            }
        }
    return true;
    }
    
    @EventHandler
    public void deathRegen(PlayerDeathEvent e){
        if(e.getEntity().getKiller() != null){
            Player p = e.getEntity().getKiller();
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, 2));
            e.getEntity().sendMessage("§8[§6SG§8] §7Player §8"+p.getName()+" §7killed you with §c"+p.getHealth()+" §7remaining health.");
        }
    }
    
    @EventHandler
    public void HealSoupEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();
            if(p.getInventory().getItemInHand().getType() == Material.MUSHROOM_SOUP){
                if((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && (p.getGameMode() == GameMode.SURVIVAL)){
                    e.setCancelled(true);
                    p.getInventory().setItemInHand(new ItemStack(Material.AIR));
                    p.playSound(p.getLocation(), Sound.EAT, 1F, 1F);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 2));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 1));
                    p.setFoodLevel(p.getFoodLevel()+5);
            }
        }
    }
}