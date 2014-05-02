package com.wettermedia.delivery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "kolli";

	private static final String DATABASE_NAME = "kolliDB";
	private static final String DATABASE_TABLE = "KolliTable";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	public static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
					+ " TEXT NOT NULL UNIQUE);"

			);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);

		}

	}

	public Database(Context c) {
		ourContext = c;
	}

	public Database open() {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();

		return this;
	}

	public Cursor fetchAllNotes() {

		return ourDatabase.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_NAME }, null, null, null, null, null);
	}

	public void close() {
		ourHelper.close();
	}

	public long createEntry(String kolli) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, kolli);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_NAME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iKolli = c.getColumnIndex(KEY_NAME);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + " " + c.getString(iKolli)
					+ "\n";
		}

		return result;

	}

	public static long insert(String kolli) { // <------------------should not
												// be static
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, kolli);
		return Database.insert(DATABASE_TABLE);
	}
}
