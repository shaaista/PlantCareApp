package com.example.plantcareapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(private val plants: List<Plant>) :
    RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val plantImage: ImageView = itemView.findViewById(R.id.plantImageView)
        val nameText: TextView = itemView.findViewById(R.id.plantNameText)
        val waterText: TextView = itemView.findViewById(R.id.waterTimeText)
        val jugIcon: ImageView = itemView.findViewById(R.id.waterButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.nameText.text = plant.name

        // Placeholder image (you can use if/else later to switch happy/sad)
        holder.plantImage.setImageResource(R.drawable.ic_plant_placeholder)

        val daysLeft = plant.wateringIntervalDays -
                ((System.currentTimeMillis() - plant.lastWateredTime) / (1000 * 60 * 60 * 24)).toInt()

        holder.waterText.text = if (daysLeft <= 0) "💧 Water me!" else "💦 In $daysLeft day(s)"
    }

    override fun getItemCount(): Int = plants.size
}
