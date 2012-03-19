package com.thevoxelbox.voxelair;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if ((event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
                && ((event.getEntity() instanceof Player))) {
            try {
                if (((Player) event.getEntity()).getItemInHand().getType().equals(Material.FEATHER)) {
                    event.setCancelled(Boolean.TRUE.booleanValue());
                }
            } catch (Exception e) {
                VoxelAir.log.warning("[VoxelAir] Error on damage");
                e.printStackTrace();
            }
        }
    }
}
