package com.wettermedia.delivery;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WetterMediaActivity extends ListActivity {

	// All static variables
	//static final String WPATH = "http://wettermedia.se/extern/spara_mitt_kolli.php?";

	static final String PATH = "http://server.logistik.posten.se/servlet/PacTrack?lang=SE&kolliid=";
	// XML node keys
	static final String KEY_ITEM = "event"; // parent
											// node-----------------------
	static final String KEY_PARENT = "parcel";
	static final String KEY_DATE = "date";
	static final String KEY_TIME = "time";
	static final String KEY_LOCA = "location";
	static final String KEY_DESC = "description";

	String URL;
	//String WURL;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar

		Intent in = getIntent();
		String kolli = in.getStringExtra("Kolli");
		//String provider = in.getStringExtra("Provider").toLowerCase();
		
		URL = PATH + kolli;
		//WURL = WPATH + "id=" + kolli + "&p=" + provider;

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		// Remove title bar

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		NodeList parcelList = doc.getElementsByTagName(KEY_PARENT);

		// KOD för hämta parcelINFO.
		HashMap<String, String> map = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();

		String[] tags = { "kollidshow", "customername", "actualweight",
				"acceptor" };

		map2.put("kollidshow", kolli);

		for (int i = 0; i < parcelList.getLength(); i++) {
			Element e = (Element) parcelList.item(i);
			for (int n = 1; n < tags.length; n++)
				map2.put(tags[n], parser.getValue(e, tags[n]));

		}

		// menuItems.add(map2);

		TextView[] fs = new TextView[4];
		fs[0] = (TextView) findViewById(R.id.kollidshow);
		fs[1] = (TextView) findViewById(R.id.customername);
		fs[2] = (TextView) findViewById(R.id.actualweight);
		fs[3] = (TextView) findViewById(R.id.acceptor);

		for (int n = 0; n < fs.length; n++)
			fs[n].setText(map2.get(tags[n]));

		// fs[0].setText("Farhan");

		// looping through all item nodes <item>
		
				
		int lastItem = nl.getLength() - 1;
		for (int i = lastItem; i >= 0; i--) {
			// creating new HashMap
			map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			String timeStamp = parser.getValue(e, KEY_TIME);
			map.put(KEY_TIME, timeFormat(timeStamp));
			map.put(KEY_DATE, dateFormat(parser.getValue(e, KEY_DATE)));
			map.put(KEY_LOCA, parser.getValue(e, KEY_LOCA));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item, new String[] { KEY_DATE, KEY_DESC,
						KEY_TIME, KEY_LOCA }, new int[] { R.id.date,
						R.id.desciption, R.id.time, R.id.location });

		setListAdapter(adapter);
		

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			// @Override--------------------------------------
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String date = ((TextView) view.findViewById(R.id.date))
						.getText().toString();
				String description = ((TextView) view
						.findViewById(R.id.desciption)).getText().toString();
				String time = ((TextView) view.findViewById(R.id.time))
						.getText().toString();
				String location = ((TextView) view.findViewById(R.id.location))
						.getText().toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						SingleMenuItemActivity.class);
				in.putExtra(KEY_DATE, date);
				in.putExtra(KEY_TIME, time);
				in.putExtra(KEY_DESC, description);
				in.putExtra(KEY_LOCA, location);
				startActivity(in);

			}
		});
	}

	public static String timeFormat(String timestamp) {
		StringBuffer sb = new StringBuffer(timestamp);
		sb.insert(2, ':');
		return sb.toString();
	}

	public static String dateFormat(String datestamp) {
		StringBuffer sb = new StringBuffer(datestamp);
		sb.insert(4, '-');
		sb.insert(7, '-');

		return sb.toString();
	}
}