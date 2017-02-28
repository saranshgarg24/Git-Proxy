package com.saransh.app.gitproxy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "gitproxy";

    private static final String TABLE_SEARCH = "login";
    private static final String TABLE_BOOKMARK = "notices";
 
    private static final String KEY_ID = "id";
    private static final String KEY_QUERY = "query_key";

    

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_SEARCH + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_QUERY + " TEXT"+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        

    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH);
        onCreate(db);
    }
 
    public void addSearchQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_QUERY, query);
        db.insert(TABLE_SEARCH, null, values);
        db.close();
    }
    public List<String> getSearchQueries() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SEARCH;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        List<String> queries = new ArrayList<String>();
        if(cursor.getCount() > 0) {
            do {
                queries.add(cursor.getString(1));
            } while(cursor.moveToNext());
        }
        return queries;
    }

    public void resetTable_Search(){
        SQLiteDatabase db = this.getWritableDatabase();
        
        db.delete(TABLE_SEARCH, null, null);
        db.close();
    }


}
