package tw.lifehackers.bghelper.model

import tw.lifehackers.bghelper.App

class Court(
    var teamA: Team? = null,
    var teamB: Team? = null
) {
    fun reset() {
        teamA = null
        teamB = null
    }

    fun isPlaying(): Boolean = teamA?.player1 != null && teamA?.player2 != null &&
            teamB?.player1 != null && teamB?.player2 != null

    val index: Int get() = App.gameStates.courts.indexOf(this)

    override fun equals(other: Any?): Boolean = this === other

}