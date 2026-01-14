package net.wetstudios.horsemod.mixin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.wetstudios.horsemod.client.renderer.HorseTrimLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseRenderer.class)
public class HorseRendererMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void horsemod$addTrimlayer(EntityRendererProvider.Context context, CallbackInfo ci){
        HorseRenderer renderer = (HorseRenderer) (Object) this;
        renderer.addLayer(new HorseTrimLayer(renderer, context.getModelSet()));
    }
}
