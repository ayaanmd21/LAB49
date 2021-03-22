package com.faizan.lab49.utils

import android.content.Context
import android.widget.Toast
import com.faizan.lab49.R

object ToastUtil {



    private var toast: Toast? = null

    fun show(context: Context?, msg: String?) {

        if (context != null) {
            if (toast != null) {
                toast!!.cancel()
            }
            toast = Toast.makeText(context, validateString(msg), toastTime)
            toast!!.show()
        }
    }

    fun showInternetError(context: Context?){
        show(context,context!!.getString(R.string.error_internet))
    }


    private fun validateString(msg: String?): String {
        return msg ?: "null"
    }


    private val toastTime: Int
        get() = Toast.LENGTH_LONG
}