package com.vn.naq.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import com.vn.naq.R;
import com.vn.naq.Utils.Constants;
import com.vn.naq.Utils.MobiLog;
import com.vn.naq.model.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PostandroidActivity extends Activity implements OnClickListener,Constants {
	Button connect, left, home, end, right;
	ImageView play;
	HttpPost httppost;
	HttpClient httpclient;int a=0;
	int num = 1,playEnd=2,connectOr=2;
   String idtemp=C_PPT.id;
   ProgressDialog loadingProgress;
  
  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		
		
		
		httpclient = new DefaultHttpClient();
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		 httpclient = new DefaultHttpClient(params);
		
		MobiLog.logI("url="+C_PPT.MSITE+"postandroid.php");
		httppost = new HttpPost(C_PPT.MSITE+"postandroid.php");
		connect = (Button) findViewById(R.id.connect);
		left = (Button) findViewById(R.id.left);
		right = (Button) findViewById(R.id.right);
		home = (Button) findViewById(R.id.home);
		end = (Button) findViewById(R.id.end);
		play=(ImageView)findViewById(R.id.play);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        play.setOnClickListener(this);
        home.setOnClickListener(this);
        end.setOnClickListener(this);
        connect.setOnClickListener(this);
        
        home.setEnabled(false);
		end.setEnabled(false);
		left.setEnabled(false);
		right.setEnabled(false);
		play.setEnabled(false);
        Toast.makeText(getBaseContext(), R.string.help_ppt, 1).show();
      
		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				connectOr++;
				num++;
				if (connectOr % 2 == 1) {
					playEnd++;
					connect.setText(R.string.ngatketnoi);
					C_PPT.id = idtemp;
					post("0000000" +C_PPT.id);
					home.setEnabled(true);
					end.setEnabled(true);
					left.setEnabled(true);
					right.setEnabled(true);
					play.setEnabled(true);
					play.setImageDrawable(getResources().getDrawable(R.drawable.stop));

				} else {

					connect.setText(R.string.ketnoilai);
					post("ttttttt" + C_PPT.id);
					C_PPT.id = "naq00219@gmail.com";
					// play.setImageDrawable(getResources().getDrawable(R.drawable.play));
					home.setEnabled(false);
					end.setEnabled(false);
					left.setEnabled(false);
					right.setEnabled(false);
					play.setEnabled(false);

				}

			}
		});
	

	}

	@Override
	public void onClick(View v) {
		if(C_PPT.id!="naq00219@gmail.com")
		switch (v.getId()) {
		case R.id.left:
			num++;
			post(reNum(num) + "01" + C_PPT.id);
			break;
		case R.id.right:
			num++;
			post(reNum(num) + "02" + C_PPT.id); break;
			
		case R.id.home:
			num++;
			
			post(reNum(num) + "03" +C_PPT.id);break;
			
			
			
		case R.id.end:
			num++;
			
			post(reNum(num) + "04" + C_PPT.id);break;
			
		case R.id.play:
			
			playEnd++;
			num++;
			if (playEnd%2==1) {
				post(reNum(num) + "55" + C_PPT.id);
				
				play.setImageDrawable(getResources().getDrawable(R.drawable.stop));
				home.setEnabled(true);
				end.setEnabled(true);
				left.setEnabled(true);
				right.setEnabled(true);
				
			} else {
				post(reNum(num) + "56" + C_PPT.id);
				home.setEnabled(false);
				end.setEnabled(false);
				left.setEnabled(false);
				right.setEnabled(false);
				play.setImageDrawable(getResources().getDrawable(R.drawable.play));
			}break;

			
		default:
			break;
		}

	}
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	
	private void post(final String control) {
		
		AsyncTask<String, String, String> ad=new AsyncTask<String, String, String>() {

			@Override
			protected void onPostExecute(String result) {
				closeProgressDialog();
				super.onPostExecute(result);
			}
			
			@Override
			protected void onPreExecute() {
				showProgressDialog("loading...");
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(String... params) {

				try {
					nameValuePairs.remove(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				MobiLog.NAQI("control="+control);
				nameValuePairs.add(new BasicNameValuePair("control", control));

				try {

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					HttpResponse response = httpclient.execute(httppost);
					// HttpEntity entity = response.getEntity();
					// InputStream is = entity.getContent();
				} catch (Exception e) {
					MobiLog.NAQE("loi ket noi  " + e.toString());
				}
				return null;
			}
		};
		
		ad.execute("sds");
		
		
		
		
		
	}
//0001010dsgdfgdf
	private String reNum(int num) {
		
		String _num=String.valueOf(num);
		String chu0="00000000";
		return chu0.substring(0, 5-_num.length())+num;

	}
	private void mtoast(String a){
		Toast.makeText(getBaseContext(), a, 1).show();
	}
	
	
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
				if (keyCode==KeyEvent.KEYCODE_VOLUME_DOWN) {
					num++;
					post(reNum(num) + "02" + C_PPT.id); 
					 
					
				}
				if (keyCode==KeyEvent.KEYCODE_VOLUME_UP) {
					num++;
					post(reNum(num) + "01" + C_PPT.id);
					 
				}
				
				
			 super.onKeyDown(keyCode, event);
			 return true;
		}
	 
		public void showProgressDialog( String message) {
			
			
			  loadingProgress = new ProgressDialog(PostandroidActivity.this);
			loadingProgress.setMessage(message);
			loadingProgress.setCanceledOnTouchOutside(false);
			loadingProgress.show();
		}

		public void closeProgressDialog() {
			if (loadingProgress != null) {
				if (loadingProgress.isShowing())
					loadingProgress.dismiss();
				loadingProgress = null;
			}
		}

}