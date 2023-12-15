class Point2D(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean {
        if (other !is Point2D) return false
        return x == other.x && y == other.y
    }

    override fun toString(): String {
        return "(${x + 1}, ${y + 1})"
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    operator fun plus(point: Point2D): Point2D {
        return Point2D(x + point.x, y + point.y)
    }
}

class Maze(val input: List<MutableList<Char>>) : Iterable<List<Char>> {
    val startPoint: Point2D = input.indexOfFirst { it.contains('S') }.let { Point2D(input[it].indexOf('S'), it) }
    fun isValid(point: Point2D): Boolean {
        return point.y in input.indices && point.x in input[point.y].indices && input[point.y][point.x] != '.'
    }

    fun at(point: Point2D): Char {
        return input[point.y][point.x]
    }

    fun set(point: Point2D, c: Char) {
        input[point.y][point.x] = c
    }

    operator fun get(index: Int): MutableList<Char> {
        return input[index]
    }

    override fun iterator(): Iterator<List<Char>> {
        return input.iterator()
    }
}

class Day10 : Day(10) {
    private val maze: Maze = Maze(inputList.map { it.toMutableList() })

    companion object {
        val UP = Point2D(0, -1)
        val DOWN = Point2D(0, 1)
        val LEFT = Point2D(-1, 0)
        val RIGHT = Point2D(1, 0)
    }

    fun displayMaze() {
        for (line in maze) {
            for (c in line) {
                when (c) {
                    'F' -> print('┌')
                    '7' -> print('┐')
                    'J' -> print('┘')
                    '|' -> print('│')
                    'L' -> print('└')
                    'S' -> print(')')
                    '-' -> print('─')
                    else -> print(c)
                }
            }
            println()
        }
    }

    private fun getLoop(): Set<Point2D> {
        val toExplore = mutableSetOf(maze.startPoint)
        val explored = mutableSetOf<Point2D>()
        while (toExplore.isNotEmpty()) {
            val point = toExplore.first()
            toExplore.addAll(getNextPoints(point).filter { it !in explored })
            explored.add(point)
            toExplore.remove(point)
        }
        return explored
    }

    private fun getNextPoints(point: Point2D): List<Point2D> {
        return getNextDirection(point).map { it + point }.filter { maze.isValid(it) }
    }

    private fun getNextDirection(point: Point2D): List<Point2D> {
        return when (maze.at(point)) {
            'S' -> listOf(UP, DOWN, LEFT, RIGHT)
            '|' -> listOf(UP, DOWN)
            '7' -> listOf(LEFT, DOWN)
            'L' -> listOf(UP, RIGHT)
            'J' -> listOf(UP, LEFT)
            'F' -> listOf(DOWN, RIGHT)
            '-' -> listOf(LEFT, RIGHT)
            else -> listOf()
        }
    }

    private fun isPointInsideLoop(point: Point2D, loop: Set<Point2D>): Boolean {
        var intersect = 0
        var pointX = point.x
        if (point in loop) {
            return false
        }
        while (pointX++ < maze[point.y].size - 1) {
            if (Point2D(pointX, point.y) in loop && maze.at(Point2D(pointX, point.y)) in "|F7S") {
                intersect++
            }
        }
        return intersect % 2 != 0
    }

    override fun partOne(): Any {
        return getLoop().size / 2
    }

    override fun partTwo(): Any {
        val tilesEnclosed = mutableSetOf<Point2D>()
        val loop = getLoop()

        for (y in 0..<maze.count()) {
            for (x in 0..<maze[y].size) {
                val point = Point2D(x, y)
                if (isPointInsideLoop(point, loop)) {
                    tilesEnclosed.add(point)
                }
            }
        }
        return tilesEnclosed.size
    }
}

fun main() {
    val day = Day10()
    day.run()
    day.displayMaze()

}



