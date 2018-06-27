package jo.com.pcstores.rpos.pos.Fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Activities.NavMainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayFragment extends Fragment {

    //DECLARE
    Button btnCancel;
    Button btnDone;
    Button btnPrint;

    String Value;

    TextView txtamount;
    TextView txtbalance;
    TextView txtchange;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btnc;
    Button btn00;
    Button btn010;
    Button btn20;
    Button btn30;
    Button btn40;
    Button btn50;
    Button btn100;
    Button btnExact;
    public PayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_pay, container, false);

         //INITIALIZE
        btnCancel = x.findViewById(R.id.btnCancel);
        btnDone = x.findViewById(R.id.btnDone);
        btnPrint = x.findViewById(R.id.btnPrint);

        txtamount = x.findViewById(R.id.txtamount);
        txtbalance = x.findViewById(R.id.txtbalance);
        txtchange = x.findViewById(R.id.txtchange);
        btn0 = x.findViewById(R.id.btn0);
        btn1 = x.findViewById(R.id.btn1);
        btn2 = x.findViewById(R.id.btn2);
        btn3 = x.findViewById(R.id.btn3);
        btn4 = x.findViewById(R.id.btn4);
        btn5 = x.findViewById(R.id.btn5);
        btn6 = x.findViewById(R.id.btn6);
        btn7 = x.findViewById(R.id.btn7);
        btn8 = x.findViewById(R.id.btn8);
        btn9 = x.findViewById(R.id.btn9);
        btnc = x.findViewById(R.id.btnc);
        btn00 = x.findViewById(R.id.btn00);
        btn20 = x.findViewById(R.id.btn20);
        btn30 = x.findViewById(R.id.btn30);
        btn40 = x.findViewById(R.id.btn40);
        btn50 = x.findViewById(R.id.btn50);
        btn100 = x.findViewById(R.id.btn100);
        btn010 = x.findViewById(R.id.btn010);
        btnExact = x.findViewById(R.id.btnexact);

        //HOLD ONCLICK EVENTS
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("0.00");setchange();
            }
        });

        btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext(".");setchange();
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("0");setchange();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("1");setchange();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("2");setchange();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("3");setchange();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("4");setchange();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("5");setchange();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("6");setchange();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("7");setchange();
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("8");setchange();
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("9");setchange();
            }
        });

        btn010.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("10");
                setchange();
            }
        });
        btn20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("20");
                setchange();
            }
        });
        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("30");
                setchange();
            }
        });
        btn40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("40");
                setchange();
            }
        });
        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("50");
                setchange();
            }
        });
        btn100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtamount.setText("100");
                setchange();
            }
        });
        btnExact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { txtamount.setText(txtbalance.getText());
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ((AppCompatActivity) getActivity()).onBackPressed();
                }catch (Exception ex) {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do done code
                //refresh fragment and back to it
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do printing code
                //refresh fragment and back to it
            }
        });
        return x;
    }
    public void settext(String number){
        String txt = (String) txtamount.getText();
        if (txt.equals("0.00")){
            Value = number;
        }
        else if (txt.contains(".") && number.equals(".")){
            Value=txt;
        }
        else{
            Value = txt + number;
        }
        txtamount.setText(Value);
    }

    public void setchange(){
        float  balance = Float.parseFloat(txtbalance.getText().toString());
        float  amount = Float.parseFloat(txtamount.getText().toString());
        Value = String.valueOf(balance - amount);
        txtchange.setText(Value);
    }
}
