import voloDrone.input.InputParser
import kotlinx.coroutines.*
import voloDrone.model.SensorData

object SensorModule {
    var lastOrder: Int = 0
    // This function is to simulate the sensor data. It will later be replaced with actual logic to read data from the sensor.
    suspend fun readSensorData(){
        val delayMillis = 1000L
        var round = 0
        val simulatedData = mutableListOf<String>()
        var isActive = true
        while (isActive){
            val data = buildString {
                appendLine(";WORLD")
                appendLine("10 10 10")
                appendLine(";DRONE")
                appendLine("5 5 5")
                appendLine(";COMMAND")
                appendLine("${round * 10 + 1} LEFT 2")
                appendLine("${round * 10 + 2} RIGHT 3")
                appendLine("${round * 10 + 3} UP 4")
                appendLine("${round * 10 + 4} DOWN 3")
                appendLine("${round * 10 + 5} FORWARD 4")
                appendLine("${round * 10 + 6} BACKWARD 3")
                appendLine("${round * 10 + 7} FORWARD 4")
                appendLine("${round * 10 + 8} LEFT 5")
                appendLine("${round * 10 + 9} RIGHT 2")
                appendLine("${round * 10 + 10} UP 3")
            }
            round +=1
            simulatedData.add(data)
            delay(delayMillis)
            InputParser.receiveSensorData(SensorData(simulatedData.asSequence()),lastOrder)
        }
    }
}
