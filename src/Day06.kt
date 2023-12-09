import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

val numbersRegex = Regex("[0-9]+")

class Day06(lines: List<String>) {

    private val races: List<Race>
    private val race: Race

    data class Race(val time: Long, val distance: Long) {
        fun getWinningSolutions(): List<Long> {
            return 0.until(time).filter { (time - it) * it > distance }
        }

        fun countWinningSolutions(): Int {
            val quadraticSolutionInf = 0.5 * (time - sqrt((-time).toDouble().pow(2.0) - 4 * distance))
            val quadraticSolutionSup = 0.5 * (time + sqrt((-time).toDouble().pow(2.0) - 4 * distance))
            return (ceil(quadraticSolutionInf).toLong()..floor(quadraticSolutionSup).toLong()).count()
        }
    }

    init {
        val times = numbersRegex.findAll(lines.first()).map { it.value }.toList()
        val distances = numbersRegex.findAll(lines.last()).map { it.value }.toList()
        races = times.zip(distances) { a, b -> Race(a.toLong(), b.toLong()) }
        race = Race(times.joinToString("").toLong(), distances.joinToString("").toLong())
    }

    fun part1(): Int {
        return races.fold(1) { acc, race ->
            acc * race.getWinningSolutions().size
        }
    }

    fun part2(): Int {
        return race.countWinningSolutions()
    }
}

fun main() {
    val day = Day06(readInputAsList("Day06"))
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}



