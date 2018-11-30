package co.smilers.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.Campaign;
import co.smilers.model.GeneralQuestionItem;
import co.smilers.model.Headquarter;
import co.smilers.model.QuestionItem;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.utils.ConstantsUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCampaignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCampaignFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = SelectCampaignFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_HEADQUARTER = "headquarter";
    public static final String ARG_ZONE = "zone";

    // TODO: Rename and change types of parameters
    private Long headquarter;
    private Long zone;


    private boolean cancel = false;

    public SelectCampaignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectCampaignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectCampaignFragment newInstance(Long headquarter, Long zone) {
        SelectCampaignFragment fragment = new SelectCampaignFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_HEADQUARTER, headquarter);
        args.putLong(ARG_ZONE, zone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
                        Log.e(TAG, "--Error: " + e.getMessage());
                    }
                }
            }
        }, ConstantsUtil.WAIT_TIME_ANSWER);
    }

    TextView textviewDescription = null;
    LinearLayout linearLayout = null;
    LayoutInflater inflater = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_campaign, container, false);
        imageViewGeneralLogo = (ImageView) view.findViewById(R.id.ImageView_general_logo);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        textviewDescription = view.findViewById(R.id.textview_description);
        this.inflater = inflater;
        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadCampaign();
        loadData();
        super.onResume();
    }

    private void loadCampaign() {
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        CampaignDAO campaignDAO = new CampaignDAO(getActivity());
        ParameterDAO parameterDAO = new ParameterDAO(getActivity());
        Headquarter headquarter_ = parameterDAO.getHeadquarterByCode(loginUser.getAccount().getCode(), headquarter);
        textviewDescription.setText("¿Cuál de estas secciones de tu " + headquarter_.getName() + " quieres evaluar?");
        if ("velez_6".equals(loginUser.getAccount().getCode())) { // Solo para veles
            textviewDescription.setText("¿Cuál de estas secciones quieres evaluar?");
        }
        List<Campaign> list = campaignDAO.getCampaign(loginUser.getAccount().getCode());
        //LayoutInflater inflater = LayoutInflater.from(getActivity());
        if (list != null) {

            if (list.size() > 1) {
                int sections = list.size()/2 + list.size() % 2;
                List<LinearLayout> linearLayouts = new ArrayList<>();
                //LayoutInflater inflater = LayoutInflater.from(getActivity());

                for (int i = 0; i < sections; i++) {
                    LinearLayout campaignLayout = (LinearLayout) inflater.inflate(R.layout.component_campaign_layout, null);
                    //LinearLayout campaignLayout = (LinearLayout) inflater.inflate(R.layout.component_campaign_layout, linearLayout, true);
                    campaignLayout.setId(i+1);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            2.0f
                    );
                    params.setMargins(8, 2, 8, 2);
                    campaignLayout.setLayoutParams(params);

                    linearLayouts.add(campaignLayout);

                }

                int j = 0;
                for (int i = 0; i < list.size(); i++) {
                    Button campaignButton = (Button) inflater.inflate(R.layout.component_campaign_button, null);
                    //LinearLayout linearLayoutButton = linearLayouts.get(j);
                    //Button campaignButton = (Button) inflater.inflate(R.layout.component_campaign_button, linearLayoutButton, true);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            1.0f
                    );
                    params.setMargins(2, 2, 2, 2);
                    campaignButton.setLayoutParams(params);

                    campaignButton.setText(list.get(i).getTitle());
                    campaignButton.setId(list.get(i).getCode().intValue());
                    campaignButton.setOnClickListener(this);
                    linearLayouts.get(j).addView(campaignButton);
                    if (i%2 == 0) {
                    } else {
                        j++;
                    }
                }

                for (LinearLayout linearLayoutCam : linearLayouts) {
                    linearLayout.addView(linearLayoutCam);
                }
            } else if (list.size() == 1) { // Si solo hay una sola campaña, pasar a las preguntas

                Campaign campaign = campaignDAO.getCampaignByCode(loginUser.getAccount().getCode(), list.get(0).getCode());
                Zone zone_ = parameterDAO.getZoneByCode(loginUser.getAccount().getCode(), zone);
                StartZoneActivity.questionItems = campaignDAO.getQuestionItemByCampaign(loginUser.getAccount().getCode(), list.get(0).getCode());

                //Fecha actual sin Zona horaria
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                String currentDate = dateFormat.format(new Date());

                for (QuestionItem questionItem : StartZoneActivity.questionItems) {
                    if ("CSAT".equals(questionItem.getQuestionType())) {
                        AnswerScore answerScore = new AnswerScore();
                        answerScore.setQuestionItem(questionItem);
                        answerScore.setCampaign(campaign);
                        answerScore.setHeadquarter(headquarter_);
                        answerScore.setZone(zone_);

                        answerScore.setExcellent(0);
                        answerScore.setGood(0);
                        answerScore.setModerate(0);
                        answerScore.setBad(0);
                        answerScore.setPoor(0);
                        answerScore.setScore(0);

                        answerScore.setRegistrationDate(currentDate);

                        StartZoneActivity.answerScores.add(answerScore);
                    } else if ("BOOLEAN".equals(questionItem.getQuestionType())) { // si es pregunta de SI NO
                        AnswerBooleanScore  answerScore = new AnswerBooleanScore();
                        answerScore.setQuestionItem(questionItem);
                        answerScore.setCampaign(campaign);
                        answerScore.setHeadquarter(headquarter_);
                        answerScore.setZone(zone_);

                        answerScore.setYesAnswer(0);
                        answerScore.setNoAnswer(0);
                        answerScore.setScore(0);

                        answerScore.setRegistrationDate(currentDate);

                        StartZoneActivity.answerBooleanScores.add(answerScore);
                    }

                }
                cancel = true;
                ((StartZoneActivity) getActivity()).navigationController.navigateToQuestion(headquarter, zone, list.get(0).getCode(), StartZoneActivity.questionItems.size(), 0, 0, 0);

            } else {
                ((AppCompatActivity)getActivity()).finish();
            }

        }
    }

    @Override
    public void onClick(View view) {
        try {
            Button campaignButton = (Button) view;
            Log.d(TAG, "--campaignButton " + campaignButton.getId());
            StartZoneActivity.answerScores = new ArrayList<>();
            UserDAO userDAO = new UserDAO(getActivity());
            User loginUser = userDAO.getUserLogin();
            CampaignDAO campaignDAO = new CampaignDAO(getActivity());
            Campaign campaign = campaignDAO.getCampaignByCode(loginUser.getAccount().getCode(), (long) campaignButton.getId());
            ParameterDAO parameterDAO = new ParameterDAO(getActivity());
            Headquarter headquarter_ = parameterDAO.getHeadquarterByCode(loginUser.getAccount().getCode(), headquarter);
            Zone zone_ = parameterDAO.getZoneByCode(loginUser.getAccount().getCode(), zone);
            StartZoneActivity.questionItems = campaignDAO.getQuestionItemByCampaign(loginUser.getAccount().getCode(), (long) campaignButton.getId());

            for (QuestionItem questionItem : StartZoneActivity.questionItems) {
                if ("CSAT".equals(questionItem.getQuestionType())) {
                    AnswerScore answerScore = new AnswerScore();
                    answerScore.setQuestionItem(questionItem);
                    answerScore.setCampaign(campaign);
                    answerScore.setHeadquarter(headquarter_);
                    answerScore.setZone(zone_);

                    answerScore.setExcellent(0);
                    answerScore.setGood(0);
                    answerScore.setModerate(0);
                    answerScore.setBad(0);
                    answerScore.setPoor(0);
                    answerScore.setScore(0);


                    StartZoneActivity.answerScores.add(answerScore);
                } else if ("BOOLEAN".equals(questionItem.getQuestionType())) { // si es pregunta de SI NO
                    AnswerBooleanScore  answerScore = new AnswerBooleanScore();
                    answerScore.setQuestionItem(questionItem);
                    answerScore.setCampaign(campaign);
                    answerScore.setHeadquarter(headquarter_);
                    answerScore.setZone(zone_);

                    answerScore.setYesAnswer(0);
                    answerScore.setNoAnswer(0);
                    answerScore.setScore(0);

                    StartZoneActivity.answerBooleanScores.add(answerScore);
                }
            }
            cancel = true;
            ((StartZoneActivity) getActivity()).navigationController.navigateToQuestion(headquarter, zone, (long) campaignButton.getId(), StartZoneActivity.questionItems.size(), 0, 0, 0);

        } catch (Exception e) {
            Log.e(TAG, "--Error: " + e.getMessage());
        }
    }

    private ImageView imageViewGeneralLogo;

    private void loadData() {
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

    }
}
