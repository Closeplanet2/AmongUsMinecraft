package com.pandapulsestudios.amongusminecraft.Items.VentItems;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.pulsecore.Items.CustomItemStack;
import com.pandapulsestudios.pulsecore.Items.ItemLocation;
import com.pandapulsestudios.pulsecore.Items.PulseItemStack;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@CustomItemStack
public class AdminVentItems implements PulseItemStack {
    @Override
    public String itemName() { return GameTaskArea.Admin.name(); }

    @Override
    public Material itemType() { return GameTaskArea.Admin.ventMaterial; }

    @Override
    public HashMap<String, String> nbtTags() {
        var hashmap = new HashMap<String, String>();
        hashmap.put("NAMEEEE", itemName());
        return hashmap;
    }
}


