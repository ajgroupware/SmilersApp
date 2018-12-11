package co.smilers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.smilers.R;
import co.smilers.adapters.CampaignListAdapter;
import co.smilers.adapters.ZoneListAdapter;
import co.smilers.model.Campaign;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.daos.CampaignDAO;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CampaignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CampaignFragment extends Fragment {
    private final static String TAG = CampaignFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_ZONE = "zone";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Long zone;
    private String mParam2;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public CampaignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param zone Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CampaignFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CampaignFragment newInstance(Long zone, String param2) {
        CampaignFragment fragment = new CampaignFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ZONE, zone != null ? zone : 0L);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            zone = getArguments().getLong(ARG_ZONE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_campaign, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //Establecer sub titulo
        final String title = getActivity().getString(R.string.title_campaign);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(title);
        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadCampaign();
        super.onResume();
    }

    private void loadCampaign() {
        CampaignDAO campaignDAO = new CampaignDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        List<Campaign> list = new ArrayList<>();
        if (zone != null && zone.longValue() > 0) {
            list = campaignDAO.getCampaign(loginUser.getAccount().getCode(), zone);
        } else {
            list = campaignDAO.getCampaign(loginUser.getAccount().getCode());
        }

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        mAdapter = new CampaignListAdapter(getActivity(), list);
        recyclerView.setAdapter(mAdapter);
    }

}
