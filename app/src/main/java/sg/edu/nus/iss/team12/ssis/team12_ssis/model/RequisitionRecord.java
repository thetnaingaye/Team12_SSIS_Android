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

public class RequisitionRecord extends HashMap<String,String> {

    public List<RequisitionRecordDetail> requisitionRecordDetailsList = new ArrayList<>();

    public  RequisitionRecord()
    {

    }

    public RequisitionRecord(String requestid,String approveddate, String approvername,String deptid,String remarks,String requestdate,String requestorname)
    {
        put("RequestID",requestid);
        put("ApprovedDate",approveddate);
        put("ApproverName",approvername);
        put("DepartmentID",deptid);
        put("Remarks",remarks);
        put("RequestDate",requestdate);
        put("RequestorName",requestorname);
    }

    public static List<RequisitionRecord> jread(String url) {
        List<RequisitionRecord> list = new ArrayList<RequisitionRecord>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i = 0; i < a.length(); i++)
            {
                JSONObject b = a.getJSONObject(i);
                RequisitionRecord r = new RequisitionRecord(
                        Integer.toString(b.getInt("RequestID")),
                        b.getString("ApprovedDate"),
                        b.getString("ApproverName"),
                        b.getString("DepartmentID"),
                        b.getString("Remarks"),
                        b.getString("RequestDate"),
                        b.getString("RequestorName")
                        );

                JSONArray jsonArray_Details = b.getJSONArray("WCF_RequisitionRecordDetails");
                r.requisitionRecordDetailsList = RequisitionRecordDetail.getRequisitionRecordDetails(jsonArray_Details);
                r.put("rdetails",Integer.toString(r.requisitionRecordDetailsList.size()));

                list.add(r);
            }
        } catch (Exception e) {
            Log.e("RequisitionRecord", "JSONArray error");
        }
        return (list);
    }

}


//        ApprovedDate	"12/15/2017"
//        ApproverName	"Dr Chia Leow Bee"
//        DepartmentID	"COMM"
//        Remarks	null
//        RequestDate	"12/5/2017"
//        RequestID	3
//        RequestorName	"Mohd Azman"
//        WCF_RequisitionRecordDetails
