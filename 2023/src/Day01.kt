fun main() {
    val lines = readInputAsList("Day01")
    println(part1(lines))
    println(part2(lines))
}

fun part1(lines: List<String>): Int {
    return lines.sumOf { line: String ->
        "${line.first { it.isDigit() }}${line.last{ it.isDigit() }}".toInt()
    }
}

fun part2(lines: List<String>): Int {
    val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    return lines.sumOf { line: String ->
        val first = line.findAnyOf(numbers)?.second
        val last = line.findLastAnyOf(numbers)?.second
        "${toDigit(first)}${toDigit(last)}".toInt()
    }
}

private fun toDigit(text: String?): String {
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
        null -> error("Unable to find digit in line")
        else -> text
    }
}