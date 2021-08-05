package sg.robin.lai.C346.p12_our_singapore;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sgIslands.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ISLANDS = "islands";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_AREA = "area";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableSQL ="CREATE TABLE " + TABLE_ISLANDS +"("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_AREA + " INTEGER,"
                + COLUMN_STARS + " FLOAT)";
        db.execSQL(createTableSQL);
        Log.i("info","created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLANDS);
        // Create table(s) again
        onCreate(db);

    }

    public long insertIsland(String name, String description, int area, float stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_DESCRIPTION,description);
        values.put(COLUMN_AREA,area);
        values.put(COLUMN_STARS,stars);
        long result = db.insert(TABLE_ISLANDS, null, values);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Island> getAllIslands() {
        ArrayList<Island> islands = new ArrayList<Island>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME +  "," + COLUMN_DESCRIPTION + "," + COLUMN_AREA + "," + COLUMN_STARS + " FROM " + TABLE_ISLANDS
                + " ORDER BY " + COLUMN_STARS + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String islandName = cursor.getString(1);
                String islandDesc = cursor.getString(2);
                int islandArea = cursor.getInt(3);
                float islandStar = cursor.getFloat(4);
                Island island = new Island(id,islandName,islandDesc,islandArea,islandStar);
                islands.add(island);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return islands;
    }

    public int updateIsland(Island data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,data.getName());
        values.put(COLUMN_DESCRIPTION,data.getDescription());
        values.put(COLUMN_AREA,data.getArea());
        values.put(COLUMN_STARS,data.getStar());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLANDS, values, condition, args);
        if (result < 1) {
            Log.d("DBHelper", "Update failed");
        }
        db.close();
        return result;
    }

    public int deleteIsland(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLANDS, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Island> getAllIslands(String keyword) {
        ArrayList<Island> islands = new ArrayList<Island>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_AREA, COLUMN_STARS};
        String condition = COLUMN_STARS + " = 5";
        Cursor cursor = db.query(TABLE_ISLANDS, columns, condition,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                int area = cursor.getInt(3);
                float stars = cursor.getFloat(4);

                Island note = new Island(name, description, area, stars);

                islands.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return islands;
    }

}
