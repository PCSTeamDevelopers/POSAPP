package jo.com.pcstores.rpos.pos.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import jo.com.pcstores.rpos.pos.Classes.Flavors;
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
    FloatingActionButton btnFlavor;
    Realm realm;
    ItemsClass itemObj = new ItemsClass(getContext());

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_item, container, false);

        realm = Realm.getDefaultInstance();

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Items");
        actionBar.setSubtitle("");

        btnCategory = x.findViewById(R.id.btnCategory);
        btnItem = x.findViewById(R.id.btnItem);
        btnFlavor = x.findViewById(R.id.btnFlavor);
        expList = x.findViewById(R.id.expList);

        //Expandable list
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        expList.setAdapter(listAdapter);
        expList.setGroupIndicator(getResources().getDrawable(R.drawable.custom_expandable));
        //we must replace prepareCategoryArray().size() with variable who's get the size
        for (int i = 0; i < prepareCategoryArray().size(); i++) {
            expList.expandGroup(i);
        }

        //ONCLICK EVENTS
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View myView = li.inflate(R.layout.add_category_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setView(myView);

                    //Initialize
                    final EditText etCategoryID = myView.findViewById(R.id.etCategoryID);
                    final EditText etCategoryName = myView.findViewById(R.id.etCategoryName);
                    final Spinner spCategory = myView.findViewById(R.id.spCategory);

                    spCategory.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_login, R.id.Name, itemObj.getMainCategories()));

                    alertDialogBuilder.setTitle(R.string.addCatTxT);
                    alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.plus));
                    alertDialogBuilder
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                itemObj.addCategory(etCategoryID.getText().toString(), etCategoryName.getText().toString(), spCategory.getSelectedItem().toString());
                                                //to resfresh fragment .....
                                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                ft.detach(ItemFragment.this).attach(ItemFragment.this).commit();
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (itemObj.getMainCategories().size() < 2) {//size is set to 2 because by default its size is 1 ==> item (none)
                        Toast.makeText(getActivity(), R.string.noCategories, Toast.LENGTH_SHORT).show();
                    } else {
                        LayoutInflater li = LayoutInflater.from(getActivity());
                        View myView = li.inflate(R.layout.add_item_dialog, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setView(myView);

                        final EditText etItemID = myView.findViewById(R.id.etItemID);
                        final EditText etItemName = myView.findViewById(R.id.etItemName);
                        final EditText etPrice = myView.findViewById(R.id.etPrice);
                        final Spinner spCategory = myView.findViewById(R.id.spCategory);
                        final Spinner spTax = myView.findViewById(R.id.spTax);

                        spCategory.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_login, R.id.Name, itemObj.getAllCategories()));

                        String[] tax = getResources().getStringArray(R.array.taxArray);
                        spTax.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_login, R.id.Name, tax));

                        alertDialogBuilder.setTitle(R.string.addItemTxT);
                        alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.plus));
                        alertDialogBuilder
                                .setPositiveButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                try {
                                                    itemObj.addItem(etItemID.getText().toString(), etItemName.getText().toString(), etPrice.getText().toString(), spCategory.getSelectedItem().toString(), spTax.getSelectedItem().toString());
                                                    //to resfresh fragment .....
                                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                    ft.detach(ItemFragment.this).attach(ItemFragment.this).commit();
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            }
                                        })
                                .setNegativeButton(android.R.string.cancel,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFlavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View myView = li.inflate(R.layout.add_flavor_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setView(myView);

                    final EditText etFlavorName = myView.findViewById(R.id.etFlavorName);
                    final EditText etPrice = myView.findViewById(R.id.etPrice);

                    alertDialogBuilder.setTitle(R.string.addFlavorTxT);
                    alertDialogBuilder.setIcon(getResources().getDrawable(R.drawable.plus));
                    alertDialogBuilder
                            .setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            try {
                                                realm.beginTransaction();
                                                Flavors flavor = new Flavors();
                                                flavor.setFlavorName(etFlavorName.getText().toString());
                                                flavor.setPrice(etPrice.getText().toString());
                                                realm.copyToRealmOrUpdate(flavor);
                                                realm.commitTransaction();
                                            } catch (Exception ex) {
                                                realm.commitTransaction();
                                            }
                                        }
                                    })
                            .setNegativeButton(android.R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    }).setNeutralButton("View Flavors", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final ArrayList<String> allFlavors = itemObj.getFlavors();
                            String[] Flavor = new String[allFlavors.size()];
                            Flavor = allFlavors.toArray(new String[allFlavors.size()]);
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle(R.string.Flavors);
                            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.setItems(Flavor, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            final AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return x;
    }

    private void prepareListData() {
        try {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            ArrayList<Items> header = new ArrayList<>();
            header = prepareCategoryArray();
            // Adding child data
            for (int i = 0; i < header.size(); i++)     {
                String name = header.get(i).getItemName();
                listDataHeader.add(name);
                List<String> items = new ArrayList<String>();
                String data;
                //U can remove beginTransaction
                realm.beginTransaction();
                RealmResults<Items> subItem = realm.where(Items.class).equalTo("father", itemObj.getItemId(name)).equalTo("itemType", 1).findAll();
                for (Items item : subItem) {
                    data = item.getItemName();
                    items.add(data);
                }
                realm.commitTransaction();
                listDataChild.put(listDataHeader.get(i), items);
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Items> prepareCategoryArray() {
        ArrayList<Items> items = new ArrayList<>();
        try {
            Items item = new Items();
            //U can remove beginTransaction
            realm.beginTransaction();
            RealmResults<Items> data = realm.where(Items.class).equalTo("itemType", 0).findAll();
            data.load();
            for (Items obj : data) {
                item.setItemName(obj.getItemName());
                items.add(obj);
            }
            realm.commitTransaction();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return items;

    }

}
