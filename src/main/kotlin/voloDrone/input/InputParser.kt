package voloDrone.input

import exception.InvalidCommandOrderException
import extension.substringBetween
import kotlinx.coroutines.channels.Channel
import voloDrone.model.*
import voloDrone.output.OutputGenerator

object InputParser{
    private var lastOrder: Int = 0
    val sensorChannel = Channel<SensorData>()
    val parsedSensorChannel = Channel<ParsedSensorData>()

    suspend fun parseInput() : Int{
        for (data in sensorChannel) {
            parseAndValidate(data)
        }
        return this.lastOrder
    }
    private suspend fun parseAndValidate(sensorData: SensorData){
        val listOfSensorData = sensorData.data.toList()
        for (line in listOfSensorData) {
            val commands = mutableListOf<Command>()
            val worldCoordinates = line.substringBetween(";WORLD",";DRONE").trim().split(" ").map { it.toInt() }
            val droneCoordinates = line.substringBetween(";DRONE",";COMMAND").trim().split(" ").map { it.toInt() }
            val commandParams = line.substringAfter(";COMMAND").trim().split("\n")
            for(commandParam in commandParams){
                val command = commandParam.trim().split(" ")
                if (command.size == 3) {
                    if (command[0].toInt() == lastOrder + 1) {
                        commands.add(Command(command[0].toInt(), command[1], command[2].toInt()))
                        lastOrder = commands.last().order
                    }else{
                        OutputGenerator.generateInvalidDataLog("Invalid sensor data order: $command")
                        throw  InvalidCommandOrderException()
                    }
                }
            }
            val world = World(worldCoordinates[0], worldCoordinates[1], worldCoordinates[2])
            val drone = Drone(droneCoordinates[0], droneCoordinates[1], droneCoordinates[2])
            val parsedData = ParsedSensorData(world, drone, commands)
            parsedSensorChannel.send(parsedData)
        }
    }
    suspend fun receiveSensorData(sensorData: SensorData, lastOrder: Int) {
        this.lastOrder =lastOrder
        sensorChannel.send(sensorData)
    }
}
