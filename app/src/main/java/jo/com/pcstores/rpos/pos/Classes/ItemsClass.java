package jo.com.pcstores.rpos.pos.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ItemsClass {

    Realm realm;
    Context c;
    byte[] bitmapdata = new byte[0];

    public ItemsClass(Context c){
        this.c = c;
        realm = Realm.getDefaultInstance();
    }

    public byte[] emptyArray() {
        try {
            Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#134f5c"));
            canvas.drawRect(0F, 0F, (float) 10, (float) 10, paint);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmapdata = stream.toByteArray();
            return bitmapdata;
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return bitmapdata;
    }

    public List<String> getMainCategories(){
        List<String> categories = new ArrayList<String>();
        try{
        categories = new ArrayList<String>();
        String item ;
        realm.beginTransaction();
        RealmResults<Items> data = realm.where(Items.class).equalTo("father","").findAll();
        categories.add("none");
        for (Items obj: data){
            item = obj.getItemName();
            categories.add(item);
        }
        realm.commitTransaction();
            return categories;
    }catch (Exception e){
        e.printStackTrace();
    }
        return categories;
    }

    public List<String> getAllCategories(){
        final List<String> categories = new ArrayList<String>();
        String item ;
        //U can remove beginTransaction
        realm.beginTransaction();
        RealmResults<Items> data = realm.where(Items.class).equalTo("itemType",0).findAll();

        for (Items obj: data){
            item = obj.getItemName();
            categories.add(item);
        }
        realm.commitTransaction();
        return categories;
    }

    public String getItemName(String itemId){
        Items item = realm.where(Items.class).equalTo("itemId",itemId).findFirst();
        String name;
        if (!(item == null)){
            name = item.getItemName();
        }else
        {
            name = "none";
        }

        return name;
    }

    public String getItemId(String ItemName){
        Items item = realm.where(Items.class).equalTo("itemName",ItemName).findFirst();
        String id = item.getItemId();
        return id;
    }

    public String getItemPrice(String ItemName){
        Items item = realm.where(Items.class).equalTo("itemName",ItemName).findFirst();
        String price = item.getItemPrice();
        return price;
    }

    public void addItem(String id, String name,String price, String father,String tax){
        try {
            realm.beginTransaction();
            Items item = new Items();
            item.setItemId(id);
            item.setItemName(name.toLowerCase());
            item.setItemType(1);
            item.setTax(tax);
            item.setItemPrice(price);
            item.setItemImage(emptyArray());
            String itemfather = getItemId(father);
            item.setFather(itemfather);

            realm.copyToRealmOrUpdate(item);
            realm.commitTransaction();
        }catch (Exception ex){
            realm.commitTransaction();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addCategory(String id, String name,String father){
        try {
            realm.beginTransaction();
            Items category = new Items();
            category.setItemId(id);
            category.setItemName(name.toLowerCase());
            category.setItemType(0);
            if (father.matches("none")){
                category.setFather("");
            }else{
                String itemfather = getItemId(father);
                category.setFather(itemfather);
            }
            realm.copyToRealmOrUpdate(category);
            realm.commitTransaction();
        }catch (Exception ex){
            realm.commitTransaction();
        }
    }

    public void saveImage(String itemID, Bitmap image) {
        try{
        realm.beginTransaction();  //open the database
        Items items = realm.where(Items.class).equalTo("itemId",itemID).findFirst();
        items.setItemImage(getImageByte(image));
        realm.copyToRealmOrUpdate(items);
        realm.commitTransaction();
        } catch (Exception ex) {
            realm.cancelTransaction();
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] getImageByte(Bitmap image) {
        try {
            if (!(image == null)) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;
            }else{
                return emptyArray();
            }
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return emptyArray();
    }

    public Bitmap getImage(byte[] image, Context context) {
        try {
            if(!(image.equals(null))) {
                if (image.length > 0) {
                    int width = context.getResources().getDisplayMetrics().widthPixels;
                    int height = context.getResources().getDisplayMetrics().heightPixels;

                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    Bitmap.createScaledBitmap(bitmap, width, height, false);
                    return bitmap;
                }
            }
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap emptybitmap = Bitmap.createBitmap(10, 10, conf);
        return emptybitmap;
    }

    public ArrayList<String> getFlavors(){
        ArrayList<String> flavors= new ArrayList<>();
        try {
            //U can remove beginTransaction
            realm.beginTransaction();
            RealmResults<Flavors> results = realm.where(Flavors.class).findAll();
            results.load();
            for (int i = 0; i<results.size(); i++){
                flavors.add(results.get(i).getFlavorName());
            }
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }
        return flavors;
    }

    public String getFlavorPrice(String flavorName){
        String price = "0";
        try {
            realm.beginTransaction();
            Flavors results = realm.where(Flavors.class).equalTo("FlavorName",flavorName).findFirst();
            results.load();
            price = results.getPrice();
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }
        return price;
    }

}
