package com.example.androidapplication.databaseManagement

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context : Context) : SQLiteOpenHelper(context, "Cat_Distressor.db",null, 1){
    private val contentValue = ContentValues()
    private  val tableNameUser = "User"
    private val columnUser = "Name"
    private  val columnPasswordUser = "Password"

    private  val tableNameAnimal = "Animal"
    private val breed = "Breed"
    private  val description = "Description"
    private  val image = "ImageUrl"

    private  val tableAnimalCollection = "AnimalCollection"
    private val userID = "UserID"
    private  val animalID = "AnimalID"

    private val SQL_DATABASE_CREATION = "CREATE TABLE $tableNameUser (ID INTEGER PRIMARY KEY, $columnUser TEXT ,$columnPasswordUser TEXT)"

    private val animalTable = "CREATE TABLE $tableNameAnimal (ID INTEGER PRIMARY KEY, $breed TEXT, $description TEXT,${image} TEXT)"

    private val collectionTable = "CREATE TABLE $tableAnimalCollection (ID INTEGER PRIMARY KEY," +
            " $userID INTEGER NOT NULL," +
            " $animalID INTEGER NOT NULL," +
            " FOREIGN KEY($userID) REFERENCES $tableNameUser(ID)," +
            " FOREIGN KEY($animalID) REFERENCES $tableNameAnimal(ID))"



    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $tableNameUser"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_DATABASE_CREATION)
        db.execSQL(animalTable)
        db.execSQL(collectionTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        //Update
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertIntoDatabase(username: String, password: String) : Boolean
    {
        //encryption
            contentValue.put(columnUser,username)
            contentValue.put(columnPasswordUser,password)
                val result = this.writableDatabase.insert(tableNameUser, null,contentValue)
                if(result.toString() == "-1")
                    return false
        return true
    }

}