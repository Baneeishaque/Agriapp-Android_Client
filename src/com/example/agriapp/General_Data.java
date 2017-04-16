package com.example.agriapp;

/**
 * Created by DK_win10-Asus_3ghz on 31-01-2017.
 */

public class General_Data {
	public static String TAG = "Agriapp Studio";

	public static String SHARED_PREFERENCE = "Agriapp_Studio";

	public static String SERVER_APPLICATION_ADDRESS;

	public static void generate_application_address(String ip, Boolean status) {
		if (status) {
			SERVER_APPLICATION_ADDRESS = ip + "/php";
		} else {
			SERVER_APPLICATION_ADDRESS = ip;
		}
	}

}
