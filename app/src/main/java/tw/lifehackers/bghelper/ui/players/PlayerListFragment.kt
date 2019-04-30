package tw.lifehackers.bghelper.ui.players

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_recyclerview.*
import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.R
import tw.lifehackers.bghelper.model.Player
import tw.lifehackers.bghelper.util.ItemSpacingDecoration
import tw.lifehackers.bghelper.util.warn

class PlayerListFragment : Fragment(), PlayerAdapter.Listener {
    private val playerAdapter = PlayerAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.view_recyclerview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.apply {
            adapter = playerAdapter
            layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
            addItemDecoration(ItemSpacingDecoration(R.dimen.large, R.dimen.large, null, true))
        }
    }

    override fun onAddClicked() {
        val context = context ?: return
        val editText = EditText(context)
        AlertDialog.Builder(context)
            .setTitle(R.string.newUserTitle)
            .setView(editText)
            .setPositiveButton(R.string.ok) { _, _ ->
                val playerName = editText.text.toString()
                if (playerName.isEmpty()) {
                    warn(context, null, "Please enter a valid name")
                    return@setPositiveButton
                }

                val existingPlayer = App.gameStates.findPlayer(playerName)
                if (existingPlayer != null) {
                    warn(context, null, "Name of player is already in the list")
                    return@setPositiveButton
                }

                App.gameStates.addPlayer(Player(playerName))
                playerAdapter.notifyItemInserted(App.gameStates.getPlayerList().size)
            }.setNegativeButton(R.string.cancel, null)
            .show()
    }
}