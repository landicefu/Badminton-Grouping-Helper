package tw.lifehackers.bghelper.ui.courts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.util.ClickAction

class ButtonViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
) {
    fun bind(text: String, clickAction: ClickAction?) {
        val button = itemView as? Button ?: return
        button.text = text
        button.setOnClickListener(clickAction)
    }
}