package com.example.wifiscan;

//private static final String ACTIVITY_TAG = "LogDemo";
//Log.e(MainActivity.ACTIVITY_TAG, "This is Error.");

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView mainText;
	WifiManager wifiManager;
	WifiReceiver wifiReceiver;
	List<ScanResult> wifiList;
	StringBuilder sb = new StringBuilder();

	private final static String HOST = "143.89.175.141";
	private final static int PORT = 8589;
	private static final String ACTIVITY_TAG = "LogDemo";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("eoe½Ì³Ì: Wifi Test. -by:IceskYsl");
		mainText = (TextView) findViewById(R.id.wifi);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiReceiver = new WifiReceiver();
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		wifiManager.startScan();
		mainText.setText("\nStarting Scan...\n");
		Log.v("ACTIVITY_TAG", "Message 11");
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Refresh");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		wifiManager.startScan();
		mainText.setText("Starting Scan");

		Log.v("ACTIVITY_TAG", "Message 11");

		Log.v("ACTIVITY_TAG", "Message 31");
		return super.onMenuItemSelected(featureId, item);
	}

	protected void onPause() {
		unregisterReceiver(wifiReceiver);
		super.onPause();
	}

	protected void onResume() {
		registerReceiver(wifiReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {

			Vector<AP> APInfos = new Vector<AP>();

			sb = new StringBuilder();
			wifiList = wifiManager.getScanResults();
			for (int i = 0; i < wifiList.size(); i++) {
				sb.append(new Integer(i + 1).toString() + ".");
				sb.append((wifiList.get(i)).toString());

				sb.append("\n\n");

				ArrayList<String> places = new ArrayList<String>();
				Log.v("ACTIVITY_TAG", "a");

				places.add("wow");
				Log.v("ACTIVITY_TAG", "b");

				String bssid = wifiList.get(i).BSSID;
				int frequency = wifiList.get(i).frequency;
				int level = wifiList.get(i).level;
				AP tmpAP = new AP(i, bssid, frequency, level);
				APInfos.add(tmpAP);

			}
			mainText.setText(sb);

			new callTask().execute(APInfos);
		}
	}

	private class callTask extends AsyncTask<Vector<AP>, Void, String> {
		@Override
		protected String doInBackground(Vector... params) {
			try {

				Vector<AP> APInfos = params[0];

				int length = APInfos.size();

				String allApMsg = "";

				for (int k = 0; k < length; k++) {

					AP tmpAP = APInfos.get(k);
					int id = tmpAP.getId();
					String mac = tmpAP.getMAC();
					int channel = tmpAP.getChannel();
					int power = tmpAP.getPower();
					allApMsg = allApMsg + id + "_" + mac + "_" + channel + "_"
							+ power + "|";

				}

				Socket Clientsocket;

				String feedback = "Error";

				Clientsocket = new Socket(HOST, PORT);

				Log.v("ACTIVITY_TAG", "Message 51");
				Scanner networkInput = new Scanner(
						Clientsocket.getInputStream());

				PrintWriter networkOutput = new PrintWriter(
						Clientsocket.getOutputStream(), true);

				Log.v("ACTIVITY_TAG", "Message 61");

				networkOutput.println(allApMsg);
				feedback = networkInput.nextLine();
				Clientsocket.close();

				return feedback;

			} catch (Exception e) {
				return "showClientListError!!!!";
			}

		}

		@Override
		protected void onPostExecute(String feedback) {

			Log.v("ACTIVITY_TAG", feedback);

		}
	}

}
