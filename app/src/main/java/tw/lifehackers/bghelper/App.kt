package tw.lifehackers.bghelper

import android.app.Application
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.PlayerPair

class App : Application() {

    companion object {
        private val playerList = mutableListOf<Player>()
        private val playerPairList = mutableListOf<PlayerPair>()
        val numTimesPlayed = hashMapOf<String, Int>()

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
    }

    override fun onCreate() {
        super.onCreate()
        initPlayerList()
    }

    private fun initPlayerList() {
        playerList.addAll(PLAYER_LIST.sorted().map(::Player))
        for (index1 in 0 until playerList.size - 1) {
            for (index2 in index1 + 1 until playerList.size) {
                playerPairList.add(PlayerPair.create(playerList[index1], playerList[index2]))
            }
        }
    }

}