//-----------Team Member : THET NAING AYE's Codes-----------//
package sg.edu.nus.iss.team12.ssis.team12_ssis.model;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ChangSiang on 1/28/2018.
 */

public class UserIdentity extends HashMap<String, String> {
    final static String host = "http://192.168.1.15/Team12_SSIS/WebServices/AuthService.svc/Login";

    public UserIdentity(String userName, String password){
        put("userName", userName);
        put("password", password);
    }

    public UserIdentity(){}

    public static String authanticateUser(String userName, String password){
        JSONObject userIdent = new JSONObject();
        try{
            userIdent.put("userName", userName);
            userIdent.put("password", password);
        }catch (Exception e){
            Log.e("UserIdentity Exception", e.toString());
        }
        String token = JSONParser.postStream(host, userIdent.toString());
        return token;

    }
}
