package com.github.allinkdev.tfutil;

import net.fabricmc.api.ClientModInitializer;

import java.util.Map;

public final class TFUtil implements ClientModInitializer {
    public static final Map<Integer, Integer> COLOUR_VALUE_REPLACEMENTS = Map.ofEntries(
            Map.entry(0, 11184810), // Black -> Gray
            Map.entry(5592405, 11184810), // Dark Gray -> Gray
            Map.entry(170, 5592575), // Dark Blue -> Blue
            Map.entry(43520, 5635925), // Dark Green -> Green
            Map.entry(43690, 5636095), // Dark Aqua -> Aqua
            Map.entry(11141120, 16733525), // Dark Red -> Red
            Map.entry(11141290, 16733695) // Dark Purple -> Light Purple
    );

    @Override
    public void onInitializeClient() {
        //
    }
}
