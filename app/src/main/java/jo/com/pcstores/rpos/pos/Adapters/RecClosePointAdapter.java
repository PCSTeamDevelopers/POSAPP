package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Activities.CloseCashActivity;
import jo.com.pcstores.rpos.pos.Classes.ClosePoint;


public class RecClosePointAdapter extends RecyclerView.Adapter<RecClosePointAdapter.cash> {

    ArrayList<ClosePoint> data;
    Context c;

    public RecClosePointAdapter(Context c, ArrayList<ClosePoint> data){
         this.data = data;
         this.c = c;
     }

     class cash extends RecyclerView.ViewHolder{

        TextView txtCashno;
        TextView txtCashier;
        Button btnClose;

         public cash(View data) {
             super(data);
             txtCashno = data.findViewById(R.id.txtCashno);
             txtCashier = data.findViewById(R.id.txtCashier);
             btnClose = data.findViewById(R.id.btnClose);
         }
     }

    @Override
    public cash onCreateViewHolder(ViewGroup parent, int viewType) {
        final View data = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_closepoint, parent, false);

        data.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(data.getContext(), CloseCashActivity.class);
                c.startActivity(i);
            }
        });

        return new cash(data);
    }

    @Override
    public void onBindViewHolder(cash holder, int position) {
        holder.txtCashno.setText(data.get(position).getCashno());
        holder.txtCashier.setText(data.get(position).getCashier());
        holder.btnClose.setText(data.get(position).getClose());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
