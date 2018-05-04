package rohit.tracker.app.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rohit.tracker.app.R;
import rohit.tracker.app.activity.MainActivity;

public class SlashScreen extends AppCompatActivity {
	int a=0;
public static String act_ser_cal="";
public static String act_ser_sms="";
	SQLiteDatabase db;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.screen);
      
	new Timer().schedule(new TimerTask(){ public void run()
	{
			go();
	}}, 5000);

	
	
}
public void go()
{
	Intent i =new Intent(this,MainActivity.class);
	startActivity(i);
	
}
}
