package example.israt.com.testbioscopeserver.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import example.israt.com.testbioscopeserver.Constant;
import example.israt.com.testbioscopeserver.model.ServerStatus;


public class DatabaseHelper extends SQLiteOpenHelper {

    int IDd;

    public static final String DB_NAME = "bioscope";
    private static final String TAG = "DatabaseHelper";
    String val=null;
    int value;
    public static String DB_PATH;
    private SQLiteDatabase database;
    private Context context;

    public DatabaseHelper(Context context){


        super(context, DB_NAME, null, 1);
        this.context = context;

        DB_PATH = context.getFilesDir().getPath() + "/databases/";
        this.database = openDatabase();

    }





    public SQLiteDatabase openDatabase(){
        if(database==null){
            createDatabase();
            Log.e(getClass().getName(), "Database created...");
        }

        return database;
    }


    private void createDatabase(){
        boolean dbExists = checkDB();
        if(!dbExists){
            this.getReadableDatabase();
            database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
            createTable();
            Log.e(getClass().getName(),"No Database");
        }

    }

    private boolean checkDB(){
        String path = DB_PATH + DB_NAME;
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return false;
    }


    public SQLiteDatabase getDatabase(){
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);
        return database;
    }







    public void createTable() {
        String TABLESTATUS = "CREATE TABLE IF NOT EXISTS " + Constant.Database.TABLESTATUS + "("
                + Constant.Database.TABLE_STATUS.ID + " INTEGER PRIMARY KEY UNIQUE,"
                + Constant.Database.TABLE_STATUS.DOWN_TIME + " TEXT,"
                + Constant.Database.TABLE_STATUS.UP_TIME + " TEXT,"
                + Constant.Database.TABLE_STATUS.DATE + " TEXT,"
                + Constant.Database.TABLE_STATUS.SERVER_STATUS + " TEXT"
                + ")";

        try {
            database.execSQL( TABLESTATUS );
            database.close();
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error Creating Table");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addStatus(ServerStatus serverStatus) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.Database.TABLE_STATUS.DOWN_TIME, serverStatus.getDownTime()); // Contact Name
        values.put(Constant.Database.TABLE_STATUS.DATE, serverStatus.getDate()); // Contact Name
        values.put(Constant.Database.TABLE_STATUS.SERVER_STATUS, serverStatus.getServerStatus());
        // Inserting Row
        db.insert(Constant.Database.TABLESTATUS, null, values);
        db.close(); // Closing database connection
    }
    public String getLast() {
        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery =  "SELECT MAX(id) FROM server_status";

        String selectQuery = "SELECT * FROM server_status ORDER BY id DESC LIMIT 1";/*  Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getCount();
        if(cursor.moveToFirst()){
            IDd=cursor.getColumnIndex("ID");
        }

        if(IDd<0)
        {
            IDd=1;
        }*/
        Cursor mCursor = db.rawQuery(selectQuery, null);
        if (mCursor != null && mCursor.moveToFirst()) {

            try {
                if (mCursor.getCount() > 0) {
                    int count = mCursor.getCount();
                    String s = mCursor.getString(mCursor.getColumnIndex("id"));
                    int id = mCursor.getInt(0);
                    String iddd = mCursor.getString(4);
                    IDd = mCursor.getInt(mCursor.getColumnIndex("id"));

                    String selectQuery2 = "SELECT * FROM server_status where id = " + IDd;
                    Cursor cursor2 = db.rawQuery(selectQuery2, null);
                    try {
                        cursor2.moveToFirst();
                        val = cursor2.getString(4);
                    } catch (Exception e) {

                    }
                    cursor2.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                db.close();
                mCursor.close();

            }

    }
return val;
    }
    public void UpdateStatus(ServerStatus serverStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery =  "SELECT * FROM server_status ORDER BY id DESC LIMIT 1";
       /*  Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.getCount();
        if(cursor.moveToFirst()){
            IDd=cursor.getColumnIndex("ID");
        }

        if(IDd<0)
        {
            IDd=1;
        }*/
        Cursor mCursor = db.rawQuery(selectQuery, null);
        try {
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                IDd = mCursor.getInt(mCursor.getColumnIndex("id"));
                ContentValues values = new ContentValues();
                values.put(Constant.Database.TABLE_STATUS.UP_TIME, serverStatus.getUpTime()); // Contact Name
                values.put(Constant.Database.TABLE_STATUS.SERVER_STATUS, serverStatus.getServerStatus());

                // Inserting Row
                db.updateWithOnConflict(Constant.Database.TABLESTATUS,  values,Constant.Database.TABLE_STATUS.ID + " = ? ", new String[] { Integer.toString(IDd) },SQLiteDatabase.CONFLICT_REPLACE);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.close();
        }


    }
    public int getCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        //.String selectQuery =  "SELECT * FROM server_status where status = "+"'Down'";

        String selectQuery =  "SELECT * FROM server_status ";
        Cursor mCursor = db.rawQuery(selectQuery, null);

            if (mCursor.getCount() > 0) {
              value= mCursor.getCount();
            }
            else value=0;

        return value;
    }
    public ArrayList<ServerStatus> getAll()
    {     ArrayList<ServerStatus> allstatus = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM server_status ORDER BY id DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ServerStatus user = new ServerStatus();
                    user.setDate(cursor.getString(3));
                    user.setUpTime(cursor.getString(2));
                    user.setDownTime(cursor.getString(1));
                    user.setServerStatus(cursor.getString(4));
                    allstatus.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e){
            Log.d(TAG, "nullpointer exception");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }



        return allstatus;

    }
}
