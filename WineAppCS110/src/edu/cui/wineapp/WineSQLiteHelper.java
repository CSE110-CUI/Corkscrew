package edu.cui.wineapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WineSQLiteHelper extends SQLiteOpenHelper {

  public static final String TABLE_WINES = "Wines";
  public static final String COLUMN_ID = "_id";
//  public static final String COLUMN_COMMENT = "comment";
//  public static final String COLUMN_AVIN = "avin";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_CODE = "code";
  public static final String COLUMN_REGION = "region";
  public static final String COLUMN_WINERY = "winery";
  public static final String COLUMN_VARIETAL = "varietal";
  public static final String COLUMN_PRICE = "price";
  public static final String COLUMN_VINTAGE = "vintage";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_LABEL_URL = "image";
  public static final String COLUMN_RANK = "rank";
  private static final String DATABASE_NAME = "wines.db";
  private static final int DATABASE_VERSION = 2;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_WINES + "(" + COLUMN_ID
      + " integer primary key autoincrement, " + COLUMN_NAME
      + " text not null, "+ COLUMN_CODE
      + " text not null, "+ COLUMN_REGION
      + " text not null, "+ COLUMN_WINERY
      + " text not null, "+ COLUMN_VARIETAL
      + " text not null, "+ COLUMN_PRICE
      + " text not null, "+ COLUMN_VINTAGE
      + " text not null, "+ COLUMN_TYPE
      + " text not null, "+ COLUMN_LABEL_URL
      + " text not null, "+ COLUMN_RANK
      + " text not null);";

  public WineSQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(WineSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_WINES);
    onCreate(db);
  }

} 