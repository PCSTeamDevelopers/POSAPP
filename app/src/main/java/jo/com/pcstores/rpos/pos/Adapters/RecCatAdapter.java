package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Interfaces.ItemsInterface;

/**
 * Created by Dev6 on 3/12/2018.
 */

public class RecCatAdapter extends RecyclerView.Adapter<RecCatAdapter.viewitem> {

    ArrayList<Items> items;
    Context c;
    List<View> itemViewList = new ArrayList<>();
    ItemsInterface inter;
    View itemView;

    public RecCatAdapter(Context c, ArrayList<Items> item,Fragment frag) {

        inter = (ItemsInterface) frag;
        items=item;
        this.c=c;
    }

    static class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView txtCatName;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            txtCatName= itemView.findViewById(R.id.txtCatName);
        }
    }
    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {

         itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_categories, parent, false);
        final viewitem myViewHolder = new viewitem(itemView);
        itemViewList.add(itemView);
         return new viewitem(itemView);
    }

    @Override
    public void onBindViewHolder(final viewitem holder, final int position) {
        holder.txtCatName.setText(items.get(position).getItemName());

        Log.d("txtCatName", "id : " + holder.txtCatName.getText());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    inter.categoryInterface(holder.txtCatName.getText().toString(), c);
                } catch (Exception ex) {
                    Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
