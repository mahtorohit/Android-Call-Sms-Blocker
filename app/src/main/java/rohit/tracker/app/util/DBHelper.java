package rohit.tracker.app.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {
    Context context;

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
        this.context = context;
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
            db.execSQL("create table track_info( sim TEXT , hlp TXT,stat TEXT)");
            db.execSQL("create table actservice( ser TEXT , stat TEXT)");


        } catch (Exception e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
