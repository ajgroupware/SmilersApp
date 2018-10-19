package co.smilers.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.api.CampaignApi;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.Campaign;
import co.smilers.model.GeneralQuestionItem;
import co.smilers.model.Headquarter;
import co.smilers.model.QuestionItem;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.AppDataHelper;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.services.SyncIntentService;
import co.smilers.services.SyncIntentServiceReceiver;
import co.smilers.utils.ConstantsUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class FooterBooleanQuestionFragment extends Fragment {

    private static final String TAG = FooterBooleanQuestionFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_HEADQUARTER = "headquarter";
    public static final String ARG_ZONE = "zone";
    private static final String ARG_CAMPAIGN = "campaign";

    // TODO: Rename and change types of parameters
    private Long headquarter;
    private Long zone;
    private Long campaign;

    private boolean cancel = false;


    private TextView textviewDescription = null;
    private ConstraintLayout constraintQuestion = null;
    private ImageView imageViewGeneralLogo;

    public FooterBooleanQuestionFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FooterBooleanQuestionFragment newInstance(Long campaign, Long headquarter, Long zone) {
        FooterBooleanQuestionFragment fragment = new FooterBooleanQuestionFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CAMPAIGN, campaign);
        args.putLong(ARG_HEADQUARTER, headquarter);
        args.putLong(ARG_ZONE, zone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            campaign = getArguments().getLong(ARG_CAMPAIGN);
            headquarter = getArguments().getLong(ARG_HEADQUARTER);
            zone = getArguments().getLong(ARG_ZONE);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!cancel) {
                    try {
                        StartZoneActivity.showAlert = false;
                        if (((StartZoneActivity) getActivity()).navigationController != null) {
                            ((StartZoneActivity) getActivity()).navigationController.navigateToHeader(headquarter, zone);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "--Error " + e.getMessage());
                    }
                }
            }
        }, ConstantsUtil.WAIT_TIME_ANSWER);
    }

    public List<AnswerBooleanScore> answerScores = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_footer_boolean_question, container, false);
        textviewDescription = view.findViewById(R.id.textview_description);
        constraintQuestion = view.findViewById(R.id.constraint_question);

        ImageView excellent = view.findViewById(R.id.imageview_excellent);
        ImageView bad = view.findViewById(R.id.imageview_appalling);

        excellent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerBooleanScore answerScore = answerScores.get(0);

                    answerScore.setYesAnswer(1);
                    answerScore.setScore(5);
                    sendAnswer();
                    showThanks();
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showThanks();
                }
            }
        });

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerBooleanScore answerScore = answerScores.get(0);

                    answerScore.setNoAnswer(1);
                    answerScore.setScore(1);
                    sendAnswer();
                    showThanks();
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showThanks();
                }
            }
        });

        return view;
    }


    public void showThanks() {
        ((StartZoneActivity) getActivity()).navigationController.navigateToThanks(headquarter, zone);
    }

    private void sendAnswer() {
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();

        //Enviar calificación SI NO
        final CampaignApi campaignApi = new CampaignApi();
        campaignApi.addAnswerBooleanScore(loginUser.getAccount().getCode(), answerScores , getActivity(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "-- onResponse: " + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "-- Error: " + error.getMessage());
                        Bundle responseBundle = new Bundle();
                        responseBundle.putString("RESULT", "ERROR");

                        saveNoSyncBooleanAnswer();

                    }
                });

    }

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadData();
        super.onResume();
    }

    private void loadData() {
        try {
            CampaignDAO campaignDAO = new CampaignDAO(getActivity());
            UserDAO userDAO = new UserDAO(getActivity());
            User loginUser = userDAO.getUserLogin();

            //Cargar logo
            ParameterDAO parameterDAO = new ParameterDAO(getActivity());
            try {
                byte[] imageData = parameterDAO.getGeneralLogo(loginUser.getAccount().getCode());
                if (imageData != null && imageData.length > 0) {
                    Log.d(TAG, "--imageData " + imageData.length);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    imageViewGeneralLogo.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                Log.e(TAG, "--Error:  " + e.getMessage());
            }

            List<QuestionItem> questionItems = campaignDAO.getFooterQuestionItem(loginUser.getAccount().getCode());
            if (questionItems != null && questionItems.size() > 0) {

                QuestionItem questionItem = questionItems.get(0);
                Headquarter headquarter_ = parameterDAO.getHeadquarterByCode(loginUser.getAccount().getCode(), headquarter);
                Zone zone_ = parameterDAO.getZoneByCode(loginUser.getAccount().getCode(), zone);
                Campaign campaign_ = new Campaign();
                campaign_.setCode(questionItem.getCampaignCode());
                String description = questionItem.getTitle();
                if (description != null) {
                    description = description.replaceAll("&sede", headquarter_.getName());
                    textviewDescription.setText(description);
                    constraintQuestion.setBackgroundColor(Color.parseColor(questionItem.getDesignColor()));
                }


                //llenar respuesta
                AnswerBooleanScore answerScore = new AnswerBooleanScore();
                answerScore.setQuestionItem(questionItem);
                answerScore.setHeadquarter(headquarter_);
                answerScore.setCityCode(headquarter_.getCity().getCode());
                answerScore.setZone(zone_);
                answerScore.setCampaign(campaign_);

                answerScore.setYesAnswer(0);
                answerScore.setNoAnswer(0);
                answerScore.setScore(0);

                answerScores.add(answerScore);
            }

        } catch (Exception e) {
            Log.e(TAG, "--Error: " + e.getMessage());
        }
    }

    private void saveNoSyncBooleanAnswer() {
        Log.d(TAG, "-- saveNoSyncBooleanAnswer: " + StartZoneActivity.answerBooleanScores.size());
        SQLiteDatabase db = null;
        try {
            UserDAO userDAO = new UserDAO(getActivity());
            User user = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getActivity());
            AppDataHelper mDbHelper = new AppDataHelper(getActivity());
            db = mDbHelper.getWritableDatabase();
            Long idNext = campaignDAO.getNextIdAnswerBooleanScore(user.getAccount().getCode());
            for (AnswerBooleanScore answerScore : answerScores) {
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

}
