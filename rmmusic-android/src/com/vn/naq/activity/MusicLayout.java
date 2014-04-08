package com.vn.naq.activity;

import static com.vn.naq.CommonUtilities.DISPLAY_MESSAGE_ACTION;

import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.vn.naq.R;
import com.vn.naq.WakeLocker;
import com.vn.naq.Utils.Constants.C_PPT;
import com.vn.naq.Utils.Constants.C_TYPE;
import com.vn.naq.Utils.MobiLog;
import com.vn.naq.Utils.Constants.C_PARA;
import com.vn.naq.model.TaskType;
import com.vn.naq.object.ControlOJ;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MusicLayout extends InitMusicLayout implements OnClickListener {
	 boolean isEXE;
	MusicAdapter adapter;
	int type_control;
	boolean isPlay = false;
	ScheduledExecutorService checkPL;
	int curentCodeControl=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		adapter = new MusicAdapter(getBaseContext(), R.layout.music_item_lv, new Vector<String>());
		lv.setAdapter(adapter);

		play.setOnClickListener(this);
		next.setOnClickListener(this);
		prev.setOnClickListener(this);
		stop.setOnClickListener(this);
		open.setOnClickListener(this);
		checkPL=Executors.newScheduledThreadPool(3);
		checkPL.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						MobiLog.logI("check playlist after 15 s!");
						getModel().executeTaskASync(TaskType.GET_CONTROL, null, null);
					}
				});
				
			}
		}, 10, 15, TimeUnit.SECONDS);
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

				ControlOJ controlOJ = new ControlOJ();
				 curentCodeControl=curentCodeControl+1;
				controlOJ.code_control =curentCodeControl;
				controlOJ.type = C_TYPE.PLAY_SELECTED;
				controlOJ.value = position + "";
				C_PARA.controlOJPost = controlOJ;
				showProgressDialog(MusicLayout.this, getString(R.string.loading));
				getModel().executeTaskASync(TaskType.POST_CONTROL, null, null);

			}
		});
		super.onResume();
	}

	@Override
	public void onSuccess(TaskType taskType, Vector<?> list, int code, String msg) {
		closeProgressDialog();

		switch (taskType) {
		case GET_CONTROL:
			
			if(C_PARA.controlData.value!=null){
				
			Vector<String> listPlay = new Vector<String>();
			try {
			
				JSONObject jsonObject = new JSONObject(C_PARA.controlData.value);
				JSONArray arayPlaylist = jsonObject.getJSONArray("list");

				for (int i = 0; i < arayPlaylist.length(); i++) {
					try {
						listPlay.add(arayPlaylist.getJSONObject(i).getString("name"));
					} catch (Exception e) {
						MobiLog.NAQE("error string to list playlist :" + e);
					}
				}

			} catch (JSONException e) {
				MobiLog.NAQE("error string to list playlist :" + e);
			}
			
			if(listPlay.size()>0)
			adapter.SetItems(listPlay);
				
			}
			break;

		case POST_CONTROL:
			showToast(type_control + "");
			switch (type_control) {
			case 1: // hardfix play
				if (isPlay) {
					isPlay = false;
					play.setImageResource(R.drawable.pause);
				} else {
					isPlay = true;
					play.setImageResource(R.drawable.play);
				}

				break;

			default:	
				break;
			}

		default:
			break;
		}
		super.onSuccess(taskType, list, code, msg);
	}

	@Override
	public void onFail(TaskType taskType, int code, String msg) {
		closeProgressDialog();
		showToast(msg);
		super.onFail(taskType, code, msg);
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString("message");
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			String a="/internal/RevierPC";
			
			String id = "1";
			String type = "1";

			try {
				id = newMessage.substring(newMessage.indexOf("--") + 2);
				type = newMessage.substring(0, newMessage.indexOf("--"));
			} catch (Exception e) {
				// TODO: handle exception
			}

			C_PARA.curentControlType = Integer.parseInt(type);
			Toast.makeText(getApplicationContext(), "New Message: " + "value1" + id + "type" + type, Toast.LENGTH_LONG).show();
			MobiLog.logI("NEW MESSAGE  music=" + newMessage);

			showProgressDialog(MusicLayout.this, "loading...");

			if (C_PPT.id.equalsIgnoreCase(id))
				getModel().executeTaskASync(TaskType.GET_CONTROL, null, null);

			WakeLocker.release();
		}
	};

	@Override
	public void onClick(View v) {
		isEXE=false;
		final ControlOJ controlOJ = new ControlOJ();
		curentCodeControl=curentCodeControl+1;
		controlOJ.code_control = curentCodeControl;
		switch (v.getId()) {
		case R.id.next:
			
			controlOJ.type = C_TYPE.NEXT;
			type_control = C_TYPE.NEXT;
			controlOJ.value = "no data";

			break;
		case R.id.prev:
			
			controlOJ.type = C_TYPE.PREVIEW;
			type_control = C_TYPE.PREVIEW;
			controlOJ.value = "no data";

			break;
		case R.id.stop:
			
			controlOJ.type = C_TYPE.STOP;
			type_control = C_TYPE.STOP;
			controlOJ.value = "no data";
			//startActivity(new Intent(getBaseContext(), PostandroidActivity.class));

			break;
		case R.id.open:
			isEXE=true;
			controlOJ.type = C_TYPE.OPEN;
			controlOJ.value = "vb";
			type_control = C_TYPE.OPEN;
			
			final Dialog dlShutdown=new Dialog(MusicLayout.this);
			
			dlShutdown.setContentView(R.layout.dlshutdown_layout);
			
			dlShutdown.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
			Button shutdown=(Button) dlShutdown.findViewById(R.id.btn_shutdown);
			Button cancel_shutdown=(Button) dlShutdown.findViewById(R.id.btn_cancel_shutdown);
			final EditText edShutdown=(EditText) dlShutdown.findViewById(R.id.ed_shutdown);
			shutdown.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					controlOJ.value = edShutdown.getText()+"";
					dlShutdown.dismiss();
					C_PARA.controlOJPost = controlOJ;
					showProgressDialog(MusicLayout.this, getString(R.string.loading));
					getModel().executeTaskASync(TaskType.POST_CONTROL, null, null);
				}
			});
			
			cancel_shutdown.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					controlOJ.type = C_TYPE.CANCEL_SHUTDOWN;
					controlOJ.value = "vb";
					type_control = C_TYPE.CANCEL_SHUTDOWN;
					dlShutdown.dismiss();
					C_PARA.controlOJPost = controlOJ;
					showProgressDialog(MusicLayout.this, getString(R.string.loading));
					getModel().executeTaskASync(TaskType.POST_CONTROL, null, null);
					
				}
			});
			dlShutdown.show();
			
			break;
		case R.id.play_music:
			
			controlOJ.type = C_TYPE.PLAY;
			if (isPlay)
				controlOJ.value = "1";
			else
				controlOJ.value = "0";
			type_control = C_TYPE.PLAY;
			break;
			
			
		default:
			
			break;
		}

	if(!isEXE){
		C_PARA.controlOJPost = controlOJ;
		showProgressDialog(MusicLayout.this, getString(R.string.loading));
		getModel().executeTaskASync(TaskType.POST_CONTROL, null, null);
	}

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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		startActivity(new Intent(getBaseContext(), PostandroidActivity.class));
		return super.onOptionsItemSelected(item);
	}


}
