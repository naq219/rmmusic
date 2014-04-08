package com.vn.naq.activity;

import static com.vn.naq.CommonUtilities.DISPLAY_MESSAGE_ACTION;

import java.util.Vector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gcm.GCMRegistrar;
import com.vn.naq.R;
import com.vn.naq.WakeLocker;
import com.vn.naq.Utils.Constants;
import com.vn.naq.Utils.MobiLog;
import com.vn.naq.Utils.Utils;
import com.vn.naq.model.TaskType;

public class Login extends LoginLayout implements Constants{
	
	String uservalue;
	String passvalue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constants.C_PARA.regIDSend=GCMRegistrar.getRegistrationId(this);
		MobiLog.logI("register sucess, save regID: "+GCMRegistrar.getRegistrationId(this));
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 uservalue=edEmail.getText().toString();
				 passvalue=edPassword.getText().toString();
				
				if (uservalue.length() < 1 || passvalue.length() < 1)
				{
					Utils.t(getBaseContext(), getString(R.string.msg_input_error));
					return;
				}
				
				Constants.C_PARA.usersend=uservalue;
				Constants.C_PARA.passwordsend=Utils.toMD5(passvalue);
				showProgressDialog(context, getString(R.string.loading));
				
				
				getModel().executeTaskASync(TaskType.CHECK_LOGIN, null, null);
				
				
				
			}

		});
		
	}
	
	@Override
	protected void onResume() {
		Intent intent=getIntent();
		String value[]=intent.getStringArrayExtra("infoRegister");
		if(value!=null)
		{
			uservalue=value[0];
			passvalue=value[1];
			showToast(passvalue);
			edEmail.setText(uservalue);
			edPassword.setText(passvalue);
		}
		super.onResume();
	}
	
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString("message");
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
			//lblMessage.append(newMessage + "\n");			
			//Toast.makeText(getApplicationContext(), "login New Message: " + newMessage, Toast.LENGTH_LONG).show();
			MobiLog.logI("NEW MESSAGE  login="+newMessage);
			
			
			// Releasing wake lock
			WakeLocker.release();
		}
	};


	@Override
	public void onSuccess(TaskType taskType, Vector<?> list, int code, String msg) {
		
		if(taskType==TaskType.CHECK_LOGIN){
			
			showToast(msg);
			closeProgressDialog();
			C_PPT.id=Constants.C_PARA.usersend+Constants.C_PARA.passwordsend;
			startActivity(new Intent(getBaseContext(), MusicLayout.class));
			finish();
			
		}
		super.onSuccess(taskType, list, code, msg);
	}
	
	@Override
	public void onFail(TaskType taskType, int code, String msg) {
		
		if(taskType==TaskType.CHECK_LOGIN){
			closeProgressDialog();
			showToast(msg);
		}
		
		super.onFail(taskType, code, msg);
	}
	
	@Override
	protected void onDestroy() {
		
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
	
		super.onPause();
	}


}
