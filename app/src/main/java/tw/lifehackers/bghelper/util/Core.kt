package tw.lifehackers.bghelper.util

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.os.Process
import androidx.appcompat.app.AlertDialog
import tw.lifehackers.bghelper.App
import tw.lifehackers.bghelper.R

typealias ClickAction = (view: View) -> Unit

fun log(str: String) = Log.d("bghelper", str)

fun dip(numDip: Int): Float = App.instance.resources.displayMetrics.density * numDip

fun warn(context: Context, title: String?, description: String?) {
    val builder = AlertDialog.Builder(context)
        .setPositiveButton(R.string.ok, null)

    if (title != null) {
        builder.setTitle(title)
    }

    if (description != null) {
        builder.setMessage(description)
    }

    builder.show()
}

fun finishApp() {
    Process.killProcess(Process.myPid())
}