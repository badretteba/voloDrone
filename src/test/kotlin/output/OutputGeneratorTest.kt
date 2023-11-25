package output
import input.Drone
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class OutputGeneratorTest {

    @Test
    fun `test generate movement log`() {
        val drone = Drone(5, 5, 5)

        val movement = Triple(1, -2, 3)
        val totalDistanceFlown = 10
        val log = OutputGenerator.generateMovementLog(movement, drone, totalDistanceFlown)
        println(log)
        val expectedLog = "(1,-2,3)->(6,3,8) [10]"
        assertEquals(expectedLog, log)
    }
}
