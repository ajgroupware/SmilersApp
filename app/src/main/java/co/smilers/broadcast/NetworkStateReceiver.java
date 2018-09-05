package co.smilers.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetworkStateReceiver extends BroadcastReceiver {
    private static final String TAG = NetworkStateReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"--Network connectivity change");
        /*
        if(intent.getExtras()!=null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_TYPE);
            if(ni!=null && ni.getState()==NetworkInfo.State.CONNECTED) {
                Log.i(TAG,"--Network "+ni.getTypeName()+" connected");
            }
        }
        if(intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)) {
            Log.d(TAG,"--There's no network connectivity");
        }
        */
    }
}
