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

    public static List<RetrivalItem> jread_getItemByDept(String url, String itemid, String token) {
        List<RetrivalItem> list = new ArrayList<RetrivalItem>();

        JSONObject jsonObject = new JSONObject();
        JSONArray a = new JSONArray();
        try {
            jsonObject.put("itemID", itemid);
            jsonObject.put("token", token);
        } catch (Exception e) {
            Log.e("jsonobject", "JSONArray error");
        }
        JSONObject result_Json = JSONParser.getJSONFromUrl_Post(url, jsonObject.toString());
        try {
            a = result_Json.getJSONArray("GetRelevantListByDeptResult");
        } catch (Exception e) {
            Log.e("jsonobject", "JSONArray error");
        }

        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                RetrivalItem s = new RetrivalItem(
                        b.getString("ItemID"),
                        Integer.toString(b.getInt("ActualQty")),
                        b.getString("DepartmentID"),
                        Boolean.toString(b.getBoolean("IsOverride")),
                        Integer.toString(b.getInt("RequestDetailID")),
                        Integer.toString(b.getInt("RequestID")),
                        Integer.toString(b.getInt("RequestedQty")));

                list.add(s);
            }
        } catch (Exception e) {
            Log.e("StockCard", "JSONArray error");
        }
        return (list);
    }

    public static String submitRetrival(List<RetrivalItem> rlist, String token, String url)
    {
        try {

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray_rlist = new JSONArray();

            for(RetrivalItem r:rlist)
            {
                JSONObject j = new JSONObject();
                j.put("ItemID",r.get("ItemID"));
                j.put("ActualQty",r.get("ActualQty"));
                j.put("DepartmentID", r.get("DepartmentID"));
                j.put("IsOverride", r.get("IsOverride"));
                j.put("RequestDetailID",r.get("RequestDetailID"));
                j.put("RequestID",r.get("RequestID"));
                j.put("RequestedQty",r.get("RequestedQty"));

                jsonArray_rlist.put(j);
            }
            jsonObject.put("tempObj", jsonArray_rlist);
            jsonObject.put("token",token);

            String str =jsonObject.toString();

            return JSONParser.postStream(url, jsonObject.toString());
        }
        catch(Exception e){

        }
        return "jason post error";
    }


}
