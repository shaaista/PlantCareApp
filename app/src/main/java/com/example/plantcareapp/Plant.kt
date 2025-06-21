package com.example.plantcareapp

data class Plant(
    val name: String,
    val imageName: String,
    val wateringIntervalDays: Int,
    var lastWateredTime: Long
) {
    fun isThirsty(): Boolean {
        val now = System.currentTimeMillis()
        val nextWaterTime = lastWateredTime + wateringIntervalDays * 24 * 60 * 60 * 1000
        return now > nextWaterTime
    }
}
