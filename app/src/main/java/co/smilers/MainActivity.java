package co.smilers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import co.smilers.fragments.CampaignFragment;
import co.smilers.fragments.HeadquarterFragment;
import co.smilers.fragments.ZoneFragment;
import co.smilers.model.Account;
import co.smilers.model.Headquarter;
import co.smilers.model.User;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.services.PeriodicalSyncService;
import co.smilers.services.SyncIntentService;
import co.smilers.services.SyncIntentServiceReceiver;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_headquarter:
                    HeadquarterFragment headquarterFragment = HeadquarterFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, headquarterFragment).commit();
                    return true;
                case R.id.navigation_zone:
                    ZoneFragment zoneFragment = ZoneFragment.newInstance(0L);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, zoneFragment).commit();
                    return true;
                case R.id.navigation_campaign:
                    CampaignFragment campaignFragment = CampaignFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, campaignFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HeadquarterFragment headquarterFragment = HeadquarterFragment.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, headquarterFragment).commit();

        syncAll ();

        startService(new Intent(this, PeriodicalSyncService.class));
    }


    private void syncAll () {
        UserDAO userDAO = new UserDAO(this);
        User loginUser = userDAO.getUserLogin();
        final ProgressDialog progressDialogHeadquarter = ProgressDialog.show(this, null, "Sincronizando sedes...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_HEADQUARTER,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult Headquarter");

                HeadquarterFragment headquarterFragment = HeadquarterFragment.newInstance("", "");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, headquarterFragment).commit();

                progressDialogHeadquarter.dismiss();
            }
        }));

        final ProgressDialog progressDialogZone = ProgressDialog.show(this, null, "Sincronizando zonas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_ZONE,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult Zone");
                progressDialogZone.dismiss();
            }
        }));

        final ProgressDialog progressDialogCampaign = ProgressDialog.show(this, null, "Sincronizando campañas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_CAMPAIGN,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult Campaign");
                progressDialogCampaign.dismiss();
            }
        }));

        final ProgressDialog progressDialogGeneralHeader = ProgressDialog.show(this, null, "Sincronizando campañas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_GENERAL_HEADER,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult GeneralHeader");
                progressDialogGeneralHeader.dismiss();
            }
        }));

        final ProgressDialog progressDialogGeneralQuestion = ProgressDialog.show(this, null, "Sincronizando campañas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_GENERAL_QUESTION,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult GeneralHeader");
                progressDialogGeneralQuestion.dismiss();
            }
        }));

        final ProgressDialog progressDialogTargetZone = ProgressDialog.show(this, null, "Sincronizando campañas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_TARGET_ZONE,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult TargetZone");
                progressDialogTargetZone.dismiss();
            }
        }));

        final ProgressDialog progressDialogSmsPhone = ProgressDialog.show(this, null, "Sincronizando campañas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_SMS_CELL_PHONE,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult SmsPhone");
                progressDialogSmsPhone.dismiss();

            }
        }));

        final ProgressDialog progressCampaignFooter = ProgressDialog.show(this, null, "Sincronizando campañas...", false);
        startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_CAMPAIGN_FOOTER,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult CampaignFooter");
                progressCampaignFooter.dismiss();

            }
        }));
    }

    private Intent createCallingIntent(String account, String action, SyncIntentServiceReceiver.Listener listener) {
        Log.d(TAG, "--action " + action);
        Intent intent = new Intent(this, SyncIntentService.class);
        SyncIntentServiceReceiver receiver = new SyncIntentServiceReceiver(new Handler());
        receiver.setListener(listener);
        intent.setAction(action);
        intent.putExtra(SyncIntentService.RECEIVER, receiver);
        intent.putExtra(SyncIntentService.ACCOUNT_PARAM, account);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_context, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.navigation_sync:
                Log.d(TAG, "--navigation_sync " );
                syncAll();
                break;
            case R.id.navigation_profile:
                Log.d(TAG, "--navigation_profile " );
                View menuItemView = findViewById(R.id.navigation_profile); // SAME ID AS MENU ID
                PopupMenu pMenu = new PopupMenu(this, menuItemView);
                pMenu.inflate(R.menu.profile_context);
                pMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item){
                        Log.d(TAG, "--logout " );
                        UserDAO userDAO = new UserDAO(MainActivity.this);
                        userDAO.logout();
                        goLaunchScreen();

                        return true;
                    }

                });
                pMenu.show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Ir a pantalla del login
     *
     */
    private void goLaunchScreen(){
        try {
            Intent intent = new Intent(this, LaunchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"--Error goLoginScreen");
        }
    }

}