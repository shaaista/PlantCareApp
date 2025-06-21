package com.example.plantcareapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(
    private val plants: MutableList<Plant>
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plantImage)
        val plantNameText: TextView = view.findViewById(R.id.plantNameText)
        val wateringStatusText: TextView = view.findViewById(R.id.wateringStatusText)
        val waterButton: ImageButton = view.findViewById(R.id.waterButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]

        holder.plantNameText.text = plant.name

        val daysLeft = plant.wateringIntervalDays -
                ((System.currentTimeMillis() - plant.lastWateredTime) / (1000 * 60 * 60 * 24)).toInt()

        holder.wateringStatusText.text =
            if (daysLeft <= 0) "Needs Water 💧" else "$daysLeft days left"

        // Set plant image
        val context = holder.itemView.context
        val imageRes = context.resources.getIdentifier(
            "${plant.imageName}_happy", "drawable", context.packageName
        )
        holder.plantImage.setImageResource(imageRes)

        // Watering
        holder.waterButton.setOnClickListener {
            plant.lastWateredTime = System.currentTimeMillis()
            StorageHelper.savePlants(holder.itemView.context, plants)
            notifyItemChanged(position)
        }
    }
}
