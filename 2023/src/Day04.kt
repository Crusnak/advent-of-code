import kotlin.math.pow

class Day04(private val lines: List<String>) {
    fun part1(): Int {
        return lines.map { Card(it) }
            .sumOf { it.point }
    }

    fun part2(): Int {
        val cardsCount = mutableMapOf<Int, Int>()
        return lines.map { Card(it) }
            .mapIndexed { index, card ->
                val cardToCount = cardsCount.getOrDefault(index, 1)
                for (j in index + 1..index + card.matchesCount) {
                    cardsCount[j] = (cardsCount[j] ?: 1) + cardToCount
                }
                cardToCount
            }
            .sum()
    }
}

class Card(input: String) {
    private val winningsNumber: Set<String>
    private val scratchNumbers: Set<String>
    val matchesCount: Int
    val point: Int
        get() {
            return if (matchesCount > 0) 2.0.pow(matchesCount - 1).toInt() else 0
        }

    init {
        this.winningsNumber = input.substringAfter(":").split('|').first().windowed(3, 3).toSet()
        this.scratchNumbers = input.substringAfter(":").split('|').last().windowed(3, 3).toSet()
        this.matchesCount = winningsNumber.intersect(scratchNumbers).size
    }
}

fun main() {
    val day = Day04(readInputAsList("Day04"))
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}



