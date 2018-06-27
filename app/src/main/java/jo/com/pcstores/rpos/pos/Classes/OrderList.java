package jo.com.pcstores.rpos.pos.Classes;

/**
 * Created by Dev6 on 3/19/2018.
 */

public class OrderList {

    private String Item;
    private String Price;
    private String Qty;
    private String Subtotal;
    private String Tax;
    private String Discount;
    private String Grandtotal;

    public OrderList(String item, String price, String qty) {
        Item = item;
        Price = price;
        Qty = qty;
    }

    public String getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(String subtotal) {
        Subtotal = subtotal;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getGrandtotal() {
        return Grandtotal;
    }

    public void setGrandtotal(String grandtotal) {
        Grandtotal = grandtotal;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }
}
