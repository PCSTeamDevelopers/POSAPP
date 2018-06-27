package jo.com.pcstores.rpos.pos.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jo.com.pcstores.rpos.R;

public class CloseCashActivity extends AppCompatActivity {

    //DECLARE
    String Value;

    TextView txtValue;
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

    FloatingActionButton btnPrint;
    FloatingActionButton btnCloseCash;
    FloatingActionButton btnCancel;

    TextView txtOpenBalance;
    TextView txtInvoicesCount;
    TextView txtCreditPayments;
    TextView txtReturnSales;
    TextView txtNetSales;
    TextView txtAmountDue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_cash);

        ////Edit ActionBar
        ActionBar actionBar = getSupportActionBar();
        String cashNo = "1001.0000001";
        String cashierName = "Cashier";
        String workTime = "from 10:00 am to 06:00 p.m";
        actionBar.setTitle("Cash# :" + cashNo);
        actionBar.setSubtitle("Cashier :" + cashierName + "                " + "WorkTime :" + workTime );

        //INITIALIZE
        txtValue = findViewById(R.id.txtValue);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnc = findViewById(R.id.btnc);
        btn00 = findViewById(R.id.btn00);

        btnPrint = findViewById(R.id.btnPrint);
        btnCloseCash = findViewById(R.id.btnCloseCash);
        btnCancel = findViewById(R.id.btnCancel);

        txtOpenBalance = findViewById(R.id.txtOpenBalance);
        txtInvoicesCount = findViewById(R.id.txtInvoicesCount);
        txtCreditPayments = findViewById(R.id.txtCreditPayments);
        txtReturnSales = findViewById(R.id.txtReturnSales);
        txtNetSales = findViewById(R.id.txtNetSales);
        txtAmountDue = findViewById(R.id.txtAmountDue);

        //fill data to table summary
        filldata();
        //ONCLICK EVENTS
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtValue.setText("0.00");
            }
        });

        btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext(".");
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("0");
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settext("9");
            }
        });
    }

    public void settext(String number){
        String txt = (String) txtValue.getText();
        if (txt.equals("0.00")){
            Value = number;
           }
            else if (txt.contains(".") && number.equals(".")){
            Value=txt;
        }
            else{
            Value = txt + number;
        }
        txtValue.setText(Value);
    }
    public void cancel(View view) {
        super.onBackPressed();
    }

    public void print(View view) {
               //do refresh code
        super.onBackPressed();
    }

    public void closecash(View view) {
               //do refresh code
        super.onBackPressed();
    }

    public void filldata(){
        txtOpenBalance.setText("0.00"+" JD");
        txtInvoicesCount.setText("0"+" Invoices");
        txtCreditPayments.setText("0.00"+" JD");
        txtReturnSales.setText("0.00"+" JD");
        txtNetSales.setText("0.00"+" JD");
        txtAmountDue.setText("0.00"+" JD");
    }


}
