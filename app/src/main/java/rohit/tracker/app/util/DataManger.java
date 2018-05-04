package rohit.tracker.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DataManger {
    static DataManger instance;
    static SQLiteDatabase db;
    static SQLiteOpenHelper helper;
    static Context con;

    private DataManger() {
        // not accessible
    }

    public static DataManger getInstance(Context con) {
        if (instance == null) {
            instance = new DataManger();
        }
        db = helper.getWritableDatabase();
        con = con;
        helper = new DBHelper(con, "TrackerActivity", null, 2);
        return instance;
    }

    public void updateCallBlackListService(Boolean falg) {
        if (falg) {
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

    public void updateCallWhiteListService(boolean falg) {
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

    public String getActiveCallService() {
        String activeService = "";
        Cursor cur = db.query("actservice", null, "ser='call'", null, null, null, null);
        while (cur.moveToNext()) {
            activeService = cur.getString(1);
        }
        cur.close();
        return activeService;
    }

    public boolean isIncommingBlocked(String incomming) {
        String active_service = "";
        Cursor cur = db.query("actservice", null, "ser='call'", null, null, null, null);
        while (cur.moveToNext()) {
            active_service = cur.getString(1);
        }
        cur.close();
        Boolean whichService = false;
        if (active_service.equals("black")) {
            whichService = isBlackListed(incomming);

        } else if (active_service.equals("white")) {
            whichService = isWhiteListed(incomming);
        }
        return whichService;
    }

    private Boolean isBlackListed(String incomingNumber) {
        Boolean flag = false;
        Cursor cur = db.rawQuery("Select * from blklst_call",null);
        while (cur.moveToNext()) {
            if (incomingNumber.equals(cur.getString(0))) {
                flag = true;
                break;
            }
            cur.close();
        }
        return flag;
    }


    private Boolean isWhiteListed(String incomingNumber) {
        Boolean flag = true;
        Cursor cur = db.rawQuery("Select * from whitlist_call",null);

        while (cur.moveToNext()) {
            if (incomingNumber.equals(cur.getString(0))) {
                flag = false;
                break;
            }
            cur.close();
        }
        return flag;
    }

    public boolean checkTrackerService() {
        String onoff = "";
        Cursor cursor =null;
        try {
            db = helper.getWritableDatabase();
            cursor = db.rawQuery("Select * from track_info",null);
            while (cursor.moveToNext()) {
                onoff = cursor.getString(6);
            }
            if (onoff.equals("true")) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            cursor.close();
            Log.d("Error", e.getMessage());
        }
        return false;
    }

    public String checkSimIfChanged() {
        String number = "";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("Select * from track_info",null);
            while (cursor.moveToNext()) {
                number = cursor.getString(0);
                break;
            }
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        } finally {
            cursor.close();
            return number;

        }
    }

    public ArrayList<String> getAllSupportNumbers(){
        ArrayList<String> numbers = new ArrayList<String>();
        Cursor cursor = db.rawQuery("Select * from track_info",null);
        while (cursor.moveToNext()) {
            numbers.add( cursor.getString(1));
            numbers.add( cursor.getString(2));
            numbers.add( cursor.getString(3));
            numbers.add( cursor.getString(4));
            numbers.add( cursor.getString(5));
        }
        return numbers;
    }


    public void addSupportNumbers(String[] numbers,String simSerialNumber){
        ContentValues val = new ContentValues();
        val.put("sim", simSerialNumber);
        for(String num: numbers) {
            val.put("hlp", num);
        }
        db.insert("track_info", null, val);
    }
}
