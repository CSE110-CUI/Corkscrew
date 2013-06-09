package edu.cui.wineapp.models.data;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserSQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_USERS = "Users";
  public static final String COLUMN_ID = "_id";
//  public static final String COLUMN_COMMENT = "comment";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_AGE = "age";
  public static final String COLUMN_WEIGHT = "weight";
  public static final String COLUMN_EMAIL = "email";
  public static final String COLUMN_SEX = "sex";
  public static final String COLUMN_COUNTRY = "country";
  public static final String COLUMN_PHOTOURL = "photourl";
  //public static final String COLUMN_DRINKEDWINES = "drinkedwine";
  //public static final String COLUMN_COMMENTS = "comments";
  public static final String COLUMN_PASSWORD = "password";
  private static final String DATABASE_NAME = "users.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_USERS + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NAME
      + " text not null, "+ COLUMN_AGE
      + " text not null, "+ COLUMN_WEIGHT
      + " text not null, "+ COLUMN_EMAIL
      + " text not null, "+ COLUMN_SEX
      + " text not null, "+ COLUMN_COUNTRY
      + " text not null, "+ COLUMN_PHOTOURL
      + " text not null, "+ COLUMN_PASSWORD
      + " text not null);";

  public UserSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(UserSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    onCreate(db);
  }

} 