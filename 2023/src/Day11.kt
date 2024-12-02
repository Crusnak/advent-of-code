import kotlin.math.abs

data class Galaxy(val x: Long, val y: Long)

class Day11 : Day(11) {
    private val width = this.inputList.first().length
    private val height = this.inputList.count()
    private val galaxies: List<Galaxy> = buildList {
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (isGalaxy(x, y)) {
                    add(Galaxy(x.toLong(), y.toLong()))
                }
            }
        }
    }
    private val emptyRows = (0..<height).filter { y -> (0..<width).all { x -> isEmpty(x, y) } }
    private val emptyColumns = (0..<width).filter { x -> (0..<height).all { y -> isEmpty(x, y) } }

    private fun isGalaxy(x: Int, y: Int): Boolean {
        return this.inputList[y][x] == '#'
    }

    private fun isEmpty(x: Int, y: Int): Boolean {
        return this.inputList[y][x] == '.'
    }

    private fun shortestPath(galaxyA: Galaxy, galaxyB: Galaxy): Long {
        return abs(galaxyA.x - galaxyB.x) + abs(galaxyA.y - galaxyB.y)
    }

    private fun expandGalaxies(step: Int): List<Galaxy> {
        return galaxies.map { galaxy ->
            val rowsExpanded = emptyRows.count { it < galaxy.y }
            val columnsExpanded = emptyColumns.count { it < galaxy.x }
            Galaxy(galaxy.x + columnsExpanded * step - columnsExpanded, galaxy.y + rowsExpanded * step - rowsExpanded)
        }
    }

    private fun pairGalaxies(galaxies: List<Galaxy>): List<Pair<Galaxy, Galaxy>> {
        return buildList {
            for (i in 0..galaxies.size) {
                for (j in i + 1..<galaxies.size) {
                    add(Pair(galaxies[i], galaxies[j]))
                }
            }
        }
    }

    override fun partOne(): Long {
        return pairGalaxies(expandGalaxies(2)).sumOf { this.shortestPath(it.first, it.second) }
    }

    override fun partTwo(): Long {
        return pairGalaxies(expandGalaxies(1000000)).sumOf { this.shortestPath(it.first, it.second) }
    }
}

fun main() {
    val day = Day11()
    day.run()
}



