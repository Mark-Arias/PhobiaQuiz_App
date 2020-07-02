package com.example.phobiaquiz_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class performs needed sql queries to create a db to track and
 * store the quiz results for a given user logged by the main app
 */
public class QuizResultsDatabaseHelper extends SQLiteOpenHelper {

    // database data fields
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "quizdata";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHOBIA = "phobia";

    private HashMap hp;


    /**
     * constructor
     * @param context
     */
    public QuizResultsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    /**
     * on creation of this object, execute sql command to create specified table with specified
     * data fields
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table quizdata " + "(id integer primary key, name text, phobia text)");
    }

    /**
     * If called, old db version is dropped and onCreate() is called to create table again
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS quizdata");
        onCreate(db);
    }

    /**
     * Insert into db a user and their phobia result
     * @param name
     * @param phobia
     * @return
     */
    public boolean insertQuizResults (String name, String phobia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phobia", phobia);
        db.insert("quizdata", null, contentValues);
        return true;
    }

    /**
     * Selected specified db item by id via query
     * @param id
     * @return
     */
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from quizdata where id="+id+"", null );
        return res;
    }

    /**
     * calculate # of rows in db
     * @return
     */
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    /**
     * update specifed data with id in db
     * @param id
     * @param name
     * @param phobia
     * @return
     */
    public boolean updateQuizResults (Integer id, String name, String phobia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phobia", phobia);
        db.update("quizdata", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /**
     * Delete specified quiz results data with specified id tag in db
     * @param id
     * @return
     */
    public Integer deleteQuizResults (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("quizdata",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    /**
     *
     * @return to caller all quiz results stored in the db
     */
    public ArrayList<String> getAllQuizResults() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from quizdata", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
            array_list.add(res.getString(res.getColumnIndex(COLUMN_PHOBIA)));
            res.moveToNext();
        }
        return array_list;
    }
}
