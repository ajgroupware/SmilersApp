package co.smilers.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import co.smilers.MainActivity;
import co.smilers.StartZoneActivity;
import co.smilers.api.ApiUtil;
import co.smilers.api.CampaignApi;
import co.smilers.api.ParameterApi;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.Headquarter;
import co.smilers.model.RequestAssistance;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.AppDataHelper;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SyncIntentService extends IntentService {

    private static final String TAG = SyncIntentService.class.getSimpleName();
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String RECEIVER = "co.smilers.services.RECEIVER";

    public static final String ACTION_SYNC_ALL              = "co.smilers.services.action.SYNC_ALL";
    public static final String ACTION_SYNC_CITY             = "co.smilers.services.action.CITY";
    public static final String ACTION_SYNC_HEADQUARTER      = "co.smilers.services.action.HEADQUARTER";
    public static final String ACTION_SYNC_ZONE             = "co.smilers.services.action.ZONE";
    public static final String ACTION_SYNC_GENERAL_HEADER   = "co.smilers.services.action.GENERAL_HEADER";
    public static final String ACTION_SYNC_GENERAL_QUESTION = "co.smilers.services.action.GENERAL_QUESTION";
    public static final String ACTION_SYNC_FOOTER_QUESTION = "co.smilers.services.action.FOOTER_QUESTION";
    public static final String ACTION_SYNC_CAMPAIGN         = "co.smilers.services.action.CAMPAIGN";
    public static final String ACTION_SYNC_CAMPAIGN_FOOTER  = "co.smilers.services.action.CAMPAIGN_FOOTER";
    public static final String ACTION_SYNC_TARGET_ZONE      = "co.smilers.services.action.TARGET_ZONE";
    public static final String ACTION_SYNC_SMS_CELL_PHONE   = "co.smilers.services.action.SMS_CELL_PHONE ";
    public static final String ACTION_SYNC_GENERAL_LOGO     = "co.smilers.services.action.GENERAL_LOGO ";
    public static final String ACTION_SYNC_GENERAL_SETTING_PARAMETER     = "co.smilers.services.action.GENERAL_SETTING_PARAMETER ";

    public static final String ACTION_SYNC_ANSWER         = "co.smilers.services.action.ANSWER";
    public static final String ACTION_SYNC_BOOLEAN_ANSWER = "co.smilers.services.action.BOOLEAN_ANSWER";
    public static final String ACTION_SYNC_GENERAL_ANSWER = "co.smilers.services.action.GENERAL_ANSWER";
    public static final String ACTION_SYNC_REQUEST_ASSISTANCE  = "co.smilers.services.action.REQUEST_ASSISTANCE";

    // TODO: Rename parameters
    public static final String ACCOUNT_PARAM    = "co.smilers.services.extra.ACCOUNT";
    public static final String SYNC_SAVED_PARAM = "co.smilers.services.extra.SYNC_SAVED";

    public SyncIntentService() {
        super("SyncIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSyncAll(Context context, String account) {
        Intent intent = new Intent(context, SyncIntentService.class);
        intent.setAction(ACTION_SYNC_ALL);
        intent.putExtra(ACCOUNT_PARAM, account);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSyncHeadquarter(Context context, String account) {
        Intent intent = new Intent(context, SyncIntentService.class);
        intent.setAction(ACTION_SYNC_HEADQUARTER);
        intent.putExtra(ACCOUNT_PARAM, account);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSyncZone(Context context, String account) {
        Intent intent = new Intent(context, SyncIntentService.class);
        intent.setAction(ACTION_SYNC_ZONE);
        intent.putExtra(ACCOUNT_PARAM, account);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra(RECEIVER);
            final String action = intent.getAction();
            if (ACTION_SYNC_ALL.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncAll(account, receiver);
            } else if (ACTION_SYNC_HEADQUARTER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncHeadquarter(account, receiver);
            } else if (ACTION_SYNC_CITY.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncCity(account, receiver);
            } else if (ACTION_SYNC_ZONE.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncZone(account, receiver);
            } else if (ACTION_SYNC_CAMPAIGN.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncCampaign(account, receiver);
            } else if (ACTION_SYNC_GENERAL_HEADER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncGeneralHeader(account, receiver);
            } else if (ACTION_SYNC_CAMPAIGN_FOOTER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncCampaignFooter(account, receiver);
            } else if (ACTION_SYNC_GENERAL_QUESTION.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncGeneralQuestion(account, receiver);
            } else if (ACTION_SYNC_FOOTER_QUESTION.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncFooterQuestion(account, receiver);
            } else if (ACTION_SYNC_GENERAL_ANSWER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);
                final Boolean syncSaved = intent.getBooleanExtra(SYNC_SAVED_PARAM, false);

                handleActionSyncGeneralAnswer(account, syncSaved, receiver);
            } else if (ACTION_SYNC_ANSWER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);
                final Boolean syncSaved = intent.getBooleanExtra(SYNC_SAVED_PARAM, false);

                handleActionSyncAnswer(account, syncSaved, receiver);
            } else if (ACTION_SYNC_BOOLEAN_ANSWER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);
                final Boolean syncSaved = intent.getBooleanExtra(SYNC_SAVED_PARAM, false);

                handleActionSyncBooleanAnswer(account, syncSaved, receiver);
            } else if (ACTION_SYNC_REQUEST_ASSISTANCE.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);
                final Boolean syncSaved = intent.getBooleanExtra(SYNC_SAVED_PARAM, false);

                handleActionSyncRequestAssistance(account, StartZoneActivity.requestAssistances, syncSaved, receiver);
            } else if (ACTION_SYNC_SMS_CELL_PHONE.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncSmsCellPone(account, receiver);
            } else if (ACTION_SYNC_TARGET_ZONE.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncTargetZone(account, receiver);
            } else if (ACTION_SYNC_GENERAL_LOGO.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncGeneralLogo(account, receiver);
            } else if (ACTION_SYNC_GENERAL_SETTING_PARAMETER.equals(action)) {
                final String account = intent.getStringExtra(ACCOUNT_PARAM);

                handleActionSyncGeneralSettingParameter(account, receiver);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncAll(String account, ResultReceiver receiver) {
        handleActionSyncHeadquarter(account, receiver);
        handleActionSyncZone(account, receiver);
        handleActionSyncCampaign(account, receiver);
        handleActionSyncCampaignFooter(account, receiver);
        handleActionSyncGeneralHeader(account, receiver);
        handleActionSyncSmsCellPone(account, receiver);
        handleActionSyncGeneralLogo(account, receiver);
        handleActionSyncGeneralQuestion(account, receiver);
        handleActionSyncTargetZone(account, receiver);
        handleActionSyncGeneralSettingParameter(account, receiver);
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncHeadquarter(String account, final ResultReceiver receiver) {
        final ParameterApi parameterApi = new ParameterApi();
        parameterApi.listHeadquarter(account, null, null, getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray headquarterJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (headquarterJson != null) {
                                for (int i = 0; i < headquarterJson.length(); i++) {
                                    JSONObject objects = headquarterJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("code", objects.getString("code"));
                                    contentValues.put("name", objects.getString("name"));
                                    JSONObject cityJson = objects.getJSONObject("city");
                                    contentValues.put("city_code", cityJson != null ? cityJson.getString("code"): null);
                                    contentValues.put("account_code", account);

                                    parameterDAO.addHeadquarter(contentValues, db);

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: Headquarter");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncCity(String account, final ResultReceiver receiver) {
        final ParameterApi parameterApi = new ParameterApi();
        parameterApi.listCity(getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray cityJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (cityJson != null) {
                                for (int i = 0; i < cityJson.length(); i++) {
                                    JSONObject objects = cityJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("code", objects.getString("code"));
                                    contentValues.put("name", objects.getString("name"));

                                    parameterDAO.addCity(contentValues, db);

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: Headquarter");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncZone(String account, final ResultReceiver receiver) {
        final ParameterApi parameterApi = new ParameterApi();
        parameterApi.listZone(account, null, null, getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray zoneJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (zoneJson != null) {
                                for (int i = 0; i < zoneJson.length(); i++) {
                                    JSONObject objects = zoneJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("code", objects.getString("code"));
                                    contentValues.put("name", objects.getString("name"));
                                    JSONObject headquarterJson = objects.getJSONObject("headquarter");
                                    contentValues.put("headquarter_code", headquarterJson != null ? headquarterJson.getString("code"): null);
                                    contentValues.put("account_code", account);

                                    parameterDAO.addZone(contentValues, db);

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: Zone");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncGeneralHeader(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listGeneralHeader(account, null, null, null,   new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray zoneJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (zoneJson != null) {
                                for (int i = 0; i < zoneJson.length(); i++) {
                                    JSONObject objects = zoneJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("id", objects.getLong("id"));
                                    contentValues.put("title", objects.getString("title"));
                                    contentValues.put("description", objects.getString("description"));
                                    contentValues.put("design_order", objects.getInt("designOrder"));
                                    contentValues.put("design_color", objects.getString("designColor"));
                                    contentValues.put("is_published", objects.getBoolean("isPublished"));
                                    contentValues.put("account_code", account);

                                    parameterDAO.addGeneralHeader(contentValues, db);

                                }
                            } else {
                                Log.e(TAG, "-- sin datos: GeneralHeader");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                }, getApplicationContext(),
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncCampaignFooter(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listCampaignFooter(account, null, null, null, null,   getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray zoneJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (zoneJson != null) {
                                for (int i = 0; i < zoneJson.length(); i++) {
                                    JSONObject objects = zoneJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("id", objects.getLong("id"));
                                    contentValues.put("title", objects.getString("title"));
                                    contentValues.put("description", objects.getString("description"));
                                    contentValues.put("design_order", objects.getInt("designOrder"));
                                    contentValues.put("design_color", objects.getString("designColor"));
                                    contentValues.put("is_published", objects.getBoolean("isPublished"));
                                    contentValues.put("account_code", account);

                                    parameterDAO.addCampaignFooter(contentValues, db);

                                }
                            } else {
                                Log.e(TAG, "-- sin datos: CampaignFooter");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncGeneralQuestion(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listGeneralQuestion(account, null, null, null,  getApplicationContext(),  new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray zoneJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (zoneJson != null) {
                                for (int i = 0; i < zoneJson.length(); i++) {
                                    JSONObject objects = zoneJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("code", objects.getLong("code"));
                                    contentValues.put("title", objects.getString("title"));
                                    contentValues.put("description", objects.getString("description"));
                                    contentValues.put("design_order", objects.getInt("designOrder"));
                                    contentValues.put("design_color", objects.getString("designColor"));
                                    contentValues.put("min_score", objects.getDouble("minScore"));
                                    contentValues.put("is_published", objects.getBoolean("isPublished"));
                                    contentValues.put("receive_comment", objects.getBoolean("receiveComment"));
                                    contentValues.put("send_sms_notification", objects.getBoolean("sendSmsNotification"));
                                    contentValues.put("account_code", account);

                                    parameterDAO.addGeneralQuestion(contentValues, db);

                                }
                            } else {
                                Log.e(TAG, "-- sin datos: GeneralQuestion");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncFooterQuestion(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listFooterQuestion(account, null, null, null, null,  getApplicationContext(),  new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray zoneJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (zoneJson != null) {
                                for (int i = 0; i < zoneJson.length(); i++) {
                                    JSONObject objects = zoneJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("code", objects.getLong("code"));
                                    contentValues.put("campaign_code", objects.getLong("campaignCode"));
                                    contentValues.put("title", objects.getString("title"));
                                    contentValues.put("description", objects.getString("description"));
                                    contentValues.put("design_order", objects.getInt("designOrder"));
                                    contentValues.put("design_color", objects.getString("designColor"));
                                    contentValues.put("min_score", objects.getDouble("minScore"));
                                    contentValues.put("is_published", objects.getBoolean("isPublished"));
                                    contentValues.put("receive_comment", objects.getBoolean("receiveComment"));
                                    contentValues.put("send_sms_notification", objects.getBoolean("sendSmsNotification"));
                                    contentValues.put("question_type", objects.getString("questionType"));
                                    contentValues.put("account_code", account);

                                    parameterDAO.addFooterQuestion(contentValues, db);

                                }
                            } else {
                                Log.e(TAG, "-- sin datos: FooterQuestion");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncCampaign(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listCampaign(account, null, null, null, null, null, null, getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray campaignJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (campaignJson != null) {
                                for (int i = 0; i < campaignJson.length(); i++) {
                                    JSONObject objects = campaignJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("code", objects.getLong("code"));
                                    contentValues.put("title", objects.getString("title"));
                                    contentValues.put("description", objects.getString("description"));
                                    contentValues.put("start_date", objects.getString("startDate"));
                                    contentValues.put("end_date", objects.getString("endDate"));
                                    contentValues.put("is_published", objects.getBoolean("isPublished"));
                                    contentValues.put("account_code", account);

                                    JSONArray arrayQuestionJson = objects.getJSONArray("questionItems");
                                    campaignDAO.addCampaign(contentValues, db);

                                    if (arrayQuestionJson != null) {
                                        for (int j = 0; j < arrayQuestionJson.length(); j++) {
                                            JSONObject objectsItem = arrayQuestionJson.getJSONObject(j);
                                            ContentValues contentValuesItem = new ContentValues();
                                            contentValuesItem.put("code", objectsItem.getLong("code"));
                                            contentValuesItem.put("title", objectsItem.getString("title"));
                                            contentValuesItem.put("description", objectsItem.getString("description"));
                                            contentValuesItem.put("design_order", objectsItem.getInt("designOrder"));
                                            contentValuesItem.put("design_color", objectsItem.getString("designColor"));
                                            contentValuesItem.put("min_score", objectsItem.getDouble("minScore"));
                                            contentValuesItem.put("is_published", objectsItem.getBoolean("isPublished"));
                                            contentValuesItem.put("receive_comment", objectsItem.getBoolean("receiveComment"));
                                            contentValuesItem.put("question_type", objectsItem.getString("questionType"));
                                            contentValuesItem.put("send_sms_notification", objectsItem.getBoolean("sendSmsNotification"));
                                            contentValuesItem.put("account_code", account);
                                            contentValuesItem.put("campaign_code", objects.getLong("code"));

                                            campaignDAO.addQuestionItem(contentValuesItem, db);
                                        }
                                    } else {
                                        Log.i(TAG, "-- sin datos: Question");
                                    }

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: campaign");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncGeneralAnswer(String account, final Boolean isSyncSaved, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.addAnswerGeneralScore(account, isSyncSaved ? StartZoneActivity.savedAnswerGeneralScore : StartZoneActivity.answerGeneralScore, getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "-- onResponse: " + response);


                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);

                        if (isSyncSaved) { //Eliminar las calificaciones generales enviadas
                            deleteSavedSyncGeneralAnswer();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");

                        if (!isSyncSaved) { //si se envia los que ya están almacenados en la base de datos, no guardar de nuevo
                            saveNoSyncGeneralAnswer();
                        }

                        receiver.send(0, responseBundle);

                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncAnswer(String account, final Boolean isSyncSaved, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        try {
            clearAnswer();
        }catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        }
        campaignApi.addAnswerScore(account, isSyncSaved ? StartZoneActivity.savedAnswerScores : StartZoneActivity.answerScores  , getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "-- onResponse: " + response);


                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);

                        if (isSyncSaved) { //Eliminar las calificaciones enviadas
                            deleteSavedSyncAnswer();
                        }

                        StartZoneActivity.answerScores = new ArrayList<>();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");

                        if (!isSyncSaved) { //si se envia los que ya están almacenados en la base de datos, no guardar de nuevo
                            saveNoSyncAnswer();
                        }

                        receiver.send(0, responseBundle);
                        StartZoneActivity.answerScores = new ArrayList<>();
                    }
                });
    }

    private void handleActionSyncBooleanAnswer(String account, final Boolean isSyncSaved, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.addAnswerBooleanScore(account, isSyncSaved ? StartZoneActivity.savedAnswerBooleanScore : StartZoneActivity.answerBooleanScores , getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "-- onResponse: " + response);


                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);

                        if (isSyncSaved) { //Eliminar las calificaciones enviadas
                            deleteSavedSyncBooleanAnswer();
                        }

                        StartZoneActivity.answerBooleanScores = new ArrayList<>();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");

                        if (!isSyncSaved) { //si se envia los que ya están almacenados en la base de datos, no guardar de nuevo
                            saveNoSyncBooleanAnswer();
                        }

                        receiver.send(0, responseBundle);

                        StartZoneActivity.answerBooleanScores = new ArrayList<>();
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncRequestAssistance(String account, List<RequestAssistance> requestAssistances, final Boolean isSyncSaved, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.addRequestAssistance(account, requestAssistances  , getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "-- onResponse: " + response);


                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);

                        if (isSyncSaved) { //Eliminar las calificaciones enviadas
                            deleteSavedSyncAnswer();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");

                        if (!isSyncSaved) { //si se envia los que ya están almacenados en la base de datos, no guardar de nuevo
                            saveNoSyncAnswer();
                        }

                        receiver.send(0, responseBundle);

                    }
                });
    }


    private void saveNoSyncAnswer() {
        Log.d(TAG, "-- saveNoSyncAnswer: " + StartZoneActivity.answerScores.size());
        SQLiteDatabase db = null;
        try {
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
            AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
            db = mDbHelper.getWritableDatabase();
            Long idNext = campaignDAO.getNextIdAnswerScore(user.getAccount().getCode());
            for (AnswerScore answerScore : StartZoneActivity.answerScores) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", idNext);
                contentValues.put("campaign_code", answerScore.getCampaign().getCode());
                contentValues.put("headquarter_code", answerScore.getHeadquarter().getCode());
                contentValues.put("zone_code", answerScore.getZone().getCode());
                contentValues.put("city_code", answerScore.getHeadquarter().getCity().getCode());
                //contentValues.put("registration_date", 23);
                contentValues.put("excellent", answerScore.getExcellent());
                contentValues.put("good", answerScore.getGood());
                contentValues.put("moderate", answerScore.getModerate());
                contentValues.put("bad", answerScore.getBad());
                contentValues.put("poor", answerScore.getPoor());
                contentValues.put("score", answerScore.getScore());
                contentValues.put("question_item_code", answerScore.getQuestionItem().getCode());
                contentValues.put("comment", answerScore.getComment());
                contentValues.put("user_id", answerScore.getUserId());
                contentValues.put("account_code", user.getAccount().getCode());

                campaignDAO.addAnswerScore(contentValues, db);
                idNext = idNext + 1L;
            }

        } catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        } finally {
            //db.endTransaction();
            if (db != null) {
                db.close();
                db = null;
            }

        }
    }

    private void saveNoSyncGeneralAnswer() {
        Log.d(TAG, "-- saveNoSyncGeneralAnswer: " + StartZoneActivity.answerGeneralScore.size());
        SQLiteDatabase db = null;
        try {
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
            AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
            db = mDbHelper.getWritableDatabase();
            Long idNext = campaignDAO.getNextIdAnswerGeneralScore(user.getAccount().getCode());
            for (AnswerGeneralScore answerScore : StartZoneActivity.answerGeneralScore) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", idNext);
                contentValues.put("headquarter_code", answerScore.getHeadquarter().getCode());
                contentValues.put("zone_code", answerScore.getZone().getCode());
                contentValues.put("city_code", answerScore.getHeadquarter().getCity().getCode());
                //contentValues.put("registration_date", 23);
                contentValues.put("excellent", answerScore.getExcellent());
                contentValues.put("good", answerScore.getGood());
                contentValues.put("moderate", answerScore.getModerate());
                contentValues.put("bad", answerScore.getBad());
                contentValues.put("poor", answerScore.getPoor());
                contentValues.put("score", answerScore.getScore());
                contentValues.put("question_item_code", answerScore.getQuestionItem().getCode());
                contentValues.put("comment", answerScore.getComment());
                contentValues.put("user_id", answerScore.getUserId());
                contentValues.put("account_code", user.getAccount().getCode());

                campaignDAO.addAnswerGeneralScore(contentValues, db);
                idNext = idNext + 1L;
            }

        } catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        } finally {
            //db.endTransaction();
            if (db != null) {
                db.close();
                db = null;
            }

        }
    }

    private void saveNoSyncBooleanAnswer() {
        Log.d(TAG, "-- saveNoSyncBooleanAnswer: " + StartZoneActivity.answerBooleanScores.size());
        SQLiteDatabase db = null;
        try {
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
            AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
            db = mDbHelper.getWritableDatabase();
            Long idNext = campaignDAO.getNextIdAnswerBooleanScore(user.getAccount().getCode());
            for (AnswerBooleanScore answerScore : StartZoneActivity.answerBooleanScores) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", idNext);
                contentValues.put("campaign_code", answerScore.getCampaign().getCode());
                contentValues.put("headquarter_code", answerScore.getHeadquarter().getCode());
                contentValues.put("zone_code", answerScore.getZone().getCode());
                contentValues.put("city_code", answerScore.getHeadquarter().getCity().getCode());
                //contentValues.put("registration_date", 23);
                contentValues.put("yes_answer", answerScore.getYesAnswer());
                contentValues.put("yes_answer", answerScore.getNoAnswer());
                contentValues.put("score", answerScore.getScore());
                contentValues.put("question_item_code", answerScore.getQuestionItem().getCode());
                contentValues.put("comment", answerScore.getComment());
                contentValues.put("user_id", answerScore.getUserId());
                contentValues.put("account_code", user.getAccount().getCode());

                campaignDAO.addAnswerBooleanScore(contentValues, db);
                idNext = idNext + 1L;
            }

        } catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        } finally {
            //db.endTransaction();
            if (db != null) {
                db.close();
                db = null;
            }

        }
    }

    private void deleteSavedSyncAnswer() {
        try {
            StringBuilder listId = new StringBuilder();
            for (AnswerScore answerScore : StartZoneActivity.savedAnswerScores) {
                listId.append(answerScore.getId() + ",");
            }

            listId.deleteCharAt(listId.length() - 1);
            Log.d(TAG, "-- listId: " + listId);
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());

            campaignDAO.deleteAnswerScore(user.getAccount().getCode(), listId.toString());

            StartZoneActivity.savedAnswerScores = new ArrayList<>();

        } catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        }
    }

    private void deleteSavedSyncGeneralAnswer() {
        try {
            StringBuilder listId = new StringBuilder();
            for (AnswerGeneralScore answerScore : StartZoneActivity.savedAnswerGeneralScore) {
                listId.append(answerScore.getId() + ",");
            }

            listId.deleteCharAt(listId.length() - 1);
            Log.d(TAG, "-- listId: " + listId);
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());

            campaignDAO.deleteGeneralAnswerScore(user.getAccount().getCode(), listId.toString());

            StartZoneActivity.savedAnswerGeneralScore = new ArrayList<>();

        } catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        }
    }

    private void deleteSavedSyncBooleanAnswer() {
        try {
            StringBuilder listId = new StringBuilder();
            for (AnswerBooleanScore answerScore : StartZoneActivity.savedAnswerBooleanScore) {
                listId.append(answerScore.getId() + ",");
            }

            listId.deleteCharAt(listId.length() - 1);
            Log.d(TAG, "-- listId: " + listId);
            UserDAO userDAO = new UserDAO(getApplicationContext());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());

            campaignDAO.deleteBooleanAnswerScore(user.getAccount().getCode(), listId.toString());

            StartZoneActivity.savedAnswerBooleanScore = new ArrayList<>();

        } catch (Exception e) {
            Log.e(TAG, "-- Error: " + e.getMessage());
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncSmsCellPone(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listSmsCellPhone(account, null, null, null, getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray headquarterJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (headquarterJson != null) {
                                campaignDAO.deleteSmsCellPhone(account);
                                for (int i = 0; i < headquarterJson.length(); i++) {
                                    JSONObject objects = headquarterJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("id", objects.getLong("id"));
                                    contentValues.put("cell_phone_number", objects.getString("cellPhoneNumber"));
                                    contentValues.put("email", objects.getString("email"));
                                    contentValues.put("campaign_code", objects.getLong("campaignCode"));
                                    contentValues.put("headquarter_code", objects.getLong("headquarterCode"));
                                    contentValues.put("zone_code", objects.getLong("zoneCode"));
                                    contentValues.put("is_active", objects.getBoolean("isActive"));
                                    contentValues.put("account_code", account);

                                    campaignDAO.addSmsCellPhone(contentValues, db);

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: SmsCellPhone");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncTargetZone(String account, final ResultReceiver receiver) {
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.listTargetZone(account, null, null,  getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        CampaignDAO campaignDAO = new CampaignDAO(getApplicationContext());
                        campaignDAO.deleteTargetZone(account);
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray headquarterJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (headquarterJson != null) {
                                for (int i = 0; i < headquarterJson.length(); i++) {
                                    JSONObject objects = headquarterJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("zone_code", objects.getLong("zoneCode"));
                                    contentValues.put("campaign_code", objects.getLong("campaignCode"));
                                    contentValues.put("account_code", account);

                                    campaignDAO.addTargetZone(contentValues, db);

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: SmsCellPhone");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncGeneralLogo(String account, final ResultReceiver receiver) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<byte[]> result = executor.submit(new Callable<byte[]>() {
            public byte[] call() throws Exception {
                try {
                    byte[] image = getLogoImage(ApiUtil.BASE_PATH + "/parameter/logo/" + account);
                    return image;

                } catch (Exception e) {
                    Log.d(TAG, "--Error: " + e.toString());
                }
                return null;
            }
        });

        try {
            byte[] image = result.get();
            if (image != null && image.length > 0) {
                ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                parameterDAO.setGeneralLogo(account, image);
                Log.d(TAG, "--setGeneralLogo: ");
            }

            Log.d(TAG, "--image: " + image.length);
            Bundle responseBundle = new Bundle();
            responseBundle.putString("RESULT", "OK");
            receiver.send(0, responseBundle);
        } catch (Exception exception) {
            exception.printStackTrace();
            Bundle responseBundle = new Bundle();
            responseBundle.putString("RESULT", "ERROR");
            receiver.send(0, responseBundle);
        }
    }

    /*
     * Obtener el contenido binario de la imagen
     */
    private byte[] getLogoImage(String imageUrl) {
        byte[] image = null;
        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();

            byte[] buf = new byte[2048];
            int length;

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //byte[] buf = new byte[1024];

            while ((length = is.read(buf)) != -1) {
                out.write(buf, 0, length);
            }

            /*
            int n = 0;
            while ((n = is.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            */

            is.close();
            out.close();

            image = out.toByteArray();

        } catch (Exception e) {
            Log.d(TAG, "--Error: " + e.toString());
        }

        return image;
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionSyncGeneralSettingParameter(String account, final ResultReceiver receiver) {
        final ParameterApi parameterApi = new ParameterApi();
        parameterApi.listGeneralSettingParameter(account, getApplicationContext(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ParameterDAO parameterDAO = new ParameterDAO(getApplicationContext());
                        AppDataHelper mDbHelper = new AppDataHelper(getApplicationContext());
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        try {
                            JSONArray generalSettingParameterJson  = new JSONArray(response);
                            //db.beginTransaction();
                            if (generalSettingParameterJson != null) {

                                for (int i = 0; i < generalSettingParameterJson.length(); i++) {
                                    JSONObject objects = generalSettingParameterJson.getJSONObject(i);
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("parameter_key", objects.getString("parameterKey"));
                                    contentValues.put("parameter_value", objects.getString("parameterValue"));
                                    contentValues.put("account_code", account);

                                    parameterDAO.addGeneralSettingParameter(contentValues, db);

                                }
                            } else {
                                Log.i(TAG, "-- sin datos: GeneralSettingParameter");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //db.endTransaction();
                            if (db != null) {
                                db.close();
                                db = null;
                            }

                        }

                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "OK");
                        receiver.send(0, responseBundle);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");
                        receiver.send(0, responseBundle);
                    }
                });
    }

    //Por alguna razón se generando respuestas vacías, se limpian las respuestas antes de enviar
    private void clearAnswer() {
        if (StartZoneActivity.answerScores != null) {
            for (Iterator<AnswerScore> i = StartZoneActivity.answerScores.iterator(); i.hasNext(); ) {
                AnswerScore answerScore  = i.next();
                if (answerScore.getScore() == null || answerScore.getScore().intValue() == 0) {
                    i.remove();
                }
            }
        }

        if (StartZoneActivity.savedAnswerScores != null) {
            for (Iterator<AnswerScore> i = StartZoneActivity.savedAnswerScores.iterator(); i.hasNext(); ) {
                AnswerScore answerScore  = i.next();
                if (answerScore.getScore() == null || answerScore.getScore().intValue() == 0) {
                    i.remove();
                }
            }
        }
    }
}
