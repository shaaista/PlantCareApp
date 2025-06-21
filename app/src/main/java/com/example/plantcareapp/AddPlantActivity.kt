package com.example.plantcareapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AddPlantActivity : AppCompatActivity() {

    private lateinit var plantType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        val cactus = findViewById<ImageView>(R.id.cactusImage)
        val monstera = findViewById<ImageView>(R.id.monsteraImage)

        cactus.setOnClickListener { choosePlant("cactus") }
        monstera.setOnClickListener { choosePlant("monstera") }
    }

    private fun choosePlant(type: String) {
        plantType = type

        val input = EditText(this)
        input.hint = "Enter plant name"

        AlertDialog.Builder(this)
            .setTitle("Name Your Plant")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val name = input.text.toString()
                if (name.isNotBlank()) {
                    val plant = Plant(name, plantType, System.currentTimeMillis())
                    val list = StorageHelper.loadPlants(this)
                    list.add(plant)
                    StorageHelper.savePlants(this, list)
                    finish() // go back to MainActivity
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
