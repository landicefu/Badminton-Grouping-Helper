package tw.lifehackers.bghelper

import tw.lifehackers.bghelper.model.Court
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.PlayerPairAttributes
import tw.lifehackers.bghelper.model.Team

class GameStates {
    private val playerList = mutableListOf<Player>()
    private val playerPairMap = HashMap<Team, PlayerPairAttributes>()

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

    fun getPlayerPairAttributes(team: Team) = playerPairMap[team]

    fun getOrCreatePlayerPairAttributes(team: Team): PlayerPairAttributes {
        val existingAttr = playerPairMap[team]
        return if (existingAttr == null) {
            val newAttr = PlayerPairAttributes()
            playerPairMap[team] = newAttr
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