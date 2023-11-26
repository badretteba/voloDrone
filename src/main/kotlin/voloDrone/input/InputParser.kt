package voloDrone.input

import extension.substringBetween
import kotlinx.coroutines.channels.Channel
import voloDrone.model.*
import voloDrone.output.OutputGenerator

object InputParser{
    var lastOrder: Int = 0
    val sensorChannel = Channel<SensorData>()
    val parsedSensorChannel = Channel<ParsedSensorData>()

    suspend fun parseInput() : Int{
        for (data in sensorChannel) {
            // Parse, validate, and structure sensor data
            parseAndValidate(data)

        }
        return this.lastOrder
    }
    private suspend fun parseAndValidate(sensorData: SensorData){
        // Implement parsing and validation logic here
        // Return parsed and validated sensor data


        var worldCoordinates: List<Int>? = null
        var droneCoordinates: List<Int>? = null
        val listOfSensorData = sensorData.data.toList()

        for (line in listOfSensorData) {
            var world: World? = null
            var drone: Drone? = null
            val commands = mutableListOf<Command>()

            worldCoordinates = line.substringBetween(";WORLD",";DRONE")?.trim()?.split(" ")?.map { it.toInt() }
            droneCoordinates = line.substringBetween(";DRONE",";COMMAND")?.trim()?.split(" ")?.map { it.toInt() }
            val commandParams = line.substringAfter(";COMMAND").trim().split("\n")

            for(commandParam in commandParams){
               val command = commandParam.trim().split(" ")
                if (command.size == 3) {
                    if (command[0].toInt() == lastOrder + 1) {
                        commands.add(Command(command[0].toInt(), command[1], command[2].toInt()))
                        lastOrder = commands.last().order
                    }else{
                        OutputGenerator.generateInvalidDataLog("Invalid sensor data order: $command")
                       // throw  InvalidCommandOrderException()
                    }
                }
            }
            world = World(worldCoordinates?.get(0)?: 0, worldCoordinates?.get(1) ?: 0, worldCoordinates?.get(2) ?: 0)
            drone = Drone(droneCoordinates?.get(0)?: 0, droneCoordinates?.get(1) ?: 0, droneCoordinates?.get(2) ?: 0)
            val parsedData = ParsedSensorData(world ?: World(0, 0, 0), drone ?: Drone(0, 0, 0), commands)
            parsedSensorChannel.send(parsedData)
        }
    }
    suspend fun receiveSensorData(sensorData: SensorData, lastOrder: Int) {
        this.lastOrder =lastOrder
        sensorChannel.send(sensorData)
    }
}
