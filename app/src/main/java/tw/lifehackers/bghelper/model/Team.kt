package tw.lifehackers.bghelper.model

data class Team private constructor(
    val player1: Player,
    val player2: Player
) {
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
