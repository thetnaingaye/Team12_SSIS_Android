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

public class Disbursement extends HashMap<String,String> implements java.io.Serializable {

    public List<DisbursementDetail> disbursementDetailsList = new ArrayList<>();
    public  Disbursement()
    {

    }

    public Disbursement(String disbursementid,String collectiondate, String collectionpointid,String deptid,String repname,String status)
    {
        put("DisbursementID",disbursementid);
        put("CollectionDate",collectiondate);
        put("CollectionPointID",collectionpointid);
        put("DepartmentID",deptid);
        put("RepresentativeName",repname);
        put("Status",status);

    }

    public static List<Disbursement> jread(String url,String token) {
        List<Disbursement> list = new ArrayList<Disbursement>();
        JSONArray a = JSONParser.getJSONArrayFromUrl_Post(url,token);
        try {
            for (int i = 0; i < a.length(); i++)
            {
                JSONObject b = a.getJSONObject(i);
                Disbursement d = new Disbursement(
                        Integer.toString(b.getInt("DisbursementID")),
                        b.getString("CollectionDate"),
                        Integer.toString(b.getInt("CollectionPointID")),
                        b.getString("DepartmentID"),
                        b.getString("RepresentativeName"),
                        b.getString("Status"));

                JSONArray jsonArray_Details = b.getJSONArray("WCF_DisbursementListDetail");
                d.disbursementDetailsList = DisbursementDetail.getDisbursementDetails(jsonArray_Details);

                d.put("WCFDetails", Integer.toString(jsonArray_Details.length()));
                list.add(d);
            }
        } catch (Exception e) {
            Log.e("Disbursement", "JSONArray error");
        }
        return (list);
    }

}


//CollectionDate	"1/3/2018"
//        CollectionPointID	1
//        DepartmentID	"ENGL"
//        DisbursementID	1
//        RepresentativeName	"Mr Pitt"
//        Status	null
//        WCF_DisbursementListDetail	[]
//        1