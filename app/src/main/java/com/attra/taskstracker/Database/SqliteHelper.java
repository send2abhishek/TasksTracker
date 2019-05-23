package com.attra.taskstracker.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SqliteHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME="task";
    private static final int DB_VERSION=2;

    //constant for table user and its columns

    public static final String TABLE_NAME="users";

    public static final String ID="_id";
    public static final String NAME="Name";
    public static final String EMAIL="Email";
    public static final String PASSWORD="Password";
    public static final String CREATION="CreatedAt";


    //constant for table task and its columns

    public static final String TABLE_NAME_TASK="task";

    public static final String TASK_ID="_id";
    public static final String TASK_TITLE="TaskTitle";
    public static final String TASK_DESC="TaskDesc";
    public static final String TASK_DATE="TaskDate";
    public static final String TASK_START_TIME="TaskStartTime";
    public static final String TASK_END_TIME="TaskEndTime";
    public static final String TASK_STATUS="TaskStatus";
    public static final String TASK_COMMENTS="TaskComments";
    public static final String TASK_USER_NAME="TaskUserNAME";


    private static final String TABLE_CREATION_QUERY="CREATE TABLE " + TABLE_NAME + " ("

            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME + " TEXT,"
            + EMAIL + " TEXT,"
            + PASSWORD + " TEXT,"
            + CREATION + " TEXT DEFAULT CURRENT_TIMESTAMP "
            + " )";



        //Table Creation Query For Task Table

        private static final String TABLE_TASK_CREATION_QUERY="CREATE TABLE " + TABLE_NAME_TASK + " ("

                + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TASK_TITLE + " TEXT,"
                + TASK_DESC + " TEXT,"
                + TASK_DATE + " TEXT,"
                + TASK_START_TIME + " TEXT,"
                + TASK_END_TIME + " TEXT,"
                + TASK_STATUS + " TEXT,"
                + TASK_COMMENTS + " TEXT,"
                + TASK_USER_NAME + " TEXT"
                + " )";





    public static final String[] TABLE_ALL_COLUMNS={ID,NAME,EMAIL,PASSWORD,CREATION};
    public static final String[] TABLE_TASK_ALL_COLUMNS={TASK_ID,TASK_TITLE,TASK_DESC,TASK_DATE,TASK_START_TIME,
            TASK_END_TIME,TASK_STATUS,TASK_COMMENTS,TASK_USER_NAME
    };


    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(TABLE_CREATION_QUERY);
            db.execSQL(TABLE_TASK_CREATION_QUERY);
            Toast.makeText(context,"Table Created ",Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context,"Exception Occurred "+ e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        try {
            Toast.makeText(context, "onUpgrade called" , Toast.LENGTH_LONG).show();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TASK);
            onCreate(db);
        }
        catch (SQLException e) {
            Toast.makeText(context, "Have Exception in  upgrading table" +e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
