import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import voloDrone.input.InputParser
import voloDrone.model.*

@ExperimentalCoroutinesApi
class InputParserTest {

    @Test
     fun `test parseInput`() {
        var totalDistanceFlown = 0
        var parsedSensorData: ParsedSensorData? = null
        val inputParserJob = GlobalScope.async { InputParser.parseInput() }
        val retrieveChannelDataJob = GlobalScope.launch {
            parsedSensorData = InputParser.parsedSensorChannel.receive()
        }
        runBlocking {
            val sensorData = buildString {
                appendLine(";WORLD")
                appendLine("10 10 10")
                appendLine(";DRONE")
                appendLine("5 5 5")
                appendLine(";COMMAND")
                appendLine("1 LEFT 2")
                appendLine("2 RIGHT 3")
                appendLine("3 UP 4")
                appendLine("4 DOWN 3")
                appendLine("5 FORWARD 4")
                appendLine("6 BACKWARD 3")
                appendLine("7 FORWARD 4")
                appendLine("8 LEFT 5")
                appendLine("9 RIGHT 2")
                appendLine("10 UP 3")
            }
            var lastOrder = 0
            val simulatedData = mutableListOf<String>()
            simulatedData.add(sensorData)
            val updatedLastOrder = InputParser.receiveSensorData(SensorData(simulatedData.asSequence()), lastOrder)
            InputParser.sensorChannel.close()
            joinAll(inputParserJob,retrieveChannelDataJob)
            InputParser.parsedSensorChannel.close()
            lastOrder = inputParserJob.await()
            // Ensure lastOrder is updated
            assertEquals(10, lastOrder)
            // Assert the received ParsedSensorData properties here
            val expectedWorld = World(10, 10, 10) // Update the expected world coordinates
            val expectedDrone = Drone(5, 5, 5) // Update the expected drone coordinates
            val expectedCommands = listOf(
                Command(1, "LEFT", 2),
                Command(2, "RIGHT", 3),
                Command(3, "UP", 4),
                Command(4, "DOWN", 3),
                Command(5, "FORWARD", 4),
                Command(6, "BACKWARD", 3),
                Command(7, "FORWARD", 4),
                Command(8, "LEFT", 5),
                Command(9, "RIGHT", 2),
                Command(10, "UP", 3)
            )

            val expectedParsedSensorData = ParsedSensorData(expectedWorld, expectedDrone, expectedCommands)
            assertEquals(expectedParsedSensorData, parsedSensorData)
        }
    }
}
