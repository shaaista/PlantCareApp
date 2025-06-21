package com.example.plantcareapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var plantAdapter: PlantAdapter
    private var plantList = mutableListOf<Plant>()

    // This handles result from AddPlantActivity
    private val addPlantLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val name = data?.getStringExtra("plantName")
            val type = data?.getStringExtra("plantType")

            if (name != null && type != null) {
                val wateringInterval = when (type) {
                    "Cactus" -> 3
                    "Monstera" -> 2
                    "Fern" -> 1
                    else -> 2
                }

                val imageName = "${type.lowercase()}_happy"

                val newPlant = Plant(
                    name,
                    imageName,
                    wateringInterval,
                    System.currentTimeMillis()
                )

                plantList.add(newPlant)
                StorageHelper.savePlants(this, plantList)
                plantAdapter.notifyItemInserted(plantList.size - 1)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plantList = StorageHelper.loadPlants(this)

        val recyclerView = findViewById<RecyclerView>(R.id.plantRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        plantAdapter = PlantAdapter(plantList)
        recyclerView.adapter = plantAdapter

        val addPlantButton = findViewById<Button>(R.id.addPlantButton)
        addPlantButton.setOnClickListener {
            val intent = Intent(this, AddPlantActivity::class.java)
            addPlantLauncher.launch(intent)
        }
    }
}
