package tw.lifehackers.bghelper.model

data class PlayerPair private constructor(
    val player1: Player,
    val player2: Player
) {
    companion object {
        fun create(playerA: Player, playerB: Player): PlayerPair {
            return if (playerB.name > playerA.name) {
                PlayerPair(playerA, playerB)
            } else {
                PlayerPair(playerB, playerA)
            }
        }
    }
}