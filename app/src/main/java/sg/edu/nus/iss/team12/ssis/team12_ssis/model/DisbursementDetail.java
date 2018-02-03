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

public class DisbursementDetail extends HashMap<String,String> implements java.io.Serializable{


    public DisbursementDetail(String id,String disbursementid, String itemid,String actualqty,String qtycollected,String qtyrequested,String remarks,String uom)
    {
        put("ID",id);
        put("DisbursementID",disbursementid);
        put("ItemID",itemid);
        put("ActualQuantity",actualqty);
        put("QuantityCollected",qtycollected);
        put("QuantityRequested",qtyrequested);
        put("Remarks",remarks);
        put("UOM",uom);

    }
    public static List<DisbursementDetail> getDisbursementDetails(JSONArray jsonArray_Details)
    {
        List<DisbursementDetail> list = new ArrayList<DisbursementDetail>();
        try {
            for (int i = 0; i < jsonArray_Details.length(); i++)
            {
                JSONObject b = jsonArray_Details.getJSONObject(i);
                DisbursementDetail d = new DisbursementDetail(
                        Integer.toString(b.getInt("ID")),
                        Integer.toString(b.getInt("DisbursementID")),
                        b.getString("ItemID"),
                        Integer.toString(b.getInt("ActualQuantity")),
                        Integer.toString(b.getInt("QuantityCollected")),
                        Integer.toString(b.getInt("QuantityRequested")),
                        b.getString("Remarks"),
                        b.getString("UOM") );


                list.add(d);
            }
        } catch (Exception e) {
            Log.e("Disbursement", "JSONArray error");
        }

        return list;
    }

}
