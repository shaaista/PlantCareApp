package com.example.plantcareapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addPlantButton = findViewById<Button>(R.id.addPlantButton)

        addPlantButton.setOnClickListener {
            // 👇 THIS OPENS THE AddPlantActivity SCREEN
            val intent = Intent(this, AddPlantActivity::class.java)
            startActivity(intent)
        }
    }
}
