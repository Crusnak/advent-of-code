import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsList(name: String) = Path("src/$name.txt").readLines()

fun readInputAsText(name: String) = Path("src/$name.txt").readText()