package com.example.studentsabsents.ViewModelsHelpersModels


class modelData {

    var key:String?=null
    var id:String?=null
    var name:String?=null

    constructor(){


    }

    constructor(id:String,name:String,key:String){
        this.id=id
        this.name=name
        this.key=key
    }
}