class Day12 : Day(12) {
    private val cache: HashMap<Pair<String, List<Int>>, Long> = hashMapOf()

    data class Row(val springs: String, val damageGroups: List<Int>) {
        fun unfold(): Row {
            return Row((0 until 5).joinToString("?") { springs }, damageGroups.repeat(5))
        }

        private fun <E> List<E>.repeat(n: Int): List<E> {
            val list = this
            return buildList { repeat(n) { addAll(list) } }
        }
    }

    private val rows = inputList.map {
        Row(
            it.split(' ').first(),
            it.split(' ').last().split(',').map(String::toInt)
        )
    }

    private fun computeArrangements(springs: String, groups: List<Int>): Long {
        var total = 0L
        if (groups.isEmpty()) {
            return if ('#' in springs) 0 else 1
        }
        if (springs.isEmpty()) {
            return 0
        }

        if (cache.contains(Pair(springs, groups))) {
            return cache[Pair(springs, groups)]!!
        }

        if (springs.first() in "?.") {
            total += computeArrangements(springs.substring(1), groups)
        }
        if (springs.first() in "#?") {
            val group = groups.first()
            if (group <= springs.length) {
                val subSprings = springs.substring(0, group)
                if ('.' !in subSprings && (springs.length == group || springs[group] != '#')) {
                    val lenToSubstring = if (group == springs.length) group else group + 1
                    total += computeArrangements(springs.substring(lenToSubstring), groups.drop(1))
                }
            }
        }
        cache[Pair(springs, groups)] = total
        return total
    }

    override fun partOne(): Long {
        cache.clear()
        return rows.sumOf { (springs, groups) -> computeArrangements(springs, groups) }
    }

    override fun partTwo(): Long {
        cache.clear()
        return rows.map(Row::unfold)
            .sumOf { (springs, groups) -> computeArrangements(springs, groups) }
    }
}

fun main() {
    val day = Day12()
    day.run()
}



