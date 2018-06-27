package jo.com.pcstores.rpos.pos.Fragments;


import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Adapters.RecInvoicesAdapter;
import jo.com.pcstores.rpos.pos.Classes.Invoices;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecieptFragment extends Fragment {

    TextView etFromDate;
    TextView etToDate;
    ImageView imgSearch;
    RecyclerView recInvoices;
    RecInvoicesAdapter recAdapter;
    public RecieptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x =  inflater.inflate(R.layout.fragment_reciept, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Reciepts");
        actionBar.setSubtitle("");

        etFromDate = x.findViewById(R.id.etFromDate);
        etToDate = x.findViewById(R.id.etToDate);
        imgSearch = x.findViewById(R.id.imgSearch);
        recInvoices = x.findViewById(R.id.recInvoices);

        //set drawables
        Drawable img = getResources().getDrawable( R.drawable.endday);
        img.setBounds( 0, 0, 32, 32 );
        etFromDate.setCompoundDrawables(img,null,null,null);
        etToDate.setCompoundDrawables(img,null,null,null);
        //

        //make instance of calendar
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        String date = "   "+day + "/" + (month +1) + "/" + year;
        etFromDate.setText(date);
        etToDate.setText(date);

//        recInvoices.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
//        recAdapter =new RecInvoicesAdapter(getContext(),fillrecycler(etFromDate.getText(),etToDate.getText()));
//        recInvoices.setAdapter(recAdapter);


        etFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = "   "+ dayOfMonth + "/" + (monthOfYear +1) + "/" + year;
                        etFromDate.setText(date);
                    }
                },year,month,day);

                dialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });

        etToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = "   "+ dayOfMonth + "/" + (monthOfYear +1) + "/" + year;
                        etToDate.setText(date);
                    }
                },year,month,day);

                dialog.show(getActivity().getFragmentManager(),"DatePickerDialog");
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recInvoices.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
                recAdapter =new RecInvoicesAdapter(getContext(),fillrecycler(etFromDate.getText(),etToDate.getText()));
                recInvoices.setAdapter(recAdapter);
            }
        });
        return x;
    }

    private ArrayList<Invoices> fillrecycler(CharSequence fromdate, CharSequence todate){

        ArrayList<Invoices> m=new ArrayList<>();
        Invoices p1=new Invoices();
        p1.setInvoiceno("1001.000001");
        p1.setActualtime("29/03/2018 10:00:01 am");
        p1.setEmployee("Employee1");
        p1.setTotal("0.00 JD");
        p1.setNettotal("0.00 JD");
        m.add(p1);

        p1=new Invoices();
        p1.setInvoiceno("1001.000002");
        p1.setActualtime("29/03/2018 10:00:01 am");
        p1.setEmployee("Employee2");
        p1.setTotal("0.00 JD");
        p1.setNettotal("0.00 JD");
        m.add(p1);

        p1=new Invoices();
        p1.setInvoiceno("1001.000003");
        p1.setActualtime("29/03/2018 10:00:01 am");
        p1.setEmployee("Employee3");
        p1.setTotal("0.00 JD");
        p1.setNettotal("0.00 JD");
        m.add(p1);

        p1=new Invoices();
        p1.setInvoiceno("1001.000004");
        p1.setActualtime("29/03/2018 10:00:01 am");
        p1.setEmployee("Employee4");
        p1.setTotal("0.00 JD");
        p1.setNettotal("0.00 JD");
        m.add(p1);
        return m;

    }
}
