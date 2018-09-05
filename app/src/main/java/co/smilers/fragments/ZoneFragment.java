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

import java.util.List;

import co.smilers.R;
import co.smilers.adapters.HeadquarterListAdapter;
import co.smilers.adapters.ZoneListAdapter;
import co.smilers.model.Headquarter;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZoneFragment extends Fragment {
    private final static String TAG = ZoneFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HEADQUARTER = "headquarter";

    // TODO: Rename and change types of parameters
    private Long headquarter;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public ZoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ZoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ZoneFragment newInstance(Long headquarter) {
        ZoneFragment fragment = new ZoneFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_HEADQUARTER, headquarter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            headquarter = getArguments().getLong(ARG_HEADQUARTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zone, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //Establecer sub titulo
        final String title = getActivity().getString(R.string.title_zone);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(title);
        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadZone();
        super.onResume();
    }

    private void loadZone() {
        ParameterDAO parameterDAO = new ParameterDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        List<Zone> list = parameterDAO.getZone(loginUser.getAccount().getCode(), headquarter);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        mAdapter = new ZoneListAdapter(getActivity(), list);
        recyclerView.setAdapter(mAdapter);
    }

}
