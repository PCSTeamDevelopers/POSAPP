package jo.com.pcstores.rpos.pos.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.ExpandableListAdapter;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.ItemsClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    ExpandableListAdapter listAdapter;
    ExpandableListView expList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    FloatingActionButton btnCategory;
    FloatingActionButton btnItem;
    Realm realm;
    ItemsClass itemObj = new ItemsClass(getContext());
    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_item, container, false);

        realm = Realm.getDefaultInstance();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Items");
        actionBar.setSubtitle("");

        btnCategory = x.findViewById(R.id.btnCategory);
        btnItem = x.findViewById(R.id.btnItem);
        expList = x.findViewById(R.id.expList);

        //Expandable list
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expList.setAdapter(listAdapter);
        expList.setGroupIndicator(getResources().getDrawable( R.drawable.custom_expandable));
        for (int i = 0;i<prepareCategoryArray().size();i++){
            expList.expandGroup(i);
        }

        //ONCLICK EVENTS
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View myView = li.inflate(R.layout.add_category_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setView(myView);

                    //Initialize
                    final EditText etCategoryID = myView.findViewById(R.id.etCategoryID);
                    final EditText etCategoryName = myView.findViewById(R.id.etCategoryName);
                    final Spinner spCategory = myView.findViewById(R.id.spCategory);

                    spCategory.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner_login,R.id.Name,itemObj.getMainCategories()));

                    alertDialogBuilder.setTitle("Add Category");
                    alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.plus));
                    alertDialogBuilder
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            itemObj.addCategory(etCategoryID.getText().toString(),etCategoryName.getText().toString(),spCategory.getSelectedItem().toString());
                                            prepareListData();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }catch (Exception ex){
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View myView = li.inflate(R.layout.fragment_add_item_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setView(myView);

                    final EditText etItemID = myView.findViewById(R.id.etItemID);
                    final EditText etItemName = myView.findViewById(R.id.etItemName);
                    final EditText etPrice = myView.findViewById(R.id.etPrice);
                    final Spinner spCategory = myView.findViewById(R.id.spCategory);
                    final Spinner spTax = myView.findViewById(R.id.spTax);

                    spCategory.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner_login,R.id.Name,itemObj.getAllCategories()));

                    String [] tax = {"0","0.04","0.16"};
                    spTax.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner_login,R.id.Name,tax));

                    alertDialogBuilder.setTitle("Add Item");
                    alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.plus));
                    alertDialogBuilder
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            itemObj.addItem(etItemID.getText().toString(),etItemName.getText().toString(),etPrice.getText().toString(),spCategory.getSelectedItem().toString(),spTax.getSelectedItem().toString());
                                            prepareListData();
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }catch (Exception ex){
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return x;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        ArrayList<Items> header = new ArrayList<>();
        header = prepareCategoryArray();
        // Adding child data
        for (int i = 0;i<header.size();i++){
            String name = header.get(i).getItemName();
            listDataHeader.add(name);
            List<String> items = new ArrayList<String>();
            String data;
            realm.beginTransaction();
            RealmResults<Items> subItem = realm.where(Items.class).equalTo("father",itemObj.getItemId(name)).equalTo("itemType",1).findAll();
            for (Items item: subItem){
                data = item.getItemName();
                items.add(data);
            }
            realm.commitTransaction();
            listDataChild.put(listDataHeader.get(i), items);
        }
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
}
