package voloDrone.movement.collision

import voloDrone.model.Drone
import voloDrone.model.World

object CollisionDetector {
    fun willCollide(world: World, drone: Drone, movement: Triple<Int, Int, Int>): Boolean {
        val newX = drone.x + movement.first
        val newY = drone.y + movement.second
        val newZ = drone.z + movement.third

        return newX !in 0..<world.width ||
                newY !in 0..<world.depth ||
                newZ !in 0..<world.height
    }

    fun resolveCollision(world: World, drone: Drone, movement: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
        var newX = drone.x + movement.first
        var newY = drone.y + movement.second
        var newZ = drone.z + movement.third

        if (newX !in 0..<world.width) {
            newX = newX.coerceIn(0..<world.width)
        }

        if (newY !in 0..<world.depth) {
            newY = newY.coerceIn(0..<world.depth)
        }

        if (newZ !in 0..<world.height) {
            newZ = newZ.coerceIn(0..<world.height)
        }

        return Triple(newX - drone.x, newY - drone.y, newZ - drone.z)
    }
}
