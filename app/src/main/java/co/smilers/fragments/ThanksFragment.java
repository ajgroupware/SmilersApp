package co.smilers.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.model.CampaignFooter;
import co.smilers.model.GeneralQuestionItem;
import co.smilers.model.Headquarter;
import co.smilers.model.User;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanksFragment extends Fragment {
    private static final String TAG = ThanksFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_HEADQUARTER = "headquarter";
    public static final String ARG_ZONE = "zone";

    // TODO: Rename and change types of parameters
    private Long headquarter;
    private Long zone;


    public ThanksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ThanksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThanksFragment newInstance(Long headquarter, Long zone) {
        ThanksFragment fragment = new ThanksFragment();
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
                try {
                    StartZoneActivity.showAlert = false;
                    if (((StartZoneActivity) getActivity()).navigationController != null) {
                        ((StartZoneActivity) getActivity()).navigationController.navigateToHeader(headquarter, zone);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

            }
        },6000);
    }

    private TextView textviewDescription;
    private TextView textviewTitle;
    ConstraintLayout constraintQuestion = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanks, container, false);
        constraintQuestion = view.findViewById(R.id.constraint_thanks);
        textviewTitle = (TextView) view.findViewById(R.id.textview_title);
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
            List<CampaignFooter> campaignFooters = campaignDAO.getCampaignFooter(loginUser.getAccount().getCode());
            if (campaignFooters != null && campaignFooters.size() > 0) {
                CampaignFooter campaignFooter = campaignFooters.get(0);

                String title = campaignFooter.getTitle();
                String description = campaignFooter.getDescription();
                if (description != null) {
                    textviewDescription.setText(description);
                    textviewTitle.setText(title);
                    constraintQuestion.setBackgroundColor(Color.parseColor(campaignFooter.getDesignColor()));
                }

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

}
