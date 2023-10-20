package com.example.geocaching

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlliteDBhelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME="Notesapp.db"
        private const val DATABASE_VERSION=1
        private const val TABLE_NAME="MyNotes"
        private const val ID_COL="id"
        private const val TITLE_COL="title"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY,$TITLE_COL TEXT )"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldversion: Int, newversion: Int) {
            val droptable="DROP TABLE IF EXISTS $TABLE_NAME"
            db?.execSQL(droptable)
            onCreate(db)
    }

    fun addNote(note:ItemCourse){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(TITLE_COL,note.course)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getNotes():List<ItemCourse>{
        val notelist= mutableListOf<ItemCourse>()
        val db = readableDatabase
        val querry="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(querry,null)
        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(TITLE_COL))
            val note=ItemCourse(id,title)
            notelist.add(note)
        }
        cursor.close()
        db.close()
        return notelist
    }

//fun update(note:Note){
//    val db=writableDatabase
//    val value=ContentValues().apply {
//        put(TITLE_COL,note.title)
//        put(CONTENT_COL,note.content)
//    }
//    val whereclause="$ID_COL= ?"
//    val whereArgs= arrayOf(note.id.toString())
//    db.update(TABLE_NAME,value,whereclause,whereArgs)
//    db.close()
//}
//    fun getNoteById(noteid:Int):Note{
//        val db=readableDatabase
//        val querry="SELECT * FROM $TABLE_NAME WHERE $ID_COL=$noteid"
//        val cursor=db.rawQuery(querry,null)
//        cursor.moveToNext()
//        val id=cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
//        val title=cursor.getString(cursor.getColumnIndexOrThrow(TITLE_COL))
//        val content=cursor.getString(cursor.getColumnIndexOrThrow(CONTENT_COL))
//        cursor.close()
//        db.close()
//        return Note(id,title,content)
//    }
//    fun deleteNote(noteId:Int){
//        val db=writableDatabase
//        val whereclause="$ID_COL= ?"
//        val whereargs= arrayOf(noteId.toString())
//        db.delete(TABLE_NAME,whereclause,whereargs)
//        db.close()
//    }

}