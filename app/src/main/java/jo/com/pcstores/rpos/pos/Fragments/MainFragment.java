package jo.com.pcstores.rpos.pos.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jo.com.pcstores.rpos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    RecyclerView recSalesTypes;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().setTitle("Main");

        //INITIALIZE
        recSalesTypes = x.findViewById(R.id.recSalesType);

        //SET RECYCLER VIEW TYPE
        recSalesTypes.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return x;
    }

}
