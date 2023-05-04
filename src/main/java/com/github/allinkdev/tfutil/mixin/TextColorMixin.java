package com.github.allinkdev.tfutil.mixin;

import com.github.allinkdev.tfutil.Tuple;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Removes RGB colors from the game
 */
@Mixin(TextColor.class)
final class TextColorMixin {
    private static final Map<Integer, TextColor> RGB_INTERPOLATION_CACHE = ExpiringMap.builder()
            .expiration(5, TimeUnit.MINUTES)
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .build();
    private static final TextColor BLACK = TextColor.fromFormatting(Formatting.BLACK);
    private static final int BLACK_RGB = Objects.requireNonNull(BLACK).getRgb();
    @Shadow
    @Final
    private static Map<Formatting, TextColor> FORMATTING_TO_COLOR;

    /**
     * @author Allink
     * @reason Remove RGB formatting
     */
    @Overwrite
    public static TextColor fromRgb(final int rgb) {
        if (RGB_INTERPOLATION_CACHE.containsKey(rgb)) {
            return RGB_INTERPOLATION_CACHE.get(rgb);
        }

        Tuple<TextColor, Integer, Integer> closest = Tuple.from(BLACK, BLACK_RGB, rgb - BLACK_RGB);

        for (final Map.Entry<Formatting, TextColor> entry : FORMATTING_TO_COLOR.entrySet()) {
            final TextColor value = entry.getValue();

            final int allowedRgb = value.getRgb();
            final int difference = Math.abs(rgb - allowedRgb);
            final int currentBestDifference = closest.third();

            if (currentBestDifference <= difference) {
                continue;
            }

            closest = Tuple.from(value, allowedRgb, difference);
        }

        final TextColor interpolatedValue = closest.first();
        RGB_INTERPOLATION_CACHE.put(rgb, interpolatedValue);

        return interpolatedValue;
    }
}
