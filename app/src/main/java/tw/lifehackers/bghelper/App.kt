package tw.lifehackers.bghelper

import android.app.Application
import tw.lifehackers.bghelper.model.Court
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.model.PlayerPair
import tw.lifehackers.bghelper.model.Team

class App : Application() {

    companion object {
        val gameStates: GameStates = GameStates()
    }

    override fun onCreate() {
        super.onCreate()
        initPlayerList()
        initCourts()
    }

    private fun initPlayerList() {
        PLAYER_LIST.sorted().map(::Player).forEach(gameStates::addPlayer)
    }

    private fun initCourts() {
        repeat(2) {
            gameStates.courts.add(Court())
        }
    }

}