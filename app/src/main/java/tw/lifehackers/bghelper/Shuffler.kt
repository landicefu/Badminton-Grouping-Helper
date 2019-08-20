package tw.lifehackers.bghelper

import tw.lifehackers.bghelper.CalculationParameter.Companion.MINUS_WEIGHTING_OPPOSE
import tw.lifehackers.bghelper.CalculationParameter.Companion.MINUS_WEIGHTING_SAME_TEAM
import tw.lifehackers.bghelper.CalculationParameter.Companion.POWER_NUM_TIME_PLAYED
import tw.lifehackers.bghelper.CalculationParameter.Companion.SCORE_BASE_NUM_TIME_PLAYED
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.Team
import tw.lifehackers.bghelper.util.log
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.random.Random

class Shuffler {
    companion object {
        private val debugLogging = BuildConfig.DEBUG
        private const val totalRandomSet = 1000

        fun shuffle(): Match {
            val gameStates = App.gameStates
            val availablePlayers = gameStates.getAvailablePlayers()

            if (debugLogging) {
                dbg("--------+ All Available Players +--------")
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
            val fact = GameStatesFact.create()
            val sortedList = randomSet.sortedBy { matchSet -> matchSet.getScore(fact) }.reversed()
            if (debugLogging) {
                dbg("-----------+ Top Ten Match +-------------")
                val sizeOr10 = Math.min(sortedList.size, 10)
                sortedList
                    .subList(0, sizeOr10)
                    .forEachIndexed { index, match -> dbg("[${index + 1}][${match.getScore(fact)}] $match") }
                dbg("-----------------------------------------")
            }
            val selected = sortedList.first()
            dbg("Selected match: $selected, score: ${selected.getScore(fact)}")
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

        private var score: Int? = null

        fun getScore(gameStatesFact: GameStatesFact): Int = score ?: calculateScore(gameStatesFact).let { calculationResult ->
            this@Match.score = calculationResult
            calculationResult
        }

        private fun calculateScore(gameStatesFact: GameStatesFact): Int {
            var score = 0.0
            score += (gameStatesFact.highNumberOfPlayedGames - teamA.player1.getNumTimesPlayed()).toDouble().times(SCORE_BASE_NUM_TIME_PLAYED).power(POWER_NUM_TIME_PLAYED)
            score += (gameStatesFact.highNumberOfPlayedGames - teamA.player2.getNumTimesPlayed()).toDouble().times(SCORE_BASE_NUM_TIME_PLAYED).power(POWER_NUM_TIME_PLAYED)
            score += (gameStatesFact.highNumberOfPlayedGames - teamB.player1.getNumTimesPlayed()).toDouble().times(SCORE_BASE_NUM_TIME_PLAYED).power(POWER_NUM_TIME_PLAYED)
            score += (gameStatesFact.highNumberOfPlayedGames - teamB.player2.getNumTimesPlayed()).toDouble().times(SCORE_BASE_NUM_TIME_PLAYED).power(POWER_NUM_TIME_PLAYED)
            score -= App.gameStates.getPlayerPairAttributes(teamA)?.numTeamUp?.toDouble()?.times(MINUS_WEIGHTING_SAME_TEAM) ?: 0.0
            score -= App.gameStates.getPlayerPairAttributes(teamB)?.numTeamUp?.toDouble()?.times(MINUS_WEIGHTING_SAME_TEAM) ?: 0.0
            score -= App.gameStates.getPlayerPairAttributes(Team.create(teamA.player1, teamB.player1))?.numOppose?.toDouble()?.times(MINUS_WEIGHTING_OPPOSE) ?: 0.0
            score -= App.gameStates.getPlayerPairAttributes(Team.create(teamA.player1, teamB.player2))?.numOppose?.toDouble()?.times(MINUS_WEIGHTING_OPPOSE) ?: 0.0
            score -= App.gameStates.getPlayerPairAttributes(Team.create(teamA.player2, teamB.player1))?.numOppose?.toDouble()?.times(MINUS_WEIGHTING_OPPOSE) ?: 0.0
            score -= App.gameStates.getPlayerPairAttributes(Team.create(teamA.player2, teamB.player2))?.numOppose?.toDouble()?.times(MINUS_WEIGHTING_OPPOSE) ?: 0.0
            return score.roundToInt()
        }
    }
}

private fun Double.power(power: Double): Double = Math.pow(this, power)
private fun Double.power(power: Int): Double = Math.pow(this, power.toDouble())

class CalculationParameter {
    companion object {
        const val SCORE_BASE_NUM_TIME_PLAYED = 2
        const val POWER_NUM_TIME_PLAYED = 2
        const val MINUS_WEIGHTING_SAME_TEAM = 0.5
        const val MINUS_WEIGHTING_OPPOSE = 1
    }
}

data class GameStatesFact private constructor(
    val highNumberOfPlayedGames: Int
) {
    companion object {
        fun create(): GameStatesFact {
            val gameStates = App.gameStates
            val highNumberOfPlayedGames = gameStates.getPlayerList().map { player -> player.getNumTimesPlayed() }.max() ?: 0
            return GameStatesFact(highNumberOfPlayedGames)
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
