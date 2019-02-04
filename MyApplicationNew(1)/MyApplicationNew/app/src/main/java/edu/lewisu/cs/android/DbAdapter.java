package edu.lewisu.cs.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {
    private static final String DATABASE_NAME = "data.db";
    private static final String DATABASE_TABLE = "tasks";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ROWID = "_id";
	public static final String DESCR_NAME = "description";
    public static final String PRIORITY_NAME = "priority";
    public static final String DONE_NAME = "done";
    
	private static final String[] allCols = new String[] {KEY_ROWID, DESCR_NAME, PRIORITY_NAME,
		DONE_NAME };

    private static final String DATABASE_CREATE =
            "create table " + 
            DATABASE_TABLE +" (" +
            KEY_ROWID + " integer primary key autoincrement, " +
            DESCR_NAME +  " text not null, " +
            PRIORITY_NAME + " text, " +
            DONE_NAME + " integer default 0"+ ");";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
  
    private final Context mCtx;

     public DbAdapter(Context ctx) {
        this.mCtx = ctx;  //set context
    }

     public void open() throws SQLException {  //open db, if fail throw an exception
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }


    public long createTask(String descr, String priority) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DESCR_NAME,  descr);
        initialValues.put(PRIORITY_NAME, priority);

        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }

     public boolean deleteTask(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchAllTasks() {
       return mDb.query(DATABASE_TABLE, allCols , null, null, null, null, null);
    }

  
    public Cursor fetchTask(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(DATABASE_TABLE, allCols, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }
    
    public Cursor fetchIncompleteTasks() throws SQLException {

        Cursor mCursor =

            mDb.query(DATABASE_TABLE, allCols, DONE_NAME + "=0", null, 
            		null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

 
    public boolean updateTask(long rowId, String descr, String priority, int done) {
        ContentValues args = new ContentValues();
        args.put(DESCR_NAME,  descr);
        args.put(PRIORITY_NAME, priority);
        args.put(DONE_NAME, done);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    

    private static class DatabaseHelper extends SQLiteOpenHelper {
    	
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DatabaseHelper", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

}
