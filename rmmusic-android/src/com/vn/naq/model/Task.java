package com.vn.naq.model;

import java.util.Vector;

import com.vn.naq.Utils.Constants;
import com.vn.naq.Utils.Constants.C_PARA;
import com.vn.naq.Utils.JsonSupport;
import com.vn.naq.Utils.NetworkSupport;
import com.vn.naq.object.ControlOJ;
import com.vn.naq.object.NetworkData;
import com.vn.naq.object.RegisterOJ;


import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

/**
 * 
 * @author NTH
 * 
 */
@SuppressLint("NewApi")
public class Task extends AsyncTask<TaskParams, Void, Integer> {

	private static final int GET_TASK_FAILED = -1;
	private static final int GET_TASK_DONE = 0;
	private TaskListener taskListener;
	private TaskType taskType;
	private int code = -1;
	private String msg = null;
	public static Context context;
	private Vector<?> list = null;

	public Task(TaskListener taskListener, TaskType taskType) {

		this.taskListener = taskListener;
		this.taskType = taskType;
	}

	public Task(TaskListener taskListener, TaskType taskType, Vector<?> list) {

		this.taskListener = taskListener;
		this.taskType = taskType;
		this.list = list;
	}

	public Task(TaskListener taskListener, TaskType taskType, Vector<?> list,
			Context context) {

		this.taskListener = taskListener;
		this.taskType = taskType;
		this.list = list;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		switch (taskType) {
		default:
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Integer doInBackground(TaskParams... params) {
		if (taskListener == null)
			return GET_TASK_FAILED;
		
		switch (taskType) {
		case CHECK_LOGIN:
			NetworkData data = NetworkSupport.checkUserPass(context, Constants.C_PARA.usersend, Constants.C_PARA.passwordsend);
			code=data.code;
			msg=data.result;
			if(data.code==Constants.C_NET.RESULT_SUCCESS){
				return GET_TASK_DONE;
			}
			return GET_TASK_FAILED;
			
		case REGISTER:
			RegisterOJ oj=Constants.C_PARA.registerOJSent;
			NetworkData data1 = NetworkSupport.register(context, oj.username, oj.password, oj.regid, oj.email, oj.name);
			code=data1.code;
			msg=data1.result;
			if(data1.code==Constants.C_NET.RESULT_SUCCESS){
				return GET_TASK_DONE;
			}
			return GET_TASK_FAILED;
			
		
			
		case GET_CONTROL:
			NetworkData dataControl = NetworkSupport.getControl(context);
			code = dataControl.code;
			msg = dataControl.result;
			if (dataControl.code == Constants.C_NET.RESULT_SUCCESS) {
				
				C_PARA.controlData=JsonSupport.toControlOJ(dataControl.nativeData);
				C_PARA.controlData=JsonSupport.toControlOJ(dataControl.nativeData);
				return GET_TASK_DONE;
			}
			return GET_TASK_FAILED;
			
		case POST_CONTROL:
			NetworkData dataControlPost = NetworkSupport.postControl(context, C_PARA.controlOJPost);
			code = dataControlPost.code;
			msg = dataControlPost.result;
			if (dataControlPost.code == Constants.C_NET.RESULT_SUCCESS) {
				
				//C_PARA.controlData=JsonSupport.toControlOJ(dataControlPost.nativeData);
				 
				return GET_TASK_DONE;
			}
			return GET_TASK_FAILED;
			
			
		default:
			return GET_TASK_FAILED;
			
	

		}

		
	
		

	}

	@Override
	protected void onPostExecute(Integer result) {
		// Log.i("nth", this.getClass().getSimpleName()+
		// " - onPostExecute - result = " + result);

		if (GET_TASK_FAILED == result) {
			if (taskListener != null)
				taskListener.onFail(taskType, code, msg);

		} else {
			if (taskListener != null) {
				taskListener.onSuccess(taskType, list, code, msg);

			}
		}
	}

	
}
