package extension

fun String.substringBetween(start: String, end: String): String? {
    val startIndex = indexOf(start)
    val endIndex = indexOf(end, startIndex + start.length)
    return if (startIndex != -1 && endIndex != -1) {
        substring(startIndex + start.length, endIndex)
    } else {
        null
    }
}