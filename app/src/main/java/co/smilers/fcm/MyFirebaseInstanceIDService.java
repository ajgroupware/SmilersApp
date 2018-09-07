package co.smilers.fcm;

import android.content.ContentValues;
import android.os.Build;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import co.smilers.model.data.daos.UserDAO;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    public MyFirebaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "--Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToDB(refreshedToken);
    }

    private void sendRegistrationToDB(String refreshedToken) {
        Log.d(TAG, "--sendRegistrationToDB: ");
        try {
            UserDAO userDAO = new UserDAO(getApplicationContext());
            ContentValues contentValues = new ContentValues();
            contentValues.put("idPush", refreshedToken);
            contentValues.put("osVersionDispositivo", "Android");
            contentValues.put("referenciaDispositivo", String.valueOf(Build.VERSION.SDK_INT));
            contentValues.put("serialDispositivo", Build.SERIAL);

            userDAO.addDevice(contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
