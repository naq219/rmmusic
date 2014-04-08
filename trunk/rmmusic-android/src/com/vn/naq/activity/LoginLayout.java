package com.vn.naq.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vn.naq.R;

public class LoginLayout extends BaseActivity{
	public Context context;
	public EditText edEmail;
	public EditText edPassword;
	public Button btnLogin;
	public ProgressDialog prodialog;
	public TextView register;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		
		context=LoginLayout.this;
		edEmail=(EditText) findViewById(R.id.email);
		edPassword=(EditText) findViewById(R.id.password);
		btnLogin=(Button) findViewById(R.id.sign_in_button);
		 prodialog = new ProgressDialog(LoginLayout.this);
		prodialog.setMessage(getString(R.string.loading));
		register=(TextView) findViewById(R.id.login_register_txt);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(context, Register.class));
				
			}
		});
		
	}

}
