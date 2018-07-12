package jo.com.pcstores.rpos.pos.Classes;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class InvoiceClass {
    Realm realm;

    public InvoiceClass() {
        realm = Realm.getDefaultInstance();
    }

    public Integer getMaxInvoiceNo(){
        Integer invoiceNo = 0;
        try {
            //realm.beginTransaction();
            RealmResults<Invoices> invoices = realm.where(Invoices.class).findAll();
            if (invoices == null || invoices.size() == 0){
                invoiceNo = 1;
            }else {
                invoiceNo = (realm.where(Invoices.class).max("invoiceno")).intValue() + 1;
            }
            realm.commitTransaction();
            return invoiceNo;
        }catch (Exception e){
            realm.cancelTransaction();
            e.printStackTrace();
        }
        return invoiceNo;
    }

    public void insertInvoice(Integer invoiceNo, String subTotal, String taxTotal, String discountTotal, String grandTotal){
        try{
            String actualTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            realm.beginTransaction();
            Invoices invoiceObj = new Invoices();
            invoiceObj.setInvoiceno(invoiceNo);
            invoiceObj.setActualtime(actualTime);
            invoiceObj.setEmployee(GlobalVar.CashierName);
            invoiceObj.setTotal(subTotal);
            invoiceObj.setTaxtotal(taxTotal);
            invoiceObj.setDiscounttotal(discountTotal);
            invoiceObj.setNettotal(grandTotal);
            realm.copyToRealmOrUpdate(invoiceObj);
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
