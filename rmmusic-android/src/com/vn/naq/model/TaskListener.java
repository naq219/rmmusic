package com.vn.naq.model;

import java.util.Vector;

import android.content.Context;

public interface TaskListener {
	Context getContext();
	
	void onSuccess(TaskType taskType, Vector<?> list, int code, String result);
	void onFail(TaskType taskType, int code, String result);
	void onProgress(TaskType taskType, int progress);
}
