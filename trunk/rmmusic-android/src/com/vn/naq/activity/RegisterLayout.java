package com.vn.naq.activity;

import com.vn.naq.R;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterLayout extends BaseActivity {
	public EditText edUserName,edPassWord,edEmail,edName;
	public Button btnRegister;
	public Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		context=RegisterLayout.this;
		edUserName=(EditText) findViewById(R.id.username);
		edPassWord=(EditText) findViewById(R.id.password);
		edEmail=(EditText) findViewById(R.id.email);
		edName=(EditText) findViewById(R.id.name);
		btnRegister=(Button) findViewById(R.id.btn_register);
		
	}

}
