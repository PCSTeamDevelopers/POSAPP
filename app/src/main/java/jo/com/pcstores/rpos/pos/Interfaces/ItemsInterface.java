package jo.com.pcstores.rpos.pos.Interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.OrderList;

public interface ItemsInterface {

    public void itemInterface(ArrayList<Items> items,Context c);
    public void orderInterface(ArrayList<OrderList> orderLists, Context c);
    public void categoryInterface(String category,Context c);

}

