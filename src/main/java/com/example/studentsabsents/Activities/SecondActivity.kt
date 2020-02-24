package com.example.studentsabsents.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studentsabsents.ViewModelsHelpersModels.Helper
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.key
import com.example.studentsabsents.ViewModelsHelpersModels.MyViewModel
import com.example.studentsabsents.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        var read = getSharedPreferences(Helper.SectionKey, Context.MODE_PRIVATE)
        var date=read.getString(key,"NoData")!!
        textViewSecond.text="You Added Your Self at $date"



        var viewModel=ViewModelProviders.of(this).get(MyViewModel::class.java)
        viewModel.isThereSection()

        viewModel.trueOrFalse.observe(this, Observer {


            if (it=="true"){

            }else{
                var i=Intent(this, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
            }

        })

    }

}
