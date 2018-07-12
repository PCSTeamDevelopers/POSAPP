package jo.com.pcstores.rpos.pos.Classes;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Invoices extends RealmObject {

    @PrimaryKey
    @Required
    private Integer invoiceno;
    private String actualtime;
    private String employee;
    private String total;
    private String taxtotal;
    private String discounttotal;
    private String nettotal;

    public String getTaxtotal() {
        return taxtotal;
    }

    public void setTaxtotal(String taxtotal) {
        this.taxtotal = taxtotal;
    }

    public String getDiscounttotal() {
        return discounttotal;
    }

    public void setDiscounttotal(String discounttotal) {
        this.discounttotal = discounttotal;
    }

    public Integer getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(Integer invoiceno) {
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

}
