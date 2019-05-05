package tw.lifehackers.bghelper.ui.courts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_menu_court.view.*
import kotlinx.android.synthetic.main.view_recyclerview.*
import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Court
import tw.lifehackers.bghelper.Shuffler
import tw.lifehackers.bghelper.util.scrollToLastItem
import tw.lifehackers.bghelper.util.warn

class CourtsFragment : Fragment(), CourtAdapter.Listener {
    private val adapter = CourtAdapter(this@CourtsFragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        recyclerView.apply {
            adapter = this@CourtsFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun addCourt() {
        val courts = App.gameStates.courts
        courts.add(Court())
        adapter.notifyItemInserted(courts.size - 1)
        recyclerView.scrollToLastItem(400)
    }

    override fun onClick(court: Court) {
        val context = context ?: return
        val playing = court.isPlaying()
        BottomSheetDialog(context).apply {
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_menu_court, null, false)
            view.apply {
                menuTitle.text = context.getString(R.string.court_name, court.index)
                buttonShuffleOrFinish.text = context.getString(if (playing) R.string.finish else R.string.shuffle)
                buttonShuffleOrFinish.setOnClickListener {
                    if (playing) {
                        finish(court)
                    } else {
                        shuffle(court)
                    }
                    dismiss()
                }
                buttonFinishAndStartNext.setOnClickListener {
                    finish(court)
                    shuffle(court)
                    dismiss()
                }

                buttonFinishAndStartNext.visibility = if (playing) View.VISIBLE else View.GONE
                buttonRemoveCourt.visibility = if (playing) View.GONE else View.VISIBLE
                buttonRemoveCourt.setOnClickListener {
                    remove(court)
                    dismiss()
                }
            }
            setContentView(view)
            show()
        }
    }

    private fun shuffle(court: Court) {
        val context = context ?: return
        if (App.gameStates.getAvailablePlayers().size < 4) {
            warn(context, "Cannot start game", "Number of available player is less than four")
            return
        }

        val indexOfCourt = App.gameStates.courts.indexOf(court)
        val match = Shuffler.shuffle()
        court.startMatch(match)
        adapter.notifyItemChanged(indexOfCourt)
    }

    private fun finish(court: Court) {
        val index = court.index
        if (index != -1) {
            court.reset()
            adapter.notifyItemChanged(index)
        }
    }

    private fun remove(court: Court) {
        val courts = App.gameStates.courts
        val position = courts.indexOf(court)
        courts.remove(court)
        adapter.notifyItemRemoved(position)

        val lastCourtIndex = courts.size
        if (position <= lastCourtIndex) {
            adapter.notifyItemRangeChanged(position, lastCourtIndex)
        }
    }
}
