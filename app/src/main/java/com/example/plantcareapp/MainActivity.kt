package com.example.plantcareapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var plantRecyclerView: RecyclerView
    private lateinit var plantAdapter: PlantAdapter
    private var plantList = mutableListOf<Plant>() // This holds all plant data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup views
        val addPlantButton: Button = findViewById(R.id.addPlantButton)
        plantRecyclerView = findViewById(R.id.plantRecyclerView)

        // Load saved plant list
        plantList = StorageHelper.loadPlants(this)

        // Setup RecyclerView
        plantAdapter = PlantAdapter(plantList)
        plantRecyclerView.layoutManager = LinearLayoutManager(this)
        plantRecyclerView.adapter = plantAdapter

        // Button: Add new plant
        addPlantButton.setOnClickListener {
            val intent = Intent(this, AddPlantActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    // Handle result from AddPlantActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("plantName")
            val type = data.getStringExtra("plantType")

            if (name != null && type != null) {
                val imageName = type.lowercase()
                val interval = when (type) {
                    "Cactus" -> 3
                    "Monstera" -> 2
                    "Fern" -> 1
                    else -> 3
                }

                val newPlant = Plant(name, imageName, interval, System.currentTimeMillis())
                plantList.add(newPlant) // ✅ Add to list
                StorageHelper.savePlants(this, plantList) // ✅ Save to local storage
                plantAdapter.notifyItemInserted(plantList.size - 1) // ✅ Show immediately
            }
        }
    }
}
