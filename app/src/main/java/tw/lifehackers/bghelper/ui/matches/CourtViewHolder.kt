package tw.lifehackers.bghelper.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_court.view.*
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Court

class CourtViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_court, parent, false)
) {
    fun bind(court: Court) {
        itemView.teamAPlayer1.text = court.teamA?.player1?.name
        itemView.teamAPlayer2.text = court.teamA?.player2?.name
        itemView.teamBPlayer1.text = court.teamB?.player1?.name
        itemView.teamBPlayer2.text = court.teamB?.player2?.name
    }
}