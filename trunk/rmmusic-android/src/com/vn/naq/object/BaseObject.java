/**
 * 
 */
package com.vn.naq.object;

import android.content.ContentValues;

/**
 * @author NAQ
 * 
 */
public class BaseObject {

	/**
	 * params for this item
	 */
	protected ContentValues params = null;
	private boolean isChecked;

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * Set the params for this item
	 * 
	 * @param params
	 */
	public void setParams(ContentValues params) {
		this.params = params;
	}

	/**
	 * Get the params for this item
	 * 
	 * @param params
	 */
	public ContentValues getParams() {
		return params;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getParam(String key) {
		if (params != null)
			return params.getAsString(key);
		else
			return null;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void setParam(String key, String value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, value);
	}

	public void setParamInt(String key, int value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, value);
	}

	public void setParamLong(String key, long value) {
		if (params == null)
			params = new ContentValues();
		params.put(key, value);
	}

	public int getParamInt(String key) {
		// nth
		if (params != null)
			if (params.containsKey(key) && params.getAsInteger(key) != null)
				return params.getAsInteger(key);
		return 0;
	}

	public long getParamLong(String key) {
		// nth
		if (params != null)
			if (params.containsKey(key) && params.getAsLong(key) != null)
				return params.getAsLong(key);
		return 0;
	}

}
