package com.vn.naq.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.vn.naq.object.ControlOJ;

public class JsonSupport {
	
	public static ControlOJ toControlOJ(String data){
		
		ControlOJ controlOJ=new ControlOJ();
		controlOJ.code_control=0;
		controlOJ.type=42234234;
		controlOJ.value="no data";
		if(data==null) return controlOJ;
		if(data.indexOf(":null")!=-1) return controlOJ;
		MobiLog.NAQE("loi nhieu:"+data);
		try {
			
			JSONObject object=new JSONObject(data);
			String vv=object.getString(ControlOJ.CODE_CONTROL);
			if(vv!=null){
				controlOJ.code_control=Integer.parseInt(object.getString(ControlOJ.CODE_CONTROL));
				controlOJ.type=Integer.parseInt(object.getString(ControlOJ.TYPE));
				controlOJ.value=object.getString(ControlOJ.VALUE);
			}
			
			
			
		
		} catch (JSONException e) {
			MobiLog.NAQE("error(7347) "+e); 
			controlOJ.code_control=0;
			controlOJ.type=42234234;
			controlOJ.value="no data";
			
			e.printStackTrace();
		}
		return controlOJ;
		
		
	}
	
}
