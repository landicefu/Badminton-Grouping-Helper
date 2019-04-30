package tw.lifehackers.bghelper

import tw.lifehackers.bghelper.model.Court
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.PlayerPair

class GameStates {
    private val playerList = mutableListOf<Player>()
    private val playerPairList = mutableListOf<PlayerPair>()

    val numTimesPlayed = hashMapOf<String, Int>()
    val courts = mutableListOf<Court>()

    fun getPlayerList() = playerList.toList()

    fun removePlayer(player: Player) {
        playerList.remove(player)
        val iterator = playerPairList.iterator()
        while (iterator.hasNext()) {
            val pair = iterator.next()
            if (pair.player1 == player || pair.player2 == player) {
                iterator.remove()
            }
        }
    }

    fun addPlayer(player: Player) {
        if (player in playerList) {
            return
        }

        for (otherPlayer in playerList) {
            playerPairList.add(PlayerPair.create(player, otherPlayer))
        }
        playerList.add(player)
    }

    fun findPlayer(name: String): Player? =
        playerList.firstOrNull { player -> player.name == name }
}