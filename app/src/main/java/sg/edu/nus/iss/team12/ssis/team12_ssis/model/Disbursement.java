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

public class Disbursement extends HashMap<String,String> {

    public Disbursement(String disbursementid,String collectiondate, String collectionpointid,String deptid,String repname)
    {
        put("DisbursementID",disbursementid);
        put("CollectionDate",collectiondate);
        put("CollectionPointID",collectionpointid);
        put("DepartmentID",deptid);
        put("RepresentativeName",deptid);
    }

    public static List<Disbursement> jread(String url) {
        List<Disbursement> list = new ArrayList<Disbursement>();
        JSONArray a = JSONParser.getJSONArrayFromUrl(url);
        try {
            for (int i = 0; i < a.length(); i++) {
                JSONObject b = a.getJSONObject(i);
                list.add(new Disbursement(
                        Integer.toString(b.getInt("DisbursementID")),
                        b.getString("CollectionDate"),
                        Integer.toString(b.getInt("CollectionPointID")),
                        b.getString("DepartmentID"),
                        b.getString("RepresentativeName")
                ));
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