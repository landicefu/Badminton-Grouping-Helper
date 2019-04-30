package tw.lifehackers.bghelper.model

import tw.lifehackers.bghelper.App

data class Player(
    val name: String
) {
    fun getNumTimesPlayed(): Int = App.gameStates.numTimesPlayed[name] ?: 0

    fun increaseNumTimesPlayed() {
        App.gameStates.numTimesPlayed[name] = getNumTimesPlayed() + 1
    }
}