package tw.lifehackers.bghelper.ui.players

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapePathModel
import kotlinx.android.synthetic.main.item_player.view.*
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.util.dip

class PlayerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
) {

    init {
        val shapePathModel = ShapePathModel().apply {
            setAllCorners(CutCornerTreatment(dip(20)))
        }

        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(ContextCompat.getColor(itemView.context, R.color.blackOverlay))
            paintStyle = Paint.Style.FILL
        }

        itemView.background = backgroundDrawable
    }

    fun bind(player: Player) {
        itemView.apply {
            playerName.text = player.name
        }
    }

}
