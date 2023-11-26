package voloDrone.model

data class ParsedSensorData(val world: World, val drone: Drone, val commands: List<Command>)