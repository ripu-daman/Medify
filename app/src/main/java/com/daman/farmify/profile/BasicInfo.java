package com.daman.farmify.profile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daman.farmify.HomeActivity;
import com.daman.farmify.LoginActivity;
import com.daman.farmify.R;
import com.daman.farmify.SignupActivity;
import com.daman.farmify.UserBean;
import com.daman.farmify.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BasicInfo extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.input_name) EditText nameText;
    @InjectView(R.id.input_mobile) EditText phoneText;
    @InjectView(R.id.input_password) EditText passwordText;
    @InjectView(R.id.input_dob) EditText dobText;
    @InjectView(R.id.btn_submit) Button submit;
    @InjectView(R.id.show_password) ImageButton show;
    DatePickerDialog datePickerDialog;

    String name,phone,address,email,password,dob,state;
    int id=0;
    boolean flag=true;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    UserBean bean;
    JSONArray jsonArray;
    JSONObject jsonObjectLog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dobText.setOnClickListener(this);
        Intent rcv = getIntent();
        name = rcv.getStringExtra("name");
        phone = rcv.getStringExtra("phone");
        password = rcv.getStringExtra("password");
        dob = rcv.getStringExtra("dob");
        setFields();
        show.setOnClickListener(this);
        submit.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.input_dob){
            showDatePickerDialog();
        } else if(id==R.id.show_password){
            if(flag){
                passwordText.setInputType(InputType.TYPE_CLASS_TEXT);
                flag=false;
            }else{
                passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                flag=true;
            }
        }else if(id==R.id.btn_submit){
            if(validate()) {
                if (isNetworkConected())
                    updateData();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }
         }

    }
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dobText.setText(year+" / "+month+" / "+dayOfMonth);
        }
    };
    void showDatePickerDialog(){

        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this,dateSetListener,yy,mm,dd);
        datePickerDialog.show();
    }
public void setFields(){
    nameText.setText(name);
    phoneText.setText(phone);
    passwordText.setText(password);
    dobText.setText(dob);
}
    void updateData(){
        sharedPreferences = getSharedPreferences("signupsp", Context.MODE_PRIVATE);
         final int ID = sharedPreferences.getInt("id",0);
        Log.i("ID",String.valueOf(ID));
        name=nameText.getText().toString().trim();
        phone=phoneText.getText().toString().trim();
        password=passwordText.getText().toString().trim();
        dob=dobText.getText().toString().trim();
     Log.i("name",name);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Util.UPDATE_USER_PHP, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if(success == 1){
                        Toast.makeText(BasicInfo.this,message,Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(BasicInfo.this);
                        builder.setMessage("Please login again with new credentials to update changes!");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferencesLogin = getSharedPreferences("loginSp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editorLogin=preferencesLogin.edit();
                                editorLogin.putBoolean("loggedin",false);
                                editorLogin.putString("email","");
                                editorLogin.commit();
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else{
                        Toast.makeText(BasicInfo.this,message,Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(BasicInfo.this,"Some Exception",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(BasicInfo.this,"Some error occured, please try again in some time.",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("id",String.valueOf(ID));
                map.put("name",name);
                map.put("phone",phone);
                map.put("password",password);
                map.put("dob",dob);
                return map;
            }
        };
        requestQueue.add(request);

    }

    boolean isNetworkConected(){
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    public boolean validate() {
        boolean valid = true;
        name=nameText.getText().toString().trim();
        phone=phoneText.getText().toString().trim();
        password=passwordText.getText().toString().trim();
        dob=dobText.getText().toString().trim();
        if (name.isEmpty()) {
            nameText.setError("Please fill this field");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }
        if(phone.isEmpty()){
            phoneText.setError("Please enter phone!");
        }else{
            if(phone.length()<10){
                flag = false;
                phoneText.setError("Please enter 10 digit phone number!");
            }
        }
        if(dob.isEmpty()){
            flag=false;
            dobText.setError("Please enter Date Of Birth!");
        }
        return valid;
    }

}
