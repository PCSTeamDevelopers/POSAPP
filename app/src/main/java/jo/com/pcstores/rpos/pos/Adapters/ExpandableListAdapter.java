package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.ItemsClass;
import jo.com.pcstores.rpos.pos.Classes.OrderList;

/**
 * Created by Dev6 on 3/14/2018.
 */

public class ExpandableListAdapter  extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
     String flag;
     Realm realm;
     ItemsClass itemObj = new ItemsClass(_context);

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.flag = flag;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
try{
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.custom_exp_child, null);
        }

        FloatingActionButton btnEdit = convertView.findViewById(R.id.btnEdit);
        if (flag.equals("Total")){
            btnEdit.setVisibility(View.GONE);
        }
        final TextView txtListChild = convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LayoutInflater li = LayoutInflater.from(_context);
                    View myView = li.inflate(R.layout.fragment_add_item_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
                    alertDialogBuilder.setView(myView);

                    final EditText etItemID = myView.findViewById(R.id.etItemID);
                    final EditText etItemName = myView.findViewById(R.id.etItemName);
                    final EditText etPrice = myView.findViewById(R.id.etPrice);
                    final Spinner spCategory = myView.findViewById(R.id.spCategory);
                    final Spinner spTax = myView.findViewById(R.id.spTax);

                    spCategory.setAdapter(new ArrayAdapter<String>(_context,R.layout.custom_spinner_login,R.id.Name,itemObj.getAllCategories()));

                    String [] tax = {"0","0.04","0.16"};
                    spTax.setAdapter(new ArrayAdapter<String>(_context,R.layout.custom_spinner_login,R.id.Name,tax));

                    //binddata
                    realm.beginTransaction();
                    Items item = realm.where(Items.class).equalTo("itemName",txtListChild.getText().toString().toLowerCase()).findFirst();
                    if (!(item == null)) {
                        for(int i= 0; i < spCategory.getAdapter().getCount(); i++)
                        {
                            if(spCategory.getAdapter().getItem(i).toString().matches(itemObj.getItemName(item.getFather())))
                            {
                                spCategory.setSelection(i);
                            }
                        }
                        etItemID.setText(item.getItemId());
                        etItemName.setText(item.getItemName());
                        etPrice.setText(item.getItemPrice());
                        for(int i= 0; i < spTax.getAdapter().getCount(); i++)
                        {
                            if(spTax.getAdapter().getItem(i).toString().contains(item.getTax()))
                            {
                                spTax.setSelection(i);
                            }
                        }
                    }
                    realm.commitTransaction();

                    alertDialogBuilder.setTitle("Edit Item");
                    alertDialogBuilder.setIcon(_context.getResources().getDrawable(R.drawable.edit));
                    alertDialogBuilder
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            itemObj.addItem(etItemID.getText().toString(),etItemName.getText().toString(),etPrice.getText().toString(),spCategory.getSelectedItem().toString(),spTax.getSelectedItem().toString());
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builder;
                            builder = new AlertDialog.Builder(_context);

                            builder.setTitle("Delete")
                                    .setMessage("Are you sure you want to delete item?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                realm.executeTransaction(new Realm.Transaction() {
                                                    @Override
                                                    public void execute(Realm realm) {
                                                        Items rows = realm.where(Items.class).equalTo("itemId",etItemID.getText().toString()).findFirst();
                                                        rows.deleteFromRealm();
                                                    }
                                                });
                                                dialog.dismiss();
                                                Toast.makeText(_context, "Item deleted!", Toast.LENGTH_SHORT).show();
                                            } catch (Exception ex) {
                                                Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                                realm.cancelTransaction();
                                            }
                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }catch (Exception ex){
                    Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
} catch (Exception ex) {
    Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT).show();
}
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        try{
        String headerTitle = (String) getGroup(groupPosition);
//        String headerTotal = (String) getGroup(groupPosition+1);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_hdr, null);
        }

        final TextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        flag = lblListHeader.getText().toString();
        if (!(flag.equals("Total"))){


        lblListHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    LayoutInflater li = LayoutInflater.from(_context);
                    View myView = li.inflate(R.layout.add_category_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_context);
                    alertDialogBuilder.setView(myView);

                    //Initialize
                    final EditText etCategoryID = myView.findViewById(R.id.etCategoryID);
                    final EditText etCategoryName = myView.findViewById(R.id.etCategoryName);
                    final Spinner spCategory = myView.findViewById(R.id.spCategory);

                    spCategory.setAdapter(new ArrayAdapter<String>(_context,R.layout.custom_spinner_login,R.id.Name,itemObj.getMainCategories()));

                    //binddata
                    realm.beginTransaction();
                    Items item = realm.where(Items.class).equalTo("itemName",lblListHeader.getText().toString().toLowerCase()).findFirst();
                    if (!(item == null)) {
                        for(int i= 0; i < spCategory.getAdapter().getCount(); i++)
                        {
                            if(spCategory.getAdapter().getItem(i).toString().matches(itemObj.getItemName(item.getFather())))
                            {
                                spCategory.setSelection(i);
                            }
                        }
                        etCategoryID.setText(item.getItemId());
                        etCategoryName.setText(item.getItemName());
                    }
                    realm.commitTransaction();

                    alertDialogBuilder.setTitle("Edit Category");
                    alertDialogBuilder.setIcon(_context.getResources().getDrawable(R.drawable.edit));
                    alertDialogBuilder
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            itemObj.addCategory(etCategoryID.getText().toString(),etCategoryName.getText().toString(),spCategory.getSelectedItem().toString());
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    }).setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builder;
                            builder = new AlertDialog.Builder(_context);

                            builder.setTitle("Delete")
                                    .setMessage("Are you sure you want to delete Category?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                realm.executeTransaction(new Realm.Transaction() {
                                                    @Override
                                                    public void execute(Realm realm) {
                                                        RealmResults findItems = realm.where(Items.class).equalTo("father",etCategoryID.getText().toString()).findAll();
                                                        if (findItems.size() > 0){
                                                            Toast.makeText(_context, "Category Cannot be deleted! it contains Items!", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Items rows = realm.where(Items.class).equalTo("itemId", etCategoryID.getText().toString()).findFirst();
                                                            rows.deleteFromRealm();
                                                            Toast.makeText(_context, "Category deleted!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                                dialog.dismiss();

                                            } catch (Exception ex) {
                                                Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                                realm.cancelTransaction();
                                            }
                                        }
                                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }catch (Exception ex){
                    Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        }
        } catch (Exception ex) {
            Toast.makeText(_context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
//        TextView txtTotal = convertView.findViewById(R.id.txtTotal);
//        txtTotal.setTypeface(null, Typeface.BOLD);
//        txtTotal.setText(headerTotal);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
