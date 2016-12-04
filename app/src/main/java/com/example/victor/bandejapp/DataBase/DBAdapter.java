package com.example.victor.bandejapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {


    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String FEEDBACK_TABLE = "feedback";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DIA = "dia"; //AAAAMMDD
    public static final String COLUMN_REFEICAO = "refeicao"; //0 = ultima refeicao foi o almoco
                                                             //1 = ultima refeicao foi a janta

    private String[] allColumns = {COLUMN_ID, COLUMN_DIA, COLUMN_REFEICAO};

    public static final String CREATE_TABLE_FEEDBACK = "create table " + FEEDBACK_TABLE + " ( "
            + COLUMN_ID + " integer primary key not null, "
            + COLUMN_DIA + " integer not null, "
            + COLUMN_REFEICAO + " integer not null"
            + ");";

    private SQLiteDatabase sqlDB;
    private Context context;

    private DBHelper dBHelper;

    public DBAdapter(Context ctx) {
        context = ctx;
    }

    public DBAdapter open() throws android.database.SQLException {
        dBHelper = new DBHelper(context);
        sqlDB = dBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dBHelper.close();
    }

    public TimeStamp createTimeStamp (int dia, int refeicao) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_DIA, dia);
        values.put(COLUMN_REFEICAO, refeicao);

        long insertId = sqlDB.insert(FEEDBACK_TABLE, null, values);

        Cursor cursor = sqlDB.query(FEEDBACK_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        TimeStamp timeStamp = cursorToTimeStamp(cursor);
        cursor.close();

        return timeStamp;
    }

    public void deleteAll(){
        sqlDB.execSQL("delete from "+ FEEDBACK_TABLE);
    }

    public TimeStamp getTimeStamp() {

        TimeStamp timeStamp = new TimeStamp(0,0);

        Cursor cursor = sqlDB.query(FEEDBACK_TABLE, allColumns, null, null, null, null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            timeStamp = cursorToTimeStamp(cursor);
        }

        cursor.close();

        return timeStamp;
    }

    private TimeStamp cursorToTimeStamp(Cursor cursor) {
        return new TimeStamp(cursor.getInt(1), cursor.getInt(2));
    }

    private static class DBHelper extends SQLiteOpenHelper {

        DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_FEEDBACK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBHelper.class.getName(), "Upgrading database from version "
                    + oldVersion + " to " + newVersion + ", which will destroy all old date");

            db.execSQL("DROP TABLE IF EXISTS " + FEEDBACK_TABLE);
            onCreate(db);
        }
    }
}
