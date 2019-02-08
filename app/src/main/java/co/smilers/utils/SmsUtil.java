package co.smilers.utils;

import android.content.Context;
import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class SmsUtil {

    private static final String TAG = SmsUtil.class.getSimpleName();

    public static void sendSms(final String phoneNo, final String sms, Context context) {
        Log.i(TAG, "-- start: sendSms " + phoneNo);
        /*
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SmsManager smsManager = null;
                    try {
                        smsManager = SmsManager.getDefault();
                        //String outSms = new String(sms.getBytes("UTF-8"));
                        //String outSms = Html.fromHtml(new String(sms.getBytes("UTF-8"))).toString();
                        //Log.i(TAG, "-- outSms " + outSms);
                        //Toast.makeText(context, outSms, Toast.LENGTH_LONG).show();
                        //ArrayList<String> messageList = SmsManager.getDefault().divideMessage(outSms);

                        //ArrayList<String> messageList = new ArrayList<>();
                        //smsList.add(outSms);


                        //smsManager.sendMultipartTextMessage(phoneNo, null, messageList, null, null);
                        smsManager.sendTextMessage(phoneNo, null, sms, null, null);

                        Log.i(TAG, "-- end: sendSms");
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (smsManager != null) {
                            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                            Log.i(TAG, "-- try: sendSms");
                        }

                    }

                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
       */
    }
}
