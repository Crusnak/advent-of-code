class Day07(lines: List<String>) {
    private val hands: List<Hand>
    private val handsWithJokers: List<Hand>

    init {
        hands = lines.map { Hand(it.substringBefore(' '), it.substringAfter(' ').toInt()) }
        handsWithJokers = lines.map { Hand(it.substringBefore(' '), it.substringAfter(' ').toInt(), true) }
    }

    fun part1(): Int {
        return hands.sorted().mapIndexed { index, it -> (index + 1) * it.bid }.sum()
    }

    fun part2(): Int {
        return handsWithJokers.sorted().mapIndexed { index, it -> (index + 1) * it.bid }.sum()
    }
}

enum class HandType {
    HighCard,
    OnePair,
    TwoPairs,
    ThreeOfAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind,
}

data class Cards(val values: String) {
    companion object {
        const val CARDS_ORDER = "*23456789TJQKA"
    }

    operator fun minus(cards: Cards): Int {
        for (i in values.indices) {
            if (values[i] != cards.values[i]) {
                return CARDS_ORDER.indexOf(values[i]) - CARDS_ORDER.indexOf(cards.values[i])
            }
        }
        return 0
    }
}

class Hand(values: String, val bid: Int, withJokers: Boolean = false) : Comparable<Hand> {
    private val handType: HandType
    private val cards: Cards

    override fun toString(): String {
        return "${cards.values} => ${handType.name} ($bid)"
    }

    init {
        cards = if (withJokers) Cards(values.replace('J', '*')) else Cards(values)
        handType = cards.values.groupingBy { it }.eachCount().let {
            val jokersCount = it.getOrDefault('*', 0)
            val handsWithoutJokers = it.filterKeys { it != '*' }
            when {
                handsWithoutJokers.count() == 1 || jokersCount == 5 -> HandType.FiveOfAKind
                handsWithoutJokers.count() == 5 -> HandType.HighCard
                handsWithoutJokers.count() == 4 -> HandType.OnePair
                handsWithoutJokers.count() == 2 && jokersCount >= 2 -> HandType.FourOfAKind
                handsWithoutJokers.count() == 2 && jokersCount == 1 && it.entries.any { it.value == 3 } -> HandType.FourOfAKind
                handsWithoutJokers.count() == 2 && jokersCount == 1 -> HandType.FullHouse
                handsWithoutJokers.count() == 2 && it.entries.any { it.value == 4 } -> HandType.FourOfAKind
                handsWithoutJokers.count() == 2 -> HandType.FullHouse
                handsWithoutJokers.count() == 3 && jokersCount > 0 ->  HandType.ThreeOfAKind
                handsWithoutJokers.count() == 3 && it.entries.any { it.value == 3 } -> HandType.ThreeOfAKind
                handsWithoutJokers.count() == 3 -> HandType.TwoPairs
                else -> error("No matching handType")
            }
        }
    }

    override fun compareTo(other: Hand): Int = when {
        this.handType != other.handType -> this.handType.ordinal - other.handType.ordinal
        else -> this.cards - other.cards
    }
}

fun main() {
    val day = Day07(readInputAsList("Day07"))
    println("Part 1: ${day.part1()}")
    println("Part 2: ${day.part2()}")
}



