//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mmu1t on 27/1/2018.
 */

public class StockCard extends HashMap<String,String> {

    InventoryCatalogue item;

    public StockCard() {

    }

    public StockCard(String id, String balance, String date, String desc, String itemid, String qty, String type, String uom) {
        put("ID", id);
        put("Balance", balance);
        put("Date", date);
        put("Description", desc);
        put("ItemID", itemid);
        put("Quantity", qty);
        put("Type", type);
        put("UOM", uom);
    }

    public static List<StockCard> jread(String url) {
        List<StockCard> list = new ArrayList<StockCard>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                StockCard s = new StockCard(
                        Integer.toString(b.getInt("ID")),
                        Integer.toString(b.getInt("Balance")),
                        b.getString("Date"),
                        b.getString("Description"),
                        b.getString("ItemID"),
                        Integer.toString(b.getInt("Quantity")),
                        b.getString("Type"),
                        b.getString("UOM")
                );

                list.add(s);
            }
        } catch (Exception e) {
            Log.e("StockCard", "JSONArray error");
        }
        return (list);
    }

    public static List<StockCard> jread_getStockCard(String url, String itemid, String token) {
        List<StockCard> list = new ArrayList<StockCard>();

        JSONObject jsonObject = new JSONObject();
        JSONArray a = new JSONArray();
        try {
            jsonObject.put("itemId", itemid);
            jsonObject.put("token", token);
        } catch (Exception e) {
            Log.e("jsonobject", "JSONArray error");
        }

        JSONObject result_Json = JSONParser.getJSONFromUrl_Post(url, jsonObject.toString());
        try {
            a = result_Json.getJSONArray("GetStockCardResult");
        } catch (Exception e) {
            Log.e("jsonobject", "JSONArray error");
        }

        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                StockCard s = new StockCard(
                        Integer.toString(b.getInt("ID")),
                        Integer.toString(b.getInt("Balance")),
                        b.getString("Date"),
                        b.getString("Description"),
                        b.getString("ItemID"),
                        Integer.toString(b.getInt("Quantity")),
                        b.getString("Type"),
                        b.getString("UOM")
                );

                list.add(s);
            }
        } catch (Exception e) {
            Log.e("StockCard", "JSONArray error");
        }
        return (list);
    }

    public static String createAVR(StockCard stockCard, String token, String url) {
        try {


            JSONObject avr_Detail = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            avr_Detail.put("Reason", stockCard.get("Description"));
            avr_Detail.put("ItemID", stockCard.get("ItemID"));
            avr_Detail.put("Quantity", stockCard.get("Quantity"));
            avr_Detail.put("Type", stockCard.get("Type"));
            avr_Detail.put("UOM", stockCard.get("UOM"));


            jsonObject.put("avrDetail", avr_Detail);

            jsonObject.put("token", token);
            String str = JSONParser.postStream(url, jsonObject.toString());
            return str;

        } catch (Exception e) {

        }
        return  "jason post error";
    }
}




//            Balance	670
//            Date	"1/10/2018"
//            Description	"Stock Adjustment"
//            ID	8
//            ItemID	"C001"
//            Quantity	3
//            Type	"ADJ"
//            UOM	"Dozen"
//            WCF_InventoryCatalogue