package jo.com.pcstores.rpos.pos.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jo.com.pcstores.rpos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesChartFragment extends Fragment {


    public SalesChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_sales_chart, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("My Sales");
        actionBar.setSubtitle("");

        return x;
    }

}
