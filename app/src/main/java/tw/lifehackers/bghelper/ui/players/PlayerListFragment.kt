package tw.lifehackers.bghelper.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_recyclerview.*
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.util.ItemSpacingDecoration

class PlayerListFragment : Fragment() {

    private val playerAdapter = PlayerAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.view_recyclerview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.apply {
            adapter = playerAdapter
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            addItemDecoration(ItemSpacingDecoration(R.dimen.large, R.dimen.large, R.dimen.large, true))
        }
    }
}