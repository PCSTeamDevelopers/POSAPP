package jo.com.pcstores.rpos.pos.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import io.realm.Realm;
import io.realm.RealmResults;
import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.ExpandableListAdapter;
import jo.com.pcstores.rpos.pos.Adapters.OrderListAdapter;
import jo.com.pcstores.rpos.pos.Adapters.RecCatAdapter;
import jo.com.pcstores.rpos.pos.Adapters.RecItemAdapter;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.ItemsClass;
import jo.com.pcstores.rpos.pos.Classes.OrderList;
import jo.com.pcstores.rpos.pos.Interfaces.ItemsInterface;
import jo.com.pcstores.rpos.pos.Interfaces.OnActivityResult;
import jo.com.pcstores.rpos.pos.realm.RealmController;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ItemsInterface {

    //DECLARE
    FloatingActionButton btnPay;
    FloatingActionButton btnPrepare;
    FloatingActionButton btnCancel;

    RecyclerView recItems;
    RecItemAdapter recAdapter;
    RecyclerView recCategories;
    RecCatAdapter recCatAdapter;

    RecyclerView recOrderList;
    OrderListAdapter adapter;

    ExpandableListAdapter listAdapter;
    ExpandableListView expList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    Realm realm;
    ItemsClass itemObj = new ItemsClass(getContext());

    ArrayList<OrderList> items;
    Context c;

    Bitmap photo;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_main, container, false);

        realm = Realm.getDefaultInstance();

        getActivity().setTitle("Main");

        ////Edit ActionBar
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        String invoiceNo = "1001.0000001";
        String cashierName = "Cashier";
        actionBar.setTitle("Invoice# :" + invoiceNo);
        actionBar.setSubtitle("Cashier :" + cashierName);
       // actionBar.setLogo(getResources().getDrawable(R.drawable.logo));


        //INITIALIZE
        btnPay = x.findViewById(R.id.btnPay);
        btnPrepare = x.findViewById(R.id.btnPrepare);
        btnCancel = x.findViewById(R.id.btnCancel);
        recItems =x.findViewById(R.id.recItems);
        recCategories =x.findViewById(R.id.recCategories);
        recOrderList = x.findViewById(R.id.recOrderList);
        expList = x.findViewById(R.id.expList);

        //Expandable list
        totalExpList("","","","");
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expList.setAdapter(listAdapter);
        expList.setGroupIndicator(getResources().getDrawable( R.drawable.custom_expandable));
        expList.expandGroup(0);
        //expList.collapseGroup(0);

        //Recycler CATEGORIES
        recCategories.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recCatAdapter =new RecCatAdapter(getContext(),prepareCategoryArray(),this);
        recCategories.setAdapter(recCatAdapter);

        //ON CLICK EVENTS
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayFragment frag = new PayFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        return x;
    }

    private void totalExpList(String Subtotal, String TaxTotal, String DiscountTotal, String GrandTotal) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // Adding child data
        listDataHeader.add("Total");
        // Adding child data
        List<String> totals = new ArrayList<String>();
        totals.add("Subtotal:  "+ Subtotal);
        totals.add("Tax Total:  "+TaxTotal);
        totals.add("Discount Total:  "+DiscountTotal);
        totals.add("GrandTotal:  "+GrandTotal);

        listDataChild.put(listDataHeader.get(0), totals); // Header, Child data
    }

    private ArrayList<Items> prepareCategoryArray() {
        ArrayList<Items> items=new ArrayList<>();
        Items item = new Items();
        realm.beginTransaction();
        RealmResults<Items> data = realm.where(Items.class).equalTo("itemType",0).findAll();
        data.load();
        for (Items obj: data){
            item.setItemName(obj.getItemName());
            items.add (obj);
        }
        realm.commitTransaction();
        return items;
    }

    @Override
    public void itemInterface(ArrayList<Items> items,Context c) {
    }

    @Override
    public void orderInterface(ArrayList<OrderList> orderLists, Context c) {
        try {
            this.items = orderLists;
            this.c = c;
            new ordersInterface().execute();
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public class ordersInterface extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
            Integer position = recOrderList.getChildCount();
            if (position == 0){
                adapter =new OrderListAdapter(c,items);
            } else{
                Integer oldpos = adapter.checkItem(0,items);
                if (oldpos > -1){
                    adapter.update(oldpos,items);
                }else{
                    adapter.insert(0,items);
                }
            }
            recOrderList.setAdapter(adapter);
            recOrderList.setLayoutManager(new LinearLayoutManager(c,LinearLayoutManager.VERTICAL,false));
            } catch (Exception ex) {
                Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
            totalExpList(adapter.getSubTotal(),adapter.getTaxTotal(),adapter.getDiscountTotal(),adapter.getGrandTotal());
            listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
            expList.setAdapter(listAdapter);
            expList.setGroupIndicator(getResources().getDrawable( R.drawable.custom_expandable));
            expList.expandGroup(0);
            // expList.collapseGroup(0);
            } catch (Exception ex) {
                Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    @Override
    public void categoryInterface(String category, Context c) {
        try{
        //FIT COLUMNS INTO Recycler ITEMS AND SET ADAPTER
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/300);
        RecyclerView.LayoutManager l = new GridLayoutManager(getActivity(),columns);
        recItems.setLayoutManager(l);
            ArrayList<Items> items=new ArrayList<>();
            Items item = new Items();
            realm.beginTransaction();
            RealmResults<Items> data = realm.where(Items.class).equalTo("itemType",1).equalTo("father",itemObj.getItemId(category)).findAll();
            data.load();
            for (Items obj: data){
                item.setItemName(obj.getItemName());
                items.add (obj);
            }
            recAdapter =new RecItemAdapter(getContext(),items,this);
            recItems.setAdapter(recAdapter);
            realm.commitTransaction();

        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            recAdapter.onActivityResult(requestCode, resultCode, data);
    }
}