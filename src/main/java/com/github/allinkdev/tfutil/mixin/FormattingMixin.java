package com.github.allinkdev.tfutil.mixin;

import com.github.allinkdev.tfutil.TFUtil;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Removes harsh colours from the game
 */
@Mixin(Formatting.class)
final class FormattingMixin {
    @Shadow
    @Final
    private @Nullable Integer colorValue;

    /**
     * @author Allink
     * @reason Removing harsh colours from the game
     */
    @Overwrite
    @Nullable
    public Integer getColorValue() {
        if (this.colorValue == null) {
            return null;
        }

        return TFUtil.COLOUR_VALUE_REPLACEMENTS.getOrDefault(this.colorValue, this.colorValue);
    }
}
