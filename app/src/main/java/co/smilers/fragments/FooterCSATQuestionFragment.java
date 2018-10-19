package co.smilers.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.smilers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FooterCSATQuestionFragment extends Fragment {


    public FooterCSATQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_footer_csatquestion, container, false);
    }

}
