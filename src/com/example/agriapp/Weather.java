package com.example.agriapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Weather extends Activity {

	DefaultHttpClient httpcnt;
	HttpPost httpost;
	ArrayList<NameValuePair> nvp;
	String response;
	ArrayList<String> spinner_list;
	ProgressDialog pd;

	TextView cor, weat, temp, pre, hum, wind, time, sun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		cor = (TextView) findViewById(R.id.TextView_cor);
		weat = (TextView) findViewById(R.id.txt_title);
		temp = (TextView) findViewById(R.id.txt_temp);
		pre = (TextView) findViewById(R.id.txt_soil);
		hum = (TextView) findViewById(R.id.TextView03);
		wind = (TextView) findViewById(R.id.TextView06);
		time = (TextView) findViewById(R.id.textView5);
		sun = (TextView) findViewById(R.id.TextView04);
		get_weather_thread();
	}

	public void get_weather_thread() {
		pd = ProgressDialog.show(this, "", "Please wait...");
		new Thread(new Runnable() {
			public void run() {
				get_weather();

			}

		}).start();

	}

	private void get_weather() {
		// TODO Auto-generated method stub
		try {
			httpcnt = new DefaultHttpClient();
			httpost = new HttpPost(
					"http://api.openweathermap.org/data/2.5/weather?id=1259411&appid=7c1e1adc9a526105d8e876c37299b1f7");

			ResponseHandler<String> s = new BasicResponseHandler();
			response = httpcnt.execute(httpost, s);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(), response,
					// Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, response);
					pd.dismiss();

					JSONObject obj;
					try {
						obj = new JSONObject(response);
						Log.d(General_Data.TAG, "Coordinates : Longitude " + obj.getJSONObject("coord").getDouble("lon")
								+ ", Latitute " + obj.getJSONObject("coord").getDouble("lat"));
						cor.setText("Longitude " + obj.getJSONObject("coord").getDouble("lon") + ", Latitute "
								+ obj.getJSONObject("coord").getDouble("lat"));
						JSONArray arr = obj.getJSONArray("weather");
						for (int i = 0; i < arr.length(); i++) {
							Log.d(General_Data.TAG, "Weather Condition : " + arr.getJSONObject(i).getString("main")
									+ "-" + arr.getJSONObject(i).getString("description"));
							weat.setText(arr.getJSONObject(i).getString("main") + "-"
									+ arr.getJSONObject(i).getString("description"));

						}

						Log.d(General_Data.TAG,
								"Temperature : " + obj.getJSONObject("main").getDouble("temp") + " Kelvin ("
										+ obj.getJSONObject("main").getDouble("temp_min") + " Kelvin to "
										+ obj.getJSONObject("main").getDouble("temp_max") + " Kelvin)");
						temp.setText(obj.getJSONObject("main").getDouble("temp") + " Kelvin");

						Log.d(General_Data.TAG,
								"Pressure : " + obj.getJSONObject("main").getDouble("pressure") + " hPa");
						pre.setText(obj.getJSONObject("main").getDouble("pressure") + " hPa");
						Log.d(General_Data.TAG, "Humidity: " + obj.getJSONObject("main").getDouble("humidity") + " %");
						hum.setText(obj.getJSONObject("main").getDouble("humidity") + " %");

						Log.d(General_Data.TAG, "Wind : Speed " + obj.getJSONObject("wind").getDouble("speed")
								+ " meter/sec , Direction " + obj.getJSONObject("wind").getDouble("deg") + " degrees");
						wind.setText("Speed " + obj.getJSONObject("wind").getDouble("speed") + " meter/sec , Direction "
								+ obj.getJSONObject("wind").getDouble("deg") + " degrees");

						Log.d(General_Data.TAG, "Time : " + new Date((long) obj.getLong("dt") * 1000));
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						time.setText(simpleDateFormat.format (new Date((long) obj.getLong("dt") * 1000)));
						SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm:ss");simpleTimeFormat = new SimpleDateFormat("hh:mm:ss");

						Log.d(General_Data.TAG,
								"Sun : Rise "
										+ simpleTimeFormat.format(
												new Date((long) obj.getJSONObject("sys").getLong("sunrise") * 1000))
										+ ", Set " + simpleTimeFormat.format(
												new Date((long) obj.getJSONObject("sys").getDouble("sunset") * 1000)));
						sun.setText("Rise "
								+ simpleTimeFormat
										.format(new Date((long) obj.getJSONObject("sys").getLong("sunrise") * 1000))
								+ ", Set " + simpleTimeFormat
										.format(new Date((long) obj.getJSONObject("sys").getDouble("sunset") * 1000)));

						// obj = new JSONObject(response);
						// Toast.makeText(getApplicationContext(), "Coordinates
						// : Longitude " +
						// obj.getJSONObject("coord").getDouble("lon")
						// + ", Latitute " +
						// obj.getJSONObject("coord").getDouble("lat"),
						// Toast.LENGTH_LONG).show();
						//
						// arr = obj.getJSONArray("weather");
						// for (int i = 0; i < arr.length(); i++) {
						// Toast.makeText(getApplicationContext(), "Weather
						// Condition : " +
						// arr.getJSONObject(i).getString("main")
						// + "-" +
						// arr.getJSONObject(i).getString("description"),
						// Toast.LENGTH_LONG).show();
						//
						//
						// }
						// Toast.makeText(getApplicationContext(),
						// "Temperature : " +
						// obj.getJSONObject("main").getDouble("temp") + "Kelvin
						// ("
						// + obj.getJSONObject("main").getDouble("temp_min") + "
						// Kelvin -"
						// + obj.getJSONObject("main").getDouble("temp_max") + "
						// Kelvin)", Toast.LENGTH_LONG).show();
						//
						// =
						// Toast.makeText(getApplicationContext(),
						// "Pressure : " +
						// obj.getJSONObject("main").getDouble("pressure") + "
						// hPa", Toast.LENGTH_LONG).show();
						//
						//
						// Toast.makeText(getApplicationContext(), "Humidity: "
						// + obj.getJSONObject("main").getDouble("humidity") + "
						// %", Toast.LENGTH_LONG).show();
						//
						//
						// Toast.makeText(getApplicationContext(), "Wind : Speed
						// " + obj.getJSONObject("wind").getDouble("speed")
						// + " meter/sec , Direction " +
						// obj.getJSONObject("wind").getDouble("deg") + "
						// degrees", Toast.LENGTH_LONG).show();
						//
						// Toast.makeText(getApplicationContext(), "Time : " +
						// new Date((long) obj.getLong("dt") * 1000),
						// Toast.LENGTH_LONG).show();
						//
						//
						// Toast.makeText(getApplicationContext(),"Sun : Rise "
						// + simpleTimeFormat.format(
						// new Date((long)
						// obj.getJSONObject("sys").getLong("sunrise") * 1000))
						// + ", Set " + simpleTimeFormat.format(
						// new Date((long)
						// obj.getJSONObject("sys").getDouble("sunset") *
						// 1000)), Toast.LENGTH_LONG).show();
						//

					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), "JSON Error : " + e.getLocalizedMessage(),
								Toast.LENGTH_LONG).show();
						Log.d(General_Data.TAG, e.getLocalizedMessage());
					}

				}
			});
		} catch (final Exception e) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "HTTP Error : " + e.getLocalizedMessage(),
							Toast.LENGTH_LONG).show();
					Log.d(General_Data.TAG, e.getLocalizedMessage());
					pd.dismiss();
				}
			});
		}

	}

}
