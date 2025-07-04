package com.example.plantcareapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.media.MediaPlayer
import android.view.animation.ScaleAnimation

class MainActivity : AppCompatActivity() {

    private lateinit var plantList: MutableList<Plant>
    private lateinit var adapter: PlantAdapter
    private val handler = Handler(Looper.getMainLooper())
    private val refreshRunnable = object : Runnable {
        override fun run() {
            adapter.notifyDataSetChanged()
            handler.postDelayed(this, 60000) // refresh every minute
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }
        val title: TextView = findViewById(R.id.titleText)
        val grid: GridView = findViewById(R.id.gridView)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        plantList = StorageHelper.loadPlants(this)
        adapter = PlantAdapter(this, plantList) { position ->
            showPlantPopup(plantList[position], position)
        }
        grid.adapter = adapter

        fab.setOnClickListener {
            startActivity(Intent(this, AddPlantActivity::class.java))
        }

        handler.post(refreshRunnable)
    }

    override fun onResume() {
        super.onResume()
        plantList.clear()
        plantList.addAll(StorageHelper.loadPlants(this))
        adapter.notifyDataSetChanged()
    }

    private fun showPlantPopup(plant: Plant, index: Int) {
        val view = layoutInflater.inflate(R.layout.plant_popup, null)
        val img = view.findViewById<android.widget.ImageView>(R.id.popupImage)
        val name = view.findViewById<TextView>(R.id.popupName)
        val timer = view.findViewById<TextView>(R.id.popupTimer)
        val water = view.findViewById<android.widget.ImageView>(R.id.popupWater)

        val resId = resources.getIdentifier(plant.getImageName(), "drawable", packageName)
        img.setImageResource(resId)
        name.text = plant.name
        timer.text = formatTime(plant.getTimeLeft())

        val dialog = AlertDialog.Builder(this).setView(view).create()
        water.setOnClickListener {
            // 💧 1. Play Watering Sound
            val mediaPlayer = android.media.MediaPlayer.create(this, R.raw.water_sound)
            mediaPlayer.start()

            // 🌱 2. Apply Bounce Animation to Plant Image
            val scale = android.view.animation.ScaleAnimation(
                1f, 1.2f, 1f, 1.2f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f
            )
            scale.duration = 300
            scale.repeatMode = android.view.animation.Animation.REVERSE
            scale.repeatCount = 1
            img.startAnimation(scale)

            // 🕒 3. Reset Timer and Save
            plant.lastWatered = System.currentTimeMillis()
            StorageHelper.savePlants(this, plantList)
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun formatTime(ms: Long): String {


        val mins = ms / 60000
        val hours = mins / 60
        val days = hours / 24
        return when {
            days > 0 -> "$days day(s) left"
            hours > 0 -> "$hours hour(s) left"
            mins > 0 -> "$mins min(s) left"
            else -> "Needs water!"
        }
    }
}
