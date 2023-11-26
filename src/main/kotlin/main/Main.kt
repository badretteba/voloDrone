import voloDrone.movement.collision.CollisionDetector
import voloDrone.input.InputParser
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import voloDrone.model.Movement
import voloDrone.model.ParsedSensorData
import voloDrone.movement.MovementCalculator
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
fun processParsedData(parsedData: ParsedSensorData, totalDistanceFlown: Int): Int {
    var totalDistance = totalDistanceFlown
    val drone = parsedData.drone
    val world = parsedData.world
    val commands = parsedData.commands
    OutputGenerator.generateInitialLogs(world.width, world.depth, world.height, drone)
    OutputGenerator.generateTakeOffLog()
    for (command in commands){
        val movement = MovementCalculator.calculateMovement(command.direction, command.distance)
        if (CollisionDetector.willCollide(world, drone, movement)) {
            OutputGenerator.generateMovementLog(movement, drone, totalDistance, true)
            val correctedMovement = CollisionDetector.resolveCollision(world, drone, movement)
            drone.x += correctedMovement.x
            drone.y += correctedMovement.y
            drone.z += correctedMovement.z
            totalDistance += correctedMovement.x + correctedMovement.y + correctedMovement.z
            OutputGenerator.generateMovementLog(correctedMovement, drone, totalDistance)
        } else {
            drone.x += movement.x
            drone.y += movement.y
            drone.z += movement.z
            totalDistance += command.distance
            OutputGenerator.generateMovementLog(Movement(movement.x, movement.y, movement.z), drone, totalDistance)
        }
    }
    OutputGenerator.generateLandingLog()
    return totalDistance
}