package com.example.studentsabsents.ViewModelsHelpersModels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import java.text.DateFormat
import java.util.*

class Helper {

    companion object{

        const val booleanPath="trueOrFalse"
        const val studentsPresentsPath="studentPresents"
        const val studentsAbsentsPath="studentAbsents"
        const val TheFather="theFather"
        const val key="MyKey"
        const val SectionKey="SectionKey"

        fun log(text:String){

            Log.i("MyTag",text)
        }
        fun checkNetwork(context: Context):Boolean{

            var connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state== NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(
                        ConnectivityManager
                            .TYPE_MOBILE).state== NetworkInfo.State
                .CONNECTED

        }
        fun showToast(context:Context,text:String){

            Toast.makeText(context,text, Toast.LENGTH_LONG).show()

        }

        fun getTime(): String {
            var calender = Calendar.getInstance()
            return DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calender.time)
        }

    }

}