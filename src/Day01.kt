fun main() {
    val lines = readInput("Day01")
    part1(lines).println()
    part2(lines).println()
}

fun part1(lines: List<String>): Int {
    return lines.sumOf { line: String ->
        "${line.first { it.isDigit() }}${line.last{ it.isDigit() }}".toInt()
    }
}

fun part2(lines: List<String>): Int {
    val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    return lines.sumOf { line: String ->
        val first = line.findAnyOf(numbers)
        val last = line.findLastAnyOf(numbers)
        "${first?.second?.let { toDigit(it) }}${last?.second?.let { toDigit(it) }}".toInt()
    }
}

private fun toDigit(text: String): String {
    return when (text) {
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        "five" -> "5"
        "six" -> "6"
        "seven" -> "7"
        "eight" -> "8"
        "nine" -> "9"
        else -> text
    }
}