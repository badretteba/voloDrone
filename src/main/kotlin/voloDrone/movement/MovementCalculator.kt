package voloDrone.movement

import voloDrone.model.Drone
import voloDrone.model.Movement
import voloDrone.model.ParsedSensorData
import voloDrone.movement.collision.CollisionDetector
import voloDrone.output.OutputGenerator

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
    fun processParsedData(parsedData: ParsedSensorData, totalDistanceFlown: Int): Int {
        var totalDistance = totalDistanceFlown
        val drone = parsedData.drone
        val world = parsedData.world
        val commands = parsedData.commands
        OutputGenerator.generateInitialLogs(world.width, world.depth, world.height, drone)
        OutputGenerator.generateTakeOffLog()
        for (command in commands){
            val movement = MovementCalculator.calculateMovement(command.direction, command.distance)
            if (CollisionDetector.willCollide(world, drone, movement)) {
                OutputGenerator.generateMovementLog(movement, drone, totalDistance, true)
                val correctedMovement = CollisionDetector.resolveCollision(world, drone, movement)
                drone.x += correctedMovement.x
                drone.y += correctedMovement.y
                drone.z += correctedMovement.z
                totalDistance += correctedMovement.x + correctedMovement.y + correctedMovement.z
                OutputGenerator.generateMovementLog(correctedMovement, drone, totalDistance)
            } else {
                drone.x += movement.x
                drone.y += movement.y
                drone.z += movement.z
                totalDistance += command.distance
                OutputGenerator.generateMovementLog(Movement(movement.x, movement.y, movement.z), drone, totalDistance)
            }
        }
        OutputGenerator.generateLandingLog()
        return totalDistance
    }
}
