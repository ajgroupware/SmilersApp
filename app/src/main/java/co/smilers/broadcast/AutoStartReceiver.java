package co.smilers.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.smilers.services.PeriodicalSyncService;

public class AutoStartReceiver extends BroadcastReceiver {
    private static final String TAG = AutoStartReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, PeriodicalSyncService.class));
    }
}
