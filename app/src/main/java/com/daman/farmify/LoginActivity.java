package com.daman.farmify;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.daman.farmify.profile.BasicInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.input_email)
    EditText emailText;

    @InjectView(R.id.input_password)
    EditText passwordText;

    @InjectView(R.id.btn_login)
    Button loginButton;

    @InjectView(R.id.link_signup)
    TextView signupLink;

    ContentResolver resolver;
    UserBean userBean;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String input_email,input_password;
    private boolean loggedIn = false;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    String name,phone,address,email,password,dob,state;
    int id=0;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        signupLink.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        resolver=getContentResolver();
        requestQueue= Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.link_signup){
            Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
            startActivity(intent);

            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        else if (id==R.id.btn_login){
            if(validate()) {
                if (isNetworkConected())
                    login();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }
        }
    }


    void login(){

        input_email = emailText.getText().toString().trim();
        input_password = passwordText.getText().toString().trim();


        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, Util.LOGIN_USER_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int i = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                    jsonObjectLog = new JSONObject(response);
                    jsonArray = jsonObjectLog.getJSONArray("user");

                }catch (Exception e){
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_SHORT).show();
               if(i==1){
                   retrieve();
                   SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("loginSp", Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putBoolean("loggedin", true);
                   editor.putString("email", input_email);
                   editor.commit();
                   Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                   startActivity(intent);
                   finish();
                   overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
               }else{
                   Toast.makeText(LoginActivity.this,"Login Failure!",Toast.LENGTH_LONG).show();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String>map=new HashMap<>();

                map.put("email",input_email);
                map.put("password",input_password);

                Log.i("email",input_email);
                Log.i("password",input_password);
                return map;
            }
        };
        requestQueue.add(request);
        clearFields();
    }
    void clearFields(){
        emailText.setText("");
        passwordText.setText("");
    }
    public   void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("loginSp", Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean("loggedin",false);
        if(loggedIn){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo!=null && networkInfo.isConnected());

    }
    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
    public void retrieve(){

        try {

            for(int i=0;i<jsonArray.length();i++){
                JSONObject jObj = jsonArray.getJSONObject(i);

                id = jObj.getInt("_ID");
                name = jObj.getString("_NAME");
                phone = jObj.getString("_PHONE");
                address = jObj.getString("_ADDRESS");
                email = jObj.getString("_EMAIL");
                password = jObj.getString("_PASSWORD");
                dob = jObj.getString("_DOB");
                state=jObj.getString("_STATE");
                userBean = new UserBean(id,name,address,email,phone,password,state,dob);
                Log.i("userobj",userBean.toString());
            }
            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("signupsp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("signup", true);
            editor.putInt("id",userBean.getId());
            editor.putString("name",userBean.getName());
            editor.putString("address",userBean.getAddress());
            editor.putString("email",userBean.getEmail());
            editor.putString("phone",userBean.getPhone());
            editor.putString("password",userBean.getPassword());
            editor.putString("state",userBean.getState());
            editor.putString("dob",userBean.getDob());
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*   CONTENT PROVIDER CODE
 void signIn() {
        String input_email = emailText.getText().toString().trim();
        String input_password = passwordText.getText().toString().trim();
        Log.i("email", input_email);
        Log.i("password", input_password);
        if (!input_email.isEmpty() && !input_password.isEmpty()) {
            String[] projection = {Util.COL_ID, Util.COL_NAME, Util.Col_ADDRESS, Util.COL_PHONE, Util.COL_EMAIL, Util.COL_PASSWORD, Util.COL_STATE};
            Cursor cursor = resolver.query(Util.User_URI, projection, null, null, null);
            Log.i("cursor", String.valueOf(cursor));
            if (cursor != null) {
                cursor.moveToFirst();

                int id = 0;
                String name = "", address = "", phone = "", email = "", password = "", state = "";

                while (cursor.moveToNext()) {
                    email = cursor.getString(cursor.getColumnIndex(Util.COL_EMAIL));
                    password = cursor.getString(cursor.getColumnIndex(Util.COL_PASSWORD));
                    Log.i("email",email);
                    Log.i("password",password);
                    Log.i("Cursor email",cursor.getString(0));
                    Log.i("Cursor password",cursor.getString(1));
                    //for (int i = 0; i < cursor.getColumnCount(); i++) {
                        if (email.equals(input_email) && password.equals(input_password)) {

                            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }else if (!email.equals(input_email) && !password.equals(input_password)) {
                            showDialog();
                        //Toast.makeText(this, "You haven't registered yet!", Toast.LENGTH_SHORT).show();

                    }

                }
            } else {

                Toast.makeText(this,
                        "Please enter the credentials!", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
    void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Wrong login Details!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        alertDialog.show();

    }*/
}
