package com.wettermedia.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// XML node keys
	static final String KEY_DATE = "date";
	static final String KEY_TIME = "time";
	static final String KEY_LOCA = "location";
	static final String KEY_DESC = "description";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.single_list_item);
        
        Intent in = getIntent();
        
        String date = in.getStringExtra(KEY_DATE);
        String time = in.getStringExtra(KEY_TIME);
        String location = in.getStringExtra(KEY_LOCA);
        String description = in.getStringExtra(KEY_DESC);
        
        TextView lblDate = (TextView) findViewById(R.id.date_label);
        TextView lblTime = (TextView) findViewById(R.id.time_label);
        TextView lblLoca = (TextView) findViewById(R.id.location_label);
        TextView lblDesc = (TextView) findViewById(R.id.description_label);
        
        lblDate.setText(date);
        lblTime.setText(time);
        lblLoca.setText(location);
        lblDesc.setText(description);
    }
}
