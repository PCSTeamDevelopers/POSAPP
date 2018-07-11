package jo.com.pcstores.rpos.pos.Classes;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;

public class InvoiceClass {
    Realm realm;

    public InvoiceClass() {
        realm = Realm.getDefaultInstance();
    }

    public String getMaxInvoiceNo(){
        String invoiceNo = "";
        try {
            realm.beginTransaction();
            Invoices invoices = realm.where(Invoices.class).equalTo("status","Pending").findFirst();
            if (invoices == null){
                Invoices obj = realm.where(Invoices.class).findFirst();
                if (obj == null){
                    invoiceNo = "1";
                    insertInvoice(invoiceNo);
                }else {
                    invoiceNo = String.valueOf(Integer.parseInt(obj.getInvoiceno()) + 1);
                    insertInvoice(invoiceNo);
                }
            }else{
                invoiceNo= invoices.getInvoiceno();
            }
            realm.commitTransaction();
            return invoiceNo;
        }catch (Exception e){
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return invoiceNo;
    }

    public void insertInvoice(String invoiceno){
        try{
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            Invoices obj = new Invoices();
            obj.setInvoiceno(invoiceno);
            obj.setActualtime(timeStamp);
            obj.setStatus("Pending");
            realm.copyToRealmOrUpdate(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
