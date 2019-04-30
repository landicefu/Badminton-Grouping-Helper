package tw.lifehackers.bghelper.ui.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import tw.lifehackers.bghelper.R

class ButtonViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
) {
    fun bind(text: String) {
        val button = itemView as? Button ?: return
        button.text = text
    }
}