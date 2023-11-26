package voloDrone.output

import voloDrone.model.Drone


object OutputGenerator {
    fun generateInitialLogs(worldWidth: Int, worldDepth: Int, worldHeight: Int, drone: Drone) {
        println("=== Volodrone Initialising")
        println("World: (x=range(0, $worldWidth), y=range(0, $worldDepth), z=range(0, $worldHeight))")
        println("Drone starts at: (${drone.x},${drone.y},${drone.z})")
    }

    fun generateTakeOffLog() {
        println("=== Volodrone Take Off")
    }

    fun generateMovementLog(movement: Triple<Int, Int, Int>, drone: Drone, totalDistanceFlown: Int, isCollision: Boolean = false){
        val movementString = "(${movement.first},${movement.second},${movement.third})"
        val direction = if (isCollision) "CRASH IMMINENT - AUTOMATIC COURSE CORRECTION" else "${"(${drone.x},${drone.y},${drone.z})"} [$totalDistanceFlown]"
        println("$movementString->$direction")
    }

    fun generateLandingLog() {
        println("=== Volodrone Landing")
    }
    fun generateInvalidDataLog(s: String) {
        println (s)
    }
}
