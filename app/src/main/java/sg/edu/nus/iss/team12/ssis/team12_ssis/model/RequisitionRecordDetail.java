//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mmu1t on 28/1/2018.
 */

public class RequisitionRecordDetail extends HashMap<String,String> {

    public RequisitionRecordDetail(String id,String requestid, String itemid,String qtyrequested,String status)
    {
        put("RequestDetailID",id);
        put("RequestID",requestid);
        put("ItemID",itemid);
        put("RequestedQuantity",qtyrequested);
        put("Status",status);
    }

    public static List<RequisitionRecordDetail> getRequisitionRecordDetails(JSONArray jsonArray_Details)
    {
        List<RequisitionRecordDetail> list = new ArrayList<RequisitionRecordDetail>();
        try {
            for (int i = 0; i < jsonArray_Details.length(); i++)
            {
                JSONObject b = jsonArray_Details.getJSONObject(i);
                RequisitionRecordDetail d = new RequisitionRecordDetail(
                        Integer.toString(b.getInt("RequestDetailID")),
                        Integer.toString(b.getInt("RequestID")),
                        b.getString("ItemID"),
                        Integer.toString(b.getInt("RequestedQuantity")),
                        b.getString("Status")
                         );


                list.add(d);
            }
        } catch (Exception e) {
            Log.e("RequisitionRecordDetail", "JSONArray error");
        }

        return list;
    }
}

//
//        ItemID	"C005"
//        RequestDetailID	3
//        RequestID	0
//        RequestedQuantity	100
//        Status	"Processed"
//        WCF_InventoryCatalogue	null
//        WCF_RequisitionRecord	null