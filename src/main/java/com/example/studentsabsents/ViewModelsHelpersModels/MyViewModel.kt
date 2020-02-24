package com.example.studentsabsents.ViewModelsHelpersModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.studentsabsents.ViewModelsHelpersModels.Helper.Companion.booleanPath
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyViewModel(application: Application):AndroidViewModel(application) {

    var databassReference=FirebaseDatabase.getInstance().getReference(booleanPath)
    var trueOrFalse=MutableLiveData<String>()

        fun isThereSection(){

        databassReference.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Helper.log("Error : ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {

                var data=p0.getValue(String::class.java)

                if (data=="true"){

                    trueOrFalse.value=data

                }else{
                    trueOrFalse.value="false"

                }
            }
        })

    }

}