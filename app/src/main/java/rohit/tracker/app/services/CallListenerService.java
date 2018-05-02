package rohit.tracker.app;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.ArrayList;


public class CallListenerService extends Service {

    TelephonyManager m_telephonyManager;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        m_telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if(DataManger.getInstance(this).checkSim()){
           String number = DataManger.getInstance(this).checkSimIfChanged();
            if (!number.equals("")) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (!number.equals(m_telephonyManager.getSimSerialNumber())) {
                    inform();
                }
            }
        }
        super.onStart(intent, startId);
    }

    private void inform() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            ArrayList<String> numbers = DataManger.getInstance(this).getAllSupportNumbers();
            String message = "Oprator :" + m_telephonyManager.getSimOperatorName() + " Ph No : " + m_telephonyManager.getLine1Number() + " Location :" + m_telephonyManager.getCellLocation() + " sim no :" + m_telephonyManager.getSimSerialNumber();
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, TrackerActivity.class), 0);
            SmsManager sms = SmsManager.getDefault();
            for (String num : numbers) {
                sms.sendTextMessage(num, null, message, pi, null);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Falied to inform " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}
