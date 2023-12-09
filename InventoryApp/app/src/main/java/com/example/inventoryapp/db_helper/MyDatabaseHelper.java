package com.example.inventoryapp.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private  static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_VERSION = 1;

    private final String INVENTORY_TABLE_NAME = "inventory";
    private final String COLUMN_TITLE = "title";
    private final String COLUMN_CATEGORY = "category";
    private final String COLUMN_QUANTITY = "quantity";
    private final String TABLE_NAME = "user_auth";
    private final String COLUMN_ID = "_id";
    private final String INVENTORY_COLUMN_ID = "_id";
    private final String COLUMN_NAME = "user_name";

    private final String COLUMN_PASSWORD = "user_pass";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT);";


            String invent_query = "CREATE TABLE " + INVENTORY_TABLE_NAME +
                    " (" + INVENTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_QUANTITY + " TEXT);"; // Assuming quantity should be an INTEGER

            db.execSQL(query);
            db.execSQL(invent_query);

            Log.d("DatabaseCreation", "Tables created successfully.");
        } catch (SQLException e) {
            Log.e("DatabaseCreation", "Error creating tables: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
   db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
   db.execSQL("DROP TABLE IF EXISTS " + INVENTORY_TABLE_NAME);
   onCreate(db);
    }
    public void addUser(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_PASSWORD,password);
       long result =  db.insert(TABLE_NAME,null, cv);

  if(result == -1){
      Toast.makeText(context, "Failed add user", Toast.LENGTH_SHORT).show();
  }
  else {
      Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show();
  }

    }
    public Cursor getUserCredential(String name, String password){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + name + "' AND " +
                COLUMN_PASSWORD + " = '" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {

                String userName = cursor.getString(1);
                Toast.makeText(context, "TB is not null" + userName, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
        }
        return cursor;
    }
    public void addInventory(String title, String category, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_QUANTITY,quantity);
        long result =  db.insert(INVENTORY_TABLE_NAME,null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed insertion data", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor getInventoryData(){
        String query = "SELECT * FROM " + INVENTORY_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateInventory(String row_id,String title, String category, String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,title);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_QUANTITY,quantity);
        long result =  db.update(INVENTORY_TABLE_NAME,cv, "_id=?",new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed update data", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Update data successfully!", Toast.LENGTH_SHORT).show();
        }

    }
}
