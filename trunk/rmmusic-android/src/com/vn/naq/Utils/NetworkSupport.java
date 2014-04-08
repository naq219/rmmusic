package com.vn.naq.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.vn.naq.R;
import com.vn.naq.object.ControlOJ;
import com.vn.naq.object.NetworkData;

import android.content.Context;
import android.util.Base64;

public class NetworkSupport implements Constants {

	public static NetworkData checkUserPass(Context context, String user, String pass) {
		String TAG = "class NetworkSupport  ";

		String userName = user;
		String PassWord = pass;
		NetworkData dt = new NetworkData();
		String strUrl = C_URL.LOGIN;
		String result = "";

		if (!Utils.checkNetworkAvailable(context)) {
			dt.result = context.getString(R.string.msg_no_network);
			dt.code = 101;
			return dt;
		}

		JSONObject jsonSend = new JSONObject();
		try {
			jsonSend.put(C_PARA.USERNAME, userName);
			jsonSend.put(C_PARA.PASSWORD, PassWord);
			jsonSend.put(C_PARA.GCM_REGID, C_PARA.regIDSend);
			MobiLog.logI(TAG + "json.sent checkpass" + jsonSend.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return postData(strUrl, jsonSend.toString());

	}

	public static NetworkData register(Context context, String user, String pass, String regID, String email, String name) {
		String TAG = "class NetworkSupport  ";

		String userName = user;
		String PassWord = pass;
		NetworkData dt = new NetworkData();
		String strUrl = C_URL.REGISTER;
		String result = "";

		if (!Utils.checkNetworkAvailable(context)) {
			dt.result = context.getString(R.string.msg_no_network);
			dt.code = 101;
			return dt;
		}

		JSONObject jsonSend = new JSONObject();
		try {
			jsonSend.put(C_PARA.USERNAME, userName);
			jsonSend.put(C_PARA.PASSWORD, PassWord);
			jsonSend.put(C_PARA.GCM_REGID, regID);
			jsonSend.put(C_PARA.EMAIL, email);
			jsonSend.put(C_PARA.NAME, name);
			MobiLog.logI(TAG + "json.sent register:" + jsonSend.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return postData(strUrl, jsonSend.toString());

	}

	
	 //---------------------class getControl-------------------------------
	
	public static NetworkData getControl(Context context) {

		NetworkData dt = new NetworkData();
		String strUrl = C_URL.GET_CONTROL;
		String result = "";

		if (!Utils.checkNetworkAvailable(context)) {
			dt.result = context.getString(R.string.msg_no_network);
			dt.code = 101;
			return dt;
		}

		JSONObject jsonSend = new JSONObject();
		try {
			jsonSend.put(C_PARA.ID, C_PPT.id);
			jsonSend.put(C_PARA.DEVICE, "mobile");

			MobiLog.logI("json.sent getPlaylist" + jsonSend.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return postData(strUrl, jsonSend.toString());
		

	}
	
	 //---------------------class postControl-------------------------------
	public static NetworkData postControl(Context context,ControlOJ controlOJ) {

		NetworkData dt = new NetworkData();
		String strUrl = C_URL.POST_CONTROL;
		String result = "";

		if (!Utils.checkNetworkAvailable(context)) {
			dt.result = context.getString(R.string.msg_no_network);
			dt.code = 101;
			return dt;
		}

		JSONObject jsonSend = new JSONObject();
		try {
			jsonSend.put(C_PARA.ID, C_PPT.id);
			jsonSend.put(ControlOJ.CODE_CONTROL, controlOJ.code_control);
			jsonSend.put(ControlOJ.DEVICE, "mobile");
			jsonSend.put(ControlOJ.TYPE, controlOJ.type);
			jsonSend.put(ControlOJ.VALUE,controlOJ.value);

			MobiLog.logI("json.sent postControl" + jsonSend.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return postData(strUrl, jsonSend.toString());

	}
	
	
	//---------------------class postData-------------------------------
	public static NetworkData postData(String strUrl, String jsonSend) {
		MobiLog.NAQI("url send: " + strUrl);
		MobiLog.NAQI("jsonSend send: " + jsonSend.toString());
		JSONObject jArray = null;

		// HttpClient client = new DefaultHttpClient();
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpClient client = new DefaultHttpClient(params);

		HttpConnectionParams.setConnectionTimeout(client.getParams(), C_NET.CONNECTION_TIMEOUT);
		HttpResponse response;
		String result = "";
		try {
			HttpPost post = new HttpPost(strUrl);
			StringEntity se = new StringEntity(jsonSend.toString(), "UTF8");
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, C_NET.CONTENT_TYPE));
			post.setEntity(se);

			response = client.execute(post);
			/* Checking response */
			if (response != null) {
				InputStream in = response.getEntity().getContent();

				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					in.close();
					result = sb.toString();
					//result=result.replace("\n", "").replace("\r", "");
					
					MobiLog.logI("json result: " + result);

				} catch (Exception e) {
					MobiLog.logD("Error converting result " + e.toString());
				}

				try {
					jArray = new JSONObject(result);
					
				} catch (JSONException e) {
					MobiLog.logE("Error parsing99(46473) data " + e.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jArray = null;
		}

		// now we have a jsonArray of networkData
		// start parse it to networkdata object

		NetworkData networkData = new NetworkData();
		try {
			if (jArray != null) {

				MobiLog.NAQI("jArray.getString(C_PARA.CODE):" + jArray.getString(C_PARA.CODE));

				networkData.code = Integer.parseInt(jArray.getString(C_PARA.CODE));
				networkData.result = jArray.getString(C_PARA.RESULT);

				if (networkData.code == C_NET.RESULT_SUCCESS) {

					networkData.nativeData = jArray.getString(C_PARA.DATA);
					
				}

			}
		} catch (JSONException e) {
			MobiLog.NAQE("error(6602):" + e);
			e.printStackTrace();
		}
		return networkData;
	}

}
