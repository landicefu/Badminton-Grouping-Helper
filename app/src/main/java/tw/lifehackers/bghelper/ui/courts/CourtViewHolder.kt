package tw.lifehackers.bghelper.ui.courts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_court.view.*
import kotlinx.android.synthetic.main.view_court.view.*
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Court

class CourtViewHolder(parent: ViewGroup, private val onClickAction: (Int) -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_court, parent, false)
) {
    fun bind(court: Court) {
        itemView.apply {
            courtName.text = context.getString(R.string.court_name, court.index)
            teamAPlayer1.text = court.teamA?.player1?.name
            teamAPlayer2.text = court.teamA?.player2?.name
            teamBPlayer1.text = court.teamB?.player1?.name
            teamBPlayer2.text = court.teamB?.player2?.name
            setOnClickListener {
                val position = adapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                onClickAction(position)
            }
        }
    }
}