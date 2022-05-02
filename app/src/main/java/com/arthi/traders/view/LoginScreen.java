package com.arthi.traders.view;

import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.arthi.traders.R;
import com.arthi.traders.constant.Helper;
import com.arthi.traders.constant.VolleySingleton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {
Button login;
EditText username,password;
SharedPreferences sharedpreferences;
String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            Helper.sharedpreferences = getSharedPreferences(Helper.MyPREFERENCES, Context.MODE_PRIVATE);
        token                   = Helper.sharedpreferences.getString("token","");
        getpermission();
        login=findViewById(R.id.login);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String log_user=username.getText().toString();
                /*startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                overridePendingTransition(R.anim.right_enter,R.anim.left_out);*/
                if (log_user.isEmpty()){
                    username.setError("Username required");
                    username.requestFocus();
                }else {
                    logincheck(log_user);
                }
            }
        });
    }

//// Permissions
    private void getpermission() {
        Dexter.withContext(LoginScreen.this).withPermissions(Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    /// End

    //// Login checks

    public void logincheck(String usernames) {
        try {
            final String username = usernames;
            final String passwords = password.getText().toString().trim();

            Helper.loading(LoginScreen.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST,Helper.login_url,
                    response -> {
                        Helper.mProgressBarHandler.hide();
                        Log.e("logincheck", response);
                        try {
                            if(response.toString().trim().equals("INVALID")) {
                                Toast.makeText(LoginScreen.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                JSONObject obj = new JSONObject(response);
                                String dol_user_id      = obj.getString("dol_user_id");
                                String dol_user_name        = obj.getString("dol_user_name");
                                String dol_user_shop         = obj.getString("dol_user_shop");
                                String dol_user_type       = obj.getString("dol_user_type");
                                String dol_user_created_at   = obj.getString("dol_user_created_at");
                                String dol_user_username    = obj.getString("dol_user_username");
                                String dol_user_mobile  = obj.getString("dol_user_mobile");
                                String dol_user_email  = obj.getString("dol_user_email");
                                String dol_user_address  = obj.getString("dol_user_address");

                                SharedPreferences.Editor ed = Helper.sharedpreferences.edit();
                                ed.putString("dol_user_id", dol_user_id);
                                ed.putString("dol_user_name", dol_user_name);
                                ed.putString("dol_user_shop", dol_user_shop);
                                ed.putString("dol_user_type", dol_user_type);
                                ed.putString("dol_user_created_at",dol_user_created_at);
                                ed.putString("dol_user_username", dol_user_username);
                                ed.putString("dol_user_mobile", dol_user_mobile);
                                ed.putString("dol_user_email", dol_user_email);
                                ed.putString("dol_user_address", dol_user_address);
                                ed.putBoolean("FIRSTTIME_LOGIN", true);
                                ed.commit();

                                startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                                overridePendingTransition(R.anim.right_enter,R.anim.left_out);
                                //customType(LoginScreen.this,"bottom-to-up");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Helper.mProgressBarHandler.hide();
                        }

                    },
                    error -> {
                        try {
                            Toast.makeText(LoginScreen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            Helper.mProgressBarHandler.hide();
                            Log.e("err", error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", passwords);
                    params.put("token", token);
                    return params;
                }
            };
            VolleySingleton.getInstance(LoginScreen.this).addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /// End
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}