package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;
import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Flavors;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.ItemsClass;
import jo.com.pcstores.rpos.pos.Classes.OrderList;
import jo.com.pcstores.rpos.pos.Fragments.MainFragment;
import jo.com.pcstores.rpos.pos.Interfaces.ItemsInterface;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.data> {

    ArrayList<OrderList> items;
    ArrayList<Integer> checkedFlavor = new ArrayList<>();
    Context c;
    ItemsInterface inter;
    Fragment frag;
    List<View> itemViewList = new ArrayList<>();
    Hashtable<String, Integer> orderlist = new Hashtable<>();
    Float subtotal = 0.0f;
    Float taxtotal = 0.0f;
    Float discounttotal = 0.0f;
    Float grandtotal = 0.0f;
    Realm realm;
    ItemsClass itemObj = new ItemsClass(c);
    Hashtable<String, String> hsQtyCounter = new Hashtable<>();

    public OrderListAdapter(Context c, ArrayList<OrderList> item, Fragment frag) {
        this.frag = frag;
        inter = (ItemsInterface) frag;
        items = item;
        this.c = c;
    }

    class data extends RecyclerView.ViewHolder {
        TextView txtItemName, txtItemPrice, txtQty, txtFlavor;
        FloatingActionButton btnDelete, btnMinus, btnPlus, btnFlavor;

        public data(View itemView) {
            super(itemView);
            btnFlavor = itemView.findViewById(R.id.btnFlavor);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtFlavor = itemView.findViewById(R.id.txtFlavor);

            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }

    @Override
    public data onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group_dtl, parent, false);
        final data myViewHolder = new data(itemView);
        itemViewList.add(itemView);

        realm = Realm.getDefaultInstance();

        return new data(itemView);
    }

    @Override
    public void onBindViewHolder(final data holder, final int position) {
        try {
            //manage flavors
            final ArrayList<String> allFlavors = itemObj.getFlavors();
            String[] Flavor1 = new String[allFlavors.size()];
            Flavor1 = allFlavors.toArray(new String[allFlavors.size()]);
            final String[] Flavor = Flavor1;
            final boolean[] checkedItems = new boolean[allFlavors.size()];

            //Binding
            holder.txtItemName.setText(items.get(position).getItem());
            holder.txtItemPrice.setText(items.get(position).getPrice());
            //set qty and totals to orderlist class
            OrderList orderListObj = new OrderList("", "", "");
            String qty = "1";
            String itemName = items.get(position).getItem();

            if (hsQtyCounter.containsKey(itemName)) {
                Integer itemqty = (Integer.parseInt(hsQtyCounter.get(itemName).toString())) + 1;
                qty = itemqty.toString();
            }
            String subtotal = String.valueOf((Float.parseFloat(items.get(position).getPrice())) * (Float.parseFloat(qty.toString())));
            String tax = String.valueOf(Float.parseFloat(items.get(position).getTax()) * (Float.parseFloat(subtotal)));
            String grandtotal = String.valueOf((Float.parseFloat(subtotal)) + (Float.parseFloat(tax)));
            orderListObj.setItem(itemName);
            orderListObj.setPrice(items.get(position).getPrice());
            orderListObj.setTax(items.get(position).getTax());
            orderListObj.setQty(qty);
            orderListObj.setSubtotal(subtotal);
            orderListObj.setTaxTotal(tax);
            orderListObj.setGrandtotal(grandtotal);
            orderListObj.setDiscount("0.00");
            items.set(position, orderListObj);

            holder.txtQty.setText(qty);
            orderlist.put(itemName, position);
            hsQtyCounter.put(itemName, qty);

            if (holder.txtFlavor.getText().equals("")) {
                holder.txtFlavor.setVisibility(View.GONE);
            } else {
                holder.btnFlavor.setImageDrawable(c.getResources().getDrawable(android.R.drawable.btn_star_big_on));
            }
            //OnClick Events
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        remove(position, holder.txtItemName.getText().toString());
                        inter.totalsInterface(getSubTotal(), getTaxTotal(), getDiscountTotal(), getGrandTotal(), c);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.txtQty.getText().equals("1")) {
                        holder.txtQty.setText("1");
                    } else {
                        Integer qty = (Integer.parseInt(holder.txtQty.getText().toString())) - 1;
                        holder.txtQty.setText(qty.toString());
                    }
                    //itemAdapterObj.updateQty(holder.txtItemName.getText().toString(),holder.txtQty.getText().toString());// here update the item qty on items hashtable
                    inter.totalsInterface(getSubTotal(), getTaxTotal(), getDiscountTotal(), getGrandTotal(), c);// here update expandable list totals
                }
            });

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer qty = (Integer.parseInt(holder.txtQty.getText().toString())) + 1;
                    holder.txtQty.setText(qty.toString());
                    //itemAdapterObj.updateQty(holder.txtItemName.getText().toString(),holder.txtQty.getText().toString());// here update the item qty on items hashtable
                    inter.totalsInterface(getSubTotal(), getTaxTotal(), getDiscountTotal(), getGrandTotal(), c);// here update expandable list totals
                }
            });

            holder.btnFlavor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        holder.txtFlavor.setVisibility(View.VISIBLE);
                        checkedFlavor = new ArrayList<>();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                        builder.setIcon(R.drawable.select);
                        builder.setTitle("Select Flavors");
                        builder.setPositiveButton(android.R.string.ok, null);

                        builder.setPositiveButton("Add Flavors", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Integer checkedItem = ((AlertDialog) dialogInterface).getListView().getCheckedItemCount();
                                if (checkedItem > 0) {
                                    //bind checked flavors
                                    String flavors = "- ";
                                    for (int c = 0; c < checkedFlavor.size(); c++) {
                                        flavors = flavors + Flavor[checkedFlavor.get(c)] + ", ";
                                    }
                                    //here substring flavors to remove the last ","
                                    flavors = flavors.substring(0, flavors.length() - 2);
                                    holder.txtFlavor.setText(flavors);

                                    //change btn flavor star color to yellow
                                    holder.btnFlavor.setImageDrawable(c.getResources().getDrawable(android.R.drawable.btn_star_big_on));
                                    Toast.makeText(c, R.string.Flavorsadded, Toast.LENGTH_LONG).show();
                                } else {
                                    holder.txtFlavor.setVisibility(View.GONE);
                                    holder.btnFlavor.setImageDrawable(c.getResources().getDrawable(android.R.drawable.btn_star_big_off));
                                    Toast.makeText(c, R.string.NoFlavorsSelected, Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setMultiChoiceItems(Flavor, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int index, boolean isChecked) {
                                if (isChecked) {
                                    if (!checkedFlavor.contains(index)) {
                                        checkedFlavor.add(index);
                                    } else {
                                        checkedFlavor.remove(index);
                                    }
                                }
                            }
                        });
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, ArrayList<OrderList> data) {
        try {

            items.add(data.get(position));
            notifyItemInserted(position);
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update(int oldposition, ArrayList<OrderList> data) {
        try {
            items.remove(oldposition);
            items.add(oldposition, data.get(0));
            notifyItemChanged(oldposition);
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position, String itemName) {
        try {
            Integer size = getItemCount();
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, size);

            if (position < size - 1) {
                for (int i = position + 1; i < size - 1; i++) {
                    Collections.swap(items, i, i - 1);
                }
                notifyItemMoved(position + 1, size - 1);
            }

            orderlist.remove(itemName);// here delete the item from orderlist hashtable
            hsQtyCounter.remove(itemName);// here delete item from qty hashtable to reset qty
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Integer checkItem(int position, ArrayList<OrderList> data) {
        String ItemName = data.get(position).getItem();
        if (orderlist.containsKey(ItemName)) {
            int itemoldposition = orderlist.get(ItemName);
            return itemoldposition;
        }
        return -1;
    }

    public String getSubTotal() {
        try {
            subtotal = 0.0f;
            for (int i = 0; i < items.size(); i++) {
                subtotal = subtotal + (Float.valueOf(items.get(i).getSubtotal().toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return subtotal.toString();
    }

    public String getTaxTotal() {
        try {
            taxtotal = 0.0f;
            for (int i = 0; i < items.size(); i++) {
                taxtotal = taxtotal + (Float.valueOf(items.get(i).getTaxTotal().toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return taxtotal.toString();
    }

    public String getDiscountTotal() {
        try {
            discounttotal = 0.0f;
            for (int i = 0; i < items.size(); i++) {
                discounttotal = discounttotal + (Float.valueOf(items.get(i).getDiscount().toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return discounttotal.toString();
    }

    public String getGrandTotal() {
        try {
            grandtotal = 0.0f;
            for (int i = 0; i < items.size(); i++) {
                grandtotal = grandtotal + (Float.valueOf(items.get(i).getGrandtotal().toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return grandtotal.toString();
    }

}
