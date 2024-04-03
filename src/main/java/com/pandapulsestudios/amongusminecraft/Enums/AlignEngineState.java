package com.pandapulsestudios.amongusminecraft.Enums;

import org.bukkit.ChatColor;

public enum AlignEngineState {
    State_000(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE00"),
    State_001(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE03"),
    State_002(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE04"),
    State_003(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE01"),
    State_004(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE06"),
    State_005(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE05"),
    State_006(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE02"),
    State_007(ChatColor.WHITE + "七七七七七七七七\uD83E\uDD23"),
    State_008(ChatColor.WHITE + "七七七七七七七七\uD83E\uDD72"),
    State_009(ChatColor.WHITE + "七七七七七七七七\uD83E\uDD79"),
    State_010(ChatColor.WHITE + "七七七七七七七七☺\uFE0F"),
    State_011(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE0A"),
    State_012(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE07"),
    State_013(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE42"),
    State_014(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE43"),
    State_015(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE09"),
    State_016(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE0C"),
    State_017(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE0D"),
    State_018(ChatColor.WHITE + "七七七七七七七七\uD83E\uDD70"),
    State_019(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE18"),
    State_020(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE17"),
    State_021(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE19"),
    State_022(ChatColor.WHITE + "七七七七七七七七\uD83D\uDE1A");

    public final String inventoryName;
    AlignEngineState(String inventoryName){
        this.inventoryName = inventoryName;
    }
}
