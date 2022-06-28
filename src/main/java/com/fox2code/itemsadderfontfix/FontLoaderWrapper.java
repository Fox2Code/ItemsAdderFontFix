package com.fox2code.itemsadderfontfix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontLoader;
import net.minecraft.resource.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class FontLoaderWrapper implements FontLoader {
    private static final Logger logger = LogUtils.getLogger();
    private static final Gson gson =
            new GsonBuilder().setPrettyPrinting().create();
    private final FontLoader fontLoader;
    private final JsonObject jsonObject;

    public static void logFail(Exception fail, JsonObject jsonObject) {
        logger.warn("Failed to load fontProvider: \n" + gson.toJson(jsonObject), fail);
    }

    public FontLoaderWrapper(FontLoader fontLoader, JsonObject jsonObject) {
        this.fontLoader = fontLoader;
        this.jsonObject = jsonObject;
    }

    @Nullable
    @Override
    public Font load(ResourceManager manager) {
        try {
            return this.fontLoader.load(manager);
        } catch (Exception e) {
            logFail(e, this.jsonObject);
            return null;
        }
    }
}
