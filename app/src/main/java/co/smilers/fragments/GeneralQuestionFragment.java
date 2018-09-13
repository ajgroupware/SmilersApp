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
import android.support.v7.app.AppCompatActivity;
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
import co.smilers.model.AnswerGeneralScore;
import co.smilers.model.AnswerScore;
import co.smilers.model.Campaign;
import co.smilers.model.GeneralQuestionItem;
import co.smilers.model.Headquarter;
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
import co.smilers.utils.SmsUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneralQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralQuestionFragment extends Fragment {
    private static final String TAG = GeneralQuestionFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_HEADQUARTER = "headquarter";
    public static final String ARG_ZONE = "zone";

    // TODO: Rename and change types of parameters
    private Long headquarter;
    private Long zone;

    private boolean cancel = false;

    public GeneralQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralQuestionFragment newInstance(Long headquarter, Long zone) {
        GeneralQuestionFragment fragment = new GeneralQuestionFragment();
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

    private TextView textviewDescription = null;
    private ConstraintLayout constraintQuestion = null;
    private ImageView imageViewGeneralLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_question, container, false);
        textviewDescription = view.findViewById(R.id.textview_description);
        constraintQuestion = view.findViewById(R.id.constraint_question);


        ImageView excellent = view.findViewById(R.id.imageview_excellent);
        ImageView good = view.findViewById(R.id.imageview_good);
        ImageView regular = view.findViewById(R.id.imageview_regular);
        ImageView bad = view.findViewById(R.id.imageview_bad);
        ImageView appalling = view.findViewById(R.id.imageview_appalling);
        imageViewGeneralLogo = (ImageView) view.findViewById(R.id.ImageView_general_logo);



        excellent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerGeneralScore answerScore = StartZoneActivity.answerGeneralScore.get(0);

                    answerScore.setExcellent(1);
                    answerScore.setScore(5);
                    sendAnswer();
                    showNext();
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showNext();
                }
            }
        });

        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerGeneralScore answerScore = StartZoneActivity.answerGeneralScore.get(0);

                    answerScore.setGood(1);
                    answerScore.setScore(4);
                    sendAnswer();
                    showNext();
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showNext();
                }
            }
        });

        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerGeneralScore answerScore = StartZoneActivity.answerGeneralScore.get(0);

                    answerScore.setModerate(1);
                    answerScore.setScore(3);
                    StartZoneActivity.showAlert = true;
                    sendAnswer();
                    showNext();

                    //Se muestra mensaje al final
                    /*
                    if (StartZoneActivity.showAlert) {
                        showNext();
                    } else {
                        StartZoneActivity.showAlert = true;
                        showAlertAssistance();
                    }
                    */
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showNext();
                }
            }
        });

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerGeneralScore answerScore = StartZoneActivity.answerGeneralScore.get(0);

                    answerScore.setBad(1);
                    answerScore.setScore(2);
                    StartZoneActivity.showAlert = true;
                    sendAnswer();
                    showNext();

                    //Se muestra mensaje al final
                    /*
                    if (StartZoneActivity.showAlert) {
                        showNext();
                    } else {
                        StartZoneActivity.showAlert = true;
                        showAlertAssistance();
                    }
                    */
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showNext();
                }
            }
        });

        appalling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AnswerGeneralScore answerScore = StartZoneActivity.answerGeneralScore.get(0);

                    answerScore.setPoor(1);
                    answerScore.setScore(1);
                    StartZoneActivity.showAlert = true;
                    sendAnswer();
                    showNext();

                    //Se muestra mensaje al final
                /*
                if (StartZoneActivity.showAlert) {
                    showNext();
                } else {
                    StartZoneActivity.showAlert = true;
                    showAlertAssistance();
                }
                */
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                    StartZoneActivity.showAlert = true;
                    showNext();
                }

            }
        });

        return view;
    }


    public void showNext() {
        cancel = true;
        soundTouch();
        try {
            ((StartZoneActivity) getActivity()).navigationController.navigateToSelectCampaign(headquarter, zone);
        } catch (Exception e) {
            Log.e(TAG, "--Error " + e.getMessage());
        }

    }

    public void showSelectCampaign() {
        cancel = true;
        try {
            ((StartZoneActivity) getActivity()).navigationController.navigateToSelectCampaign(headquarter, zone);
        } catch (Exception e) {
            Log.e(TAG, "--Error " + e.getMessage());
        }

    }

    private void sendAnswer() {
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        getActivity().startService(createCallingIntent(loginUser.getAccount().getCode(), SyncIntentService.ACTION_SYNC_GENERAL_ANSWER,  new SyncIntentServiceReceiver.Listener() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d(TAG, "--onReceiveResult Answer");
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
        try {
            MediaPlayer mPlayer = MediaPlayer.create(getActivity(), R.raw.stapler_sound);
            mPlayer.start();
        } catch (Exception e) {
            Log.e(TAG, "--Error: " + e.getMessage());
        }

    }

    //Se muestra mensaje al final
    /*
    public void showAlertAssistance() {
        ((StartZoneActivity)getActivity()).delayedHide(0);
        DialogAssitanceFragment dialogFragment = DialogAssitanceFragment.newInstance(new DialogAssitanceFragment.OnButtonClickDialogListener() {
            @Override
            public void onAcceptClick(DialogAssitanceFragment dialogFragment) {
                sendSms();
                showAlertSuccessAssistance();
                showNext();
            }

            @Override
            public void onCancelClick(DialogAssitanceFragment dialogFragment) {
                showNext();
            }
        });
        dialogFragment.show(getFragmentManager(), DialogAssitanceFragment.TAG);
    }
    */

    /*
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
                } catch (Exception e) {
                    Log.e(TAG, "--Error: " + e.getMessage());
                }


            }
        }, 3000);

    }
    */

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadData();
        super.onResume();
    }

    private void loadData() {
        StartZoneActivity.answerGeneralScore = new ArrayList<>();

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

            List<GeneralQuestionItem> generalQuestionItems = campaignDAO.getGeneralQuestionItem(loginUser.getAccount().getCode());
            if (generalQuestionItems != null && generalQuestionItems.size() > 0) {
                GeneralQuestionItem generalQuestionItem = generalQuestionItems.get(0);
                Headquarter headquarter_ = parameterDAO.getHeadquarterByCode(loginUser.getAccount().getCode(), headquarter);
                Zone zone_ = parameterDAO.getZoneByCode(loginUser.getAccount().getCode(), zone);
                String description = generalQuestionItem.getTitle();
                if (description != null) {
                    description = description.replaceAll("&sede", headquarter_.getName());
                    textviewDescription.setText(description);
                    constraintQuestion.setBackgroundColor(Color.parseColor(generalQuestionItem.getDesignColor()));
                }


                //llenar respuesta
                AnswerGeneralScore answerScore = new AnswerGeneralScore();
                QuestionItem questionItem = new QuestionItem();
                questionItem.setCode(generalQuestionItem.getCode());
                answerScore.setQuestionItem(questionItem);
                answerScore.setHeadquarter(headquarter_);
                answerScore.setZone(zone_);

                answerScore.setExcellent(0);
                answerScore.setGood(0);
                answerScore.setModerate(0);
                answerScore.setBad(0);
                answerScore.setPoor(0);
                answerScore.setScore(0);


                StartZoneActivity.answerGeneralScore.add(answerScore);

            } else {
                // No hay pregunta general que mostrar
                showSelectCampaign();
            }
        } catch (Exception e) {
            Log.e(TAG, "--Error: " + e.getMessage());
        }
    }

    private void sendAlert() {
        ParameterDAO parameterDAO = new ParameterDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();

        String sendSms = parameterDAO.getGeneralSettingParameterValue(loginUser.getAccount().getCode(), "");
        String sendEmail = parameterDAO.getGeneralSettingParameterValue(loginUser.getAccount().getCode(), "");

        List<SmsCellPhone> smsCellPhones = parameterDAO.getSmsCellPhone(loginUser.getAccount().getCode(), headquarter);
        if (smsCellPhones != null) {
            List<SmsCellPhone> smsCellPhonesZones = new ArrayList<>();
            List<SmsCellPhone> smsCellPhonesHeadquarter = new ArrayList<>();
            for (SmsCellPhone smsCellPhone: smsCellPhones) {
                if (smsCellPhone.getZoneCode() != null && smsCellPhone.getZoneCode().intValue() > 0 && smsCellPhone.getZoneCode().intValue() == zone.intValue()) {
                    smsCellPhonesZones.add(smsCellPhone);
                } else {
                    smsCellPhonesHeadquarter.add(smsCellPhone);
                }
            }

            Zone zone_ = parameterDAO.getZoneByCode(loginUser.getAccount().getCode(), zone);
            if (smsCellPhonesZones.size() > 0) {
                sendSms(smsCellPhonesZones, zone_);
            } else {
                sendSms(smsCellPhonesHeadquarter, zone_);
            }

        }
    }

    private void sendSms(List<SmsCellPhone> smsCellPhones, Zone zone) {
        for (SmsCellPhone smsCellPhone: smsCellPhones) {
            SmsUtil.sendSms(smsCellPhone.getCellPhoneNumber(), "Alerta de Servicio negativa en " + zone.getHeadquarter().getName() + ", " + zone.getName(), getActivity());
        }
    }



}
