package com.example.sudoku;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Κλάση που διαχειρίζεται τη βάση δεδομένων
 */
public class DataTable extends SQLiteOpenHelper {
    //Σταθερές για τη ΒΔ (όνομα ΒΔ, έκδοση, πίνακες κλπ)
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usersDB.db"; //όνομα της βάσης
    public static final String TABLE_USERS = "users"; //όνομα πίνακα
    public static final String COLUMN_ID = "_id"; //στήλη των ids
    public static final String COLUMN_USERNAME = "username"; //στήλη των ψευδωνύμων
    public static final String COLUMN_WINS = "wins"; //στήλη για τις νίκες
    public static final String COLUMN_GAME = "game"; //στήλη που κρατάει το κλασικό παιχνίδι
    public static final String COLUMN_LOSSES = "losses"; //στήλη για τις ήττες

    //Constructor για την αρχικοποίηση της βάσης
    public DataTable(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //Δημιουργία του σχήματος της ΒΔ (πίνακας users)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
                TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_GAME + " INTEGER," +
                COLUMN_LOSSES + " INTEGER," +
                COLUMN_WINS + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    //Αναβάθμιση ΒΔ: εδώ τη διαγραφώ και τη ξαναδημιουργώ ίδια
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    /**
      *Μέθοδος για προσθήκη ενός ψευδωνύμου χρήστη στη ΒΔ
     * @param user ο νέος χρήστης
      */
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getNickname());
        values.put(COLUMN_LOSSES,0);
        values.put(COLUMN_GAME,1);
        values.put(COLUMN_WINS,0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    /**
     * Μέθοδος για εύρεση χρήστη βάσει ψευδωνύμου που έχει καταχωρήσει.
     * @param username το ψευδώνυμο του χρήστη που ψάχνουμε
     */
    public User findUser(String username) {
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = '" + username + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User(username);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setNickname(cursor.getString(1));

            user.setCurrentClassic(Integer.parseInt(cursor.getString(2)));

            user.setLosses(Integer.parseInt(cursor.getString(3)));

            user.setWins(Integer.parseInt(cursor.getString(4)));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;
    }

    /**
     * Συνάρτηση που διαβάζει όλα τα περιεχόμενα του πίνακα της βάσης και τα επιστρέφει.
     * @return τα περιεχόμενα της βάσης
     */
    public ArrayList<String> getAllItem() {
        ArrayList<String> allItem = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String data = "";
                data += cursor.getString(0);
                data += "\n";
                data += cursor.getString(1);


                allItem.add(data);

            } while (cursor.moveToNext());
        }
        return allItem;
    }

    /**
     * Μέθοδος για την ανανέωση των καταχωρήσεων για κάποιο χρήστη στη βάση (για παράδειγμα
     * προσθήκη ήττας ή νίκης ή αν ανανέωση του τρέχοντος παιχνιδιού σουντόκου).
     * @param user
     */
    public void update(User user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_WINS,user.getWins());
        contentValues.put(COLUMN_GAME,user.getCurrentClassic());
        contentValues.put(COLUMN_ID,user.getId());
        contentValues.put(COLUMN_LOSSES,user.getLosses());
        contentValues.put(COLUMN_USERNAME,user.getNickname());
        db.update(TABLE_USERS,contentValues,"_id=" + user.getId(), null);

    }
}