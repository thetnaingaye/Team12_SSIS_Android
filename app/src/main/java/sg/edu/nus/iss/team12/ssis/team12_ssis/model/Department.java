//-----------Team Member : THET NAING AYE's Codes-----------//
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

public class Department extends HashMap<String,String> {

public Department(String depdID,String deptname)
        {
           put("DeptID",depdID);
           put("DepartmentName",deptname);
        }


    public static Department jread(String url) {

        JSONObject a = JSONParser.getJSONFromUrl(url);
        try {

                if(a !=null)
                {
                    return new Department(a.getString("DeptID"),a.getString("DepartmentName"));
                }

            }
        catch (Exception e) {
            Log.e("Dept", "JSONArray error");
        }
       return new Department("","");
    }

}
