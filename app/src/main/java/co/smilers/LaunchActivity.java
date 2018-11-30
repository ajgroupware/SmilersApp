package co.smilers;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import co.com.groupware.quantum.LoginActivity;
import co.smilers.api.AccountApi;
import co.smilers.fragments.SelectCampaignFragment;
import co.smilers.model.CurrentConfig;
import co.smilers.model.Login;
import co.smilers.model.MeterDevice;
import co.smilers.model.User;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.services.PeriodicalSyncService;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.MEDIA_CONTENT_CONTROL;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.REORDER_TASKS;
import static android.Manifest.permission.SEND_SMS;

public class LaunchActivity extends AppCompatActivity {

    private static final String TAG = LaunchActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        UserDAO userDAO = new UserDAO(this);
        User user = userDAO.getUserLogin();


        if (user != null) {
            ParameterDAO parameterDAO = new ParameterDAO(this);
            CurrentConfig currentConfig = parameterDAO.getCurrentConfig(user.getAccount().getCode());
            if (currentConfig != null && currentConfig.getZoneCode() != null && currentConfig.getHeadquarterCode().intValue() > 0) {
                startService(new Intent(this, PeriodicalSyncService.class));
                goCurrentConfig(currentConfig.getHeadquarterCode(), currentConfig.getZoneCode());
            } else {
                goMainScreen();
            }

        } else {
            goLoginScreen();

            // Check if we're running on Android 6.0 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermission();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "--requestCode: "+requestCode);
        if (data != null && requestCode == LoginActivity.REQUEST_CODE_LOGIN_BUTTON) {
            final String userName = data.getStringExtra(LoginActivity.RESPONSE_USERNAME);
            final String password = data.getStringExtra(LoginActivity.RESPONSE_PASSWORD);
            UserDAO userDAO = new UserDAO(this);
            MeterDevice meterDevice = userDAO.getDevice();
            Login login = new Login();
            login.setUserName(userName);
            login.setPassword(password);

            if (meterDevice != null && meterDevice.getId() != null) {
                login.setIdPush(meterDevice.getDeviceIdPush());
            } else {
                // Now manually call onTokenRefresh()
                Log.d(TAG, "--Getting new token");
                String hedToken = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "--hedToken " + hedToken);

                if (hedToken != null) {
                    userDAO.deleteDevice();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("idPush", hedToken);
                    contentValues.put("osVersionDispositivo", "Android");
                    contentValues.put("referenciaDispositivo", String.valueOf(Build.VERSION.SDK_INT));
                    contentValues.put("serialDispositivo", Build.SERIAL);

                    userDAO.addDevice(contentValues);

                    login.setIdPush(hedToken);
                } else {
                    // Now manually call onTokenRefresh()
                    login.setIdPush("NO_ID_PUSH");
                }
            }

            AccountApi accountApi = new AccountApi();
            //DeviceDAO deviceDAO = new DeviceDAO(this);
            //final Device device = deviceDAO.getDevice();
            if (true) {
                //user.setDevices(new ArrayList<Device>(){{add(device);}});

                accountApi.loginUser(login, this, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "--response: "+response);
                        addUserFromResponse(response, password);
                        goMainScreen();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        //Log.d(TAG, "--error: "+error.networkResponse.toString());
                        try {
                            String errorMsg = "";
                            NetworkResponse response = error.networkResponse;
                            if (response != null) {
                                String str = new String(response.data, StandardCharsets.UTF_8);
                                Log.d(TAG, "--error: " + str);
                                errorMsg = "Credenciales incorrectas";
                            } else {
                                errorMsg = error.getMessage();
                            }

                            new AlertDialog.Builder(LaunchActivity.this).setTitle("Información").setMessage(errorMsg).setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goLoginScreen();
                                }
                            }).create().show();

                        } catch (Exception e) {
                            Log.d(TAG, "--error: " + e.getMessage());
                            new AlertDialog.Builder(LaunchActivity.this).setTitle("Información").setMessage("Se presentó un inconveniente con el servicio.\nPor favor comunicarse con el administrador").setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    goLoginScreen();
                                }
                            }).create().show();
                        }

                    }
                });
            } else {

                new AlertDialog.Builder(this).setTitle("Información").setMessage("No se ha establecido conexión con el servidor de Google para obtener el idPush. Por favor verifique su conexión a internet").setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goLoginScreen();
                    }
                }).create().show();
            }


        } else {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
        //FirebaseMessaging.getInstance().subscribeToTopic("sigem");
    }

    private void addUserFromResponse(String user, String password) {
        try {
            UserDAO userDAO = new UserDAO(this);
            JSONObject userJson = new JSONObject(user);

            ContentValues userValues = new ContentValues();
            userValues.put("userName", userJson.getString("userName"));
            userValues.put("password", password);
            userValues.put("name", userJson.getString("name"));

            JSONObject accountJson = userJson.getJSONObject("account");

            userValues.put("accountCode", accountJson.getString("code"));
            userValues.put("accountName", accountJson.getString("name"));

            userDAO.addUser(userValues);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ir a pantalla principal del dispositivo
     *
     */
    private void goMainScreen(){
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"--Error goMainScreen");
        }
    }

    /**
     * Ir a la configuración actual
     *
     */
    private void goCurrentConfig(Long headquarterCode, Long zoneCode){
        try {
            Intent intent = new Intent(this, StartZoneActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(SelectCampaignFragment.ARG_HEADQUARTER, headquarterCode);
            intent.putExtra(SelectCampaignFragment.ARG_ZONE, zoneCode);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"--Error goCurrentConfig");
        }
    }

    /**
     * Ir a pantalla del login
     *
     */
    private void goLoginScreen(){
        try {
            Intent intent = new Intent(this, LoginActivity.class);

            UserDAO userDAO = new UserDAO(this);
            ArrayList<String> listComplete = userDAO.getListUser();
            if (listComplete != null) {
                intent.putStringArrayListExtra(LoginActivity.EXTRAS_LIST_AUTO_COMPLETE, listComplete);
            }

            startActivityForResult(intent, LoginActivity.REQUEST_CODE_LOGIN_BUTTON);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"--Error goLoginScreen");
        }
    }

    /**
     * Validar permisos Aplica para android marshmallow
     */
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{RECORD_AUDIO, ACCESS_FINE_LOCATION, REORDER_TASKS, SEND_SMS, RECEIVE_BOOT_COMPLETED, ACCESS_NETWORK_STATE}, PERMISSION_REQUEST_CODE);
        //ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, READ_EXTERNAL_STORAGE, MEDIA_CONTENT_CONTROL, CAMERA, RECORD_AUDIO, AUDIO_SERVICE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                /*
                TelephonyManager tManager = (TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
                String simSerialNumber = tManager.getSimSerialNumber();
                Log.d(TAG, "--SimSerialNumber " + simSerialNumber);
                */

                break;
            default:
                break;

        }
    }
}
