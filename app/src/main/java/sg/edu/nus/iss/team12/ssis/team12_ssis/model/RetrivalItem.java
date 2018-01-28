package sg.edu.nus.iss.team12.ssis.team12_ssis.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mmu1t on 26/1/2018.
 */

public class RetrivalItem extends HashMap<String,String> {

    public RetrivalItem(String itemID,String actualqty,String deptid, String isoverride, String requestdetailid,
                              String requestid,String requestedqty)
    {

        put("ItemID",itemID);
        put("ActualQty",actualqty);
        put("DepartmentID", deptid);
        put("IsOverride", isoverride);
        put("RequestDetailID",requestdetailid);
        put("RequestID",requestid);
        put("RequestedQty",requestedqty);

    }

    public static List<RetrivalItem> jread(String url) {
        List<RetrivalItem> list = new ArrayList<RetrivalItem>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i =0; i<a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new RetrivalItem(
                        b.getString("ItemID"),
                        Integer.toString(b.getInt("ActualQty")),
                        b.getString("DepartmentID"),
                        Boolean.toString(b.getBoolean("IsOverride")),
                        Integer.toString(b.getInt("RequestDetailID")),
                        Integer.toString(b.getInt("RequestID")),
                        Integer.toString(b.getInt("RequestedQty"))));

            }
        } catch (Exception e) {
            Log.e("RetrivalItem", "JSONArray error");
        }
        return(list);
    }


}
