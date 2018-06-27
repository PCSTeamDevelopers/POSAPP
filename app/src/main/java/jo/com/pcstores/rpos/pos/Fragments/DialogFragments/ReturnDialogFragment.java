package jo.com.pcstores.rpos.pos.Fragments.DialogFragments;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.RecReturnAdapter;
import jo.com.pcstores.rpos.pos.Classes.ReturnInvoice;
import jo.com.pcstores.rpos.pos.Fragments.RecieptFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReturnDialogFragment extends DialogFragment {

    RecyclerView recInvoice;
    RecReturnAdapter adapter;

    Button btnReturn;
    Button btnCancel;

    static Integer type;

    public static ReturnDialogFragment newInstance(int num) {
        ReturnDialogFragment f = new ReturnDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        type = num;

        return f;
    }

    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(500, 600);
        window.setGravity(Gravity.CENTER);
        //TODO:
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_return_dialog, container, false);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnReturn = x.findViewById(R.id.btnReturn);
        btnCancel = x.findViewById(R.id.btnCancel);

        recInvoice = x.findViewById(R.id.recInvoice);
        recInvoice.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new RecReturnAdapter(filldata(),getContext());
        recInvoice.setAdapter(adapter);


        if (type == 1){
            btnReturn.setVisibility(View.GONE);
        }

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DO RETURN

                //DISMISS DIALOG
                getDialog().dismiss();

                //REFRESH RECIEPT
                RecieptFragment frag = new RecieptFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content, frag);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return x;
    }

    public ArrayList<ReturnInvoice> filldata(){
        ArrayList<ReturnInvoice> list = new ArrayList<>();

        ReturnInvoice data = new ReturnInvoice();
        data.setItem("Item1");
        data.setQty("1");
        data.setPrice("1.00");
        data.setTotal("1.00");
        list.add(data);

        data = new ReturnInvoice();
        data.setItem("Item2");
        data.setQty("2");
        data.setPrice("2.00");
        data.setTotal("4.00");
        list.add(data);

        data = new ReturnInvoice();
        data.setItem("Item3");
        data.setQty("3");
        data.setPrice("3.00");
        data.setTotal("9.00");
        list.add(data);

        data = new ReturnInvoice();
        data.setItem("Item4");
        data.setQty("4");
        data.setPrice("4.00");
        data.setTotal("16.00");
        list.add(data);

        data = new ReturnInvoice();
        data.setItem("Net Total");
        data.setQty("");
        data.setPrice("");
        data.setTotal("30.00");
        list.add(data);

        return list;
    }
}
