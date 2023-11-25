package collision


import collision.CollisionDetector
import input.World
import input.Drone
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CollisionDetectorTest {

    @Test
    fun `test collision detection`() {
        val world = World(10, 10, 10)
        val drone = Drone(5, 5, 5)

        val outOfBoundsMovement = Triple(0, 0, 6)
        val willCollide = CollisionDetector.willCollide(world, drone, outOfBoundsMovement)
        assertEquals(true, willCollide)

        val withinBoundsMovement = Triple(0, 0, -3)
        val wontCollide = CollisionDetector.willCollide(world, drone, withinBoundsMovement)
        assertEquals(false, wontCollide)
    }
}