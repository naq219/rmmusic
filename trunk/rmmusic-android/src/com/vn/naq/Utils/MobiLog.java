/**
 * copyright WISeKey SA
 * author: Phathv 
 * date: 09/04/2012
 * GPL
 */

package com.vn.naq.Utils;

import android.content.Context;
import android.util.Log;

public class MobiLog {
	protected static MobiLog instance;
	private static String TAG = "Ngocnv";

	public static MobiLog getInstance() {
		if (instance == null)
			instance = new MobiLog();
		return instance;
	}

	public static void logD(String info) {
		
			Log.d(TAG, info+"");
		
	}

	public static void logD(Context context, String info) {
		if (info != null) {
			Log.d(TAG, context.getClass().getSimpleName() + " - " + info);
		}
	}

	public static void logE(String info) {
		if (info != null) {
			Log.e("Ngocnv1", info);
		}
	}

	public static void logI(String info) {
		if (info != null) {
			Log.i(TAG, info);
		}
	}

	public static void NAQI(String info) {
		Log.i("NAQ", info + "");
	}

	public static void NAQE(String info) {
		Log.e("NAQ", info + "");
	}
	
	public static void NAQW(String info) {
		Log.w("NAQ", info + "");
	}

}
