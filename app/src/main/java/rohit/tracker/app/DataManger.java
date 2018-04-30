package rohit.tracker.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataManger {
    static DataManger instance;
    static SQLiteDatabase db;
    static SQLiteOpenHelper helper ;
    static Context con;

    private DataManger(){
        // not accessible
    }

    public static DataManger getInstance(Context con){
        if(instance==null){
            instance = new DataManger();
        }
        db = helper.getWritableDatabase();
        con = con;
        helper = new DBHelper(con, "TrackerActivity", null, 2);
        return instance;
    }

    public void updateCallBlackListService(Boolean falg){
        if (falg)
        {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
            ContentValues val = new ContentValues();
            val.put("ser", "call");
            val.put("stat", "black");
            db.insert("actservice", null, val);
            Toast.makeText(con, "Black List is on !", Toast.LENGTH_SHORT).show();
        } else {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
        }
    }

    public void updateCallWhiteListService(Boolean falg) {
        if (falg) {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
            ContentValues val = new ContentValues();
            val.put("ser", "call");
            val.put("stat", "white");
            db.insert("actservice", null, val);
        } else {
            String[] args = new String[1];
            args[0] = "call";
            db.delete("actservice", "ser = ? ", args);
        }
    }

    public String getActiveCallService(){
        String activeService = "";
        Cursor cur = db.query("actservice", null, "ser='call'", null, null, null, null);
        while (cur.moveToNext()) {
            activeService = cur.getString(1);
        }
        cur.close();
        return activeService;
    }
}
