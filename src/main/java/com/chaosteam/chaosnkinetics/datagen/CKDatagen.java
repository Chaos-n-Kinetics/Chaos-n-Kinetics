package com.chaosteam.chaosnkinetics.datagen;

import com.chaosteam.chaosnkinetics.ChaosKinetics;
import com.chaosteam.chaosnkinetics.compat.ponder.CKPonderPlugin;
import com.chaosteam.chaosnkinetics.datagen.tags.ItemTagProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.function.BiConsumer;

public class CKDatagen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(ChaosKinetics.MODID)) {
            ItemTagProvider.addGen();

            ChaosKinetics.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
                BiConsumer<String, String> langConsumer = provider::add;

                provideDefaultLang("interface", langConsumer);
                providePonderLang(langConsumer);
            });
        }
    }

    // Thanks to Create Connected for this stuff:
    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/chaosnkinetics/lang/default/" + fileName + ".json";
        var jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }

    private static void providePonderLang(BiConsumer<String, String> consumer) {
        // Register this since FMLClientSetupEvent does not run during datagen
        PonderIndex.addPlugin(new CKPonderPlugin());

        PonderIndex.getLangAccess().provideLang(ChaosKinetics.MODID, consumer);
    }
}
