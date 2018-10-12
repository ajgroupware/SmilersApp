package co.smilers.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import co.smilers.StartZoneActivity;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.User;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

public class PeriodicalSyncService extends Service {
    private static final String TAG = PeriodicalSyncService.class.getSimpleName();
    private Timer timer = new Timer();

    public PeriodicalSyncService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "-- onCreate");
        timer.scheduleAtFixedRate(new MainTask(), 0, 1000*60*5); //5 min
        //timer.scheduleAtFixedRate(new MainTask(), 0, 1000*5); //5 seg
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.i(TAG, "-- startService");
        //timer.scheduleAtFixedRate(new MainTask(), 0, 5000); //5 seg
        return super.startService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "-- onDestroy");
    }

    private final Handler timerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "-- handleMessage " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                UserDAO userDAO = new UserDAO(getApplicationContext());
                User loginUser = userDAO.getUserLogin();
                CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
                List<AnswerScore> answerScores = campaignDAO.getAnswerScore(loginUser.getAccount().getCode());
                if (answerScores != null && answerScores.size() > 0) {
                    Log.i(TAG, "-- answerScores " + answerScores.size());
                    StartZoneActivity.savedAnswerScores = answerScores;
                    startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_ANSWER,  new SyncIntentServiceReceiver.Listener() {
                        @Override
                        public void onReceiveResult(int resultCode, Bundle resultData) {
                            Log.d(TAG, "--onReceiveResult syncAnswer");

                        }
                    }));
                }


                List<AnswerGeneralScore> answerGeneralScores = campaignDAO.getAnswerGeneralScore(loginUser.getAccount().getCode());
                if (answerGeneralScores != null && answerGeneralScores.size() > 0) {
                    Log.i(TAG, "-- answerGeneralScores " + answerGeneralScores.size());
                    StartZoneActivity.savedAnswerGeneralScore = answerGeneralScores;
                    startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_GENERAL_ANSWER,  new SyncIntentServiceReceiver.Listener() {
                        @Override
                        public void onReceiveResult(int resultCode, Bundle resultData) {
                            Log.d(TAG, "--onReceiveResult syncAnswerGeneral");

                        }
                    }));
                }

                List<AnswerBooleanScore> answerBooleanScore = campaignDAO.getAnswerBooleanScore(loginUser.getAccount().getCode());
                Log.i(TAG, "-- answerBooleanScore " + answerBooleanScore.size());
                if (answerBooleanScore != null && answerBooleanScore.size() > 0) {
                    Log.i(TAG, "-- answerBooleanScore " + answerBooleanScore.size());
                    StartZoneActivity.savedAnswerBooleanScore = answerBooleanScore;
                    startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_BOOLEAN_ANSWER,  new SyncIntentServiceReceiver.Listener() {
                        @Override
                        public void onReceiveResult(int resultCode, Bundle resultData) {
                            Log.d(TAG, "--onReceiveResult syncAnswerBoolean");

                        }
                    }));
                }

            } catch (Exception e) {
                Log.e(TAG, "-- Error: " + e.getMessage());
            }

        }
    };

    private class MainTask extends TimerTask {
        public void run() {
            timerHandler.sendEmptyMessage(0);
        }
    }


    private Intent createCallingIntent(String account, String action, SyncIntentServiceReceiver.Listener listener) {
        Log.d(TAG, "--action " + action);
        Intent intent = new Intent(this, SyncIntentService.class);
        SyncIntentServiceReceiver receiver = new SyncIntentServiceReceiver(new Handler());
        receiver.setListener(listener);
        intent.setAction(action);
        intent.putExtra(SyncIntentService.RECEIVER, receiver);
        intent.putExtra(SyncIntentService.ACCOUNT_PARAM, account);
        intent.putExtra(SyncIntentService.SYNC_SAVED_PARAM, true);
        return intent;
    }
}
