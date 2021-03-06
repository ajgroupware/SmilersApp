package co.smilers.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class SyncIntentServiceReceiver extends ResultReceiver{

    private Listener listener;

    public SyncIntentServiceReceiver(Handler handler) {
        super(handler);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (listener != null)
        listener.onReceiveResult(resultCode, resultData);
    }

    public static interface Listener {
        void onReceiveResult(int resultCode, Bundle resultData);

    }
}
