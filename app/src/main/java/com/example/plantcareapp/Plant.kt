package com.example.plantcareapp

data class Plant(
    val name: String,
    val type: String,
    var lastWatered: Long
) {
    fun isThirsty(): Boolean {
        val now = System.currentTimeMillis()
        val interval = if (type == "cactus") 7 * 24 * 60 * 60 * 1000L else 5 * 60 * 1000L
        return now > lastWatered + interval
    }

    fun getImageName(): String {
        return if (isThirsty()) "${type}_sad" else "${type}_happy"
    }

    fun getTimeLeft(): Long {
        val interval = if (type == "cactus") 2 * 60 * 1000L else 1 * 60 * 1000L

        return (lastWatered + interval - System.currentTimeMillis()).coerceAtLeast(0)
    }
}
