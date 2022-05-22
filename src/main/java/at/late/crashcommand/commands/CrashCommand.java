package at.late.crashcommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static at.late.crashcommand.Main.prefix;

public class CrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if(!(sender instanceof Player)) {
                return false;
            }
            Player player = (Player) sender;
            if(args.length >= 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if(target == player) {
                    player.sendMessage(prefix + "§cDu kannst dich nicht selbst crashen!");
                    return false;
                }
                if(target == null) {
                    player.sendMessage(prefix + "§cDer Spieler " + args[0] + " ist nicht Online!");
                    playSoundFail(player);
                    return false;
                }
                if (args.length == 1) {
                    player.sendMessage(prefix + "§aDu hast den Spieler " + target.getName() + " erfolgreich gecrasht!");
                    target.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), Integer.MAX_VALUE);
                    for (Player x : Bukkit.getServer().getOnlinePlayers()) {
                        if (x.hasPermission("late.team") && x != target && x != player) {
                            x.sendMessage(prefix + "§fDer Spieler §6" + player.getName() + " §fhat den Spieler §6" + target.getName() + "§f gecrasht!");
                        }
                    }
                    return true;
                }
                if(args.length >= 2) {
                    String[] reason = args;
                    boolean first = false;
                    String finalreason = "";
                    for(int x = 0; x < reason.length; x++) {
                        if(x == 0) {

                        }
                        else if(finalreason == "") {
                            finalreason = reason[x];
                        } else {
                            finalreason = finalreason + " " + reason[x];
                        }
                    }
                    player.sendMessage(prefix + "§aDu hast den Spieler " + target.getName() + " erfolgreich für " + finalreason + " gecrasht!");
                    for (Player x : Bukkit.getServer().getOnlinePlayers()) {
                        if (x.hasPermission("late.team") && x != target && x != player) {
                            x.sendMessage(prefix + "§fDer Spieler §6" + player.getName() + " §fhat den Spieler §6" + target.getName() + "§f für §6" + finalreason + "§f gecrasht!");
                        }
                    }
                    target.sendMessage(prefix + "§fDu wurdest von dem Spieler §c§l" + player.getName() + " §ffür§c§l " + finalreason + "§f gecrasht!");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    target.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), Integer.MAX_VALUE);
                    return true;
                } else {
                    sendUsage(player);
                    playSoundFail(player);
                }
            } else {
                sendUsage(player);
                playSoundFail(player);
            }
            return false;
        }

    public static void playSoundSucces(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0f, 1.0f);
    }
    public static void playSoundFail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 20.0f, 1.0f);
    }
    public static void playSoundLevelup(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3.0f, 1.0f);
    }

    public static void sendUsage(Player player) {
        player.sendMessage(prefix + "§cVerwende: §7/crash <Spieler>, <Begründung>");
    }
}
