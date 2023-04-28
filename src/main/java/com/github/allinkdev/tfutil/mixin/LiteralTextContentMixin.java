package com.github.allinkdev.tfutil.mixin;

import com.github.allinkdev.tfutil.TextStuff;
import net.minecraft.text.LiteralTextContent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LiteralTextContent.class)
final class LiteralTextContentMixin {
    @Mutable
    @Shadow
    @Final
    private String string;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/text/LiteralTextContent;string:Ljava/lang/String;", opcode = Opcodes.PUTFIELD))
    private void onInit(final LiteralTextContent instance, final String value) {
        string = TextStuff.getFullReplacement(value);
    }
}
