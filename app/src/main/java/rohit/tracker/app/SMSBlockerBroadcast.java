package rohit.tracker.app;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.telephony.SmsMessage;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SMSBlockerBroadcast extends BroadcastReceiver  {
String incomingNumber="";
SQLiteDatabase db;
String act_ser_sms="";
ToggleButton t,t1;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
		 Bundle bundle = intent.getExtras();   
		 
		    SmsMessage[] msgs = null;
		                
		    if (bundle != null)
		    {
		        //---retrieve the SMS message received---
		        Object[] pdus = (Object[]) bundle.get("pdus");
		        msgs = new SmsMessage[pdus.length];            
		        for (int i=0; i<msgs.length; i++)
		        {
		            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
		            incomingNumber = msgs[i].getOriginatingAddress();
		            
		        }
		    }
		    
		    SQLiteOpenHelper helper = new DBHelper(context, "TrackerActivity", null, 2);
		    db = helper.getWritableDatabase();
		     
		      Cursor c = db.query("actservice", null , "ser='sms'", null, null, null, null);
		      
		      while( c.moveToNext() ) 
		      {
		      	
		    	  act_ser_sms=c.getString(1);
		      	
		      	
		      }
		     c.close();
		     
		     Boolean bb=false;
		     if(act_ser_sms.equals("black"))
            	{
            		 bb=   findnobl(incomingNumber);
            		
            	}
            
            	else if(act_ser_sms.equals("white"))
            	{
            		 bb=   findnowht(incomingNumber);
				}
		    
               if (bb) // if incomingNumber need to be blocked
               {
              
            	   abortBroadcast();
            	   Toast.makeText(context, act_ser_sms+bb, Toast.LENGTH_SHORT).show();
		
		
               }
	}

	private Boolean findnobl(String incomingNumber) 
	{
		Boolean fl=false;
		
	     
	    	Cursor  c = db.query("blklst_sms", null , null, null, null, null, null);
	    	  while( c.moveToNext() ) 
		        {
		        	
		        	if(incomingNumber.equals(c.getString(0)))
		        	{
		        		fl=true;
		        		break;
		        	}
		        	c.close();
		        }
	    	 
	     
		return fl;
	}
	
	
	private Boolean findnowht(String incomingNumber) 
	{
		Boolean fl=true;
		
		Cursor c = db.query("whitlist_sms", null , null, null, null, null, null);
   	  while( c.moveToNext() ) 
	        {
	        	
	        	if(incomingNumber.equals(c.getString(0)))
	        	{
	        		fl=false;
	        		break;
	        	}
	        	c.close();
	        }
	        
	       
		return fl;
	}
}
