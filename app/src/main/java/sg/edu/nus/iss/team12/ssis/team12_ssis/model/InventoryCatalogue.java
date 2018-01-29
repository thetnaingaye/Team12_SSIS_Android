package sg.edu.nus.iss.team12.ssis.team12_ssis.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mmu1t on 26/1/2018.
 */

public class InventoryCatalogue extends HashMap<String,String> {

    public static final String URI_SERVICE = "http://172.17.248.45/Team12_SSIS/WebServices/Service.svc/";

    public InventoryCatalogue(String itemID,String bin,String bufferstocklevel, String catId, String desc,
                              String discon,String level,String reorderlevel,
                              String reorderqty,String shelf,String uom,String unitStock,String unitOrder)
    {

        put("ItemID",itemID);
        put("BIN",bin);
        put("BufferStockLevel", bufferstocklevel);
        put("CategoryID", catId);
        put("Description",desc);
        put("Discontinued",discon);
        put("Level",level);
        put("ReorderLevel",reorderlevel);
        put("ReorderQty",reorderqty);
        put("Shelf",shelf);
        put("UOM",uom);
        put("UnitsInStock",unitStock);
        put("UnitsOnOrder",unitOrder);

    }

    public static List<InventoryCatalogue> jread(String url) {
        List<InventoryCatalogue> list = new ArrayList<InventoryCatalogue>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new    InventoryCatalogue(b.getString("ItemID"),
                                b.getString("BIN"),
                                Integer.toString(b.getInt("BufferStockLevel")),
                                b.getString("CategoryID"),
                                b.getString("Description"),
                                b.getString("Discontinued"),
                                Integer.toString(b.getInt("Level")),
                                Integer.toString(b.getInt("ReorderLevel")),
                                Integer.toString(b.getInt("ReorderQty")),
                                b.getString("Shelf"),b.getString("UOM"),
                                Integer.toString(b.getInt("UnitsInStock")),
                                Integer.toString(b.getInt("UnitsOnOrder"))));
            }
        } catch (Exception e) {
            Log.e("InventoryItem", "JSONArray error");
        }
        return(list);
    }
    public static String jread_RetrieveQty(String url){
        JSONObject a = JSONParser.getJSONFromUrl(url);

        return  a.toString();
    }

}

