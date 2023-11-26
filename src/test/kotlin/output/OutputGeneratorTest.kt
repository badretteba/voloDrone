package output
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import voloDrone.model.Drone
import voloDrone.output.OutputGenerator
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class OutputGeneratorTest {

    @Test
    fun `test generate movement log`() {
        // Redirect System.out to the created PrintStream log
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val originalOut = System.out
        System.setOut(printStream)
        val drone = Drone(5, 5, 5)
        val movement = Triple(1, -2, 3)
        val totalDistanceFlown = 10
        OutputGenerator.generateMovementLog(movement, drone, totalDistanceFlown)
        // Get the captured output as a string
        val log  = outputStream.toString().trim()
        // Reset System.out to its original state
        System.setOut(originalOut)
        val expectedLog = "(1,-2,3)->(5,5,5) [10]"
        assertEquals(expectedLog, log)
    }
}
