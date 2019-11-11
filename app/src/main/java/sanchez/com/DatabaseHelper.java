package sanchez.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import sanchez.Model.Accounts;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public static final String
            DATABASE_NAME = "Accounts_Details.db",
            TABLE_NAME = "Register_table",
            TABLE_ID = "ID",
            TABLE_FULLNAME = "Name",
            TABLE_USERNAME = "Username",
            TABLE_PASSWORD = "Password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       try {
            db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Username TEXT, Password TEXT)");
        }
        catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Insert Account Data in Database
    public boolean insert(String Name, String Username, String Password){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name",Name);
            contentValues.put("Username",Username);
            contentValues.put("Password",Password);
            long ins = db.insert("Register_table",null,contentValues);
            if (ins==-1) return false;
            else return true;
    }

    //Check if Username Exists
    public Boolean chkUser(String Username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Register_table where Username=?", new String[]{Username});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    //Check Username and Password
    public Boolean username_password(String Username, String Password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Register_table where Username=? AND Password=?", new String[]{Username,Password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }
    public Boolean userNames(String Usernames){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Register_table where Username=?", new String[]{Usernames});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

       //Display All Accounts Record
    public ArrayList<Accounts> getAllRecords()
    {
        ArrayList<Accounts> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Register_table",null);
        while (cursor.moveToNext())
        {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(3);
            String username = cursor.getString(2);
            String password = cursor.getString(1);
            Accounts accounts = new Accounts(id,name,username,password);

            arrayList.add(accounts);
        }
        return arrayList;
    }

    //Update Accounts Record
    public void updateRecords(int id, String Name, String Username, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",Name);
        contentValues.put("Username",Username);
        contentValues.put("Password",Password);
        db.update(TABLE_NAME, contentValues, TABLE_ID + "=" + id,null);
        db.close();
    }

    //Delete Accounts Record
    public void deleteRecords(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,TABLE_ID + "=" + id,null);
        db.close();
    }

}
