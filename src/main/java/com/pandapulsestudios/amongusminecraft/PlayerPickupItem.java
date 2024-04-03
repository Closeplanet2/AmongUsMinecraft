package com.pandapulsestudios.amongusminecraft;

import com.pandapulsestudios.amongusminecraft.Enums.AlignEngineState;
import com.pandapulsestudios.pulsecore.Events.CustomEvent;
import com.pandapulsestudios.pulsecore.Inventory.InventoryAPI;
import com.pandapulsestudios.pulsecore.Loops.CustomLoop;
import com.pandapulsestudios.pulsecore.Loops.PulseLoop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import javax.swing.text.html.parser.Entity;

@CustomEvent
@CustomLoop
public class PlayerPickupItem implements Listener, PulseLoop {
    @EventHandler
    public void onPlayerPickUpItem(EntityPickupItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(EntityDropItemEvent event){
        event.getItemDrop().remove();
    }

    private int count = -1;

    @Override
    public Long StartDelay() {
        return 0L;
    }

    @Override
    public Long LoopInterval() {
        return 1L;
    }

    @Override
    public void LoopFunction() {
        for(var player : Bukkit.getOnlinePlayers()){
            if(count == -1) player.openInventory(Bukkit.createInventory(null, 54, AlignEngineState.State_000.inventoryName));
            var playerInv = player.getOpenInventory();
            count += 1;
            if(count >= AlignEngineState.values().length) count = 0;
            Bukkit.broadcastMessage(count + "");
            var alignEnginestate = AlignEngineState.values()[count];
            playerInv.setTitle(alignEnginestate.inventoryName);
            player.updateInventory();
        }
    }
}
