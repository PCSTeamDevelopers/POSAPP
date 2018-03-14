package jo.com.pcstores.rpos.pos.Fragments;


import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.ExpandableListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().setTitle("Main");

        expListView = x.findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);
        expListView.expandGroup(1);
        return x;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Total");
        listDataHeader.add("Order List");

        // Adding child data
        List<String> totals = new ArrayList<String>();
        totals.add("Subtotal");
        totals.add("Tax Total");
        totals.add("Discount Total");
        totals.add("GrandTotal");

        List<String> orderlist = new ArrayList<String>();
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");
        orderlist.add("");


        listDataChild.put(listDataHeader.get(0), totals); // Header, Child data
        listDataChild.put(listDataHeader.get(1), orderlist);

    }
}

