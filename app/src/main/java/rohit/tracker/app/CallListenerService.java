package rohit.tracker.app;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.widget.Toast;


public class CallListenerService extends Service {

	SQLiteDatabase db;
	TelephonyManager m_telephonyManager;
	
	
	String onoff="",stat="";
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		run();
		super.onStart(intent, startId);
	}
	
	public void run()
	{
		try
		{
		 SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
	     db = helper.getWritableDatabase();
	     m_telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	     
	     Cursor c = db.query("track_info", null ,null, null, null, null, null);
	     
	     while( c.moveToNext() ) 
	     {
	     	
	    	 onoff=c.getString(6);
	    	
	     	
	     }
	      
	      c.close();
	      
	      if(onoff.equals("true"))
	      {
	     	 
	     	 check();
	     	 
	      }
	     
		}
		catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this,"oncreat "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	
	private void check() {
		try
		{
			Boolean bb=false;
	 Cursor c = db.query("track_info", null ,null, null, null, null, null);
	     String no="";
	     while( c.moveToNext() ) 
	     {
	     	
	    	 no=c.getString(0);
	    	break;
	     	
	     }
	     c.close();
	     if(!no.equals(""))
	     {
	      if(!no.equals(m_telephonyManager.getSimSerialNumber()))
	      {
	    	  inform();
	    	  bb=true;
	      }
	     }
	      Toast.makeText(this,"sim :"+bb, Toast.LENGTH_SHORT).show();
	}
	catch (Exception e) {
		// TODO: handle exception
		Toast.makeText(this,"oncreat "+e.getMessage(), Toast.LENGTH_SHORT).show();
	}
		
	}
	private void inform() {
		try
		{
		Cursor c = db.query("track_info", null ,null, null, null, null, null);
	    String no="";
	    String message="Oprator :"+m_telephonyManager.getSimOperatorName()+" Ph No : "+m_telephonyManager.getLine1Number()+" Location :"+m_telephonyManager.getCellLocation()+" sim no :"+m_telephonyManager.getSimSerialNumber();
	    while( c.moveToNext() ) 
	    {
	    	
	   	 no=c.getString(1);
	   	PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,TrackerActivity.class), 0);
		SmsManager sms = SmsManager.getDefault(); 
		//Toast.makeText(this,"no 0: "+no, Toast.LENGTH_SHORT).show();
		sms.sendTextMessage(no, null, message, pi, null);
		no=c.getString(2);
		//Toast.makeText(this,"no 1: "+no, Toast.LENGTH_SHORT).show();
		sms.sendTextMessage(no, null, message, pi, null);
		no=c.getString(3);
		//Toast.makeText(this,"no 2: "+no, Toast.LENGTH_SHORT).show();
		sms.sendTextMessage(no, null, message, pi, null);
		no=c.getString(4);
		//Toast.makeText(this,"no 3: "+no, Toast.LENGTH_SHORT).show();
		sms.sendTextMessage(no, null, message, pi, null);
		no=c.getString(5);
		//Toast.makeText(this,"no 4: "+no, Toast.LENGTH_SHORT).show();
		sms.sendTextMessage(no, null, message, pi, null);
	   	break;
	    	
	    }
	    c.close();
	    
	   // Toast.makeText(this, "fired", Toast.LENGTH_SHORT).show();
	}
	catch (Exception e) {
		// TODO: handle exception
		Toast.makeText(this,"oncreat "+e.getMessage(), Toast.LENGTH_SHORT).show();
	}

		
	}

}
