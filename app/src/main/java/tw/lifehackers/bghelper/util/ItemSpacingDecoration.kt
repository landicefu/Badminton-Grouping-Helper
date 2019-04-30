package tw.lifehackers.bghelper.util

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class ItemSpacingDecoration(
    private val spaceResId: Int,
    private val leadingSpaceResId: Int?,
    private val trailingSpaceResId: Int?,
    private val vertical: Boolean
) : RecyclerView.ItemDecoration() {

    private var space: Int? = null
    private var leadingSpace: Int = 0
    private var trailingSpace: Int = 0

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        if (space == null) {
            view.context.resources.apply {
                space = getDimensionPixelSize(spaceResId)
                leadingSpaceResId?.let { leadingSpace = getDimensionPixelSize(it) }
                trailingSpaceResId?.let { trailingSpace = getDimensionPixelSize(it) }
            }
        }
        val position = parent.getChildAdapterPosition(view)
        val isLast = position == state.itemCount - 1
        val isFirst = position == 0
        val space = space!!

        if (vertical) {
            outRect.bottom = if (isLast) trailingSpace else space / 2
            outRect.top = if (isFirst) leadingSpace else space / 2
        } else {
            outRect.right = if (isLast) trailingSpace else space / 2
            outRect.left = if (isFirst) leadingSpace else space / 2
        }
    }

}
