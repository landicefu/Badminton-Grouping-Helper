package tw.lifehackers.bghelper.model

data class Court(
    var teamA: Team? = null,
    var teamB: Team? = null
) {
    fun reset() {
        teamA = null
        teamB = null
    }
}