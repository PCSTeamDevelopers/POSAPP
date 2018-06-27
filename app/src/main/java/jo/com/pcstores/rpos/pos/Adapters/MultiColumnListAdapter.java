package jo.com.pcstores.rpos.pos.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.OrderList;

/**
 * Created by Dev6 on 3/19/2018.
 */

public class MultiColumnListAdapter extends ArrayAdapter<OrderList> {

    private static final String TAG = "OrderListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView item;
        TextView price;
        TextView Qty;
    }


    public MultiColumnListAdapter(Context context, int resource, ArrayList<OrderList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = getItem(position).getItem();
        String price = getItem(position).getPrice();
        String Qty = getItem(position).getQty();

        OrderList person = new OrderList(item, price, Qty);


        final View result;


        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_group_dtl, null);
            holder = new ViewHolder();
//            holder.item = convertView.findViewById(R.id.lblListItem);
//            holder.price = convertView.findViewById(R.id.lblListItem2);
//            holder.Qty = convertView.findViewById(R.id.lblListItem3);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;

        holder.item.setText(person.getItem());
        holder.price.setText(person.getPrice());
        holder.Qty.setText(person.getQty());


        return convertView;
    }
}