package com.example.plantcareapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var plantRecyclerView: RecyclerView
    private lateinit var addPlantButton: Button
    private lateinit var plantList: MutableList<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plantRecyclerView = findViewById(R.id.plantRecyclerView)
        addPlantButton = findViewById(R.id.addPlantButton)

        loadPlants()

        plantRecyclerView.layoutManager = LinearLayoutManager(this)
        plantRecyclerView.adapter = PlantAdapter(plantList)

        addPlantButton.setOnClickListener {
            val intent = Intent(this, AddPlantActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadPlants() {
        val prefs = getSharedPreferences("plants", MODE_PRIVATE)
        val gson = Gson()
        val listJson = prefs.getString("plantList", null)
        val type = object : TypeToken<MutableList<Plant>>() {}.type
        plantList = if (listJson != null) {
            gson.fromJson(listJson, type)
        } else {
            mutableListOf()
        }
    }
}
