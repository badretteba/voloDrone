package movement

import voloDrone.movement.MovementCalculator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import voloDrone.model.Drone
import voloDrone.model.Movement

class MovementCalculatorTest {

    @Test
    fun `test calculate movement`() {
        val drone = Drone(5, 5, 5)

        val leftMovement = MovementCalculator.calculateMovement("LEFT", 2)
        val expectedLeft = Movement(0, -2, 0)
        assertEquals(expectedLeft, leftMovement)

        val forwardMovement = MovementCalculator.calculateMovement("FORWARD", 4)
        val expectedForward = Movement(4, 0, 0)
        assertEquals(expectedForward, forwardMovement)
    }
}
