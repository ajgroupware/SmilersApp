package co.smilers.fcm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.fragments.HeadquarterFragment;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.User;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.services.SyncIntentService;
import co.smilers.services.SyncIntentServiceReceiver;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        UserDAO userDAO = new UserDAO(getApplicationContext());
        User loginUser = userDAO.getUserLogin();
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (loginUser != null && ("/topics/"+loginUser.getAccount().getCode()).equals(remoteMessage.getFrom()) && remoteMessage.getData().size() > 0) {
            Log.d(TAG, "--Topic Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            String action = data.get("action");

            if ("SYNC_ALL".equals(action)) {
                Log.d(TAG, "--SYNC_ALL " );

                startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_ALL,  new SyncIntentServiceReceiver.Listener() {
                    @Override
                    public void onReceiveResult(int resultCode, Bundle resultData) {
                        Log.d(TAG, "--onReceiveResult syncAll");


                    }
                }));

            } else if ("SYNC_ANSWER".equals(action)) {
                syncAnswer();
            }

            return;
        } else if (loginUser != null && ("/topics/smilersConfig").equals(remoteMessage.getFrom()) && remoteMessage.getData().size() > 0) {
            Log.d(TAG, "--Topic smilersConfig Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            String action = data.get("action");

            if ("SYNC_ALL".equals(action)) {
                Log.d(TAG, "--SYNC_ALL " );

                startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_ALL,  new SyncIntentServiceReceiver.Listener() {
                    @Override
                    public void onReceiveResult(int resultCode, Bundle resultData) {
                        Log.d(TAG, "--onReceiveResult syncAll");


                    }
                }));

            } else if ("SYNC_ANSWER".equals(action)) {
                syncAnswer();
            }

            return;
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "--Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private Intent createCallingIntent(String account, String action, SyncIntentServiceReceiver.Listener listener) {
        Log.d(TAG, "--action " + action);
        Intent intent = new Intent(this, SyncIntentService.class);
        try {
            SyncIntentServiceReceiver receiver = new SyncIntentServiceReceiver(mHandler);
            receiver.setListener(listener);
            intent.setAction(action);
            intent.putExtra(SyncIntentService.RECEIVER, receiver);
            intent.putExtra(SyncIntentService.ACCOUNT_PARAM, account);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return intent;
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            // This is where you do your work in the UI thread.
            // Your worker tells you in the message what to do.
            Log.d(TAG, "--handleMessage " + message);
        }
    };


    private void syncAnswer() {
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

}
