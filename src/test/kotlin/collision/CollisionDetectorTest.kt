package collision


import voloDrone.movement.collision.CollisionDetector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import voloDrone.model.Drone
import voloDrone.model.Movement
import voloDrone.model.World

class CollisionDetectorTest {

    @Test
    fun `test collision detection`() {
        val world = World(10, 10, 10)
        val drone = Drone(5, 5, 5)

        val outOfBoundsMovement = Movement(0, 0, 6)
        val willCollide = CollisionDetector.willCollide(world, drone, outOfBoundsMovement)
        assertEquals(true, willCollide)

        val withinBoundsMovement = Movement(0, 0, -3)
        val wontCollide = CollisionDetector.willCollide(world, drone, withinBoundsMovement)
        assertEquals(false, wontCollide)
    }
}