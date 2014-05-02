package com.wettermedia.delivery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class MainWindow extends Activity implements OnClickListener {
	
	String kolli;
	String provider;
	Context mContext;
	Button foretag;
	//Kollar ifall det finns tillgång till internet
	private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mContext = this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainwin);
		Button history = (Button) findViewById(R.id.tidigare);
		EditText ko = (EditText) findViewById(R.id.kolliidnr);
		foretag = (Button) findViewById(R.id.provider);
		Button b = (Button) findViewById(R.id.searchbutton);
		ko.setText("87337996715SE");
		foretag.setOnClickListener(this);
		
		b.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				if (!haveNetworkConnection()) {
					AlertDialog alertDialog = new AlertDialog.Builder(mContext)
							.create();
					alertDialog.setTitle("Info");
					alertDialog
							.setMessage("Finns ingen tillgång till internet, var god och sätt på internet och prova igen");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});

					alertDialog.show();

				} else {

					EditText ko = (EditText) findViewById(R.id.kolliidnr);
					// String data = ko.getText().toString();
					// historyList.add(kolli);

					kolli = ko.getText().toString();
					Database entry = new Database(MainWindow.this);
					entry.open();
					entry.createEntry(kolli);
					entry.close();

					// http://www.youtube.com/watch?v=j-IV87qQ00M example

					// provider = "posten"; // "dhl", "schenker", "ups"
					Intent in = new Intent("android.intent.action.CLEARSCREEN");
					// data(new String(ko.getText().toString()));
					in.putExtra("Kolli", kolli);
					Log.d("pritt", "i'm starting activity");
					// in.putExtra("Provider", provider);
					startActivity(in);
				}
			}

		});

		history.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// Bundle b = new Bundle();

				// b.putStringArrayList("history", historyList);

				Intent hist = new Intent("android.intent.action.SEARCHED");
				// hist.putExtras(b);
				// hist.putStringArrayListExtra("history", historyList);
				// Log.d("pritt", "stuckit");
				startActivity(hist);
			}
		});

	}


	public void onClick(View view) {

		if (view.equals(foretag)) {
			showDialogButtonClick();
			// foretag.setText(choice);
		}
	}

	private void showDialogButtonClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Välj ett företag");

		final CharSequence[] choiceList = { "Posten"/*, "Schenker", "DHL", "UPS"*/ };

		int selected = -1;

		builder.setItems(choiceList, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), choiceList[item],
						Toast.LENGTH_SHORT).show();
				// HÄR SKA DET FINNAS KOD FÖR ATT SÄTTA PROVIDER TILL valet!
				provider = choiceList[item].toString();
				foretag.setText(provider);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

}
