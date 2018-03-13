package jo.com.pcstores.rpos.pos.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.RecItemAdapter;
import jo.com.pcstores.rpos.pos.Classes.Items;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsFragment extends Fragment {

    //DECLARE
    RecyclerView recItems;

    public ItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_items, container, false);

        //INITIALIZE
        recItems =x.findViewById(R.id.recItems);
        recItems.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        RecItemAdapter a=new RecItemAdapter(getContext(),prepareArray());
        recItems.setAdapter(a);



        return x;
    }

    private ArrayList<Items> prepareArray()
    {
        ArrayList<Items> m=new ArrayList<>();

        Items p1=new Items();
        p1.setItemName("Burger");
        p1.setItemPrice("3.00 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);

        p1=new Items();
        p1.setItemName("Pizza");
        p1.setItemPrice("4.00 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);

        p1=new Items();
        p1.setItemName("Mansaf");
        p1.setItemPrice("10.00 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);


        p1=new Items();
        p1.setItemName("Falafel");
        p1.setItemPrice("0.35 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);


        p1=new Items();
        p1.setItemName("Kabseh");
        p1.setItemPrice("7.35 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);

        p1=new Items();
        p1.setItemName("Burger");
        p1.setItemPrice("3.00 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);

        p1=new Items();
        p1.setItemName("Pizza");
        p1.setItemPrice("4.00 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);

        p1=new Items();
        p1.setItemName("Mansaf");
        p1.setItemPrice("10.00 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);


        p1=new Items();
        p1.setItemName("Falafel");
        p1.setItemPrice("0.35 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);


        p1=new Items();
        p1.setItemName("Kabseh");
        p1.setItemPrice("7.35 JD");
        p1.setItemImage(R.drawable.itemborder);
        m.add (p1);

        return m;

    }
}
