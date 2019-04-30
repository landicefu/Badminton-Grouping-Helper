package tw.lifehackers.bghelper.ui.players

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.lifehackers.bghelper.App

class PlayerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PLAYER = 0
    }

    override fun getItemCount(): Int = App.gameStates.getPlayerList().size

    override fun getItemViewType(position: Int): Int = TYPE_PLAYER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayerViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val playerViewHolder = holder as? PlayerViewHolder ?: return
        playerViewHolder.bind(App.gameStates.getPlayerList()[position])
    }

}