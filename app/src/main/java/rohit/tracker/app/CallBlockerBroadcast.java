package rohit.tracker.app;

import java.lang.reflect.Method;


import com.android.internal.telephony.ITelephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallBlockerBroadcast extends BroadcastReceiver {


    SQLiteDatabase db;

    String act_ser_cal = "";

    private TelephonyManager m_telephonyManager;
    private ITelephony m_telephonyService;

    private AudioManager m_audioManager;
    Context context1;

    public void onReceive(Context context, Intent intent) {
        SQLiteOpenHelper helper = new DBHelper(context, "TrackerActivity", null, 2);
        db = helper.getWritableDatabase();
        context1 = context;
        m_telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = null;
            c = Class.forName(m_telephonyManager.getClass().getName());
            Method m = null;
            m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            m_telephonyService = (ITelephony) m.invoke(m_telephonyManager);
            m_audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            m_telephonyManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {
            Toast.makeText(context1, incomingNumber, Toast.LENGTH_LONG).show();
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    m_audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //CALL_STATE_OFFHOOK;
                    //m_telephonyService.endCall();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    act_ser_cal = "";
                    Cursor cur = db.query("actservice", null, "ser='call'", null, null, null, null);
                    while (cur.moveToNext()) {
                        act_ser_cal = cur.getString(1);
                    }
                    // cur.close();

                    Boolean bb = false;
                    if (act_ser_cal.equals("black")) {
                        bb = findnobl(incomingNumber);

                    } else if (act_ser_cal.equals("white")) {
                        bb = findnowht(incomingNumber);
                    }
                    Toast.makeText(context1, act_ser_cal + bb, Toast.LENGTH_SHORT).show();

                    if (bb) // if incomingNumber need to be blocked
                    {
                        m_audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        m_telephonyService.endCall();

                    } else {
                        m_audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }
                    break;
                default:
                    break;
            }
        }


        private Boolean findnobl(String incomingNumber) {
            Boolean fl = false;


            Cursor cc = db.query("blklst_call", null, null, null, null, null, null);
            while (cc.moveToNext()) {

                if (incomingNumber.equals(cc.getString(0))) {
                    fl = true;
                    break;
                }
                //	cc.close();
            }


            return fl;
        }


        private Boolean findnowht(String incomingNumber) {
            Boolean fl = true;

            Cursor cur1 = db.query("whitlist_call", null, null, null, null, null, null);

            while (cur1.moveToNext()) {

                if (incomingNumber.equals(cur1.getString(0))) {
                    fl = false;
                    break;
                }
                //cur1.close();
            }


            return fl;
        }
    }
}
   