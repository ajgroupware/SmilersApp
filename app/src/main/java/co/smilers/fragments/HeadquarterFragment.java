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
import co.smilers.model.Headquarter;
import co.smilers.model.User;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeadquarterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeadquarterFragment extends Fragment {
    private final static String TAG = HeadquarterFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public HeadquarterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeadquarterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeadquarterFragment newInstance(String param1, String param2) {
        HeadquarterFragment fragment = new HeadquarterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "--onResume");
        loadHeadquarter();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headquarter, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //Establecer sub titulo
        final String title = getActivity().getString(R.string.title_headquarter);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(title);
        return view;
    }

    private void loadHeadquarter() {
        ParameterDAO parameterDAO = new ParameterDAO(getActivity());
        UserDAO userDAO = new UserDAO(getActivity());
        User loginUser = userDAO.getUserLogin();
        List<Headquarter> list = parameterDAO.getHeadquarter(loginUser.getAccount().getCode());

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        mAdapter = new HeadquarterListAdapter(getActivity(), list);
        recyclerView.setAdapter(mAdapter);
    }

}
