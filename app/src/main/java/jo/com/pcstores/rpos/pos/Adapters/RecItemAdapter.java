package jo.com.pcstores.rpos.pos.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import io.realm.Realm;
import jo.com.pcstores.rpos.R;
import jo.com.pcstores.rpos.pos.Classes.Items;
import jo.com.pcstores.rpos.pos.Classes.ItemsClass;
import jo.com.pcstores.rpos.pos.Classes.OrderList;
import jo.com.pcstores.rpos.pos.Fragments.MainFragment;
import jo.com.pcstores.rpos.pos.Interfaces.ItemsInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;
import jo.com.pcstores.rpos.pos.Interfaces.OnActivityResult;

import static android.support.v4.graphics.TypefaceCompatUtil.getTempFile;
import static java.security.AccessController.getContext;

/**
 * Created by Dev6 on 3/12/2018.
 */

public class RecItemAdapter extends RecyclerView.Adapter<jo.com.pcstores.rpos.pos.Adapters.RecItemAdapter.viewitem> implements OnActivityResult {

        ArrayList<Items> items;
        ArrayList<OrderList> orderObj;
        Context c;
        ItemsInterface inter;
        View itemView;
        Hashtable<String,String> counter = new Hashtable<>();
        Bitmap photo;
        ImageView imgItem;
        Fragment frag;
        private static final int CAMERA_REQUEST = 1888;
        private static final int SELECT_PICTURE = 1;
        ItemsClass itemObj = new ItemsClass(c);

        public RecItemAdapter(Context c, ArrayList<Items> item,Fragment frag) {
            this.frag = frag;
            inter = (ItemsInterface) frag;
            items=item;
            this.c=c;
            itemView = null;
        }

    class  viewitem extends RecyclerView.ViewHolder
        {
            //Declare
            TextView txtItemName,txtItemPrice;
            ImageView imgOptions,imgItem;

            //initialize
            public viewitem(View itemView) {
                super(itemView);
                txtItemName= itemView.findViewById(R.id.txtItemName);
                txtItemPrice= itemView.findViewById(R.id.txtItemPrice);
                imgItem= itemView.findViewById(R.id.imgItem);
                imgOptions= itemView.findViewById(R.id.imgOptions);
            }

        }
        @Override
        public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_rec_items, parent, false);

            itemView.findViewById(R.id.imgOptions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(c);
                        builder.setTitle("Select Option");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (options[item].equals("Take Photo")) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    ((Fragment) frag).startActivityForResult(intent, CAMERA_REQUEST);
                                } else if (options[item].equals("Choose From Gallery")) {
                                    dialog.dismiss();
                                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    ((Fragment) frag).startActivityForResult(pickPhoto, SELECT_PICTURE);
                                } else if (options[item].equals("Cancel")) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

            return new viewitem(itemView);
        }

        @Override
        public void onBindViewHolder(final viewitem holder, final int position) {
            holder.txtItemName.setText(items.get(position).getItemName());
            holder.txtItemPrice.setText(items.get(position).getItemPrice());
            //holder.imgItem.setImageBitmap(itemObj.getImage(items.get(position).getItemImage()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String itemName = holder.txtItemName.getText().toString();
                        String itemPrice = holder.txtItemPrice.getText().toString();
                        String qty = "1";
                        if (counter.containsKey(itemName)) {
                            Integer itemqty = (Integer.parseInt(counter.get(itemName).toString())) + 1;
                            qty = itemqty.toString();
                        }
                        //Integer Price = ((Integer.parseInt(itemPrice.toString())) * (Integer.parseInt(qty.toString())));
                        String subtotal = String.valueOf((Float.parseFloat(itemPrice.toString())) * (Float.parseFloat(qty.toString())));
                        String tax = String.valueOf(Float.parseFloat(items.get(position).getTax()) * (Float.parseFloat(subtotal)));
                        String grandtotal = String.valueOf((Float.parseFloat(subtotal)) + (Float.parseFloat(tax)));
                        orderObj = new ArrayList<>();
                        OrderList obj = new OrderList(itemName, itemPrice, qty);
                        obj.setItem(itemName);
                        obj.setPrice(itemPrice);
                        obj.setQty(qty);
                        obj.setSubtotal(subtotal);
                        obj.setTax(tax);
                        obj.setDiscount("0.00");
                        obj.setGrandtotal(grandtotal);
                        orderObj.add(obj);
                        inter.orderInterface(orderObj, c);

//                    ArrayList<Items> m=new ArrayList<>();
//                    Items i = new Items();
//                    i.setItemPrice(itemPrice);
//                    i.setItemName(itemName);
//                    i.setItemQty(qty);
//                    i.setFlavors("");
//                    m.add(i);
//                    inter.itemInterface(m,c);
//
                        counter.put(itemName, qty);

                    } catch (Exception ex) {
                        Toast.makeText(c, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (requestCode == SELECT_PICTURE) {
                if (resultCode == RESULT_OK) {
                    if (requestCode == SELECT_PICTURE) {
                        Uri selectedImageUri = data.getData();
                        imgItem.setImageURI(selectedImageUri);

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(c.getContentResolver(), selectedImageUri);
                        itemObj.saveImage(bitmap);
                    }
                }
            }else if (requestCode == CAMERA_REQUEST) {
                photo = (Bitmap) data.getExtras().get("data");
                imgItem.setImageBitmap(photo);
                itemObj.saveImage(photo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
