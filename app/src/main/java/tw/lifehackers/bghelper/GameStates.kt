package tw.lifehackers.bghelper

import tw.lifehackers.bghelper.model.Court
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.PlayerPair
import tw.lifehackers.bghelper.model.PlayerPairAttributes

class GameStates {
    private val playerList = mutableListOf<Player>()
    private val playerPairMap = HashMap<PlayerPair, PlayerPairAttributes>()

    val numTimesPlayed = hashMapOf<String, Int>()
    val courts = mutableListOf<Court>()

    fun getPlayerList() = playerList.toList()

    fun removePlayer(player: Player) {
        playerList.remove(player)
        val iterator = playerPairMap.iterator()
        while (iterator.hasNext()) {
            val pair = iterator.next().key
            if (pair.player1 == player || pair.player2 == player) {
                iterator.remove()
            }
        }
    }

    fun addPlayer(player: Player) {
        if (player in playerList) {
            return
        }
        playerList.add(player)
    }

    fun findPlayer(name: String): Player? =
        playerList.firstOrNull { player -> player.name == name }

    fun getPlayerPairAttributes(playerPair: PlayerPair) = playerPairMap[playerPair]

    fun getOrCreatePlayerPairAttributes(playerPair: PlayerPair): PlayerPairAttributes {
        val existingAttr = playerPairMap[playerPair]
        return if (existingAttr == null) {
            val newAttr = PlayerPairAttributes()
            playerPairMap[playerPair] = newAttr
            newAttr
        } else existingAttr
    }

    fun getAvailablePlayers(): List<Player> = mutableListOf<Player>().apply {
        addAll(getPlayerList())
        for (c in courts) {
            if (c.isPlaying()) {
                remove(c.teamA?.player1)
                remove(c.teamA?.player2)
                remove(c.teamB?.player1)
                remove(c.teamB?.player2)
            }
        }
    }.toList()
}