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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenKey, department, role;
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        //The httpURL is inside Model/UserIdentity. Do modify the value inside to your approriate wifi IP address
                        return UserIdentity.authanticateUser(username, password);
                    }

                    @Override
                    protected void onPostExecute(String token){
                        if(token == "Invalid"){
                            Toast t = Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT);
                            t.show();
                            this.cancel(true);
                            String seperator = "/";
                            String[] tokenArray = token.split(seperator);
                            String tokenKey = tokenArray[0];
                            String department = tokenArray[0];
                            String role = tokenArray[1];
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("tokenKey", tokenKey);
                            editor.putString("department", department);
                            editor.putString("role", role);
                            editor.commit();
                        }
                }
            }.execute();
                //to get the tokenKey, use SharedPreference.getString("tokenKey", "hereJustPutRandomDefaultValue");
                tokenKey = pref.getString("tokenKey", "defautValue");
                department = pref.getString("department", "public");
                role = pref.getString("role", "public");

                //start intent here.


            }

        });
    }
}
