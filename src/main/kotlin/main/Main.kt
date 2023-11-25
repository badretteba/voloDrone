import input.InputParser
import input.World
import input.Drone
import movement.MovementCalculator
import collision.CollisionDetector
import output.OutputGenerator

fun main() {
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
        07 FORWARD 4
    """.trimIndent()

    val (world, drone, commands) = InputParser.parseInput(input)
    OutputGenerator.generateInitialLogs(world.width, world.depth, world.height, drone)
    OutputGenerator.generateTakeOffLog()

    var totalDistanceFlown = 0
    for (command in commands) {
        val movement = MovementCalculator.calculateMovement(command.direction, command.distance, drone)
        if (CollisionDetector.willCollide(world, drone, movement)) {
            OutputGenerator.generateMovementLog(movement, drone, totalDistanceFlown, true)
            val correctedMovement = CollisionDetector.resolveCollision(world, drone, movement)
            drone.x += correctedMovement.first
            drone.y += correctedMovement.second
            drone.z += correctedMovement.third
            totalDistanceFlown += correctedMovement.first + correctedMovement.second + correctedMovement.third
            OutputGenerator.generateMovementLog(correctedMovement, drone, totalDistanceFlown)
        } else {
            drone.x += movement.first
            drone.y += movement.second
            drone.z += movement.third
            totalDistanceFlown += command.distance
            OutputGenerator.generateMovementLog(Triple(movement.first, movement.second, movement.third), drone, totalDistanceFlown)
        }
    }

    OutputGenerator.generateLandingLog()
}
