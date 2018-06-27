package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.ReturnInvoice;

public class RecReturnAdapter extends RecyclerView.Adapter<RecReturnAdapter.data> {

    ArrayList<ReturnInvoice> invoice;
    Context c;

    public RecReturnAdapter(ArrayList<ReturnInvoice> invoice, Context c) {
        this.invoice = invoice;
        this.c = c;
    }

    class data extends RecyclerView.ViewHolder{

        CheckedTextView Item;
        TextView Qty,Price,Total;

        public data(View itemView) {
            super(itemView);
            Item = itemView.findViewById(R.id.txtItem);
            Qty = itemView.findViewById(R.id.txtQty);
            Price = itemView.findViewById(R.id.txtPrice);
            Total = itemView.findViewById(R.id.txtTotal);
        }
    }

    @Override
    public data onCreateViewHolder(ViewGroup parent, int viewType) {
        final View info = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_return, parent, false);

        return new data(info);
    }

    @Override
    public void onBindViewHolder(data holder, int position) {
        holder.Item.setText(invoice.get(position).getItem());
        holder.Qty.setText(invoice.get(position).getQty());
        holder.Price.setText(invoice.get(position).getPrice());
        holder.Total.setText(invoice.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return invoice.size();
    }


}

