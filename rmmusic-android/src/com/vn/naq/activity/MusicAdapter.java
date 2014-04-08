package com.vn.naq.activity;

import java.util.Vector;

import com.vn.naq.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MusicAdapter extends ArrayAdapter<String> {

	private final LayoutInflater mInflater;
	private final int mLayoutRes;
	private final Vector<String> list;

	public MusicAdapter(Context context, int textViewResourceId, Vector<String> list) {
		super(context, textViewResourceId, list);
		this.mLayoutRes = textViewResourceId;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list = list;
	}

	static class GridViewHolder {
		
		TextView title;
		int position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		GridViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(mLayoutRes, parent, false);
			viewHolder = new GridViewHolder();
			;
			viewHolder.title = (TextView) convertView.findViewById(R.id.music_item_name);
		
		} else {
			viewHolder = (GridViewHolder) convertView.getTag();
		}

		final String item = getItem(position);
		updateListView(viewHolder, item, position);
		convertView.setTag(viewHolder);

		return convertView;
	}

	public void SetItems(Vector<String> items) {
		clear();
		AddAll(items);
	}

	private void AddAll(Vector<String> items) {
		if (items != null) {
			for (String item : items) {
				add(item);
			}
		}
		notifyDataSetChanged();
	}

	private void updateListView(GridViewHolder viewHolder, String item, int position) {
		
		// set value
		viewHolder.title.setText(item);
		

	}
}
