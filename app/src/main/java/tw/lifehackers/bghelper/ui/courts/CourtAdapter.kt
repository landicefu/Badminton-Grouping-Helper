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
        const val ITEM_TYPE_START_ALL = 2
        const val ITEM_TYPE_FINISH_ALL = 3
    }

    private val courts: List<Court> get() = App.gameStates.courts
    private val buttons = mutableListOf(ITEM_TYPE_ADD_COURT)
    private val addCourtPosition = courts.size

    override fun getItemCount(): Int = courts.size + buttons.size

    override fun getItemViewType(position: Int): Int = if (position < courts.size) {
        ITEM_TYPE_COURT
    } else {
        buttons[position - courts.size]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        ITEM_TYPE_COURT -> CourtViewHolder(parent) { position -> listener.onClick(App.gameStates.courts[position]) }
        else -> ButtonViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (holder) {
            is CourtViewHolder -> holder.bind(courts[position])
            is ButtonViewHolder -> {
                when (viewType) {
                    ITEM_TYPE_ADD_COURT -> holder.bind("add court") { listener.addCourt() }
                    ITEM_TYPE_START_ALL -> holder.bind("start all games") { listener.startAllGames() }
                    ITEM_TYPE_FINISH_ALL -> holder.bind("finish all games") { listener.finishAllGames() }
                }
            }
        }
    }

    fun refreshButtonStatus() {
        val showStartAll = courts.any { court -> !court.isPlaying() }
        val showFinishAll = courts.any { court -> court.isPlaying() }
        if (showStartAll && !buttons.contains(ITEM_TYPE_START_ALL)) {
            buttons.add(1, ITEM_TYPE_START_ALL)
            notifyItemInserted(addCourtPosition + 1)
        }

        if (!showStartAll && buttons.contains(ITEM_TYPE_START_ALL)) {
            buttons.remove(ITEM_TYPE_START_ALL)
            notifyItemRemoved(addCourtPosition + 1)
        }

        if (showFinishAll && !buttons.contains(ITEM_TYPE_FINISH_ALL)) {
            buttons.add(ITEM_TYPE_FINISH_ALL)
            notifyItemInserted(addCourtPosition + buttons.size - 1)
        }

        if (!showFinishAll && buttons.contains(ITEM_TYPE_FINISH_ALL)) {
            val position = buttons.indexOf(ITEM_TYPE_FINISH_ALL)
            buttons.remove(ITEM_TYPE_FINISH_ALL)
            notifyItemRemoved(addCourtPosition + position)
        }
    }

    interface Listener {
        fun addCourt()
        fun startAllGames()
        fun finishAllGames()
        fun onClick(court: Court)
    }
}
