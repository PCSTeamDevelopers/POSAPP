package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Items;

/**
 * Created by Dev6 on 3/12/2018.
 */

public class RecItemAdapter extends RecyclerView.Adapter<RecItemAdapter.viewitem> {

    ArrayList<Items> items;
    Context c;

    public RecItemAdapter(Context c, ArrayList<Items> item) {

        items=item;
        this.c=c;
    }

    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView txtItemName,txtItemPrice;
        ImageView imgItem;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            txtItemName=(TextView)itemView.findViewById(R.id.txtItemName);
            txtItemPrice=(TextView)itemView.findViewById(R.id.txtItemPrice);
            imgItem=(ImageView)itemView.findViewById(R.id.imgItem);
        }
    }
    @Override
    public viewitem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_items, parent, false);

        return new viewitem(itemView);
    }

    @Override
    public void onBindViewHolder(viewitem holder, int position) {
        holder.txtItemName.setText(items.get(position).getItemName());
        holder.txtItemPrice.setText(items.get(position).getItemPrice());
        holder.imgItem.setImageResource(items.get(position).getItemImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
