package com.pandapulsestudios.amongusminecraft.Items.VentItems;

import com.pandapulsestudios.amongusminecraft.Enums.GameTaskArea;
import com.pandapulsestudios.pulsecore.Items.CustomItemStack;
import com.pandapulsestudios.pulsecore.Items.ItemLocation;
import com.pandapulsestudios.pulsecore.Items.PulseItemStack;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@CustomItemStack
public class SecurityVentItem implements PulseItemStack {
    @Override
    public String itemName() { return GameTaskArea.Security.name(); }

    @Override
    public Material itemType() { return GameTaskArea.Security.ventMaterial; }

    @Override
    public HashMap<String, String> nbtTags() {
        var hashmap = new HashMap<String, String>();
        hashmap.put("NAMEEEE", itemName());
        return hashmap;
    }
}
