package rohit.tracker.app.services;

import java.lang.reflect.Method;


import com.android.internal.telephony.ITelephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import rohit.tracker.app.util.DataManger;

public class CallBlockerBroadcast extends BroadcastReceiver {


    private TelephonyManager m_telephonyManager;
    private ITelephony m_telephonyService;

    private AudioManager m_audioManager;
    Context context;

    public void onReceive(Context context, Intent intent) {
        this.context = context;
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
            Toast.makeText(context, incomingNumber, Toast.LENGTH_LONG).show();
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    boolean whichService = DataManger.getInstance(context).isIncommingBlocked(incomingNumber.substring(incomingNumber.length() - 10, incomingNumber.length()));
                    if (whichService) // if incoming Number need to be blocked
                    {
                        m_audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        m_telephonyService.endCall();
                    }
                    break;
                default:
                    break;
            }
        }


    }
}
   