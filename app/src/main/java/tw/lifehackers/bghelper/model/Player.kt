package tw.lifehackers.bghelper.model

import tw.lifehackers.bghelper.App

data class Player(
    val name: String
) {
    fun getNumTimesPlayed(): Int = App.gameStates.numTimesPlayed[name] ?: 0

    fun increaseNumTimesPlayed() {
        App.gameStates.numTimesPlayed[name] = getNumTimesPlayed() + 1
    }

    fun isPlaying(): Boolean {
        for (court in App.gameStates.courts) {
            when (this) {
                court.teamA?.player1 -> return true
                court.teamA?.player2 -> return true
                court.teamB?.player1 -> return true
                court.teamB?.player2 -> return true
            }
        }
        return false
    }
}