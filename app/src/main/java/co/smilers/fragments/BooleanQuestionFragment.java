package co.smilers.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
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

import java.util.ArrayList;
import java.util.List;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.model.AnswerBooleanScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.QuestionItem;
import co.smilers.model.RequestAssistance;
import co.smilers.model.SmsCellPhone;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.services.SyncIntentService;
import co.smilers.services.SyncIntentServiceReceiver;
import co.smilers.ui.DialogAssitanceFragment;
import co.smilers.ui.DialogSuccessAssitanceFragment;
import co.smilers.utils.ConstantsUtil;
import co.smilers.utils.SendEmailUtil;
import co.smilers.utils.SmsUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooleanQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooleanQuestionFragment extends Fragment {
    private static final String TAG = BooleanQuestionFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_HEADQUARTER = "headquarter";
    public static final String ARG_ZONE = "zone";
    private static final String ARG_CAMPAIGN = "campaign";
    private static final String ARG_SIZE_QUESTION = "sizeQuestion";
    private static final String ARG_INDEX_QUESTION = "indexQuestion";
    private static final String ARG_INDEX_BOOLEAN_QUESTION = "indexBooleanQuestion";
    private static final String ARG_ACTUAL_QUESTION = "actualQuestion";

    // TODO: Rename and change types of parameters
    private Long campaign;
    private Integer sizeQuestion;
    private Integer actualQuestion;
    private Integer indexQuestion;
    private Integer indexBooleanQuestion;
    private Long headquarter;
    private Long zone;

    private boolean cancel = false;

    private ImageView imageViewGeneralLogo;

    public BooleanQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BooleanQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BooleanQuestionFragment newInstance(Long campaign, Integer sizeQuestion, Integer actualQuestion, Integer indexQuestion, Integer indexBooleanQuestion, Long headquarter, Long zone) {
        BooleanQuestionFragment fragment = new BooleanQuestionFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CAMPAIGN, campaign);
        args.putInt(ARG_SIZE_QUESTION, sizeQuestion);
        args.putInt(ARG_ACTUAL_QUESTION, actualQuestion);
        args.putInt(ARG_INDEX_QUESTION, indexQuestion);
        args.putInt(ARG_INDEX_BOOLEAN_QUESTION, indexBooleanQuestion);
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
            sizeQuestion = getArguments().getInt(ARG_SIZE_QUESTION);
            actualQuestion = getArguments().getInt(ARG_ACTUAL_QUESTION);
            indexQuestion = getArguments().getInt(ARG_INDEX_QUESTION);
            indexBooleanQuestion = getArguments().getInt(ARG_INDEX_BOOLEAN_QUESTION);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boolean_question, container, false);
        imageViewGeneralLogo = (ImageView) view.findViewById(R.id.ImageView_general_logo);
        //Verificar si hay preguntas cargadas
        if (StartZoneActivity.answerBooleanScores!= null && StartZoneActivity.answerBooleanScores.size() > 0) {
            TextView textviewQuestionNumber = view.findViewById(R.id.textview_question_number);
            TextView textviewQuestionCount = view.findViewById(R.id.textview_question_count);
            TextView textviewDescription = view.findViewById(R.id.textview_description);


            AnswerBooleanScore answerScore = StartZoneActivity.answerBooleanScores.get(indexBooleanQuestion.intValue());
            answerScore.setCityCode(answerScore.getHeadquarter().getCity() != null ? answerScore.getHeadquarter().getCity().getCode() : null);
            ConstraintLayout constraintQuestion = view.findViewById(R.id.constraint_question);

            int index = actualQuestion.intValue() + 1;
            textviewQuestionNumber.setText("" + index);
            textviewQuestionCount.setText("" + sizeQuestion.intValue());
            textviewDescription.setText(answerScore.getQuestionItem().getTitle());
            constraintQuestion.setBackgroundColor(Color.parseColor(answerScore.getQuestionItem().getDesignColor()));


            ImageView excellent = view.findViewById(R.id.imageview_excellent);
            ImageView bad = view.findViewById(R.id.imageview_appalling);

            excellent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerScore.setYesAnswer(1);
                    answerScore.setScore(5);
                    if (sizeQuestion.intValue() == actualQuestion.intValue() + 1) {
                        sendAnswer();
                        if (StartZoneActivity.showAlert) {
                            showAlertAssistance();
                        } else {
                            endQuestion();
                        }
                    } else {
                        showNext();
                    }
                }
            });

            bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StartZoneActivity.showAlert = true;
                    answerScore.setNoAnswer(1);
                    answerScore.setScore(1);
                    if (sizeQuestion.intValue() == actualQuestion.intValue() + 1) {
                        sendAnswer();
                        if (StartZoneActivity.showAlert) {
                            showAlertAssistance();
                        } else {
                            endQuestion();
                        }
                    } else {
                        showNext();
                        //Se muestra mensaje al final

                    }
                }
            });

        }

        return view;
    }

    public void showNext() {
        cancel = true;
        soundTouch();
        ((StartZoneActivity) getActivity()).navigationController.navigateToQuestion(headquarter, zone, campaign, sizeQuestion, actualQuestion.intValue() + 1, indexQuestion, indexBooleanQuestion + 1);

    }

    public void showThanks() {
        ((StartZoneActivity) getActivity()).navigationController.navigateToThanks(headquarter, zone);
    }

    public void endQuestion() {
        //Verificar si hay preguntas de finalización
        CampaignDAO campaignDAO = new CampaignDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        List<QuestionItem> questionItems = campaignDAO.getFooterQuestionItem(loginUser.getAccount().getCode());
        if (questionItems != null && questionItems.size() > 0) {
            ((StartZoneActivity) getActivity()).navigationController.navigateToFooterQuestion(questionItems.get(0), headquarter, zone);
        } else {
            showThanks();
        }

    }

    public void showHeader() {
        ((StartZoneActivity) getActivity()).navigationController.navigateToHeader(headquarter, zone);
    }

    private void sendAnswer() {
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        //Enviar calificación
        getActivity().startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_ANSWER,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult Answer");
            }
        }));

        //Enviar calificación SI NO
        getActivity().startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_BOOLEAN_ANSWER,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult BooleanAnswer");
            }
        }));

    }

    private Intent createCallingIntent(String account, String action, SyncIntentServiceReceiver.Listener listener) {
        Log.d(TAG, "--action " + action);
        Intent intent = new Intent(getActivity(), SyncIntentService.class);
        SyncIntentServiceReceiver receiver = new SyncIntentServiceReceiver(new Handler());
        receiver.setListener(listener);
        intent.setAction(action);
        intent.putExtra(SyncIntentService.RECEIVER, receiver);
        intent.putExtra(SyncIntentService.ACCOUNT_PARAM, account);
        return intent;
    }

    private void soundTouch() {
        MediaPlayer mPlayer = MediaPlayer.create(getActivity(), R.raw.stapler_sound);
        mPlayer.start();
    }

    public void showAlertAssistance() {
        ParameterDAO parameterDAO = new ParameterDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();

        String sendSms = parameterDAO.getGeneralSettingParameterValue(loginUser.getAccount().getCode(), "SEND_SMS");
        String sendEmail = parameterDAO.getGeneralSettingParameterValue(loginUser.getAccount().getCode(), "SEND_EMAIL");
        //Verificar si la cuenta genera alerta
        if ("true".equals(sendSms) || "true".equals(sendEmail)) {
            ((StartZoneActivity)getActivity()).delayedHide(0);
            DialogAssitanceFragment dialogFragment = DialogAssitanceFragment.newInstance(new DialogAssitanceFragment.OnButtonClickDialogListener() {
                @Override
                public void onAcceptClick(DialogAssitanceFragment dialogFragment) {
                    sendAlert();
                    showAlertSuccessAssistance();
                }

                @Override
                public void onCancelClick(DialogAssitanceFragment dialogFragment) {
                    endQuestion();
                }
            });
            dialogFragment.show(getFragmentManager(), DialogAssitanceFragment.TAG);
        } else {
            sendAlert();
            endQuestion();
        }

    }

    public void showAlertSuccessAssistance() {
        ((StartZoneActivity)getActivity()).delayedHide(0);
        final DialogSuccessAssitanceFragment dialogFragment = DialogSuccessAssitanceFragment.newInstance(new DialogSuccessAssitanceFragment.OnButtonClickDialogListener() {
            @Override
            public void onAcceptClick(DialogSuccessAssitanceFragment dialogFragment) {

            }

            @Override
            public void onCancelClick(DialogSuccessAssitanceFragment dialogFragment) {

            }
        });
        dialogFragment.show(getFragmentManager(), DialogAssitanceFragment.TAG);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    dialogFragment.closeDialog();
                    endQuestion();
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                }


            }
        }, 3000);

    }


    private void sendAlert() {
        ParameterDAO parameterDAO = new ParameterDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();

        String sendSms = parameterDAO.getGeneralSettingParameterValue(loginUser.getAccount().getCode(), "SEND_SMS");
        String sendEmail = parameterDAO.getGeneralSettingParameterValue(loginUser.getAccount().getCode(), "SEND_EMAIL");

        if ("true".equals(sendSms) && "true".equals(sendEmail)) {
            Log.d(TAG, "--send Alert! ");
            List<SmsCellPhone> smsCellPhones = parameterDAO.getSmsCellPhone(loginUser.getAccount().getCode(), headquarter);
            if (smsCellPhones != null) {
                List<SmsCellPhone> smsCellPhonesZones = new ArrayList<>();
                List<SmsCellPhone> smsCellPhonesCampaign = new ArrayList<>();
                List<SmsCellPhone> smsCellPhonesHeadquarter = new ArrayList<>();
                for (SmsCellPhone smsCellPhone: smsCellPhones) {
                    if (smsCellPhone.getCampaignCode() != null && smsCellPhone.getCampaignCode().intValue() > 0 && smsCellPhone.getCampaignCode().intValue() == campaign.intValue()) {
                        smsCellPhonesCampaign.add(smsCellPhone);
                    } else if (smsCellPhone.getZoneCode() != null && smsCellPhone.getZoneCode().intValue() > 0 && smsCellPhone.getZoneCode().intValue() == zone.intValue()) {
                        smsCellPhonesCampaign.add(smsCellPhone);
                    } else {
                        smsCellPhonesHeadquarter.add(smsCellPhone);
                    }
                }

                Zone zone_ = parameterDAO.getZoneByCode(loginUser.getAccount().getCode(), zone);
                if (smsCellPhonesCampaign.size() > 0) {
                    sendAlert(smsCellPhonesCampaign, zone_, sendSms, sendEmail);
                } else if (smsCellPhonesZones.size() > 0) {
                    sendAlert(smsCellPhonesZones, zone_, sendSms, sendEmail);
                } else {
                    sendAlert(smsCellPhonesHeadquarter, zone_, sendSms, sendEmail);
                }

            }

            //Toast.makeText(getActivity(), "En unos momentos estaremos contigo, gracias", Toast.LENGTH_LONG).show();

            try { //Enviar solicitud de asistencia
                List<RequestAssistance> requestAssistances = new ArrayList<>();
                RequestAssistance requestAssistance = new RequestAssistance();

                requestAssistance.setHeadquarter(StartZoneActivity.answerScores.get(actualQuestion.intValue()).getHeadquarter());
                requestAssistance.setZone(StartZoneActivity.answerScores.get(actualQuestion.intValue()).getZone());
                requestAssistance.setQuestionItem(StartZoneActivity.answerScores.get(actualQuestion.intValue()).getQuestionItem());
                requestAssistance.setCityCode(StartZoneActivity.answerScores.get(actualQuestion.intValue()).getHeadquarter().getCity().getCode());

                requestAssistances.add(requestAssistance);
                StartZoneActivity.requestAssistances = requestAssistances;
                getActivity().startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_REQUEST_ASSISTANCE,  new SyncIntentServiceReceiver.Listener() {
                    @Override
                    public void onReceiveResult(int resultCode, Bundle resultData) {
                        Log.d(TAG, "--onReceiveResult requestAssistance");
                        StartZoneActivity.requestAssistances = new ArrayList<>();

                    }
                }));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAlert(List<SmsCellPhone> smsCellPhones, Zone zone, String sendSms, String sendEmail) {
        for (SmsCellPhone smsCellPhone: smsCellPhones) {
            Log.d(TAG, "--smsCellPhone " + smsCellPhone.getCellPhoneNumber());
            Log.d(TAG, "--email " + smsCellPhone.getEmail());
            String message = "Alerta de Servicio negativa en " + zone.getHeadquarter().getName() + ", " + zone.getName();
            if ("true".equals(sendSms)) {
                SmsUtil.sendSms(smsCellPhone.getCellPhoneNumber(), message, getActivity());
            }

            if ("true".equals(sendEmail)) {
                SendEmailUtil.sendTextEmail("Alerta de servicio negativo", message, smsCellPhone.getEmail());
            }
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadData();
        super.onResume();
    }

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
