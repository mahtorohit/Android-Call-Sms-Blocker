package rohit.tracker.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStartBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		 Intent i =new Intent(context,CallListenerService.class);
	    context.startService(i);
	}

}
