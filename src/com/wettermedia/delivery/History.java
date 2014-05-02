package com.wettermedia.delivery;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class History extends Activity  {
	private static final int DIALOG_ID = 100;

	private SQLiteDatabase database;

	private CursorAdapter dataSource;

	private View entryView;
	private static final String fields[] = { "kolli", 
        BaseColumns._ID };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		/*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.historyresult);
		
		Database info = new Database(this);
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);*/
		
		setContentView(R.layout.history);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		Database info = new Database(this);
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
		
		/*Database.DbHelper helper = new Database.DbHelper(this);
		database = helper.getWritableDatabase();
        Cursor data = database.query("KolliTable", fields, 
            null, null, null, null, null);
		
        dataSource = new SimpleCursorAdapter(this, 
            R.layout.history, data, fields,	
            new int[] { R.id.tvSQLinfo });

       

		setListAdapter(dataSource);*/

	}
}
