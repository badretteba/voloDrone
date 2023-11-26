package voloDrone.movement.collision

import voloDrone.model.Drone
import voloDrone.model.Movement
import voloDrone.model.World

object CollisionDetector {
    fun willCollide(world: World, drone: Drone, movement: Movement): Boolean {
        val newX = drone.x + movement.x
        val newY = drone.y + movement.y
        val newZ = drone.z + movement.z

        return newX !in 0..<world.width ||
                newY !in 0..<world.depth ||
                newZ !in 0..<world.height
    }

    fun resolveCollision(world: World, drone: Drone, movement: Movement): Movement {
        var newX = drone.x + movement.x
        var newY = drone.y + movement.y
        var newZ = drone.z + movement.z

        if (newX !in 0..<world.width) {
            newX = newX.coerceIn(0..<world.width)
        }

        if (newY !in 0..<world.depth) {
            newY = newY.coerceIn(0..<world.depth)
        }

        if (newZ !in 0..<world.height) {
            newZ = newZ.coerceIn(0..<world.height)
        }

        return Movement(newX - drone.x, newY - drone.y, newZ - drone.z)
    }
}
