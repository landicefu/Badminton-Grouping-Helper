package tw.lifehackers.bghelper.ui.players

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.ui.matches.ButtonViewHolder

class PlayerAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_PLAYER = 0
        private const val TYPE_ADD = 1
    }

    override fun getItemCount(): Int = App.gameStates.getPlayerList().size + 1

    override fun getItemViewType(position: Int): Int = if (position == App.gameStates.getPlayerList().size) TYPE_ADD else TYPE_PLAYER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = if (viewType == TYPE_PLAYER)
        PlayerViewHolder(parent) else ButtonViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is PlayerViewHolder -> holder.bind(App.gameStates.getPlayerList()[position])
            is ButtonViewHolder -> holder.bind("add") { listener.onAddClicked() }
        }
    }

    interface Listener {
        fun onAddClicked()
    }
}