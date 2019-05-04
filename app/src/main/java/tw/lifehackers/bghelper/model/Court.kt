package tw.lifehackers.bghelper.model

import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.Shuffler

class Court {

    var teamA: Team? = null
    var teamB: Team? = null

    fun reset() {
        teamA = null
        teamB = null
    }

    fun isPlaying(): Boolean = teamA?.player1 != null && teamA?.player2 != null &&
            teamB?.player1 != null && teamB?.player2 != null

    val index: Int get() = App.gameStates.courts.indexOf(this)

    override fun equals(other: Any?): Boolean = this === other

    fun startMatch(match: Shuffler.Match) {
        val gameStates = App.gameStates
        teamA = match.teamA
        teamB = match.teamB
        gameStates.getOrCreatePlayerPairAttributes(match.teamA.toPlayerPair()).numTeamUp++
        gameStates.getOrCreatePlayerPairAttributes(match.teamB.toPlayerPair()).numTeamUp++
        gameStates.getOrCreatePlayerPairAttributes(PlayerPair.create(match.teamA.player1, match.teamB.player1)).numOppose++
        gameStates.getOrCreatePlayerPairAttributes(PlayerPair.create(match.teamA.player1, match.teamB.player2)).numOppose++
        gameStates.getOrCreatePlayerPairAttributes(PlayerPair.create(match.teamA.player2, match.teamB.player1)).numOppose++
        gameStates.getOrCreatePlayerPairAttributes(PlayerPair.create(match.teamA.player2, match.teamB.player2)).numOppose++
    }
}