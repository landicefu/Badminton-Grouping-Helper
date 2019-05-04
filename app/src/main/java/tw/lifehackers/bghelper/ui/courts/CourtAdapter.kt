package tw.lifehackers.bghelper.ui.courts

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.model.Court

class CourtAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_COURT = 0
        const val ITEM_TYPE_ADD_COURT = 1
    }

    private val courts: List<Court> get() = App.gameStates.courts

    override fun getItemCount(): Int = courts.size + 1

    override fun getItemViewType(position: Int): Int = if (position == courts.size) {
        ITEM_TYPE_ADD_COURT
    } else {
        ITEM_TYPE_COURT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        ITEM_TYPE_COURT -> CourtViewHolder(parent) { position -> listener.onClick(App.gameStates.courts[position]) }
        ITEM_TYPE_ADD_COURT -> ButtonViewHolder(parent)
        else -> CourtViewHolder(parent) { position -> listener.onClick(App.gameStates.courts[position]) }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourtViewHolder -> holder.bind(courts[position])
            is ButtonViewHolder -> holder.bind("add court") { listener.addCourt() }
        }
    }

    interface Listener {
        fun addCourt()
        fun onClick(court: Court)
    }
}
