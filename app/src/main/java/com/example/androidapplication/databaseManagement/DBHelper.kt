package com.example.androidapplication.databaseManagement

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidapplication.activities.LoginActivity
import com.example.androidapplication.activities.MainActivity
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.factory.Cat
import com.example.androidapplication.factory.CatFactory

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

    fun insertCatIntoDatabase(name: String?, descrip: String?, url: String)
    {

        contentValue.put(breed,name)
        contentValue.put(description,descrip)
        contentValue.put(image, url)
        this.writableDatabase.insert(tableNameAnimal, null,contentValue)
    }

    @SuppressLint("Range")
    fun getListOfCatsByUser(userId: Int) : ArrayList<Animal>
    {
        val querry: String =
            "SELECT\n" +
                "  a.ID,\n" +
                "  a.Breed,\n" +
                "  a.Description,\n" +
                "  a.ImageUrl\n" +
                "FROM\n" +
                "  Animal as a,\n" +
                "  User as u,\n" +
                "  AnimalCollection as c\n" +
                "WHERE\n" +
                "  a.ID = c.AnimalID\n" +
                "  AND\n" +
                "  u.ID = c.UserID\n" +
                "  AND\n" +
                "  u.ID = ?"

        val string = arrayOf(userId.toString())
        val result = this.readableDatabase.rawQuery(querry, string)
        result.close()

        val cats : ArrayList<Animal> = ArrayList()

        while (result.moveToNext()) {

            val id = result.getInt(result.getColumnIndex("ID"))
            val breed = result.getString(result.getColumnIndex("Breed"))
            val description = result.getString(result.getColumnIndex("Description"))
            val imageURL = result.getString(result.getColumnIndex("imageURL"))

            val cat = CatFactory.CreateAnimal(id, breed, description, imageURL)

            cats.add(cat)
        }

        return cats
    }

}