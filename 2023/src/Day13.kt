class Day13 : Day(13) {
    class Pattern(private val lines: List<String>) {
        private val lineMax = lines.size
        private val columnMax = lines.first().length

        private fun findHorizontalMirror(withSmudge: Boolean = false): Int? {
            var smudge: Int
            for (i in 0..<this.lineMax - 1) {
                smudge = 0
                if (lines[i] == lines[i + 1]) {
                    var count = 1
                    while (i - count >= 0 && i + 1 + count < lineMax && lines[i - count] == lines[i + 1 + count]) count++
                    if (count + 1 + i == this.lineMax || i - count + 1 == 0) return i + 1
                }
            }
            return null
        }

        private fun findVerticalMirror(withSmudge: Boolean = false): Int? {
            for (i in 0..<this.columnMax - 1) {
                if (column(i) == column(i + 1)) {
                    var count = 1
                    while (i - count >= 0 && i + 1 + count < columnMax && column(i - count) == column(i + 1 + count)) count++
                    if (count + 1 + i == this.columnMax || i - count + 1 == 0) return i + 1
                }
            }
            return null
        }

//        private fun diff(s1: String, s2: String) {
//            return s1 - s2
//        }

        private fun column(i: Int): String {
            return lines.map { it[i] }.joinToString()
        }

        fun getScore(withSmudge: Boolean = false): Int {
            val verticalMirrorIndex = findVerticalMirror(withSmudge)
            val horizontalMirrorIndex = findHorizontalMirror(withSmudge)

            if (horizontalMirrorIndex != null) return horizontalMirrorIndex * 100
            if (verticalMirrorIndex != null) return verticalMirrorIndex
            error("No mirror found")
        }
    }

    private val patterns = inputString.split("\n\n").map { Pattern(it.split("\n")) }

    override fun partOne(): Int {
        return patterns.sumOf { it.getScore() }
    }

    override fun partTwo(): Int {
        return patterns.sumOf { it.getScore(true) }
    }
}

fun main() {
    val day = Day13()
    day.run()
}



