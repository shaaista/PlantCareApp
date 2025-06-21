package com.example.plantcareapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StorageHelper {
    private const val PREFS = "plant_prefs"
    private const val KEY = "plants"

    fun savePlants(context: Context, plants: List<Plant>) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = Gson().toJson(plants)
        prefs.edit().putString(KEY, json).apply()
    }

    fun loadPlants(context: Context): MutableList<Plant> {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Plant>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}
