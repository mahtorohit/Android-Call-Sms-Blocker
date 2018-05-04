package rohit.tracker.app.services;


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

import rohit.tracker.app.util.DBHelper;

public class SMSBlockerBroadcast extends BroadcastReceiver {
    String incomingNumber = "";
    SQLiteDatabase db;
    String act_ser_sms = "";
    ToggleButton t, t1;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                incomingNumber = msgs[i].getOriginatingAddress();
            }
        }
        SQLiteOpenHelper helper = new DBHelper(context, "TrackerActivity", null, 2);
        db = helper.getWritableDatabase();
        Cursor cursor = db.query("actservice", null, "ser='sms'", null, null, null, null);
        while (cursor.moveToNext()) {
            act_ser_sms = cursor.getString(1);
        }
        cursor.close();
        Boolean bb = false;
        if (act_ser_sms.equals("black")) {
            bb = findnobl(incomingNumber);
        } else if (act_ser_sms.equals("white")) {
            bb = findnowht(incomingNumber);
        }
        if (bb) // if incomingNumber need to be blocked
        {
            abortBroadcast();
            Toast.makeText(context, act_ser_sms + bb, Toast.LENGTH_SHORT).show();
        }
    }

    private Boolean findnobl(String incomingNumber) {
        Boolean fl = false;
        Cursor cursor = db.query("blklst_sms", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (incomingNumber.equals(cursor.getString(0))) {
                fl = true;
                break;
            }
            cursor.close();
        }
        return fl;
    }

    private Boolean findnowht(String incomingNumber) {
        Boolean fl = true;
        Cursor cursor = db.query("whitlist_sms", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if (incomingNumber.equals(cursor.getString(0))) {
                fl = false;
                break;
            }
            cursor.close();
        }
        return fl;
    }
}
