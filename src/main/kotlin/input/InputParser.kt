package input

data class World(val width: Int, val depth: Int, val height: Int)
data class Drone(var x: Int, var y: Int, var z: Int)
data class Command(val order: Int, val direction: String, val distance: Int)

object InputParser {
    fun parseInput(input: String): Triple<World, Drone, List<Command>> {
        val lines = input.trim().split("\n")
        val worldParams = lines[1].trim().split(" ").map { it.toInt() }
        val droneParams = lines[3].trim().split(" ").map { it.toInt() }
        val world = World(worldParams[0], worldParams[1], worldParams[2])
        val drone = Drone(droneParams[0], droneParams[1], droneParams[2])

        val commands = mutableListOf<Command>()
        for (i in 5..< lines.size) {
            val commandParams = lines[i].trim().split(" ")
            commands.add(Command(commandParams[0].toInt(), commandParams[1], commandParams[2].toInt()))
        }
        return Triple(world, drone, commands)
    }
}
