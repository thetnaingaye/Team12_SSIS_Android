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
    public static void updateDisbursement(Disbursement disbursement, String token, String url)
    {
        try {


            JSONObject disbursementList = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DisbursementID", disbursement.get("DisbursementID"));
            jsonObject.put("Status", disbursement.get("Status"));
            JSONArray d_details = new JSONArray();
            for(DisbursementDetail d:disbursement.disbursementDetailsList)
            {
                JSONObject j = new JSONObject();
                j.put("ID",d.get("ID"));
                j.put("QuantityCollected",d.get("QuantityCollected"));
                j.put("Remarks",d.get("Remarks"));
                d_details.put(j);
            }
            jsonObject.put("WCF_DisbursementListDetail", d_details);

            disbursementList.put("disbursementList", jsonObject );

            disbursementList.put("token", token);
            String str = disbursementList.toString();

            JSONParser.postStream(url, disbursementList.toString());
        }
        catch(Exception e){

        }
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