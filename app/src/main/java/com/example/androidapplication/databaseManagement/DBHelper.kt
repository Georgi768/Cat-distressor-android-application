package com.example.androidapplication.databaseManagement

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.factory.CatFactory
import com.example.androidapplication.factory.Factory

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Cat_Distressor.db", null, 1) {
    private val contentValue = ContentValues()

    // Properties for user table
    private val tableNameUser = "User"
    private val columnUser = "Name"
    private val columnPasswordUser = "Password"
    private val spyUser = "SpyUser"

    // Properties for animal table
    private val tableNameAnimal = "Animal"
    private val breed = "Breed"
    private val description = "Description"
    private val image = "ImageUrl"

    // Properties for user collection
    private val tableAnimalCollection = "AnimalCollection"
    private val userID = "UserID"
    private val animalID = "AnimalID"

    private val SQL_DATABASE_CREATION =
        "CREATE TABLE $tableNameUser (ID INTEGER PRIMARY KEY, $columnUser TEXT ,$columnPasswordUser TEXT,$spyUser INTEGER)"

    private val animalTable =
        "CREATE TABLE $tableNameAnimal (ID INTEGER PRIMARY KEY, $breed TEXT, $description TEXT,$image TEXT)"

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

    fun insertIntoDatabase(username: String, password: String, isSpy: Int): Boolean {
        val query =
            "INSERT INTO $tableNameUser($columnUser,$columnPasswordUser,$spyUser) VALUES (?,?,?)"
        val array = arrayOf(username, password, isSpy)
        this.writableDatabase.execSQL(query, array)
        val userInserted = "SELECT * FROM $tableNameUser WHERE $columnUser = ?"
        val result = this.readableDatabase.rawQuery(userInserted, arrayOf(username))
        result.close()
        if (result.toString() == "-1")
            return false
        return true
    }

    fun insertCatIntoDatabase(name: String?, descrip: String?, url: String) {
        val query = "INSERT INTO $tableNameAnimal($breed,$description,$image) VALUES (?,?,?)"
        val array = arrayOf(name, descrip, url)
        this.writableDatabase.execSQL(query, array)
        println("Animal added to animal collection")
    }

    @SuppressLint("Range")
    fun getListOfCatsByUser(userId: Int): ArrayList<Animal> {
        val querry: String =
            "SELECT\n" +
                    "  a.ID as id_User,\n" +
                    "  a.Breed as breed_Animal,\n" +
                    "  a.Description as description_Animal,\n" +
                    "  a.ImageUrl as cat_Url\n" +
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

        val cats = ArrayList<Animal>()

        while (result.moveToNext()) {

            val id = result.getInt(result.getColumnIndex("id_User"))
            val breed = result.getString(result.getColumnIndex("breed_Animal"))
            val description = result.getString(result.getColumnIndex("description_Animal"))
            val imageURL = result.getString(result.getColumnIndex("cat_Url"))
            val catFactory: Factory = CatFactory()
            val cat = catFactory.createAnimal(id, breed, description, imageURL)
            cats.add(cat)
        }
        result.close()

        return cats
    }

    @SuppressLint("Range")
    fun addCatInUserCollection(UserID: Int, animalImage: String) {
        val selectQuery = "SELECT ID as Cat_ID FROM $tableNameAnimal WHERE $image = ?"
        val idSearchResult = this.readableDatabase.rawQuery(selectQuery, arrayOf(animalImage))
        idSearchResult.moveToFirst()
        val id = idSearchResult.getInt(idSearchResult.getColumnIndex("Cat_ID"))
        idSearchResult.close()
        insertIntoUserCollection(id, UserID)
    }

    private fun insertIntoUserCollection(id: Int, UserID: Int) {
        contentValue.put(userID, UserID)
        contentValue.put(animalID, id)

        val query = "INSERT INTO $tableAnimalCollection($userID,$animalID) VALUES (?,?)"
        val array = arrayOf(UserID, id)
        this.writableDatabase.execSQL(query, array)
        println("Animal added to user collection")
    }

    fun loginUser(username: String, password: String): Boolean {
        val query = "SELECT * FROM $tableNameUser WHERE Name = ? AND Password = ?"
        val result = this.readableDatabase.rawQuery(query, arrayOf(username, password))
        return result.count == 1
    }

    @SuppressLint("Range")
    fun getUserCollection(userName: String): ArrayList<Animal> {
        val query = "SELECT ID as user_ID FROM $tableNameUser WHERE $columnUser = ?"
        val userID = this.readableDatabase.rawQuery(query, arrayOf(userName))
        userID.moveToFirst()
        try {
            val iD = userID.getInt(userID.getColumnIndex("user_ID"))
            userID.close()
            return getListOfCatsByUser(iD)
        } catch (e: IndexOutOfBoundsException) {
            println(e)
        }
        return ArrayList()
    }
}