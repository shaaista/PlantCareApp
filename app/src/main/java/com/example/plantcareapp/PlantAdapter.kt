package com.example.plantcareapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.concurrent.TimeUnit

class PlantAdapter(
    private val context: Context,
    private val plants: List<Plant>,
    private val onClick: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = plants.size
    override fun getItem(position: Int): Any = plants[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.plant_item, parent, false)

        val plant = plants[position]
        val image = view.findViewById<ImageView>(R.id.plantImage)
        val name = view.findViewById<TextView>(R.id.plantName)
        val timer = view.findViewById<TextView>(R.id.plantTimer)

        // Refresh image based on plant state
        val imageId = context.resources.getIdentifier(plant.getImageName(), "drawable", context.packageName)
        image.setImageResource(imageId)

        name.text = plant.name
        timer.text = formatTime(plant.getTimeLeft())

        view.setOnClickListener { onClick(position) }

        return view
    }


    private fun formatTime(ms: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(ms)
        val hours = minutes / 60
        val days = hours / 24
        return when {
            days > 0 -> "$days day(s) left"
            hours > 0 -> "$hours hour(s) left"
            minutes > 0 -> "$minutes min(s) left"
            else -> "Needs water!"
        }
    }
}
