package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.MySales;

public class RecSalesAdapter extends RecyclerView.Adapter<RecSalesAdapter.data> {
    ArrayList<MySales> sales;
    Context c;

    public RecSalesAdapter(Context c, ArrayList<MySales> item){
        sales=item;
        this.c=c;
    }

    class data extends RecyclerView.ViewHolder{
        TextView txtCatName,txtGrandTotal;

        public data(View itemView) {
            super(itemView);
            txtCatName= itemView.findViewById(R.id.txtCatName);
            txtGrandTotal= itemView.findViewById(R.id.txtGrandTotal);
        }
    }

    @Override
    public RecSalesAdapter.data onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_sales, parent, false);

        return new data(itemView);
    }

    @Override
    public void onBindViewHolder(RecSalesAdapter.data holder, int position) {
        holder.txtCatName.setText(sales.get(position).getCategoryName());
        holder.txtGrandTotal.setText(sales.get(position).getGradtotal());
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }
}
