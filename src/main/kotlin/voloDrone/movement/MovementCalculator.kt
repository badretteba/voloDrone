package voloDrone.movement

import voloDrone.model.Drone
import voloDrone.model.Movement

object MovementCalculator {
    fun calculateMovement(direction: String, distance: Int): Movement {
        return when (direction.uppercase()) {
            "LEFT" -> Movement(0, -distance, 0)
            "RIGHT" -> Movement(0, distance, 0)
            "UP" -> Movement(0, 0, distance)
            "DOWN" -> Movement(0, 0, -distance)
            "FORWARD" -> Movement(distance, 0, 0)
            "BACKWARD" -> Movement(-distance, 0, 0)
            else -> Movement(0, 0, 0)
        }
    }
}
