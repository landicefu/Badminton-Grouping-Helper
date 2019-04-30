package tw.lifehackers.bghelper.ui.matches

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.lifehackers.bghelper.App

class CourtAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_COURT = 0
        const val ITEM_TYPE_ADD_COURT = 1
    }

    override fun getItemCount(): Int = App.courts.size + 1

    override fun getItemViewType(position: Int): Int = if (position == App.courts.size) {
        ITEM_TYPE_ADD_COURT
    } else {
        ITEM_TYPE_COURT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        ITEM_TYPE_COURT -> CourtViewHolder(parent)
        ITEM_TYPE_ADD_COURT -> ButtonViewHolder(parent)
        else -> CourtViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CourtViewHolder -> holder.bind(App.courts[position])
            is ButtonViewHolder -> holder.bind("add court")
        }
    }

}
