package com.example.vehicle_try;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button btnlogin;
    EditText email, password;
    TextView reglink;
    String Mob_No, name, u_id;
    public static String Login_Url = "http://192.168.1.8/VehicleBook/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        reglink = findViewById(R.id.reglink);
        btnlogin = findViewById(R.id.loginbtn);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        reglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        final String memail = this.email.getText().toString().trim();
        final String mpassword = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Login_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JSON", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);// name="",email=",id=""
                                    name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    Mob_No = object.getString("Mobile_Number").trim();
                                    u_id = object.getString("id").trim();

//                                    Toast.makeText(MainActivity.this, "login success  ", Toast.LENGTH_SHORT).show();

                                }
                                SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userId", memail);
                                editor.putString("Mob_No", Mob_No);
                                editor.putString("U_Name", name);
                                editor.putString("U_id", u_id);
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Username or Password is Invalid", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "login fail" + e.toString(), Toast.LENGTH_SHORT).show();
                            // reg.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Login.this, "login failed" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", memail);
                params.put("password", mpassword);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
