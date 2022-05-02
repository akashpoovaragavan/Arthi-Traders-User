package com.arthi.traders.constant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*import com.arthi.traders.activity.DashboardActivity;
import com.arthi.traders.model.ShopCategory;*/

import com.arthi.traders.R;
import com.arthi.traders.model.Product;
import com.arthi.traders.view.HomeScreen;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "arthitradersusers.db";
    public static final String ORDERS_TABLE_NAME = "orders";
    public static final String WHISLIST_TABLE_NAME = "whislist";
    public static final String ORDERS_COLUMN_ID = "id";
    public static final String ORDERS_COLUMN_PRODUCT_ID = "dol_id";
    public static final String ORDERS_COLUMN_IMAGE = "dol_product_image";
    public static final String ORDERS_COLUMN_NAME = "dol_product_name";
    public static final String ORDERS_COLUMN_WEIGHT = "dol_product_weight";
    public static final String ORDERS_COLUMN_PRICE = "dol_product_price";
    public static final String ORDERS_COLUMN_QUANTITY = "dol_product_quantity";
    public static final String ORDERS_COLUMN_SUBCATEGORY ="dol_sub_category";
    public static final String  ORDERS_COLUMN_CATEGORY="dol_category";
    public static final String ORDERS_COLUMN_DESCRIPTION="dol_product_description";
    public static final String ORDERS_COLUMN_CODE="dol_product_code";
    public static final String ORDERS_COLUMN_PRODUCTPACK="dol_product_pack";
    public static final String ORDERS_COLUMN_CREATEDAT="dol_created_at";
    public static final String ORDERS_COLUMN_UPDATEDAT="dol_updated_at";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table orders " + "(id integer primary key,dol_id text,dol_category text,dol_sub_category text,dol_product_name text,dol_product_description text, dol_product_code text," +
                "dol_product_pack text,dol_product_weight text,dol_product_image text,dol_product_quantity text,dol_product_price text,dol_created_at text,dol_updated_at text)");
       // db.execSQL("create table whislist " + "(id integer primary key,product_id text,image text,name text,rate text,price text, quantity text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }

    public boolean insertOrders (String dol_id, String dol_category, String dol_sub_category, String dol_product_name, String dol_product_description,
                                 String dol_product_code, String dol_product_pack,String dol_product_weight,String dol_product_image,String dol_product_quantity,String dol_product_price,
                                 String dol_created_at,String dol_updated_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int res = getOrder(dol_id);
            if(res ==0) {
                HomeScreen.pendingCartCount++;
                HomeScreen.chip_bottom.showBadge(R.id.cart_nav,HomeScreen.pendingCartCount);
                Log.e("count ",HomeScreen.pendingCartCount+"");

                ContentValues contentValues = new ContentValues();
                contentValues.put("dol_id", dol_id);
                contentValues.put("dol_category", dol_category);
                contentValues.put("dol_sub_category", dol_sub_category);
                contentValues.put("dol_product_name", dol_product_name);
                contentValues.put("dol_product_description", dol_product_description);
                contentValues.put("dol_product_code", dol_product_code);
                contentValues.put("dol_product_pack", dol_product_pack);
                contentValues.put("dol_product_weight", dol_product_weight);
                contentValues.put("dol_product_image", dol_product_image);
                contentValues.put("dol_product_quantity", dol_product_quantity);
                contentValues.put("dol_product_price", dol_product_price);
                contentValues.put("dol_created_at", dol_created_at);
                contentValues.put("dol_updated_at", dol_updated_at);
                Log.e("val ",contentValues.toString());
                db.insert("orders", null, contentValues);
            }
            else{
                updateContact(dol_id,dol_product_price,dol_product_quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public int getOrder(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from orders where dol_id="+id+"", null );
        int count = res.getCount();
        res.close();
        return count;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from orders where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, ORDERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (String id, String price, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dol_product_price", price);
        contentValues.put("dol_product_quantity", quantity);
        db.update("orders", contentValues, "dol_id = ? ", new String[] { id } );
        return true;
    }

    public void deleteOrder() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from orders");

    }
    public int deleteitem(String productid) {
        int vv = 0;
        try {
            HomeScreen.pendingCartCount--;
            HomeScreen.chip_bottom.showBadge(R.id.cart_nav,HomeScreen.pendingCartCount);
            Log.e("del ",HomeScreen.pendingCartCount+"");
            Log.e("productid ",productid+"");
            SQLiteDatabase db = this.getWritableDatabase();
            vv = db.delete("orders",
                    "dol_id = ? ",
                    new String[] { productid });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vv;
    }
    public ArrayList<Product> getAllOrders() {
        ArrayList<Product> array_list = new ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from orders", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            try {
                Product job = new Product(res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_PRODUCT_ID)),res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_CATEGORY)),
                        res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_SUBCATEGORY)),res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_NAME)),
                        res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_DESCRIPTION)),res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_CODE)),
                        res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_PRODUCTPACK)),res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_WEIGHT)),
                        res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_IMAGE)),res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_QUANTITY)),
                        res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_PRICE)),res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_CREATEDAT)),
                        res.getString(res.getColumnIndexOrThrow(ORDERS_COLUMN_UPDATEDAT))
                );
                Log.e("job ",job.toString());
                array_list.add(job);
                res.moveToNext();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return array_list;
    }
}
