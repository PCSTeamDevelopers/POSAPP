package jo.com.pcstores.rpos.pos.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Activities.LoginActivity;
import jo.com.pcstores.rpos.pos.Activities.NavMainActivity;
import jo.com.pcstores.rpos.pos.Adapters.RecClosePointAdapter;
import jo.com.pcstores.rpos.pos.Adapters.RecItemAdapter;
import jo.com.pcstores.rpos.pos.Classes.ClosePoint;
import jo.com.pcstores.rpos.pos.Classes.Items;

/**
 * A simple {@link Fragment} subclass.
 */
public class EndShiftFragment extends Fragment {

    //DECLARE
    RecyclerView recData;
    RecClosePointAdapter recAdapter;
    Button btnEndday;

    public EndShiftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_end_shift, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Close Cash");
        actionBar.setSubtitle("");
            //INITIALIZE
            btnEndday = x.findViewById(R.id.btnEndday);
            recData = x.findViewById(R.id.recData);

            //SET ADAPTER TO Recycler DATA
            recData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
            recAdapter =new RecClosePointAdapter(getContext(),fillrecycler());
            recData.setAdapter(recAdapter);

            //HOLD ONCLICK EVENTS
        btnEndday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do endday code
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
            return x;
    }

    private ArrayList<ClosePoint> fillrecycler(){
        ArrayList<ClosePoint> m=new ArrayList<>();
        ClosePoint p1=new ClosePoint();
        p1.setCashno("1001.000001");
        p1.setCashier("Cashier");
        p1.setClose("Close Point");
        m.add(p1);

        p1=new ClosePoint();
        p1.setCashno("1001.000002");
        p1.setCashier("Cashier2");
        p1.setClose("Close Point");
        m.add(p1);

        p1=new ClosePoint();
        p1.setCashno("1001.000003");
        p1.setCashier("Cashier3");
        p1.setClose("Closed");
        m.add(p1);

        p1=new ClosePoint();
        p1.setCashno("1001.000004");
        p1.setCashier("Cashier4");
        p1.setClose("Closed");
        m.add(p1);
        return m;
    }
}
