package rohit.tracker.app.util;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.ArrayList;

import rohit.tracker.app.activity.TrackerActivity;

public class SMSOps {

    static Context context;
    static SMSOps instance;
    TelephonyManager m_telephonyManager;
    private SMSOps() {

    }

    public static SMSOps getInstance(Context con) {
        if(instance==null){
            instance= new SMSOps();
        }
        context = con;
        return instance;
    }

    public void checkSimChange() {
        String number = DataManger.getInstance(context).checkSimIfChanged();
        if (!number.equals("")) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (!number.equals(m_telephonyManager.getSimSerialNumber())) {
                inform();
            }
        }
    }

    public void inform() {
        try {
            m_telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, "android.permission.READ_PHONE_NUMBERS") != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            ArrayList<String> numbers = DataManger.getInstance(context).getAllSupportNumbers();
            String message = "Oprator :" + m_telephonyManager.getSimOperatorName() + " Ph No : " + m_telephonyManager.getLine1Number() + " Location :" + m_telephonyManager.getCellLocation() + " sim no :" + m_telephonyManager.getSimSerialNumber();
            PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, TrackerActivity.class), 0);
            SmsManager sms = SmsManager.getDefault();
            for (String num : numbers) {
                sms.sendTextMessage(num, null, message, pi, null);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Falied to inform " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}
