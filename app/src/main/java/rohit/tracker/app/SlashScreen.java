package rohit.tracker.app;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class SlashScreen extends Activity {
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
