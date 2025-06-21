package com.example.plantcareapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(private val plants: MutableList<Plant>) :
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.plantImage)
        val plantNameText: TextView = itemView.findViewById(R.id.plantNameText)
        val wateringStatusText: TextView = itemView.findViewById(R.id.wateringStatusText)
        val waterButton: ImageButton = itemView.findViewById(R.id.waterButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        val context = holder.itemView.context

        // Load image by name
        val imageResId = context.resources.getIdentifier(
            plant.imageName, "drawable", context.packageName
        )
        holder.plantImage.setImageResource(
            if (imageResId != 0) imageResId else R.drawable.ic_plant_placeholder
        )

        holder.plantNameText.text = plant.name

        // Show days left
        val now = System.currentTimeMillis()
        val nextWaterTime = plant.lastWateredTime + plant.wateringIntervalDays * 24 * 60 * 60 * 1000
        val daysLeft = ((nextWaterTime - now) / (1000 * 60 * 60 * 24)).toInt()

        holder.wateringStatusText.text = if (daysLeft <= 0) {
            "Needs water!"
        } else {
            "$daysLeft day(s) left"
        }

        holder.waterButton.setOnClickListener {
            plant.lastWateredTime = System.currentTimeMillis()
            notifyItemChanged(position)
            StorageHelper.savePlants(context, plants)
        }
    }

    override fun getItemCount(): Int = plants.size
}
