package be.heh.feraine_projetandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper
{
    public static final String USER_TABLE = "user_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGINMAIL = "mail";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_PRIVILEGE = "privilege";  // 2 -> Super User || 1 -> User (R/W) || 0 -> User (R)

    public DataBaseHelper(Context context)
    {
        super(context, "user.db", null, 1);
    }

    /** onCreate **/
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + USER_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LOGINMAIL + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                COLUMN_LASTNAME + " TEXT NOT NULL, " +
                COLUMN_PRIVILEGE + " INTEGER NOT NULL);"
        );
    }

    /** onUpgrade **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    /** ======== METHODES ======== **/
    // ==== Add User ====
    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase(); // Write into DB

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LOGINMAIL, user.getLoginMail());
        cv.put(COLUMN_PASSWORD, user.getPassword());
        cv.put(COLUMN_FIRSTNAME, user.getFirstName());
        cv.put(COLUMN_LASTNAME, user.getLastName());
        cv.put(COLUMN_PRIVILEGE, user.getPrivilege());

        db.insert(USER_TABLE, null, cv);
    }

    // ==== Delete User ====
    public void deleteUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(USER_TABLE, COLUMN_LOGINMAIL + " = " + "'" + user.getLoginMail() + "'", null);
    }

    // ==== Update ====
    // -- Update Login --
    public void updateLoginMail(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newCv = new ContentValues();
        newCv.put(COLUMN_LOGINMAIL, user.getLoginMail());

        db.update(USER_TABLE, newCv, COLUMN_ID + " = " + user.getId(), null);
    }

    // -- Update Password --
    public void updatePassword(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newCv = new ContentValues();
        newCv.put(COLUMN_PASSWORD, user.getPassword());

        db.update(USER_TABLE, newCv, COLUMN_LOGINMAIL + " = " + "'" + user.getLoginMail() + "'", null);
    }

    // -- Update First name --
    public void updateFirstName(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newCv = new ContentValues();
        newCv.put(COLUMN_FIRSTNAME, user.getFirstName());

        db.update(USER_TABLE, newCv, COLUMN_LOGINMAIL + " = " + "'" + user.getLoginMail() + "'", null);
    }

    // -- Update Last Name --
    public void updateLastName(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newCv = new ContentValues();
        newCv.put(COLUMN_LASTNAME, user.getLastName());

        db.update(USER_TABLE, newCv, COLUMN_LOGINMAIL + " = " + "'" + user.getLoginMail() + "'", null);
    }

    // -- Update privilege --
    public void updatePrivilege(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newCv = new ContentValues();
        newCv.put(COLUMN_PRIVILEGE, user.getPrivilege());

        db.update(USER_TABLE, newCv, COLUMN_LOGINMAIL + " = " + "'" + user.getLoginMail() + "'", null);
    }

    // ==== Get ====
    // -- Get User with Login/Mail --
    public User getUser(String mail)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_LOGINMAIL + " IS " + "'" + mail + "';", null);

        // If user doesn't exist
        if(cursor.getCount() == 0)
        {
            cursor.close();
            return null;
        }

        // If user exists
        cursor.moveToFirst();
        User user = new User();

        user.setId(cursor.getInt(0));
        user.setLoginMail(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        user.setFirstName(cursor.getString(3));
        user.setLastName(cursor.getString(4));
        user.setPrivilege(cursor.getInt(5));

        cursor.close();

        return user;
    }

    // -- Get All User --
    public ArrayList<User> getAllUsers()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE, null);
        ArrayList<User> users = new ArrayList<>();

        // If db is empty
        if(cursor.getCount() == 0)
        {
            cursor.close();
            return null;
        }

        while(cursor.moveToNext())
        {
            User user = new User();

            user.setId(cursor.getInt(0));
            user.setLoginMail(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setPrivilege(cursor.getInt(5));

            users.add(user);
        }

        cursor.close();

        return users;
    }
}
