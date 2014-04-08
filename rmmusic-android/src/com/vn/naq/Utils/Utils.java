package com.vn.naq.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;

import com.vn.naq.model.TaskParams;


import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

public class Utils {
	static String TAG="class Utils:";
	
	public static boolean checkNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isAvailable() && ni.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String toMD5(String str){
		String temp=str;
        byte[] defaultBytes = str.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            str = hexString + "";
        } catch (NoSuchAlgorithmException e) {
        	
        	MobiLog.NAQE(TAG+e);
        }
        return str;
       // return temp;
        
    }
	
	public static void t(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
	
	private static boolean isHB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	@SuppressLint("NewApi")
	public static void executeAsyncTask(Executor executor,
		AsyncTask<TaskParams, Void, Integer> asyncTask, TaskParams[] params) {
	if (isHB) {
		asyncTask.executeOnExecutor(executor, params);
	} else {
		asyncTask.execute(params);
	}
}
	
	

}
