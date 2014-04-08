package com.vn.naq.activity;

import java.util.Vector;

import com.google.android.gcm.GCMRegistrar;
import com.vn.naq.R;
import com.vn.naq.Utils.Constants;
import com.vn.naq.Utils.Utils;
import com.vn.naq.model.TaskType;
import com.vn.naq.object.RegisterOJ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Register extends RegisterLayout {
	String uservalue;
	String passvalue ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final String regidvalue=GCMRegistrar.getRegistrationId(this);
		
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				 uservalue = edUserName.getText().toString();
				 passvalue = edPassWord.getText().toString();
				passvalue=Utils.toMD5(passvalue);
				String emailvalue = edEmail.getText().toString();
				String namevalue = edName.getText().toString();

				if (uservalue.length() < 1 || passvalue.length() < 1 || regidvalue.length() < 1 || emailvalue.length() < 1 || namevalue.length() < 1) {
					Utils.t(getBaseContext(), getString(R.string.msg_input_error));
					return;
				}
				if (regidvalue.equals(""))
					showToast("can't get RegID");
				else {

					Constants.C_PARA.registerOJSent = new RegisterOJ(uservalue, passvalue, regidvalue, emailvalue, namevalue);
					showProgressDialog(context, getString(R.string.loading));
					getModel().executeTaskASync(TaskType.REGISTER, null, null);
				}

			}
		});
	}

	@Override
	public void onSuccess(TaskType taskType, Vector<?> list, int code, String msg) {
		if(taskType==TaskType.REGISTER){
			closeProgressDialog();
			showToast(msg);
			Intent intent=new Intent(getBaseContext(), Login.class);
			String info[]={uservalue,edPassWord.getText().toString()};
			intent.putExtra("infoRegister",info );
			startActivity(intent);
			finish();
		}
		super.onSuccess(taskType, list, code, msg);
	}
	
	@Override
	public void onFail(TaskType taskType, int code, String msg) {
		closeProgressDialog();
		showToast(msg);
		super.onFail(taskType, code, msg);
	}

}
