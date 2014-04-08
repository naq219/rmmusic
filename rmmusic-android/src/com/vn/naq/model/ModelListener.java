package com.vn.naq.model;

import java.util.Vector;

import android.content.Context;


public interface ModelListener {
	Context getContext();
	void onSuccess(TaskType taskType, Vector<?> list, int code, String msg);
	void onFail(TaskType taskType, int code, String msg);
	void onProgress(TaskType taskType, int progress);
}
