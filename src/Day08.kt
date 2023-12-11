class Day08 : Day(8) {
    private val pattern: Regex = Regex("""(\w+) = \((\w+), (\w+)\)""")
    private val directions: String = inputList.first()
    private val network: Map<String, Pair<String, String>> = inputList.drop(2).associate {
        pattern.matchEntire(it)?.destructured?.let { (s, l, r) -> s to Pair(l, r) }!!
    }

    private fun stepsToEndNode(nodeStart: String, endCheck: (String) -> Boolean): Int {
        var index = 0
        var node = nodeStart
        var count: Int = 0

        do {
            val step = directions[index++ % directions.length]
            if (step == 'L') {
                node = network[node]?.first ?: error("Node $node missing in network")
            } else {
                node = network[node]?.second ?: error("Node $node missing in network")
            }
            count++
        } while (endCheck(node))
        return count
    }

    private fun lcm(numbers: List<Int>): Long {
        return numbers.fold(1L) { acc, i -> acc * i / gcd(acc, i.toLong()) }.toLong()
    }

    private tailrec fun gcd(a: Long, b:Long): Long {
        if (b == 0L) return a
        return gcd(b, a % b)
    }

    override fun partOne(): Any {
        return stepsToEndNode("AAA") { node -> node != "ZZZ" }
    }

    override fun partTwo(): Any {
        val nodes = network.keys.filter { it.endsWith('A') }
        val steps = nodes.map { stepsToEndNode(it) { node -> !node.endsWith('Z') } }
        return lcm(steps)
    }
}

fun main() {
    val day = Day08()
    day.run()
}



