package tw.lifehackers.bghelper.util

import android.util.Log
import android.view.View

typealias ClickAction = (view: View) -> Unit

fun log(str: String) = Log.d("bghelper", str)