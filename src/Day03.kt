class Day03(lines: List<String>) {
    private val numbers = mutableListOf<Number>()
    private val symbols = mutableSetOf<Symbol>()

    init {
        val numbersRegex = Regex("[0-9]+")
        val symbolsRegex = Regex("[^0-9\\.]+")

        lines.forEachIndexed { index, line ->
            numbers += numbersRegex.findAll(line).map { matchResult ->
                Number(matchResult.value.toInt(), Vector2(matchResult.range.first, index), matchResult.range)
            }
            symbols += symbolsRegex.findAll(line).map { matchResult ->
                Symbol(matchResult.value[0], Vector2(matchResult.range.first, index))
            }
        }
    }

    data class Vector2(val x: Int, val y: Int)
    data class Symbol(val char: Char, val coords: Vector2) {
        fun isGear(): Boolean {
            return char == '*'
        }

        fun adjacentCoords(): Set<Vector2> {
            return buildSet {
                add(Vector2(coords.x, coords.y - 1))
                add(Vector2(coords.x, coords.y + 1))
                add(Vector2(coords.x - 1, coords.y))
                add(Vector2(coords.x - 1, coords.y - 1))
                add(Vector2(coords.x - 1, coords.y + 1))
                add(Vector2(coords.x + 1, coords.y))
                add(Vector2(coords.x + 1, coords.y - 1))
                add(Vector2(coords.x + 1, coords.y + 1))
            }
        }
    }

    data class Number(val value: Int, val coords: Vector2, val range: IntRange) {
        fun adjacentCoords(): Set<Vector2> {
            return buildSet {
                for (i in range) {
                    add(Vector2(i, coords.y - 1))
                    add(Vector2(i, coords.y + 1))
                }
                add(Vector2(range.first - 1, coords.y))
                add(Vector2(range.first - 1, coords.y - 1))
                add(Vector2(range.first - 1, coords.y + 1))
                add(Vector2(range.last + 1, coords.y))
                add(Vector2(range.last + 1, coords.y - 1))
                add(Vector2(range.last + 1, coords.y + 1))
            }
        }
    }

    private infix fun Number.intersect(symbols: MutableSet<Symbol>): Boolean {
        return this.adjacentCoords().any { it in symbols.map { it.coords } }
    }

    private infix fun Symbol.intersect(number: Number): Boolean {
        return this.coords in number.adjacentCoords()
    }

    private infix fun Symbol.intersect(numbers: MutableList<Number>): Set<Number> {
        val symbolNumbers = mutableSetOf<Number>()
        for (number in numbers) {
            if (this intersect number) {
                symbolNumbers.add(number)
            }
        }
        return symbolNumbers
    }

    fun part1(): Int {
        return numbers.filter { it intersect symbols }
            .sumOf { it.value }
    }

    fun part2(): Int {
        return symbols.filter { it.isGear() }
            .sumOf {
                val gearNumbers = it intersect numbers
                if (gearNumbers.size == 2) gearNumbers.first().value * gearNumbers.last().value else 0
            }
    }
}

fun main() {
    val day = Day03(readInput("Day03"))
    println(day.part1())
    println(day.part2())
}



