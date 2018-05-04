package rohit.tracker.app.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import rohit.tracker.app.R;
import rohit.tracker.app.util.Constant;
import rohit.tracker.app.util.DBHelper;

public class SMSBlockerActivity extends AppCompatActivity {
	SQLiteDatabase db;
	ToggleButton t,t1;
	String act_ser_sms="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_block);
		SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
		 db = helper.getWritableDatabase();
		 t=(ToggleButton)findViewById(R.id.toggleButton1);
		 t1=(ToggleButton)findViewById(R.id.toggleButton2);
		 
		 Cursor c = db.query("actservice", null , "ser='sms'", null, null, null, null);
		    
		    while( c.moveToNext() ) 
		    {
		    	
		    	act_ser_sms=c.getString(1);
		    	
		    	
		    }
		    if(act_ser_sms.equals("black"))
		     {
		    	 t.setChecked(true);
		    	 t1.setChecked(false);
		     }
		     else if(act_ser_sms.equals("white")) {
		    	 t1.setChecked(true);
		    	 t.setChecked(false);
			}
		     else  {
		    	 t1.setChecked(false);
		    	 t.setChecked(false);
			}
		   
		 
	}
	public void editblk (View v)
	{
		Intent i =new Intent(this,SMSEditBlackListActivity.class);;
		startActivity(i);
	}

	public void whitelist(View v)
	{
		Intent i =new Intent(this,SMSEditWhiteListActivity.class);;
		startActivity(i);
	}
	public void onoff1(View v)
	{
		if(t.isChecked())
		{	
			String [] args = new String[1];
			args[0] = "sms";
			db.delete("actservice", "ser = ? ", args );
			ContentValues val = new ContentValues();
			val.put("ser", "sms");
			val.put("stat","black");
			db.insert("actservice", null, val);
			Toast.makeText(getApplicationContext(), "Black List is on !", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String [] args = new String[1];
			args[0] = "sms";
			db.delete("actservice", "ser = ? ", args );
			Toast.makeText(getApplicationContext(), "Black List off !", Toast.LENGTH_SHORT).show();
		}
	}

	public void onoff2(View v)
	{
		if(t1.isChecked())
		{	
			String [] args = new String[1];
			args[0] = Constant.SMS_BLOCK_SERVICE;
			db.delete("actservice", "ser = ? ", args );
			ContentValues val = new ContentValues();
			val.put("ser", "sms");
			val.put("stat","white");
			db.insert("actservice", null, val);
			Toast.makeText(getApplicationContext(), "white List is on !", Toast.LENGTH_SHORT).show();
		}
		else
		{
			String [] args = new String[1];
			args[0] = Constant.SMS_BLOCK_SERVICE;
			db.delete("actservice", "ser = ? ", args );
			Toast.makeText(getApplicationContext(), "white List off !", Toast.LENGTH_SHORT).show();
		}
	}

}
