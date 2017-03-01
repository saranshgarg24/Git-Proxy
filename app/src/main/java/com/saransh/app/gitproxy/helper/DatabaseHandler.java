package com.saransh.app.gitproxy.helper;

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

    private static final String TABLE_SEARCH = "search";
    private static final String TABLE_BOOKMARK = "bookmark";

    private static final String KEY_ID = "id";
    private static final String KEY_QUERY = "query_key";

    private static final String KEY_TITLE = "title";
    private static final String KEY_LANG= "lang";
    private static final String KEY_STAR = "star";
    private static final String KEY_FORK = "forks";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_LASTUPDATE = "lastUpdate";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_SEARCH + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_QUERY + " TEXT"+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_BOOKMARK_TABLE = "CREATE TABLE " + TABLE_BOOKMARK + "("
                + KEY_TITLE + " TEXT,"
                + KEY_LANG + " TEXT,"
                + KEY_STAR + " TEXT,"
                + KEY_FORK + " TEXT,"
                + KEY_OWNER + " TEXT,"
                + KEY_LASTUPDATE + " TEXT"+ ")";
        db.execSQL(CREATE_BOOKMARK_TABLE);



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

    public void addBookmark(String title, String lang, String star, String forks, String owner, String lastUpdate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_LANG, lang);
        values.put(KEY_STAR, star);
        values.put(KEY_FORK, forks);
        values.put(KEY_OWNER, owner);
        values.put(KEY_LASTUPDATE, lastUpdate);
        db.insert(TABLE_BOOKMARK, null, values);
        db.close();
    }
    public Boolean IsBookmarked(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_BOOKMARK + " WHERE " + KEY_TITLE + " = '" + title + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;

    }

    public void deleteBookmark(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_BOOKMARK + " WHERE " + KEY_TITLE + " = '" + title + "'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        cursor.close();
        db.close();

    }

    public List<List<String>> getBookmarks() {

        List<List<String>> list = new ArrayList<List<String>>();

        String query = "SELECT * FROM " + TABLE_BOOKMARK +";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            do {
                List<String> data = new ArrayList<>();
                data.add(cursor.getString(0));
                data.add(cursor.getString(1));
                data.add(cursor.getString(2));
                data.add(cursor.getString(3));
                data.add(cursor.getString(4));
                data.add(cursor.getString(5));
                list.add(data);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;

    }
}
