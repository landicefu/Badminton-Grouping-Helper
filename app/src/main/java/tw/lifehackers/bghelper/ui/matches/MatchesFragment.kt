package tw.lifehackers.bghelper.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_matches.*
import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Court

class MatchesFragment : Fragment(), CourtAdapter.Listener {

    private val adapter = CourtAdapter(this@MatchesFragment)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {
        recyclerView.apply {
            adapter = this@MatchesFragment.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun addCourt() {
        App.courts.add(Court())
        adapter.notifyItemInserted(App.courts.size - 1)
    }
}
