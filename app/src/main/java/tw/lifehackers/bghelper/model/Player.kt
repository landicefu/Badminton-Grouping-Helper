package tw.lifehackers.bghelper.model

import tw.lifehackers.bghelper.App

data class Player(
    val name: String
) {
    fun getNumTimesPlayed(): Int = App.numTimesPlayed[name] ?: 0

    fun increaseNumTimesPlayed() {
        App.numTimesPlayed[name] = getNumTimesPlayed() + 1
    }
}