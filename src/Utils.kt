import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText
import kotlin.time.measureTimedValue

fun readInputAsList(name: String) = Path("src/$name.txt").readLines()

fun readInputAsText(name: String) = Path("src/$name.txt").readText()

abstract class Day(dayNumber: Int) {
    protected val inputList: List<String> by lazy { readInputAsList("Day${dayNumber.toString().padStart(2, '0')}") }
    protected val inputString: String by lazy { readInputAsText("Day${dayNumber.toString().padStart(2, '0')}") }
    fun run() {
        val partOne = measureTimedValue { partOne() }
        val partTwo = measureTimedValue { partTwo() }

        println("Part 1: ${partOne.value} in ${partOne.duration}")
        println("Part 2: ${partTwo.value} in ${partTwo.duration}")
    }

    abstract fun partOne(): Any
    abstract fun partTwo(): Any
}