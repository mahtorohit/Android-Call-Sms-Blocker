package rohit.tracker.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TrackerActivity extends Activity {
	

	SQLiteDatabase db;
	TelephonyManager m_telephonyManager;
	EditText t,t1,t2,t3,t4;
	ToggleButton  tog;
	String onoff="",stat="";
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	try{
		
	 setContentView(R.layout.track);
	 SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
     db = helper.getWritableDatabase();
     m_telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
     t=(EditText)findViewById(R.id.editText1);
     t1=(EditText)findViewById(R.id.editText2);
     t2=(EditText)findViewById(R.id.editText3);
     t3=(EditText)findViewById(R.id.editText4);
     t4=(EditText)findViewById(R.id.editText5);
     tog=(ToggleButton)findViewById(R.id.toggleButton1);
     Cursor c = db.query("track_info", null ,null, null, null, null, null);
     
     while( c.moveToNext() ) 
     {
     	
    	 onoff=c.getString(6);
    	
     	
     }
      
      c.close();
      
      if(onoff.equals("true"))
      {
     	 tog.setChecked(true);
     	 check();
     	 
      }
      else if(onoff.equals("false")) {
     	 
     	 tog.setChecked(false);
 	}
	}
	catch (Exception e) {
		// TODO: handle exception
		Toast.makeText(this,"oncreat "+e.getMessage(), Toast.LENGTH_SHORT).show();
	}
    
}


private void check() {
	// TODO Auto-generated method stub
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
	// TODO Auto-generated method stub
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
    
    Toast.makeText(this, "fired", Toast.LENGTH_SHORT).show();
}
catch (Exception e) {
	// TODO: handle exception
	Toast.makeText(this,"oncreat "+e.getMessage(), Toast.LENGTH_SHORT).show();
}

	
}
 

public void trackonoff(View v)
{
	try
	{
		ContentValues val = new ContentValues();
	

	if(tog.isChecked()==true)
	{
		val.put("stat","true");
		Toast.makeText(this, "Protection On !", Toast.LENGTH_SHORT).show();
	}
	else if(tog.isChecked()==false)
	{
		val.put("stat","false");
		db.delete("track_info", null, null);
		Toast.makeText(this, "Protection Off !", Toast.LENGTH_SHORT).show();
	}
		
	
	
	db.update("track_info", val, null, null);
	
}
catch (Exception e) {
	// TODO: handle exception
	Toast.makeText(this,"onoff "+e.getMessage(), Toast.LENGTH_SHORT).show();
}

	
}
public void add(View v)
{
	try
	{
	ContentValues val = new ContentValues();
	val.put("sim", m_telephonyManager.getSimSerialNumber());
	
	val.put("hlp1",t.getEditableText().toString());
	val.put("hlp2",t1.getEditableText().toString());
	val.put("hlp3",t2.getEditableText().toString());
	val.put("hlp4",t3.getEditableText().toString());
	val.put("hlp5",t4.getEditableText().toString());
	
	
	db.insert("track_info", null, val);
	Toast.makeText(this, "Data Stored !", Toast.LENGTH_SHORT).show();
	}
	catch (Exception e) {
		// TODO: handle exception
		Toast.makeText(this,"add "+e.getMessage(), Toast.LENGTH_SHORT).show();
	}
    
}

}
