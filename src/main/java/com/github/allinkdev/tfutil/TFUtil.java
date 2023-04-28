package com.github.allinkdev.tfutil;

import net.fabricmc.api.ClientModInitializer;

public final class TFUtil implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TextStuff.init();
    }
}
