import input.Command
import input.Drone
import input.InputParser
import input.World
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class InputParserTest {

    @Test
    fun `test parseInput`() {
        val input = """
            ;WORLD
            10 10 10
            ;DRONE
            5 5 5
            ;COMMAND
            01 LEFT 2
            02 RIGHT 3
            03 UP 4
            04 DOWN 3
            05 FORWARD 4
            06 BACKWARD 3
        """.trimIndent()

        val (world, drone, commands) = InputParser.parseInput(input)

        val expectedWorld = World(10, 10, 10)
        val expectedDrone = Drone(5, 5, 5)
        val expectedCommands = listOf(
            Command(1, "LEFT", 2),
            Command(2, "RIGHT", 3),
            Command(3, "UP", 4),
            Command(4, "DOWN", 3),
            Command(5, "FORWARD", 4),
            Command(6, "BACKWARD", 3)
        )

        assertEquals(expectedWorld, world)
        assertEquals(expectedDrone, drone)
        assertEquals(expectedCommands, commands)
    }
}
