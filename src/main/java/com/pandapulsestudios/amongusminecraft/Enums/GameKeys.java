package com.pandapulsestudios.amongusminecraft.Enums;

import org.bukkit.entity.Player;

public class GameKeys {
    public static final String TRAITOR_KEY = "TRAITOR";
    public static final String VENT_MAP_KEY = "VENT_MAP";
    public static String PLAYER_TASKS(Player player){ return String.format("TASKS:%s", player.getUniqueId()); }
}
