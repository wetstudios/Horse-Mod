package net.wetstudios.horsemod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HorseshoeLayer extends RenderLayer<Horse, HorseModel<Horse>> {
    private static final ResourceLocation HORSESHOE_LOCATION =
            ResourceLocation.fromNamespaceAndPath("horsemod", "textures/entity/horse/armor/horseshoes.png");
    public HorseshoeLayer(RenderLayerParent<Horse, HorseModel<Horse>> renderer) {
        super(renderer);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Horse horse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        // 1. Access the new slot we created in the Mixin
        // For a standard horse, the new slot index is 2 (Saddle=0, Armor=1, Shoes=2)
        // Grab the item from the shoes custom slot (idx 2)
        ItemStack shoeStack = horse.getInventory().getItem(2);

        if (!shoeStack.isEmpty()) {
            // 2. Setup the model animations
            this.getParentModel().prepareMobModel(horse, limbSwing, limbSwingAmount, partialTick);
            this.getParentModel().setupAnim(horse, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            // 3. FIX: Use the 'buffer' variable from the method parameters above
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(HORSESHOE_LOCATION));

            // 4. Render the hooves
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
