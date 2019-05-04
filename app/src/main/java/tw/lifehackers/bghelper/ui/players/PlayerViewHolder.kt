package tw.lifehackers.bghelper.ui.players

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapePathModel
import kotlinx.android.synthetic.main.item_player.view.*
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.util.dip

class PlayerViewHolder(parent: ViewGroup, val deleteAction: (Int, Context) -> Unit) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
) {

    private val green: Int = ContextCompat.getColor(itemView.context, R.color.courtGround)
    private val gray: Int = ContextCompat.getColor(itemView.context, R.color.blackOverlay)

    init {
        val shapePathModel = ShapePathModel().apply {
            setAllCorners(CutCornerTreatment(dip(20)))
        }

        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(gray)
            paintStyle = Paint.Style.FILL
        }

        itemView.background = backgroundDrawable
    }

    fun bind(player: Player) {
        itemView.apply {
            playerName.text = player.name
            deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    deleteAction(position, context)
                }
            }

            if (player.isPlaying()) {
                val drawable = background.mutate()
                DrawableCompat.setTint(drawable, green)
                background = drawable
            } else {
                val drawable = background.mutate()
                DrawableCompat.setTint(drawable, gray)
                background = drawable
            }
        }
    }
}
