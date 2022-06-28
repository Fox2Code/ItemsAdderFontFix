package com.fox2code.itemsadderfontfix.mixin;

import com.fox2code.itemsadderfontfix.FontLoaderWrapper;
import com.google.gson.JsonObject;
import net.minecraft.client.font.FontLoader;
import net.minecraft.client.font.FontType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Function;

@Mixin(FontType.class)
public class MixinFontType {
    @Shadow @Final private Function<JsonObject, FontLoader> loaderFactory;

    /**
     * @author Fox2Code
     * @reason I don't know how to mixin try catch.
     */
    @Overwrite
    public FontLoader createLoader(JsonObject json) {
        try {
            return new FontLoaderWrapper(this.loaderFactory.apply(json), json);
        } catch (Exception e) {
            FontLoaderWrapper.logFail(e, json);
            return __ -> null; // Do not make game panic
        }
    }
}
