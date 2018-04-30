package rohit.tracker.app;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class EditWhiteList extends Activity {
	ArrayList<String> no;
	ArrayAdapter<String> lis;
	SQLiteDatabase db;
	ListView sp ;
	EditText phno;
	String delitem="";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.white_list);
		
		sp =(ListView)findViewById(R.id.listView1);
		phno =(EditText)findViewById(R.id.editText1);
		  SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
	      db = helper.getWritableDatabase();
	     sp.setOnItemClickListener(new OnItemClickListener() {
	    	 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		// TODO Auto-generated method stub
	    		 delitem=String.valueOf(sp.getItemAtPosition(arg2));
	    	}
		});
	no = new ArrayList<String>();
	      popup();
	}
	
	public void popup()
	{
		no.clear();
		try
		{ 
			if(db!=null)
			{
				Cursor c = db.query("whitlist_call", null , null, null, null, null,"ph_no desc");
				while( c.moveToNext() ) 
				{
				String num = c.getString(0);
			    no.add( num );
			   
				 
				  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, no);
			     sp.setAdapter(adapter);
				}
				c.close();
			}
			else
			{
				
				Toast.makeText(getApplicationContext(),"blank", Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception e) {
			Log.d("Rohit error", e.getMessage()+e.getStackTrace());
			Toast.makeText(getApplicationContext(),"popup : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		  
	}

	public void delete(View v)
	{
		try
		{
		if(!delitem.equals(""))
		{
			String [] args = new String[1];
			args[0] = delitem;
		db.delete("whitlist_call", "ph_no = ? ", args );
		popup();
		Toast.makeText(getApplicationContext(), "Number deleted !", Toast.LENGTH_SHORT).show();
		}
		else
		{
		Toast.makeText(getApplicationContext(), "Select Number frist !", Toast.LENGTH_SHORT).show();
		
		}
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(),"del : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		  
	}

	public void add(View v)
	{
		try
		{
		if(!phno.getText().equals(""))
		{
		ContentValues val = new ContentValues();
		val.put("ph_no", phno.getText().toString());
		val.put("blk","0");
		
		
		db.insert("whitlist_call", null, val);
		popup();
		Toast.makeText(getApplicationContext(), "New Number Added !", Toast.LENGTH_SHORT).show();
		}
		else
		{
		Toast.makeText(getApplicationContext(), "Enter Number frist !", Toast.LENGTH_SHORT).show();
		
		}
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(),"add : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		  
	}

	}
