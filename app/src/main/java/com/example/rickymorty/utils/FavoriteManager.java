package com.example.rickymorty.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.rickymorty.model.Character;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Set;

public class FavoriteManager {
    private static final String PREFS_NAME = "favorite_prefs";
    private static final String FAVORITES_KEY = "favorites";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public FavoriteManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void addFavorite(Character character) {
        Set<String> favorites = getFavorites();
        favorites.add(character.getId());
        saveFavorites(favorites);
    }

    public void removeFavorite(Character character) {
        Set<String> favorites = getFavorites();
        favorites.remove(character.getId());
        saveFavorites(favorites);
    }

    public boolean isFavorite(String characterId) {
        Set<String> favorites = getFavorites();
        return favorites.contains(characterId);
    }

    private Set<String> getFavorites() {
        String json = sharedPreferences.getString(FAVORITES_KEY, "[]");
        Type type = new TypeToken<Set<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveFavorites(Set<String> favorites) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(favorites);
        editor.putString(FAVORITES_KEY, json);
        editor.apply();
    }
}
