package rohit.tracker.app.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import rohit.tracker.app.util.DBHelper;
import rohit.tracker.app.R;
import rohit.tracker.app.util.DataManger;
import rohit.tracker.app.util.SMSOps;

public class TrackerActivity extends AppCompatActivity {


    SQLiteDatabase db;
    TelephonyManager m_telephonyManager;
    EditText t, t1, t2, t3, t4;
    ToggleButton tog;
    String onoff = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.track);
            SQLiteOpenHelper helper = new DBHelper(this, "TrackerActivity", null, 2);
            db = helper.getWritableDatabase();
            m_telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            t = (EditText) findViewById(R.id.editText1);
            t1 = (EditText) findViewById(R.id.editText2);
            t2 = (EditText) findViewById(R.id.editText3);
            t3 = (EditText) findViewById(R.id.editText4);
            t4 = (EditText) findViewById(R.id.editText5);
            tog = (ToggleButton) findViewById(R.id.toggleButton1);
            Cursor c = db.query("track_info", null, null, null, null, null, null);
            while (c.moveToNext()) {
                onoff = c.getString(6);
            }
            c.close();

            if (DataManger.getInstance(this).checkTrackerService()){
                tog.setChecked(true);
                SMSOps.getInstance(this).checkSimChange();
            } else if (onoff.equals("false")) {
                tog.setChecked(false);
            }
        } catch (Exception e) {
            Toast.makeText(this, "oncreat " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void trackonoff(View v) {
        try {
            ContentValues val = new ContentValues();
            if (tog.isChecked() == true) {
                val.put("stat", "true");
                Toast.makeText(this, "Protection On !", Toast.LENGTH_SHORT).show();
            } else if (tog.isChecked() == false) {
                val.put("stat", "false");
                db.delete("track_info", null, null);
                Toast.makeText(this, "Protection Off !", Toast.LENGTH_SHORT).show();
            }
            db.update("track_info", val, null, null);
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this, "onoff " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void add(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            String[] numbers = new String[5];
            numbers[0] = t.getEditableText().toString();
            numbers[1] = t1.getEditableText().toString();
            numbers[2] = t2.getEditableText().toString();
            numbers[3] = t3.getEditableText().toString();
            numbers[4] = t4.getEditableText().toString();
            DataManger.getInstance(this).addSupportNumbers(numbers,m_telephonyManager.getSimSerialNumber());
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this, "Failed to add support numbers  " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
