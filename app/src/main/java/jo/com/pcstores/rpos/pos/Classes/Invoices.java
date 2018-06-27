package jo.com.pcstores.rpos.pos.Classes;

public class Invoices {

    private String invoiceno;
    private String actualtime;
    private String employee;
    private String total;
    private String nettotal;

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
}
