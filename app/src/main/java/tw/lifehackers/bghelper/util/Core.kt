package tw.lifehackers.bghelper.util

import android.util.Log
import android.view.View
import tw.lifehackers.bghelper.App

typealias ClickAction = (view: View) -> Unit

fun log(str: String) = Log.d("bghelper", str)

fun dip(numDip: Int): Float = App.instance.resources.displayMetrics.density * numDip