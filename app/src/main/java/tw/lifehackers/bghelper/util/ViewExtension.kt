package tw.lifehackers.bghelper.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToLastItem(delayMillis: Long = 0) {
    postDelayed({
        val size = adapter?.itemCount ?: return@postDelayed
        smoothScrollToPosition(size - 1)
    }, delayMillis)
}