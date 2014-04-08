package com.vn.naq.activity;

import java.util.Vector;



import com.vn.naq.model.Model;
import com.vn.naq.model.ModelListener;
import com.vn.naq.model.TaskType;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity implements ModelListener {
	public static Model model = null;
	ProgressDialog loadingProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if (model == null) {
			model = new Model();
			model.setModelListener1(this);
		}
	}
	

	
	@Override
	protected void onResume() {
		model.setModelListener1(this);
		super.onResume();
	}
	
	@Override
	public Context getContext() {
		return this.getApplicationContext();
		
	}

	@Override
	public void onSuccess(TaskType taskType, Vector<?> list, int code, String msg) {
		
		
	}
	
	protected Model getModel() {
		return BaseActivity.model;
	}

	@Override
	public void onFail(TaskType taskType, int code, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgress(TaskType taskType, int progress) {
		// TODO Auto-generated method stub
		
	}
	
	public void showToast(String message) {
		if (message != null)
			Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getContext(), "Null msg", Toast.LENGTH_SHORT).show();
	}

	
	public void showProgressDialog(Context context, String message) {
		closeProgressDialog();
		// loadingProgress = DialogUtils.progressDialogWaitingCustom(this,
		// message);
		
		 loadingProgress = new ProgressDialog(context);
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
