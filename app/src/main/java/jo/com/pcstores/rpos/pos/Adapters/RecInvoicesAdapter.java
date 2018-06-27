package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Invoices;
import jo.com.pcstores.rpos.pos.Fragments.DialogFragments.ReturnDialogFragment;


public class RecInvoicesAdapter extends RecyclerView.Adapter<RecInvoicesAdapter.data>{

    ArrayList<Invoices> data;
    Context c;

    public RecInvoicesAdapter(Context c, ArrayList<Invoices> data) {
        this.data = data;
        this.c = c;
    }

    class data extends RecyclerView.ViewHolder{

        TextView invoice;
        TextView actualtime;
        TextView employee;
        TextView total;
        TextView nettotal;

        public data(View itemView) {
            super(itemView);

            invoice = itemView.findViewById(R.id.txtinvoiceno);
            actualtime = itemView.findViewById(R.id.txtactualtime);
            employee = itemView.findViewById(R.id.txtemployee);
            total = itemView.findViewById(R.id.txttotal);
            nettotal = itemView.findViewById(R.id.txtnettotal);

            Drawable img = c.getResources().getDrawable(R.drawable.velipsis);
            img.setBounds( 0, 0, 32, 32 );
            invoice.setCompoundDrawables(img,null,null,null);
        }
    }

    @Override
    public data onCreateViewHolder(ViewGroup parent, int viewType) {
        final View info = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_invoices, parent, false);

        info.findViewById(R.id.txtinvoiceno);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setIcon(R.drawable.logo);
                builder.setTitle("Invoices Options");

                final String[] Items = {"Preview", "Reprint", "Return"};

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setItems(Items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            FragmentTransaction ft = ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction();
                            Fragment prev = ((AppCompatActivity)c).getSupportFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                ft.remove(prev);
                            }
                            ft.addToBackStack(null);

                            // Create and show the dialog.
                            DialogFragment newFragment = ReturnDialogFragment.newInstance(1);
                            newFragment.show(ft,"dialog");
                        }
                        if (i == 1) {
                            //DO REPRINT
                            dialogInterface.dismiss();
                        }
                        if (i == 2) {

                            FragmentTransaction ft = ((AppCompatActivity)c).getSupportFragmentManager().beginTransaction();
                            Fragment prev = ((AppCompatActivity)c).getSupportFragmentManager().findFragmentByTag("dialog");
                            if (prev != null) {
                                ft.remove(prev);
                            }
                            ft.addToBackStack(null);

                            // Create and show the dialog.
                            DialogFragment newFragment = ReturnDialogFragment.newInstance(2);
                            newFragment.show(ft,"dialog");

                        }
                    }
                });


                final AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return new data(info);
    }

    @Override
    public void onBindViewHolder(data holder, int position) {
        holder.invoice.setText(data.get(position).getInvoiceno());
        holder.actualtime.setText(data.get(position).getActualtime());
        holder.employee.setText(data.get(position).getEmployee());
        holder.total.setText(data.get(position).getTotal());
        holder.nettotal.setText(data.get(position).getNettotal());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
