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
    public  StockCard()
    {

    }

    public StockCard(String id,String balance, String date,String desc,String itemid,String qty,String type,String uom)
    {
        put("ID",id);
        put("Balance",balance);
        put("Date",date);
        put("Description",desc);
        put("ItemID",itemid);
        put("Quantity",qty);
        put("Type",type);
        put("UOM",uom);
    }

    public static List<StockCard> jread(String url) {
        List<StockCard> list = new ArrayList<StockCard>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i = 0; i < a.length(); i++)
            {
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
//
//                JSONArray jsonArray_Details = b.getJSONArray("WCF_DisbursementListDetail");
//                d.disbursementDetailsList = DisbursementDetail.getDisbursementDetails(jsonArray_Details);
//
//                d.put("WCFDetails", Integer.toString(jsonArray_Details.length()));
                list.add(s);
            }
        } catch (Exception e) {
            Log.e("StockCard", "JSONArray error");
        }
        return (list);
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