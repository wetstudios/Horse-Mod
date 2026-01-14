package net.wetstudios.horsemod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;

public class HorseTrimLayer extends RenderLayer<Horse, HorseModel<Horse>> {
    private final HorseModel<Horse> model;
    public HorseTrimLayer(RenderLayerParent<Horse, HorseModel<Horse>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new HorseModel<>(modelSet.bakeLayer(ModelLayers.HORSE));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Horse horse, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack armorStack = horse.getArmorSlots().iterator().next(); // Get horse armor

        if (!armorStack.isEmpty()) {
            // Check for the Trim Data Component (1.21 style)
            ArmorTrim trim = armorStack.get(DataComponents.TRIM);
            if (trim != null) {
                // 1. Get the Pattern's asset ID (e.g., "minecraft:sentry")
                ResourceLocation patternId = trim.pattern().value().assetId();

                // 2. Construct the path manually to our custom horse_armor folder
                // This will point to: assets/[namespace]/textures/trims/models/horse_armor/[pattern_name].png
                ResourceLocation horseTrimRes = ResourceLocation.fromNamespaceAndPath(
                        patternId.getNamespace(),
                        "textures/trims/models/horse_armor/" + patternId.getPath() + ".png"
                );

                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(horse, limbSwing, limbSwingAmount, partialTick);
                this.model.setupAnim(horse, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.armorCutoutNoCull(horseTrimRes));
                this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}
