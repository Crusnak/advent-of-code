class Day02(private val lines: List<String>) {
    private val maxColor = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    fun part1(): Int {
        return lines.filter { isPossible(it) }
            .sumOf { getId(it) }
    }

    fun part2(): Int {
        return lines.sumOf { powerOfMaxColor(it) }
    }

    private fun isPossible(line: String): Boolean {
        val colors = line.split(":")[1].split(",", ";")
        return maxColor.none { entry ->
            getMaxColor(colors, entry.key) > entry.value
        }
    }

    private fun powerOfMaxColor(line: String): Int {
        val colors = line.split(":")[1].split(",", ";")
        return getMaxColor(colors, "green") * getMaxColor(colors, "blue") * getMaxColor(colors, "red")
    }

    private fun getMaxColor(colors: List<String>, colorToFind: String): Int {
        return colors.filter { it.contains(colorToFind) }.maxOf { getNumber(it) }
    }

    private fun getId(line: String): Int {
        return getNumber(line.split(":")[0])
    }

    private fun getNumber(line: String): Int {
        return line.filter { it.isDigit() }.toInt()
    }
}

fun main() {
    val day = Day02(readInput("Day02"))
    println(day.part1())
    println(day.part2())
}



