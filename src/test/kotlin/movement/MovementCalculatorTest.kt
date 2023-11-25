package movement

import input.Drone
import movement.MovementCalculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MovementCalculatorTest {

    @Test
    fun `test calculate movement`() {
        val drone = Drone(5, 5, 5)

        val leftMovement = MovementCalculator.calculateMovement("LEFT", 2, drone)
        val expectedLeft = Triple(0, -2, 0)
        assertEquals(expectedLeft, leftMovement)

        val forwardMovement = MovementCalculator.calculateMovement("FORWARD", 4, drone)
        val expectedForward = Triple(4, 0, 0)
        assertEquals(expectedForward, forwardMovement)
    }
}
