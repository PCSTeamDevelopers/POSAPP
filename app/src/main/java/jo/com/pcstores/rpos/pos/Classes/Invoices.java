package jo.com.pcstores.rpos.pos.Classes;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Invoices extends RealmObject {

    @PrimaryKey
    @Required
    private String invoiceno;
    private String actualtime;
    private String employee;
    private String total;
    private String nettotal;
    private String status;

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getActualtime() {
        return actualtime;
    }

    public void setActualtime(String actualtime) {
        this.actualtime = actualtime;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNettotal() {
        return nettotal;
    }

    public void setNettotal(String nettotal) {
        this.nettotal = nettotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
