package com.example.plantcareapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StorageHelper {
    private const val PREF_NAME = "PlantPrefs"
    private const val KEY_PLANTS = "plants"

    fun savePlants(context: Context, plants: List<Plant>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(plants)
        editor.putString(KEY_PLANTS, json)
        editor.apply()
    }

    fun loadPlants(context: Context): MutableList<Plant> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_PLANTS, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Plant>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}
