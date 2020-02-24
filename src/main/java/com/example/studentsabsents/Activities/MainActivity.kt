package com.example.studentsabsents.Activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.SectionKey
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.TheFather
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.checkNetwork
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.getTime
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.key
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.log
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.showToast
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.studentsAbsentsPath
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.studentsPresentsPath
import com.example.studentsabsents.ViewModelsHelpersModels.MyViewModel
import com.example.studentsabsents.R
import com.example.studentsabsents.ViewModelsHelpersModels.modelData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_layout.view.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MyViewModel
    private lateinit var databaseReference3: DatabaseReference
    private lateinit var alertDialog: AlertDialog
    ///////////////////////////////////////////////////////////
    var firebaseDatabase = FirebaseDatabase.getInstance()
    var databaseReference2 = firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath)
    var databaseReference4 = firebaseDatabase.getReference(TheFather).child(studentsPresentsPath).push()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        alertDialog = AlertDialog.Builder(this).create()
        var layoutInflater = LayoutInflater.from(this).inflate(R.layout.alert_layout, null, false)
        alertDialog.setView(layoutInflater)

        //////////////////////////////////////////////////////////

        var progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Wait Please")
        progressDialog.show()
        progressDialog.setCancelable(false)

        //////////////////////////////////
        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        viewModel.isThereSection()

        //////////////////////////////////////////////////////////

        viewModel.trueOrFalse.observe(this, Observer {

            var read = getSharedPreferences(SectionKey, Context.MODE_PRIVATE)
            var checking = read.getString(key, "NoData")!!
            var editor = getSharedPreferences(SectionKey, Context.MODE_PRIVATE).edit()


            if (it=="true"&& (checking=="NoData"|| checking!= getTime())){

                editor.putString(key,"NoData")
                editor.apply()

                buttonAddStudent.isEnabled=true
                buttonAddStudent.text="Add Student"

                progressDialog.dismiss()

            } else {

                if (it=="true"){


                    var i=Intent(this, SecondActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    progressDialog.dismiss()
                    startActivity(i)


                }else{

                    buttonAddStudent.isEnabled=false
                    buttonAddStudent.text="No section now"
                    editor.putString(key,"NoData")
                    editor.apply()
                    progressDialog.dismiss()

                }
            }

        })
        //////////////////////////////////////////////////////////







        buttonAddStudent.setOnClickListener {

            var progressDialog2=ProgressDialog(this@MainActivity)
            progressDialog.setTitle("Wait Please")


            if (!checkNetwork(this@MainActivity)) {
                showToast(this@MainActivity, "No Network")
            } else {

                if (editID.text.isEmpty()) {
                    showToast(this, "that is empty")

                } else {

                    progressDialog2.show()

                    databaseReference2.orderByChild("id").equalTo(editID.text.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                showToast(this@MainActivity, p0.message)
                                progressDialog2.dismiss()
                            }

                            override fun onDataChange(p0: DataSnapshot) {

                                if (p0.value != null) {
                                    for (i2 in p0.children) {
                                        ////////////////////////////////////////////////////
                                        var data2 = i2.getValue(modelData::class.java)
                                        ////////////////////////////////////////////////////
                                        progressDialog2.dismiss()
                                        ///////////////////////////
                                        alertDialog.setTitle("is your name ${data2!!.name} ")
                                        alertDialog.show()
                                        ////////////////////////////////////////////////////

                                        layoutInflater.yes.setOnClickListener {
                                            var editor = getSharedPreferences(
                                                SectionKey,
                                                Context.MODE_PRIVATE
                                            ).edit()
                                            editor.putString(key, getTime())
                                            editor.apply()

                                            ////////////////////////////////////////////////////

                                            databaseReference4.setValue(
                                                modelData(
                                                    data2.id!!,
                                                    data2.name!!,
                                                    databaseReference4.key.toString()
                                                )
                                            )

                                                .addOnCompleteListener {
                                                    databaseReference3 =
                                                        firebaseDatabase.getReference(TheFather)
                                                            .child(studentsAbsentsPath)
                                                            .child(data2!!.key!!)

                                                    databaseReference3.removeValue()
                                                        .addOnCompleteListener {

                                                            showToast(
                                                                this@MainActivity,
                                                                "${data2.name} is added successfully"
                                                            )
                                                            ///////////////////////////////////////////


                                                            var i = Intent(
                                                                this@MainActivity,
                                                                SecondActivity::class.java
                                                            )
                                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                                            startActivity(i)


                                                        }
                                                }
                                            alertDialog.dismiss()
                                        }
                                        layoutInflater.no.setOnClickListener {
                                            alertDialog.dismiss()
                                        }
                                    }
                                } else {
                                    progressDialog2.dismiss()
                                    showToast(this@MainActivity, "You are  Already Present")


                                }
                            }
                        })
                    }
                }
            }
        }
    }
