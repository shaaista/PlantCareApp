package com.example.plantcareapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddPlantActivity : AppCompatActivity() {

    private var selectedPlantType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        val plantNameInput = findViewById<EditText>(R.id.plantNameInput)
        val selectImageButton = findViewById<Button>(R.id.selectImageButton)
        val savePlantButton = findViewById<Button>(R.id.savePlantButton)

        selectImageButton.setOnClickListener {
            // Show a list of plant types
            val plantTypes = arrayOf("Cactus", "Monstera", "Fern")

            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Choose a plant type")

            builder.setItems(plantTypes) { _, which ->
                selectedPlantType = plantTypes[which]
                Toast.makeText(this, "Selected: $selectedPlantType", Toast.LENGTH_SHORT).show()
            }

            builder.show()
        }

        savePlantButton.setOnClickListener {
            val plantName = plantNameInput.text.toString()

            if (plantName.isBlank()) {
                Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show()
            } else if (selectedPlantType == null) {
                Toast.makeText(this, "Please select a plant type!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Plant $plantName ($selectedPlantType) added!",
                    Toast.LENGTH_SHORT
                ).show()

                // 1. Set image name and watering interval
                val imageName = when (selectedPlantType) {
                    "Cactus" -> "cactus"
                    "Monstera" -> "monstera"
                    "Fern" -> "fern"
                    else -> "plant_placeholder"
                }
                val wateringInterval = when (selectedPlantType) {
                    "Cactus" -> 5
                    "Monstera" -> 3
                    "Fern" -> 2
                    else -> 3
                }

// 2. Create the Plant object
                val plant = Plant(
                    name = plantName,
                    imageName = imageName,
                    wateringIntervalDays = wateringInterval,
                    lastWateredTime = System.currentTimeMillis()
                )

// 3. Save it using StorageHelper
                val existing = StorageHelper.loadPlants(this)
                existing.add(plant)
                StorageHelper.savePlants(this, existing)

// 4. Finish
                Toast.makeText(this, "Plant $plantName added!", Toast.LENGTH_SHORT).show()
                finish()

            }
        }
    }
}
