package com.github.allinkdev.tfutil;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class TextStuff {
    private static final Gson GSON = new Gson();
    private static final Map<Character, String> NORMALISATION_CACHE = ExpiringMap.builder()
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .expiration(3, TimeUnit.MINUTES)
            .build();
    private static final Map<String, String> NORMALISED_STRING_CACHE = ExpiringMap.builder()
            .expirationPolicy(ExpirationPolicy.ACCESSED)
            .expiration(10, TimeUnit.MINUTES)
            .build();
    private static final Map<String, String> NORMALISATION_MAP = new HashMap<>();

    private TextStuff() {
        //
    }

    public static void init() {
        final Class<TextStuff> clazz = TextStuff.class;
        final ClassLoader classLoader = clazz.getClassLoader();
        final InputStream resourceInputStream = classLoader.getResourceAsStream("unicodeMap.json");

        if (resourceInputStream == null) {
            throw new IllegalStateException("unicodeMap.json was not found!");
        }

        final InputStreamReader inputStreamReader = new InputStreamReader(resourceInputStream);
        final Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();


        final Map<String, String> deserialized = GSON.fromJson(inputStreamReader, mapType);
        NORMALISATION_MAP.putAll(deserialized);
    }

    public static String getReplacement(final char character) {
        if (NORMALISATION_CACHE.containsKey(character)) {
            return NORMALISATION_CACHE.get(character);
        }

        final String characterAsString = String.valueOf(character);

        for (final Map.Entry<String, String> entry : NORMALISATION_MAP.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();

            if (!value.contains(characterAsString)) {
                continue;
            }

            final String lowercaseKey = key.toLowerCase();
            NORMALISATION_CACHE.put(character, lowercaseKey);

            return lowercaseKey;
        }

        return characterAsString;
    }

    public static String getFullReplacement(final String string) {
        if (NORMALISED_STRING_CACHE.containsKey(string)) {
            return NORMALISED_STRING_CACHE.get(string);
        }

        final StringBuilder replacedStringBuilder = new StringBuilder();
        final char[] asCharacterArray = string.toCharArray();

        for (final char character : asCharacterArray) {
            final String replacement = getReplacement(character);
            replacedStringBuilder.append(replacement);
        }

        final String replacedString = replacedStringBuilder.toString();
        NORMALISED_STRING_CACHE.put(string, replacedString);

        return replacedString;
    }
}
