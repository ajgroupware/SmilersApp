package co.smilers.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.model.GeneralHeader;
import co.smilers.model.Headquarter;
import co.smilers.model.User;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GeneralHeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralHeaderFragment extends Fragment {
    private final static String TAG = GeneralHeaderFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_HEADQUARTER = "headquarter";
    public static final String ARG_ZONE = "zone";

    // TODO: Rename and change types of parameters
    private Long headquarter;
    private Long zone;


    public GeneralHeaderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GeneralHeaderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralHeaderFragment newInstance(Long headquarter, Long zone) {
        GeneralHeaderFragment fragment = new GeneralHeaderFragment();
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
    }

    private TextView textviewDescription;
    ConstraintLayout constraintQuestion = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_header, container, false);
        Button nextButton = (Button) view.findViewById(R.id.button_option_next);
        constraintQuestion = view.findViewById(R.id.constraint_header);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StartZoneActivity) getActivity()).navigationController.navigateToHeaderQuestion(headquarter, zone);
            }
        });

        textviewDescription = (TextView) view.findViewById(R.id.textview_description);

        return view;
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
            List<GeneralHeader> generalHeaders = campaignDAO.getGeneralHeader(loginUser.getAccount().getCode());
            if (generalHeaders != null && generalHeaders.size() > 0) {
                ParameterDAO parameterDAO = new ParameterDAO(getActivity());
                Headquarter headquarter_ = parameterDAO.getHeadquarterByCode(loginUser.getAccount().getCode(), headquarter);
                GeneralHeader generalHeader = generalHeaders.get(0);

                String description = generalHeader.getTitle();
                if (description != null) {
                    description = description.replaceAll("&sede", headquarter_.getName());
                    textviewDescription.setText(description);
                    constraintQuestion.setBackgroundColor(Color.parseColor(generalHeader.getDesignColor()));
                }

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

}
