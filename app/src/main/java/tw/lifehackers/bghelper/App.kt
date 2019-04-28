package tw.lifehackers.bghelper

import android.app.Application

class App : Application() {

    companion object {
        val playerList = mutableListOf<String>()
    }

    override fun onCreate() {
        super.onCreate()
        initPlayerList()
    }

    private fun initPlayerList() {
        playerList.addAll(playerList)
    }

}