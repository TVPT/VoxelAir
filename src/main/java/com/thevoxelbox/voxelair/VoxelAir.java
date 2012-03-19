package com.thevoxelbox.voxelair;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class VoxelAir extends JavaPlugin {

    protected static final Logger log = Logger.getLogger("Minecraft");
    public static Server s;
    public static boolean globalAllowFlying;
    public static boolean derp;
    public static boolean consume;
    public static boolean allowClassicFRF;
    public static boolean allowHover;
    public static int cruise;
    public static int thrust;
    int count = 0;
    static int elevn;
    Random rnd = new Random();
    String godmsg;
    public static HashSet<String> admns = new HashSet();

    @Override
    public void onDisable() {
    
    }

    @Override
    public void onEnable() {
        s = getServer();
        s.getPluginManager().registerEvents(new PlayerListener(), this);
        s.getPluginManager().registerEvents(new EntityListener(), this);
        loadConfig();
        saveConfig();
        initFly();
        PluginDescriptionFile pdfFile = getDescription();
        s.getLogger().info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String[] trimmedArgs = args;
        String commandName = command.getName().toLowerCase();

        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            String playerName = p.getName();
            if (isAdmin(p.getName())) {
                if (commandName.equalsIgnoreCase("derp")) {
                    derp = !derp;
                    p.sendMessage(ChatColor.GOLD + "The derp has been toggled " + (derp ? "on" : "off"));

                    return true;
                }
                if (commandName.equalsIgnoreCase("letmefly")) {
                    globalAllowFlying = !globalAllowFlying;
                    s.broadcastMessage(ChatColor.LIGHT_PURPLE + playerName + " has just turned flight" + (globalAllowFlying ? " on" : " off"));

                    return true;
                }
                if (commandName.equalsIgnoreCase("toggleclassic")) {
                    allowClassicFRF = !allowClassicFRF;
                    s.broadcastMessage(ChatColor.LIGHT_PURPLE + playerName + " has just turned classic flight" + (allowClassicFRF ? " on" : " off"));

                    return true;
                }
                if (commandName.equalsIgnoreCase("togglehover")) {
                    allowHover = !allowHover;
                    s.broadcastMessage(ChatColor.LIGHT_PURPLE + playerName + " has just turned hovering" + (allowHover ? " on" : " off"));

                    return true;
                }
                if (commandName.equalsIgnoreCase("godsays")) {
                    if (trimmedArgs.length < 1) {
                        return false;
                    }

                    this.godmsg = "";

                    for (String str : trimmedArgs) {
                        this.godmsg = (this.godmsg + str + " ");
                    }

                    s.broadcastMessage(ChatColor.LIGHT_PURPLE + "[GOD] " + godmsg);

                    return true;
                }
                if (commandName.equalsIgnoreCase("evilsays")) {
                    if (trimmedArgs.length < 1) {
                        return false;
                    }

                    this.godmsg = "";

                    for (String str : trimmedArgs) {
                        this.godmsg = (this.godmsg + str + " ");
                    }

                    s.broadcastMessage(ChatColor.DARK_RED + "[EVIL] " + godmsg);

                    return true;
                }
                if (commandName.equalsIgnoreCase("scisays")) {
                    if (trimmedArgs.length < 1) {
                        return false;
                    }

                    this.godmsg = "";

                    for (String str : trimmedArgs) {
                        this.godmsg = (this.godmsg + str + " ");
                    }

                    s.broadcastMessage(ChatColor.DARK_GREEN + "[SCIENCE] " + godmsg);

                    return true;
                }
                if (commandName.equalsIgnoreCase("phisays")) {
                    if (trimmedArgs.length < 1) {
                        return false;
                    }

                    this.godmsg = "";

                    for (String str : trimmedArgs) {
                        this.godmsg = (this.godmsg + str + " ");
                    }

                    s.broadcastMessage(ChatColor.AQUA + "[PHILOSOPHY] " + godmsg);

                    return true;
                }
                if (commandName.equalsIgnoreCase("ignsays")) {
                    if (trimmedArgs.length < 1) {
                        return false;
                    }

                    this.godmsg = "";

                    for (String str : trimmedArgs) {
                        this.godmsg = (this.godmsg + str + " ");
                    }

                    s.broadcastMessage(ChatColor.DARK_PURPLE + "[IGNORANCE] " + godmsg);

                    return true;
                }
                if (commandName.equalsIgnoreCase("toggleconsume")) {
                    consume = !consume;
                    s.broadcastMessage(ChatColor.LIGHT_PURPLE + "Flight will " + (consume ? "consume" : "not consume") + " feathers from now on.");

                    return true;
                }
                if (commandName.equalsIgnoreCase("cruise")) {
                    try {
                        cruise = Integer.valueOf(Integer.parseInt(trimmedArgs[0]));
                        p.sendMessage("Setting cruise level to " + cruise);
                    } catch (NumberFormatException n) {
                        p.sendMessage(ChatColor.RED + trimmedArgs[0] + " is not valid.");
                    }

                    return true;
                }
                if (commandName.equalsIgnoreCase("thrust")) {
                    try {
                        thrust = Integer.valueOf(Integer.parseInt(trimmedArgs[0]));
                        p.sendMessage("Setting thrust level to " + thrust);
                    } catch (NumberFormatException n) {
                        p.sendMessage(ChatColor.RED + trimmedArgs[0] + " is not valid.");
                    }

                    return true;
                }
            } else {
                if (commandName.equalsIgnoreCase("vinfo")) {
                    Location l = p.getLocation();
                    p.sendMessage("Your x " + (int) l.getX() + ", y " + (int) l.getY() + ", z " + (int) l.getZ() + ", rot " + (int) l.getYaw() % 360 + ", pitch " + (int) l.getPitch());
                    return true;
                }
            }
            if (commandName.equalsIgnoreCase("voxelair")) {
                if (trimmedArgs.length == 0) {
                    p.sendMessage("===================================");
                    p.sendMessage("VoxelAir settings:");
                    p.sendMessage("Allow flying: " + globalAllowFlying);
                    p.sendMessage("Allow Classic: " + allowClassicFRF);
                    p.sendMessage("Allow hovering " + allowHover);
                    p.sendMessage("Derp mode: " + derp);
                    p.sendMessage("Consuming: " + consume);
                    p.sendMessage("Cruise level: " + cruise);
                    p.sendMessage("Thrust level: " + thrust);
                    p.sendMessage("===================================");
                } else if ((trimmedArgs[0].equals("save")) && (isAdmin(p.getName()))) {
                    saveConfig();
                    p.sendMessage("Successfully saved the flyRidgeFly configuration.");
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public void loadConfig() {
        try {
            FileConfiguration config = getConfig();
            globalAllowFlying = Boolean.valueOf(config.getBoolean("globalAllowFlying", true));
            allowClassicFRF = config.getBoolean("allowClassicFRF", true);
            allowHover = config.getBoolean("allowHover", true);
            derp = Boolean.valueOf(config.getBoolean("derp", false));
            consume = Boolean.valueOf(config.getBoolean("consume", false));

            cruise = Integer.valueOf(config.getInt("cruise", 110));
            thrust = Integer.valueOf(config.getInt("thrust", 8));
        } catch (Exception ex) {
            s.getLogger().severe("Exception while loading config");
        }
    }

    @Override
    public void saveConfig() {
        FileConfiguration config = getConfig();
        config.set("globalAllowFlying", globalAllowFlying);
        config.set("allowClassicFRF", allowClassicFRF);
        config.set("allowHover", allowHover);
        config.set("derp", derp);
        config.set("consume", consume);
        config.set("cruise", cruise);
        config.set("thrust", thrust);
        try {
            config.save(config.getCurrentPath());
        } catch (IOException ex) {
            s.getLogger().severe("Could not save config");
        }
    }

    public static boolean isAdmin(String s) {
        return admns.contains(s);
    }

    public void initFly() {
        readAdmins();
    }

    public void readAdmins() {
        try {
            File fi = new File("plugins/admns.txt");
            Scanner snr = new Scanner(fi);
            while (snr.hasNext()) {
                String st = snr.nextLine();
                admns.add(st);
            }
            snr.close();
        } catch (Exception ex) {
            s.getLogger().warning("[VoxelAir] Error while loading admns.txt");
        }
    }
}
