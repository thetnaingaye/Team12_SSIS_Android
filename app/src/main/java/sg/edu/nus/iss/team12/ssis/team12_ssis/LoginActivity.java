package sg.edu.nus.iss.team12.ssis.team12_ssis;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sg.edu.nus.iss.team12.ssis.team12_ssis.model.UserIdentity;

public class LoginActivity extends Activity {
    String tokenKey;
    String department;
    String role;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String tokenKey, department, role;
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        //The httpURL is inside Model/UserIdentity. Do modify the value inside to your approriate wifi IP address
                        return UserIdentity.authanticateUser(params[0], params[1]);
                    }

                    @Override
                    protected void onPostExecute(String token) {
                        tokenKey = token;
                        proceed(tokenKey);
//                        if(token.equals("Invalid")) {
//                            Toast t = Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT);
//                            t.show();
//                           // this.cancel(true);
//                        }
//                        else{
//                            String seperator = "/";
//                            String[] tokenArray = token.split(seperator);
//                            String tokenKey = tokenArray[0];
//                            String department = tokenArray[0];
//                            String role = tokenArray[1];
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putString("tokenKey", tokenKey);
//                            editor.putString("department", department);
//                            editor.putString("role", role);
//                            editor.commit();
//                        }
                    }
                }.execute(username, password);


                //to get the tokenKey, use SharedPreference.getString("tokenKey", "hereJustPutRandomDefaultValue");

//                tokenKey = pref.getString("tokenKey", "defautValue");
//                department = pref.getString("department", "public");
//                role = pref.getString("role", "public");

                //start intent here.


            }

        });

    }

    private void proceed(String token) {
        String check = token.substring(1,8);
        if (check.equals("Invalid")) {
            Toast t = Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT);
            t.show();
        }
        else
        {
        String seperator = "/";
        String[] tokenArray = token.split(seperator);
        String tokenKey = tokenArray[0].replace("\\", "").replace("\"", "");
        String department = tokenArray[1].replace("\\", "");
        String role = tokenArray[2].replace("\\", "").replace("\"", "");
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("tokenKey", tokenKey);
        editor.putString("department", department);
        editor.putString("role", role);
        editor.commit();

        tokenKey = pref.getString("tokenKey", "defautValue");
        department = pref.getString("department", "public");
        role = pref.getString("role", "public");

        String str = tokenKey+"\n" + department+"\n" + role;

        Toast t = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);
        t.show();
        }


//
//        if(token.equals("Invalid")) {
//                            Toast t = Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT);
//                            t.show();
//                           //this.cancel(true);
//                        }
//                        else{
//                            String seperator = "/";
//                            String[] tokenArray = token.split(seperator);
//                            String tokenKey = tokenArray[0];
//                            String department = tokenArray[0];
//                            String role = tokenArray[1];
//                            SharedPreferences.Editor editor = pref.edit();
//                            editor.putString("tokenKey", tokenKey);
//                            editor.putString("department", department);
//                            editor.putString("role", role);
//                            editor.commit();
//                        }
    }
}
