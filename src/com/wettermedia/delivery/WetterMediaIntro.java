package com.wettermedia.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class WetterMediaIntro extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introscreen);

		Thread logoTimer = new Thread() {
			public void run() {
				try {
					int logoTimer = 0;
					while (logoTimer < 1000) {
						sleep(100);
						logoTimer = logoTimer + 100;
					}
					startActivity(new Intent("android.intent.action.MAINWINDOW"));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		logoTimer.start();
	}
}
