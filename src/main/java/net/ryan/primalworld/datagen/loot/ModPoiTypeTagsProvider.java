package net.ryan.primalworld.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.ryan.primalworld.primalworld;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
    public ModPoiTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, primalworld.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(new ResourceLocation(primalworld.MOD_ID, "paleotable_poi"));
    }
}