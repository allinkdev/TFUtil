package com.github.allinkdev.tfutil.mixin;

import net.minecraft.text.Style;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Removes chat message obfuscation entirely. It is known to cause lag, especially with CJK characters.
 */
@Mixin(Style.class)
final class StyleMixin {
    @Mutable
    @Shadow
    @Final
    @Nullable Boolean obfuscated;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/text/Style;obfuscated:Ljava/lang/Boolean;", opcode = Opcodes.PUTFIELD))
    private void onPutObfuscated(final Style instance, final Boolean value) {
        if (value == null) {
            obfuscated = null;
        }

        obfuscated = false;
    }
}
