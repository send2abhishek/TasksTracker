package com.attra.taskstracker.Providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.attra.taskstracker.Database.SqliteHelper;

public class TaskProvider extends ContentProvider {


    private static final String AUTHORITY="com.attra.taskstracker.Providers";
    private static final String BASE_PATH="user";
    private static final String BASE_PATH_TASK="task";
    public static final Uri CONTENT_URI=Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final Uri CONTENT_URI_TASK=Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_TASK);

    //constant to identify request operation

    private static final int USER=1;
    private static final int USER_ID=2;

    private static final int TASK=3;
    private static final int TASK_ID=4;
    private static final UriMatcher uriMatcher;
    public static final String CONTENT_ITEM_TYPE="USER";



    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,BASE_PATH,USER);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",USER_ID);

        uriMatcher.addURI(AUTHORITY,BASE_PATH_TASK,TASK);
        uriMatcher.addURI(AUTHORITY,BASE_PATH_TASK + "/#",TASK_ID);
    }

    private SqliteHelper helper;
    private SQLiteDatabase database;
    @Override
    public boolean onCreate() {
        helper=new SqliteHelper(getContext());
        database=helper.getWritableDatabase();
        return true;
    }


    @Override
    public Cursor query(Uri uri,   String[] projection,   String selection,
                        String[] selectionArgs,   String sortOrder) {

        if(uriMatcher.match(uri)==USER){
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(SqliteHelper.TABLE_NAME);
            Cursor cursor=queryBuilder.query(database,projection,selection,
                    selectionArgs,null,null,sortOrder);

            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            //getContext().getContentResolver().notifyChange(uri, null, false);
            return cursor;
        }

        else {

            if(uriMatcher.match(uri)==TASK_ID){

                selection=SqliteHelper.TASK_ID + "=" + uri.getLastPathSegment();
            }
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(SqliteHelper.TABLE_NAME_TASK);
            Cursor cursor=queryBuilder.query(database,projection,selection,
                    selectionArgs,null,null,sortOrder);
            //getContext().getContentResolver().notifyChange(uri, null, false);
            cursor.setNotificationUri(getContext().getContentResolver(),uri);

            return cursor;

        }





    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri,   ContentValues values) {


        if(uriMatcher.match(uri)==USER){

            long id=database.insert(SqliteHelper.TABLE_NAME,null,values);
            getContext().getContentResolver().notifyChange(uri, null, false);
            return Uri.parse(BASE_PATH + "/" +id);
        }

        else {
            long id1=database.insert(SqliteHelper.TABLE_NAME_TASK,null,values);
            getContext().getContentResolver().notifyChange(uri, null, false);
            return Uri.parse(BASE_PATH_TASK + "/" +id1);

        }



    }

    @Override
    public int delete(Uri uri,   String selection,   String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,   ContentValues values,   String selection,   String[] selectionArgs) {

        getContext().getContentResolver().notifyChange(uri, null, false);
        return database.update(SqliteHelper.TABLE_NAME_TASK,values,selection,selectionArgs);
    }
}
