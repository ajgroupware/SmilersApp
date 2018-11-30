package co.smilers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import co.smilers.fragments.GeneralHeaderFragment;
import co.smilers.fragments.HeadquarterFragment;
import co.smilers.fragments.QuestionFragment;
import co.smilers.fragments.SelectCampaignFragment;
import co.smilers.fragments.ThanksFragment;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.QuestionItem;
import co.smilers.model.RequestAssistance;
import co.smilers.model.User;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartZoneActivity extends AppCompatActivity {

    public String mCurrentFragment;

    public static final String HEADER_FRAGMENT_TAG = "HEADER_FRAGMENT";
    public static final String HEADER_QUESTION_FRAGMENT_TAG = "HEADER_QUESTION_FRAGMENT";
    public static final String QUESTION_FRAGMENT_TAG = "QUESTION_FRAGMENT";
    public static final String THANKS_FRAGMENT_TAG = "THANKS_FRAGMENT";
    public static final String SELECT_FRAGMENT_TAG = "THANKS_FRAGMENT";

    private static final String TAG = StartZoneActivity.class.getSimpleName();
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    public NavigationController navigationController;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            Log.d(TAG, "--mHidePart2Runnable " );
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    //private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            //mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "--mHideRunnable " );
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "--onCreate " );
        setContentView(R.layout.activity_start_zone);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mVisible = true;
        //mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        try {
            registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_ON));
            registerReceiver(mybroadcast, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        } catch (Exception e) {
            Log.e(TAG, "--Error: " + e.getMessage());
        }

        //GeneralHeaderFragment generalHeaderFragment = GeneralHeaderFragment.newInstance("", "");
        //ThanksFragment thanksFragment = ThanksFragment.newInstance("", "");


        navigationController = new NavigationController(this);
        questionItems = new ArrayList<>();
        answerScores = new ArrayList<>();
        answerBooleanScores = new ArrayList<>();
        answerGeneralScore = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            Long headquarter = intent.getLongExtra(SelectCampaignFragment.ARG_HEADQUARTER, 0);
            Long zone = intent.getLongExtra(SelectCampaignFragment.ARG_ZONE, 0);
            //SelectCampaignFragment selectCampaignFragment = SelectCampaignFragment.newInstance(headquarter, zone);
            mCurrentFragment = HEADER_FRAGMENT_TAG;
            GeneralHeaderFragment generalHeaderFragment = GeneralHeaderFragment.newInstance(headquarter, zone);
            getSupportFragmentManager().beginTransaction().replace(R.id.fullscreen_content, generalHeaderFragment).commit();
        }

        //Temporalmente
        //Suscribir la aplicación al FCM topic
        //FirebaseMessaging.getInstance().subscribeToTopic("smilersConfig");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(TAG, "--onPostCreate " );
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    private void hide() {
        Log.d(TAG, "--hide " );
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    public void delayedHide(int delayMillis) {
        Log.d(TAG, "--delayedHide " );
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Do nothing or catch the keys you want to block
        Log.d(TAG, "--onKeyDown " + keyCode);
        Log.d(TAG, "--mCurrentFragment " + mCurrentFragment);
        if (HEADER_FRAGMENT_TAG.equals(mCurrentFragment) || SELECT_FRAGMENT_TAG.equals(mCurrentFragment) || QUESTION_FRAGMENT_TAG.equals(mCurrentFragment)) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ingrese pin de seguridad");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     String pin = input.getText().toString();
                     if ("1234".equals(pin)) {
                         ParameterDAO parameterDAO = new ParameterDAO(StartZoneActivity.this);
                         UserDAO userDAO = new UserDAO(StartZoneActivity.this);
                         User user = userDAO.getUserLogin();
                         parameterDAO.deleteCurrentConfig(user.getAccount().getCode()); // Ingresar configuración

                         finish();
                     }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        }

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "--onPause " );
        //if(password!=storedPassword) {
            //Lockscreen activity shouldn't ever be escaped without the right password!
            //Return to launcher without root!
            /*
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            */
        //}
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "--onResume " );

        hide();
    }

    //Create a receiver for screen-on/screen-off
    BroadcastReceiver mybroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                //Show lock-screen
                Log.d(TAG, "--lock-screen " );
            }
            else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                //Also show lock-screen, to remove flicker/delay when screen on?
                Log.d(TAG, "-- " );
            }

        }
    };

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "--onAttachedToWindow " );
        //this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        super.onAttachedToWindow();
    }

    public static boolean showAlert = false;
    public static List<QuestionItem> questionItems;
    public static List<AnswerScore> answerScores;
    public static List<AnswerBooleanScore> answerBooleanScores;
    public static List<AnswerGeneralScore> answerGeneralScore;
    public static List<AnswerScore> savedAnswerScores;
    public static List<AnswerGeneralScore> savedAnswerGeneralScore;
    public static List<AnswerBooleanScore> savedAnswerBooleanScore;
    public static List<RequestAssistance> requestAssistances;
}
