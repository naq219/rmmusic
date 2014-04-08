package com.vn.naq.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.vn.naq.R;

public class InitMusicLayout extends BaseActivity {
	ListView lv;
	Button next, prev, stop, open;
	ImageView play;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_layout);
		play = (ImageView) findViewById(R.id.play_music);
		next = (Button) findViewById(R.id.next);
		prev = (Button) findViewById(R.id.prev);
		stop = (Button) findViewById(R.id.stop);
		open = (Button) findViewById(R.id.open);
		lv = (ListView) findViewById(R.id.lv_music);

	}

}
