package com.vn.naq.Utils;

import java.util.Vector;

import com.vn.naq.object.ControlOJ;
import com.vn.naq.object.RegisterOJ;



public interface Constants {
	public class C_URL{
		
		public static String BASE_URL="http://naq.name.vn/";
		public static String REGISTER=BASE_URL+"rest/index.php/register/user/format/json";
		public static String LOGIN=BASE_URL+"rest/index.php/login/user/format/json";
		
		public static String GETPLAYLIST_URL=BASE_URL+"rest/index.php/playlist/getplaylist/format/json";
		public static String GET_CONTROL=BASE_URL+"rest/index.php/playlist/getcontrol/format/json";
		public static String POST_CONTROL=BASE_URL+"rest/index.php/playlist/savecontrol/format/json";
		
	}
	
	public class C_NET{
		public static int CONNECTION_TIMEOUT=15000;
		public static String CONTENT_TYPE="application/json";
		public static int RESULT_SUCCESS=0;
		public static int RESULT_FALSE=-1;
	}
	
	public class C_PARA{
		public static String USERNAME="username";
		public static String PASSWORD="password";
		public static String CODE="code";
		public static String EMAIL="email";
		public static String NAME="name";
		public static String GCM_REGID="gcm_regid";
		public static String RESULT="result";
		public static String DATA="data";
		public static String ID="id";
		public static String DEVICE="device";
		
		public static String regIDSend="";
		public static String usersend="username";
		public static String passwordsend="password";
		public static RegisterOJ registerOJSent=null;
		public static String strPlaylistResult="";
		public static ControlOJ controlData=null;
		public static ControlOJ controlOJPost=null;
		public static int  curentControlType=0;
		//public static int  curentCodeControl=5;
	}
	
	public class C_GCM{
		public static String USERNAME="username";
		public static String PASSWORD="password";
		public static String CODE="code";
		public static String EMAIL="email";
		public static String NAME="name";
		public static String GCM_REGID="gcm_regid";
		public static String RESULT="result";
		public static String DATA="data";
	}
	
	public class C_TYPE{
		
		public static int PLAYLIST = 0;
         public static int PLAY = 1;
         public static int PAUSE = 2;
         public static int NEXT = 3;
         public static int PREVIEW = 4;
         public static int PLAY_SELECTED = 5;
         public static int STOP = 6;
         public static final int OPEN = 7;
         public static final int CANCEL_SHUTDOWN = 8;
	}
	
	public class C_PPT{
		public static String id="";
		public static String MSITE=C_URL.BASE_URL+"erpptsv/";
	}
	
	
	 
	

}
