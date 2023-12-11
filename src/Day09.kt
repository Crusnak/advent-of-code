import kotlin.math.abs

class Day09 : Day(9) {
    private val histories = inputList.map { it.split(' ').map(String::toInt) }

    private fun predictLast(history: List<Int>): Int {
        if (history.all { it == 0 }) return history.last()
        return predictLast(history.zipWithNext { a, b -> b - a }) + history.last()
    }

    private fun predictFirst(history: List<Int>): Int {
        if (history.all { it == 0 }) return history.first()
        return history.first() - predictFirst(history.zipWithNext { a, b -> b - a })
    }

    override fun partOne(): Any {
        return histories.sumOf { predictLast(it) }
    }

    override fun partTwo(): Any {
        return histories.sumOf { predictFirst(it) }
    }
}

fun main() {
    val day = Day09()
    day.run()
}



