package jo.com.pcstores.rpos.pos.Classes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Flavors extends RealmObject {

    @PrimaryKey
    @Required
    String FlavorID;
    String FlavorName;
    String Price;

    public String getFlavorID() {
        return FlavorID;
    }

    public void setFlavorID(String flavorID) {
        FlavorID = flavorID;
    }

    public String getFlavorName() {
        return FlavorName;
    }

    public void setFlavorName(String flavorName) {
        FlavorName = flavorName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

}
