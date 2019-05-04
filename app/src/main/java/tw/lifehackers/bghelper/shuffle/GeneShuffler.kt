package tw.lifehackers.bghelper.shuffle

import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.model.Court
import tw.lifehackers.bghelper.model.Team
import tw.lifehackers.bghelper.util.log

class GeneShuffler {
    companion object {
        private const val debugLogging = true
        private const val totalRandomSet = 100

        fun shuffle(court: Court): MatchSet {
            val gameStates = App.gameStates
            val allAvailablePlayers = gameStates.getAvailablePlayers()

            if (debugLogging) {
                dbg("--------- All Available Players ---------")
                allAvailablePlayers.forEachIndexed { index, player ->
                    dbg("$index - ${player.name}")
                }
            }

//            val randomSet = mutableSetOf<MatchSet>()
            val teamA = Team.create(allAvailablePlayers[0], allAvailablePlayers[1])
            val teamB = Team.create(allAvailablePlayers[2], allAvailablePlayers[3])
            return MatchSet.create(teamA, teamB)
        }

        fun dbg(str: String) {
            if (!debugLogging) {
                return
            }

            log("[Shuffle] $str")
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