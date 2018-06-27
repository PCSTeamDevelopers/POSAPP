package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
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
import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Flavors;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.OrderList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.data> {

    ArrayList<OrderList> items;
    ArrayList<Integer> checkedFlavor = new ArrayList<>();
    Context c;
    List<View> itemViewList = new ArrayList<>();
    Hashtable<String,Integer> orderlist = new Hashtable<>();
    Flavors flavors = new Flavors();

    Float subtotal=0.0f;
    Float taxtotal=0.0f;
    Float discounttotal=0.0f;
    Float grandtotal=0.0f;
    Realm realm;

    public OrderListAdapter(Context c, ArrayList<OrderList> item){
        items = item;
        this.c = c;
    }

    class data extends RecyclerView.ViewHolder{
        TextView txtItemName,txtItemPrice,txtQty, txtFlavor;
        FloatingActionButton btnDelete, btnMinus, btnPlus,btnFlavor;

        public data(View itemView) {
            super(itemView);
            btnFlavor= itemView.findViewById(R.id.btnFlavor);
            txtItemName= itemView.findViewById(R.id.txtItemName);
            txtItemPrice= itemView.findViewById(R.id.txtItemPrice);
            txtQty= itemView.findViewById(R.id.txtQty);
            txtFlavor= itemView.findViewById(R.id.txtFlavor);

            btnMinus= itemView.findViewById(R.id.btnMinus);
            btnPlus= itemView.findViewById(R.id.btnPlus);
            btnDelete= itemView.findViewById(R.id.btnDelete);

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
        final String[] Flavor = {"Flavor 1", "Flavor 2", "Flavor 3", "Flavor 4", "Flavor 5"};
        final boolean[] checkedItems = {false, false, false, false, false};

            holder.txtItemName.setText(items.get(position).getItem());
            holder.txtItemPrice.setText(items.get(position).getPrice());
            holder.txtQty.setText(items.get(position).getQty());
            //holder.txtFlavor.setText(items.get(position).getFlavors());
            orderlist.put(holder.txtItemName.getText().toString(), position);

//            String rowNum = ((Integer) (position + 1)).toString();
//            holder.txtnum.setText(rowNum);

        if (holder.txtFlavor.getText().equals("")) {
            holder.txtFlavor.setVisibility(View.GONE);
        }else{
            holder.btnFlavor.setImageDrawable(c.getResources().getDrawable(android.R.drawable.btn_star_big_on));
        }
        //OnClick Events
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                remove(position);
                }catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.txtQty.getText().equals("1")){
                    holder.txtQty.setText("1");
                }else{
                    Integer qty =(Integer.parseInt(holder.txtQty.getText().toString())) - 1 ;
                    holder.txtQty.setText(qty.toString());
                }
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qty = (Integer.parseInt(holder.txtQty.getText().toString())) + 1 ;
                holder.txtQty.setText(qty.toString());
            }
        });

        holder.btnFlavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.txtFlavor.setVisibility(View.VISIBLE);
                checkedFlavor = new ArrayList<>();
                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setIcon(R.drawable.logo);
                builder.setTitle("Select Flavors");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Integer checkedItem = ((AlertDialog) dialogInterface).getListView().getCheckedItemCount();
                        if (checkedItem > 0) {
                            String flavors = "- ";
                            for (int c = 0; c < checkedFlavor.size();c++){
                                flavors = flavors + Flavor[checkedFlavor.get(c)] +", ";
                            }
                            //here substring flavors to remove the last ","
                            flavors = flavors.substring(0,flavors.length()-2);
                            //items.get(position).setFlavors(flavors);
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
                        //((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON1).setEnabled(true); //positive button enabled
                        if (isChecked){
                            if(! checkedFlavor.contains(index)){
                                checkedFlavor.add(index);
                            }else{
                                checkedFlavor.remove(index);
                            }
                        }
                    }
                });


                final AlertDialog dialog = builder.create();
                dialog.show();

                //dialog.getButton(AlertDialog.BUTTON1).setEnabled(false); //positive button disabled
            }
        });
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
        }catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update(int oldposition, ArrayList<OrderList> data) {
        try {
            items.remove(oldposition);
            items.add(oldposition,data.get(0));
            notifyItemChanged(oldposition);
        }catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void remove(int position) {
        try {
            Integer size = getItemCount();
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, size);

                if (position < size-1){
                    for (int i = position+1; i < size-1; i++) {
                        Collections.swap(items, i, i - 1);
                    }
                    notifyItemMoved(position+1, size-1);
                }
        }catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public Integer checkItem(int position,ArrayList<OrderList> data){
        String ItemName = data.get(position).getItem();
        if (orderlist.containsKey(ItemName)){
            int itemoldposition = orderlist.get(ItemName);
            return itemoldposition;
        }
        return -1;
    }

    public String getSubTotal(){
        for (int i =0; i < items.size();i++){
            subtotal = subtotal + (Float.valueOf(items.get(i).getSubtotal().toString()));
        }
        return subtotal.toString();
    }

    public String getTaxTotal(){
        for (int i =0; i < items.size();i++){
            taxtotal = taxtotal + (Float.valueOf(items.get(i).getTax().toString()));
        }
        return taxtotal.toString();
    }

    public String getDiscountTotal(){
        for (int i =0; i < items.size();i++){
            discounttotal = discounttotal + (Float.valueOf(items.get(i).getDiscount().toString()));
        }
        return discounttotal.toString();
    }

    public String getGrandTotal(){
        for (int i =0; i < items.size();i++){
            grandtotal = grandtotal + (Float.valueOf(items.get(i).getGrandtotal().toString()));
        }
        return grandtotal.toString();
    }
}
