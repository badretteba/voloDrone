import voloDrone.movement.collision.CollisionDetector
import voloDrone.input.InputParser
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import voloDrone.model.Movement
import voloDrone.model.ParsedSensorData
import voloDrone.movement.MovementCalculator
import voloDrone.movement.MovementCalculator.processParsedData
import voloDrone.output.OutputGenerator

fun main() {
    var totalDistanceFlown = 0
    val parsedSensorChannel = InputParser.parsedSensorChannel

    val sensorJob = GlobalScope.launch { SensorModule.readSensorData() }
    val inputParserJob = GlobalScope.launch { InputParser.parseInput() }
    val retrieveChannelDataJob = GlobalScope.launch {
        parsedSensorChannel.consumeEach { parsedData ->
            totalDistanceFlown =  processParsedData(parsedData,totalDistanceFlown)
        }
    }

    runBlocking {
        joinAll(sensorJob, inputParserJob,retrieveChannelDataJob)
    }
}
