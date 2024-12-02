import kotlinx.coroutines.*

class Day05(private val blocks: List<String>) {
    private val seeds: List<Long> = blocks.first().substringAfter(": ").split(' ').map { it.toLong() }
    private val seedsRange = seeds.chunked(2) { it.first()..it.last() + it.first() }
    private val maps: List<List<Pair<LongRange, LongRange>>> = blocks.drop(1).map {
        it.split('\n').drop(1).map {
            it.split(' ').map(String::toLong).let { (dest, src, range) ->
                dest..<(dest + range) to src..<(src + range)
            }
        }
    }

    fun part1(): Long {
        return seeds.minOf { seed ->
            var value = seed
            for (map in maps) {
                for (range in map) {
                    if (value in range.second) {
                        value = range.first.start - range.second.start + value
                        break
                    }
                }
            }
            value
        }
    }

    // Brutforce solution to get results as I'm behind schedule. Would compute directly range of seeds otherwise to get better perf
    fun part2(): Long {
        return runBlocking(Dispatchers.Default) {
            seedsRange.map { seeds ->
                async {
                    seeds.minOf {
                        var value = it
                        for (map in maps) {
                            for (range in map) {
                                if (value in range.second) {
                                    value = range.first.start - range.second.start + value
                                    break
                                }
                            }
                        }
                        value
                    }
                }
            }.awaitAll().min()
        }
    }
}

fun main() {
    val day = Day05(readInputAsText("Day05").split("\n\n"))
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}



