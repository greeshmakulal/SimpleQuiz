package com.simplequiz.app;

import static android.icu.text.UnicodeSet.CASE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class Airoplane_Mode_Changed extends BroadcastReceiver {
        String tag;
        String voice;

        @Override
        public void onReceive(Context context, Intent intent) {
                tag = intent.getExtras ().get ("TAG").toString ();
                voice = intent.getExtras ().get ("VOICE").toString ();
                if (tag.equals ("YESNO")) {
                        Toast.makeText (context, "Hey the command is :" + voice, Toast.LENGTH_LONG).show ();
                }


        }
}


