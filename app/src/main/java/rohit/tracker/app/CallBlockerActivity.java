package rohit.tracker.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CallBlockerActivity extends Activity {
    ToggleButton toggel_black_list, toggel_white_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_block);
        String active_service = DataManger.getInstance(this).getActiveCallService();
        toggel_black_list = (ToggleButton) findViewById(R.id.toggleButton1);
        toggel_white_list = (ToggleButton) findViewById(R.id.toggleButton2);

        if (active_service.equals("black")) {
            toggel_black_list.setChecked(true);
            toggel_white_list.setChecked(false);
        } else if (active_service.equals("white")) {
            toggel_white_list.setChecked(true);
            toggel_black_list.setChecked(false);
        } else {
            toggel_white_list.setChecked(false);
            toggel_black_list.setChecked(false);
        }


    }

    public void editblk(View v) {
        Intent i = new Intent(this, EditBlackList.class);
        startActivity(i);
    }

    public void whitelist(View v) {
        Intent i = new Intent(this, EditWhiteList.class);
        startActivity(i);
    }

    public void setBlackList(View v) {
        DataManger.getInstance(this).updateCallBlackListService(toggel_black_list.isChecked());
        if (toggel_black_list.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Black List is on !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Black List off !", Toast.LENGTH_SHORT).show();
        }
    }

    public void edit_white_list(View v) {
        DataManger.getInstance(this).updateCallWhiteListService(toggel_white_list.isChecked());
        if (toggel_white_list.isChecked()) {
            Toast.makeText(getApplicationContext(), "white List is on !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "white List off !", Toast.LENGTH_SHORT).show();
        }
    }

}
