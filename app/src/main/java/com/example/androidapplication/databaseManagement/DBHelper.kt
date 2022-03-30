package com.example.androidapplication.databaseManagement

import android.annotation.SuppressLint
import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidapplication.activities.LoginActivity
import com.example.androidapplication.activities.MainActivity
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.factory.Cat
import com.example.androidapplication.factory.CatFactory
import com.example.androidapplication.user.User

class DBHelper(context : Context) : SQLiteOpenHelper(context, "Cat_Distressor.db",null, 1){
    private val contentValue = ContentValues()
    private  val tableNameUser = "User"
    private val columnUser = "Name"
    private  val columnPasswordUser = "Password"
    private val spyUser = "SpyUser"

    private  val tableNameAnimal = "Animal"
    private val breed = "Breed"
    private  val description = "Description"
    private  val image = "ImageUrl"

    private  val tableAnimalCollection = "AnimalCollection"
    private val userID = "UserID"
    private  val animalID = "AnimalID"

    private val SQL_DATABASE_CREATION = "CREATE TABLE $tableNameUser (ID INTEGER PRIMARY KEY, $columnUser TEXT ,$columnPasswordUser TEXT,$spyUser INTEGER)"

    private val animalTable = "CREATE TABLE $tableNameAnimal (ID INTEGER PRIMARY KEY, $breed TEXT, $description TEXT,$image TEXT)"

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

    fun insertIntoDatabase(username: String, password: String,isSpy : Int) : Boolean
    {
        val query = "INSERT INTO $tableNameUser($columnUser,$columnPasswordUser,$spyUser) VALUES (?,?,?)"
        val array = arrayOf(username,password,isSpy)
        this.writableDatabase.execSQL(query,array)
        val userInserted = "SELECT * FROM $tableNameUser WHERE $columnUser = ?"
        val result = this.readableDatabase.rawQuery(userInserted, arrayOf(username))
        result.close()
        if(result.toString() == "-1")
            return false
        return true
    }

    fun insertCatIntoDatabase(name: String?, descrip: String?, url: String)
    {
        val query = "INSERT INTO $tableNameAnimal($breed,$description,$image) VALUES (?,?,?)"
        val array = arrayOf(name,descrip,url)
        this.writableDatabase.execSQL(query,array)
        println("Animal added to animal collection")
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

    fun checkUserName(username: String): Boolean {
        val query = "SELECT * FROM $tableNameUser WHERE Name = ?"
        val array  = arrayOf(username)
        val result = this.readableDatabase.rawQuery(query, array)
        result.close()
        return result.count == 1
    }

    @SuppressLint("Range")
    fun addCatInUserCollection(UserID : Int, animalImage: String)
    {
        val selectQuery = "SELECT ID as Cat_ID FROM $tableNameAnimal WHERE $image = ?"
        val idSearchResult = this.readableDatabase.rawQuery(selectQuery, arrayOf(animalImage))
        idSearchResult.moveToFirst()
        val id =  idSearchResult.getInt(idSearchResult.getColumnIndex("Cat_ID"))
        idSearchResult.close()
        insertIntoUserCollection(id,UserID)

    }
    private fun insertIntoUserCollection(id: Int,UserID: Int)
    {
        contentValue.put(userID,UserID)
        contentValue.put(animalID, id)

        val query = "INSERT INTO $tableAnimalCollection($userID,$animalID) VALUES (?,?)"
        val array = arrayOf(UserID,id)
        this.writableDatabase.execSQL(query,array)
        println("Animal added to user collection")
    }

    fun loginUser(username: String, password: String): Boolean {
        val query = "SELECT * FROM $tableNameUser WHERE Name = ? AND Password = ?"
        val result = this.readableDatabase.rawQuery(query, arrayOf(username,password))
        return result.count == 1
    }
}