package tw.lifehackers.bghelper.model

import java.lang.IllegalArgumentException

data class Team private constructor(
    val player1: Player,
    val player2: Player
) {
    fun toPlayerPair(): PlayerPair = PlayerPair.create(player1, player2)

    companion object {
        fun create(playerA: Player, playerB: Player): Team {
            if (playerA == playerB) {
                throw IllegalArgumentException("cannot create Team with same player")
            }

            return if (playerA.name < playerB.name) {
                Team(playerA, playerB)
            } else {
                Team(playerB, playerA)
            }
        }
    }
}
