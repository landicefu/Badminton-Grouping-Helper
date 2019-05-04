package tw.lifehackers.bghelper.shuffle

import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.BuildConfig
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.Team
import tw.lifehackers.bghelper.util.log
import kotlin.math.absoluteValue
import kotlin.random.Random

class GeneShuffler {
    companion object {
        private val debugLogging = BuildConfig.DEBUG
        private const val totalRandomSet = 100

        fun shuffle(): MatchSet {
            val gameStates = App.gameStates
            val availablePlayers = gameStates.getAvailablePlayers()

            if (debugLogging) {
                dbg("--------- All Available Players ---------")
                availablePlayers.forEachIndexed { index, player ->
                    dbg("$index - ${player.name}")
                }
                dbg("-----------------------------------------")
            }

            val randomSet = mutableSetOf<MatchSet>()
            repeat(totalRandomSet) {
                randomSet.add(createRandomMatchSet(availablePlayers))
            }
            dbg("Number of sets: ${randomSet.size}")
            return randomSet.elementAt(0)
        }

        private fun dbg(str: String) {
            if (!debugLogging) {
                return
            }

            log("[Shuffle] $str")
        }

        private fun createRandomMatchSet(availablePlayers: List<Player>): MatchSet = mutableListOf<Player>()
            .apply { addAll(availablePlayers) }
            .run {
                val teamA = Team.create(getRandomAndRemove(), getRandomAndRemove())
                val teamB = Team.create(getRandomAndRemove(), getRandomAndRemove())
                return MatchSet.create(teamA, teamB)
            }
    }

    data class MatchSet private constructor(
        val teamA: Team,
        val teamB: Team
    ) {
        companion object {
            fun create(teamX: Team, teamY: Team): MatchSet {
                if (hashSetOf(teamX.player1, teamX.player2, teamY.player1, teamY.player2).size != 4) {
                    throw IllegalArgumentException("duplicate player in a MatchSet")
                }

                return if (teamX.player1.name < teamY.player1.name) {
                    MatchSet(teamX, teamY)
                } else {
                    MatchSet(teamY, teamX)
                }
            }
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
