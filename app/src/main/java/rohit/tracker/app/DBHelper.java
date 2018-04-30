package rohit.tracker.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
Context c;
	public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
		super(context, name, factory, version);
		c=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//if( db.get)
		try {
			db.execSQL("create table blklst_call( ph_no TEXT , ct TEXT)");
			db.execSQL("create table whitlist_call( ph_no TEXT , blk TEXT)");
			db.execSQL("create table blklst_sms( ph_no TEXT , ct TEXT)");
			db.execSQL("create table whitlist_sms( ph_no TEXT , blk TEXT)");
			db.execSQL("create table login( user TEXT , pas TEXT)");
			db.execSQL("create table track_info( sim TEXT , hlp1 TEXT , hlp2 TEXT , hlp3 TEXT , hlp4 TEXT , hlp5 TEXT,stat TEXT)");
			db.execSQL("create table actservice( ser TEXT , stat TEXT)");
			
			
			
		}
		catch(Exception e ) {
			
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
