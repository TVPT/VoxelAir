package com.thevoxelbox.voxelair;

import java.util.HashSet;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    private HashSet<String> hovering = new HashSet<String>();
    int count = 0;
    Random rnd = new Random();
    boolean checked = false;
    int featherAmount;
    int first;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (hovering.contains(event.getPlayer().getName())) {
            event.getPlayer().setVelocity(event.getPlayer().getVelocity().setY(0.2));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getItemInHand().getType().equals(Material.FEATHER)) {
            if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (VoxelAir.isAdmin(e.getPlayer().getName())) {
                    if (hovering.contains(e.getPlayer().getName())) {
                        hovering.remove(e.getPlayer().getName());
                        e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "You feel the pull of the earth beneath you.");
                        return;
                    } else {
                        hovering.add(e.getPlayer().getName());
                        e.getPlayer().sendMessage(ChatColor.BLUE + "A gentle wind holds you aloft...");
                        return;
                    }
                } else if (VoxelAir.allowHover) {
                    if (hovering.contains(e.getPlayer().getName())) {
                        hovering.remove(e.getPlayer().getName());
                        e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "You feel the pull of the earth beneath you.");
                        return;
                    } else {
                        hovering.add(e.getPlayer().getName());
                        e.getPlayer().sendMessage(ChatColor.BLUE + "A gentle wind holds you aloft...");
                        return;
                    }
                }
            }
            if ((e.getAction().equals(Action.RIGHT_CLICK_AIR)) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                if (VoxelAir.isAdmin(e.getPlayer().getName())) {
                    fly(e.getPlayer());
                    return;
                } else if (VoxelAir.globalAllowFlying) {
                    onArmSwing(e.getPlayer());
                    return;
                }
            }
        }
        if (VoxelAir.derp) {
            VoxelAir.elevn = this.rnd.nextInt(40000);
            switch (VoxelAir.elevn) {
                case 10210:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Derp");
                    break;
                case 7050:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Herp derp");
                    break;
                case 4030:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Derp derp");
                    break;
                case 3245:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Derp a derp, herp derp");
                    break;
                case 789:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Gentlemen.");
                    break;
                case 1223:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Hurrrrr");
                    break;
                case 4556:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Durrrrr");
                    break;
                case 2314:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Hurrrrr Durrrrr");
                    break;
                case 25721:
                    VoxelAir.s.broadcastMessage("<" + e.getPlayer().getName() + ChatColor.WHITE + "> Herp te Derp");
                    break;
            }
        }
    }

    private void fly(Player player) {
        String playerName = player.getName();
        if (player.getItemInHand().getType().equals(Material.FEATHER)) {
            double cProt = player.getLocation().getYaw() % 360.0F;
            if (cProt > 0.0D) {
                cProt -= 720.0D;
            }
            double pRot = Math.abs(cProt % 360.0D);
            double pX = 0.0D;
            double pZ = 0.0D;
            double pY = 0.0D;
            double pPit = player.getLocation().getPitch();
            double pyY = 0.0D;
            if ((pPit < 21.0D) && (pPit > -21.0D)) {
                pX = Math.sin(Math.toRadians(pRot)) * 10.0D;
                pZ = Math.cos(Math.toRadians(pRot)) * 10.0D;
                if ((player.getLocation().getY() > VoxelAir.cruise) && (player.getLocation().getY() <= VoxelAir.cruise + 5)) {
                    pY = 1.0D;
                } else if (player.getLocation().getY() > VoxelAir.cruise + 5) {
                    pY = 0.0D;
                } else if (player.getLocation().getY() < VoxelAir.cruise) {
                    pY = 2.5D;
                }
            } else {
                pX = Math.sin(Math.toRadians(pRot)) * 6.0D;
                pZ = Math.cos(Math.toRadians(pRot)) * 6.0D;
                if (pPit < 0.0D) {
                    pY = Math.sin(Math.toRadians(Math.abs(pPit))) * 10.0D;
                    pyY = Math.cos(Math.toRadians(Math.abs(pPit))) * 10.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * pyY;
                    pZ = Math.cos(Math.toRadians(pRot)) * pyY;
                } else if ((pPit > 0.0D) && (pPit < 30.0D)) {
                    pY = 4.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * 6.0D;
                    pZ = Math.cos(Math.toRadians(pRot)) * 6.0D;
                } else if ((pPit >= 30.0D) && (pPit < 60.0D)) {
                    pY = 5.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * 3.0D;
                    pZ = Math.cos(Math.toRadians(pRot)) * 3.0D;
                } else if ((pPit >= 60.0D) && (pPit < 75.0D)) {
                    pY = 6.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * 1.5D;
                    pZ = Math.cos(Math.toRadians(pRot)) * 1.5D;
                } else if (pPit >= 75.0D) {
                    pY = VoxelAir.thrust;
                    pX = 0.0D;
                    pZ = 0.0D;
                }
            }
            if (player.isSneaking()) {
                player.teleport(new Location(player.getWorld(), player.getLocation().getX() + pX, player.getLocation().getY() + pY, player.getLocation().getZ() + pZ, player.getLocation().getYaw(), player.getLocation().getPitch()));
            } else {
                player.setVelocity(new Vector(pX, pY / 2.5, pZ));
            }
        }
    }

    public void onArmSwing(Player player) {
        String playerName = player.getName();
        if (player.isSneaking() && !VoxelAir.allowClassicFRF) {
            player.sendMessage("You are not allowed access to classic flyRidgeFly");
            return;
        }
        if (player.getItemInHand().getType().equals(Material.FEATHER)) {
            double cProt = player.getLocation().getYaw() % 360.0F;
            if (cProt > 0.0D) {
                cProt -= 720.0D;
            }
            if (VoxelAir.consume) {
                if (this.count < 5) {
                    this.count += 1;
                } else {
                    int firstSlot = player.getInventory().first(Material.FEATHER);
                    int num = player.getInventory().getItem(firstSlot).getAmount();

                    if (num == 1) {
                        player.getInventory().clear(firstSlot);
                    } else if (num > 1) {
                        player.getInventory().getItem(firstSlot).setAmount(num - 1);
                    } else {
                        player.getInventory().clear(firstSlot);
                    }
                    this.count = 0;
                }
            }
            double pRot = Math.abs(cProt % 360.0D);
            double pX = 0.0D;
            double pZ = 0.0D;
            double pY = 0.0D;
            double pPit = player.getLocation().getPitch();
            double pyY = 0.0D;
            if ((pPit < 21.0D) && (pPit > -21.0D)) {
                pX = Math.sin(Math.toRadians(pRot)) * 10.0D;
                pZ = Math.cos(Math.toRadians(pRot)) * 10.0D;
                if ((player.getLocation().getY() > VoxelAir.cruise) && (player.getLocation().getY() <= VoxelAir.cruise + 5)) {
                    pY = 1.0D;
                } else if (player.getLocation().getY() > VoxelAir.cruise + 5) {
                    pY = 0.0D;
                } else if (player.getLocation().getY() < VoxelAir.cruise) {
                    pY = 2.5D;
                }
            } else {
                pX = Math.sin(Math.toRadians(pRot)) * 6.0D;
                pZ = Math.cos(Math.toRadians(pRot)) * 6.0D;
                if (pPit < 0.0D) {
                    pY = Math.sin(Math.toRadians(Math.abs(pPit))) * 10.0D;
                    pyY = Math.cos(Math.toRadians(Math.abs(pPit))) * 10.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * pyY;
                    pZ = Math.cos(Math.toRadians(pRot)) * pyY;
                } else if ((pPit > 0.0D) && (pPit < 30.0D)) {
                    pY = 4.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * 6.0D;
                    pZ = Math.cos(Math.toRadians(pRot)) * 6.0D;
                } else if ((pPit >= 30.0D) && (pPit < 60.0D)) {
                    pY = 5.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * 3.0D;
                    pZ = Math.cos(Math.toRadians(pRot)) * 3.0D;
                } else if ((pPit >= 60.0D) && (pPit < 75.0D)) {
                    pY = 6.0D;
                    pX = Math.sin(Math.toRadians(pRot)) * 1.5D;
                    pZ = Math.cos(Math.toRadians(pRot)) * 1.5D;
                } else if (pPit >= 75.0D) {
                    pY = VoxelAir.thrust;
                    pX = 0.0D;
                    pZ = 0.0D;
                }
            }
            if (player.isSneaking()) {
                player.teleport(new Location(player.getWorld(), player.getLocation().getX() + pX, player.getLocation().getY() + pY, player.getLocation().getZ() + pZ, player.getLocation().getYaw(), player.getLocation().getPitch()));
            } else {
                player.setVelocity(new Vector(pX, pY / 2.5, pZ));
            }
        }
    }
}
