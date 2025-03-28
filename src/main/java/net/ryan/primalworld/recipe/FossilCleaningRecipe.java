package net.ryan.primalworld.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class FossilCleaningRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack altOutput;
    private final int brushDurabilityLoss;
    private final double boneChance;
    private final double boneMealChance;
    private final ResourceLocation id;

    public FossilCleaningRecipe(ResourceLocation id, ItemStack output, ItemStack altOutput,
                                NonNullList<Ingredient> inputItems, int brushDurabilityLoss,
                                double boneChance, double boneMealChance) {
        this.inputItems = inputItems;
        this.output = output;
        this.altOutput = altOutput;
        this.brushDurabilityLoss = brushDurabilityLoss;
        this.boneChance = boneChance;
        this.boneMealChance = boneMealChance;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return inputItems.get(0).test(pContainer.getItem(0)) && inputItems.get(1).test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    public ItemStack getAltOutput() {
        return altOutput.copy();
    }

    public double getBoneChance() {
        return boneChance;
    }

    public double getBoneMealChance() {
        return boneMealChance;
    }

    public int getBrushDurabilityLoss() {
        return brushDurabilityLoss;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<FossilCleaningRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "fossil_cleaning";
    }

    public static class Serializer implements RecipeSerializer<FossilCleaningRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation("primalworld", "fossil_cleaning");

        @Override
        public FossilCleaningRecipe fromJson(ResourceLocation id, JsonObject json) {
            // Deserializando a saída principal do arquivo JSON
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            // Deserializando a saída alternativa do arquivo JSON
            ItemStack altOutput = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "alt_output"));

            // Deserializando os ingredientes
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            // Desgaste do pincel durante a limpeza
            int brushDurabilityLoss = GsonHelper.getAsInt(json, "brush_durability_loss");

            // Deserializando as chances para os itens de saída
            JsonObject chanceObject = GsonHelper.getAsJsonObject(json, "chance");
            double boneChance = GsonHelper.getAsDouble(chanceObject, "bone");
            double boneMealChance = GsonHelper.getAsDouble(chanceObject, "bone_meal");

            return new FossilCleaningRecipe(id, output, altOutput, inputs, brushDurabilityLoss, boneChance, boneMealChance);
        }

        @Override
        public FossilCleaningRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            ItemStack altOutput = buf.readItem();
            int brushDurabilityLoss = buf.readInt();
            double boneChance = buf.readDouble();
            double boneMealChance = buf.readDouble();

            return new FossilCleaningRecipe(id, output, altOutput, inputs, brushDurabilityLoss, boneChance, boneMealChance);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FossilCleaningRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(null), false);
            buf.writeItemStack(recipe.getAltOutput(), false);
            buf.writeInt(recipe.getBrushDurabilityLoss());
            buf.writeDouble(recipe.getBoneChance());
            buf.writeDouble(recipe.getBoneMealChance());
        }
    }
}
