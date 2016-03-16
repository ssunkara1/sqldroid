package peerpayments.learnsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sushantc on 3/5/16.
 */



public class DbAdapter {
    DbHelper helper;

    public DbAdapter(Context context)
    {
        helper=new DbHelper(context);
    }

    public long insertData(String name, String password)
    {
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, name);
        contentValues.put(DbHelper.PASSWORD, password);
        long id= db.insert(DbHelper.TABLE_NAME,null,contentValues);
        return id;
    }

    public String getAllData()
    {
        SQLiteDatabase db= helper.getWritableDatabase();

        String[] columns= {DbHelper.UID, DbHelper.NAME, DbHelper.PASSWORD};
        Cursor cursor=db.query(DbHelper.TABLE_NAME, columns, null, null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            int index1=cursor.getColumnIndex(DbHelper.UID);
            int cid=cursor.getInt(index1);
            int index2=cursor.getColumnIndex(DbHelper.NAME);
            String name = cursor.getString(index2);
            int index3=cursor.getColumnIndex(DbHelper.PASSWORD);
            String password = cursor.getString(index3);
            buffer.append(cid+" "+name+" "+password+"\n");
        }
        return buffer.toString();
    }

    public String getData(String name)
    {
        // select name, password from MYTABLE where name = 'sri'
        SQLiteDatabase db= helper.getWritableDatabase();

        String[] columns= {DbHelper.NAME, DbHelper.PASSWORD};
        Cursor cursor=db.query(DbHelper.TABLE_NAME, columns, DbHelper.NAME+" ='"+name+"'", null, null, null, null);
        // last 5 nulls were for selection, selectionArgs, groubBy, having, orderBy
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            int index2=cursor.getColumnIndex(DbHelper.NAME);
            String personName = cursor.getString(index2);
            int index3=cursor.getColumnIndex(DbHelper.PASSWORD);
            String password = cursor.getString(index3);
            buffer.append(personName+" "+password+"\n");
        }
        return buffer.toString();
    }

    public int updateName(String oldName, String newName)
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.NAME, newName);
        String whereClause=DbHelper.NAME+"=?";
        String[] whereArgs={oldName};
        int count = db.update(DbHelper.TABLE_NAME,contentValues, whereClause, whereArgs);
        return count;
    }

    public int deleteRow(String nameToDel)
    {
        SQLiteDatabase db= helper.getWritableDatabase();
        String whereClause=DbHelper.NAME+"=?";
        String[] whereArgs={nameToDel};
        int count = db.delete(DbHelper.TABLE_NAME,whereClause, whereArgs);
        return count;
    }

    static class DbHelper extends SQLiteOpenHelper { //static because of concept of least privilege, nonstatic also fine (static inner class can only access the static fields of outer class.)

        private static final String DATABASE_NAME = "mydb";
        private static final String TABLE_NAME = "MYTABLE";
        private static final int DATABASE_VERSION = 5;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String PASSWORD = "Password";
        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255), " + PASSWORD + " VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Message.message(context, "constructor called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE TABLE1 (-id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255));

            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context, "onCreate called");
            } catch (SQLException e) {
                Message.message(context, " " + e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "onUpgrade called");
                db.execSQL(DROP_TABLE); //or add cols, or back it up to secondary db or cloud..
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, " " + e);
            }
        }

    }
}
