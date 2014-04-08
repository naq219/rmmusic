package com.vn.naq.model;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vn.naq.Utils.Utils;

import android.content.Context;
import android.os.AsyncTask;


public class Model implements TaskListener {

	private static int MODEL_NTHREADS = 5;
	// private static int UI_NTHREADS = 5;

	private static ExecutorService modelExecutor = Executors
			.newFixedThreadPool(MODEL_NTHREADS);
	// private static ExecutorService uiExecutor =
	// Executors.newFixedThreadPool(UI_NTHREADS);

	private ModelListener modelListener = null;

	/**
	 * @param modelListener
	 *            the modelListener to set
	 */
	public void setModelListener1(ModelListener modelListener) {
		this.setModelListener(modelListener);
	}

	@Override
	public Context getContext() {
		if (modelListener != null)
			return modelListener.getContext();
		return null;
	}

	

	public void executeTaskASync(TaskType taskType, Vector<?> list, TaskParams params) {
		AsyncTask task = null;
		switch (taskType) {
		case CHECK_LOGIN:
			task = new Task(this, taskType, null, getContext());
		case REGISTER:
			task = new Task(this, taskType, null, getContext());
		
		case GET_CONTROL:
			task = new Task(this, taskType, null, getContext());
		case POST_CONTROL:
			task = new Task(this, taskType, null, getContext());
		default:
			break;
		}
		if (task != null) {
			Utils.executeAsyncTask(modelExecutor, task, new TaskParams[] { params });
		}

	}

	@Override
	public void onSuccess(TaskType taskType, Vector<?> list, int code,
			String msg) {
		if (modelListener != null)
			modelListener.onSuccess(taskType, list, code, msg);
	}

	@Override
	public void onFail(TaskType taskType, int code, String msg) {
		if (modelListener != null)
			modelListener.onFail(taskType, code, msg);
	}

	@Override
	public void onProgress(TaskType taskType, int progress) {
		if (modelListener != null)
			modelListener.onProgress(taskType, progress);
	}

	/**
	 * @return the modelListener
	 */
	public ModelListener getModelListener() {
		return modelListener;
	}

	/**
	 * @param modelListener
	 *            the modelListener to set
	 */
	public void setModelListener(ModelListener modelListener) {
		this.modelListener = modelListener;
	}

	/*
	 * 
	 */

}
