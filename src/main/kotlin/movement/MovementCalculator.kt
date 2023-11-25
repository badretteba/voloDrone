package movement

import input.Drone

object MovementCalculator {
    fun calculateMovement(direction: String, distance: Int, drone: Drone): Triple<Int, Int, Int> {
        return when (direction.uppercase()) {
            "LEFT" -> Triple(0, -distance, 0)
            "RIGHT" -> Triple(0, distance, 0)
            "UP" -> Triple(0, 0, distance)
            "DOWN" -> Triple(0, 0, -distance)
            "FORWARD" -> Triple(distance, 0, 0)
            "BACKWARD" -> Triple(-distance, 0, 0)
            else -> Triple(0, 0, 0)
        }
    }

    private const val MAX_X = 10 // Replace this with the maximum X value of your world
    // ... other constants or logic for world boundaries ...
}
