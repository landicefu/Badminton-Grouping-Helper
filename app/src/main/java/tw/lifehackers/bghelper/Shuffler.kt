package tw.lifehackers.bghelper

import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.Team
import tw.lifehackers.bghelper.util.log
import kotlin.math.absoluteValue
import kotlin.random.Random

class Shuffler {
    companion object {
        private val debugLogging = BuildConfig.DEBUG
        private const val totalRandomSet = 1000

        fun shuffle(): Match {
            val gameStates = App.gameStates
            val availablePlayers = gameStates.getAvailablePlayers()

            if (debugLogging) {
                dbg("--------- All Available Players ---------")
                availablePlayers.forEachIndexed { index, player ->
                    dbg("$index - ${player.name}")
                }
                dbg("-----------------------------------------")
            }

            val randomSet = mutableSetOf<Match>()
            repeat(totalRandomSet) {
                randomSet.add(createRandomMatchSet(availablePlayers))
            }
            dbg("Number of sets: ${randomSet.size}")
            val selected = randomSet.sortedBy { matchSet -> matchSet.currentScore }.last()
            dbg("Selected match: $selected, score: ${selected.currentScore}")
            return selected
        }

        private fun dbg(str: String) {
            if (!debugLogging) {
                return
            }

            log("[Shuffle] $str")
        }

        private fun createRandomMatchSet(availablePlayers: List<Player>): Match = mutableListOf<Player>()
            .apply { addAll(availablePlayers) }
            .run {
                val teamA = Team.create(getRandomAndRemove(), getRandomAndRemove())
                val teamB = Team.create(getRandomAndRemove(), getRandomAndRemove())
                return Match.create(teamA, teamB)
            }
    }

    data class Match private constructor(
        val teamA: Team,
        val teamB: Team
    ) {

        companion object {
            fun create(teamX: Team, teamY: Team): Match {
                if (hashSetOf(teamX.player1, teamX.player2, teamY.player1, teamY.player2).size != 4) {
                    throw IllegalArgumentException("duplicate player in a Match")
                }

                return if (teamX.player1.name < teamY.player1.name) {
                    Match(teamX, teamY)
                } else {
                    Match(teamY, teamX)
                }
            }
        }

        val currentScore by lazy { calculateScore() }

        private fun calculateScore(): Int {
            return Random.nextInt() % 10
        }

    }
}

private fun <E> Collection<E>.getRandom(): E {
    val index = Random.nextInt().absoluteValue % size
    return elementAt(index)
}

private fun <E> MutableCollection<E>.getRandomAndRemove(): E {
    val value = getRandom()
    remove(value)
    return value
}
