package com.daman.farmify;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    @InjectView(R.id.input_name) EditText nameText;
    @InjectView(R.id.input_address) EditText addressText;
    @InjectView(R.id.input_email) EditText emailText;
    @InjectView(R.id.input_mobile) EditText phoneText;
    @InjectView(R.id.input_password) EditText passwordText;
    @InjectView(R.id.input_state) Spinner state;
    @InjectView(R.id.input_dob) EditText dobText;
    @InjectView(R.id.btn_signup) Button signupButton;
    @InjectView(R.id.link_login) TextView loginLink;
    UserBean userBean;
    ArrayAdapter<String> adapter;
    ContentResolver resolver;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        loginLink.setOnClickListener(this);
        signupButton.setOnClickListener(this);
        dobText.setOnClickListener(this);
        userBean=new UserBean();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCancelable(false);
        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item);
        adapter.add("Select State");
        adapter.add("New Delhi");
        adapter.add("Punjab");
        adapter.add("Goa");
        adapter.add("Maharashtra");
        adapter.add("Tamil Nadu");
        adapter.add("Rajasthan");
        adapter.add("Uttar Pradesh");
        adapter.add("Gujrat");
        adapter.add("Bihar");
        adapter.add("Haryana");
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    userBean.setState(adapter.getItem(position));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        resolver=getContentResolver();
        requestQueue= Volley.newRequestQueue(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.link_login){
            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else if (id==R.id.btn_signup){
            userBean.setName(nameText.getText().toString().trim());
            userBean.setAddress(addressText.getText().toString().trim());
            userBean.setEmail(emailText.getText().toString().trim());
            userBean.setPhone(phoneText.getText().toString().trim());
            userBean.setPassword(passwordText.getText().toString().trim());
            userBean.setDob(dobText.getText().toString().trim());
            if(validateFields()) {
                if (isNetworkConected())
                    insertOverCloud();
                else
                    Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_LONG).show();
            }
        }
        else if(id==R.id.input_dob){
            showDatePickerDialog();
        }
    }
    /*void insertIntoDB(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.COL_NAME,userBean.getName());
        contentValues.put(Util.Col_ADDRESS,userBean.getAddress());
        contentValues.put(Util.COL_EMAIL,userBean.getEmail());
        contentValues.put(Util.COL_PHONE,userBean.getPhone());
        contentValues.put(Util.COL_PASSWORD,userBean.getPassword());
        contentValues.put(Util.COL_STATE,userBean.getState());
        Uri dummy =  resolver.insert(Util.User_URI,contentValues);

        Toast.makeText(this,userBean.getName()+"Registered Successfully"+dummy.getLastPathSegment(),Toast.LENGTH_LONG).show();
        clearFields();
    }*/
    void clearFields(){
        nameText.setText("");
        addressText.setText("");
        emailText.setText("");
        phoneText.setText("");
        passwordText.setText("");
        dobText.setText("");
        state.setSelection(0);
    }
    void insertOverCloud(){
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("TOKEN",token);
        progressDialog.show();
        StringRequest request=new StringRequest(Request.Method.POST, Util.INSERT_USER_PHP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                int i = 0;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    i = jsonObject.getInt("success");
                }catch (Exception e){
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_SHORT).show();
                if(i==1){
                    SharedPreferences sharedPreferences = SignupActivity.this.getSharedPreferences("signupsp", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("signup", true);

                    editor.putString("name",userBean.getName());
                    editor.putString("address",userBean.getAddress());
                    editor.putString("email",userBean.getEmail());
                    editor.putString("phone",userBean.getPhone());
                    editor.putString("password",userBean.getPassword());
                    editor.putString("state",userBean.getState());
                    editor.putString("dob",userBean.getDob());
                    editor.commit();
                    Toast.makeText(SignupActivity.this,"Registration Successful!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "Something Went Wrong! ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map <String,String>map=new HashMap<>();
                map.put("name",userBean.getName());
                map.put("address",userBean.getAddress());
                map.put("email",userBean.getEmail());
                map.put("phone",userBean.getPhone());
                map.put("password",userBean.getPassword());
                map.put("state",userBean.getState());
                map.put("dob",userBean.getDob());
                map.put("token",token);
                return map;
            }
        };
        requestQueue.add(request);
        clearFields();
    }
    boolean isNetworkConected(){

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();


        return (networkInfo!=null && networkInfo.isConnected());

    }
    boolean validateFields(){
        boolean flag = true;
    if(userBean.getName().isEmpty()){
        flag=false;
    nameText.setError("Please enter name!");
}
if(userBean.getAddress().isEmpty()){
    flag=false;
    addressText.setError("Please enter address!");

}
        if(userBean.getEmail().isEmpty()){
            flag = false;
            emailText.setError("Please enter email!");
        }else{
            if(!(userBean.getEmail().contains("@") && userBean.getEmail().contains("."))){
                flag = false;
                emailText.setError("Please enter a valid email!");
            }
        }
        if(userBean.getPhone().isEmpty()){
            flag = false;
            phoneText.setError("Please enter phone!");
        }else{
            if(userBean.getPhone().length()<10){
                flag = false;
                phoneText.setError("Please enter 10 digit phone number!");
            }
        }
        if(userBean.getPassword().isEmpty()){
            flag =false;
            passwordText.setError("Please enter password!");
        }else{
            if(userBean.getPassword().length()<4){
                flag = false;
                passwordText.setError("Password too short!");
            }
        }
        if(state.getSelectedItem().toString().trim().equalsIgnoreCase("Select State")){
            flag=false;
            Toast.makeText(SignupActivity.this,"Please select city!",Toast.LENGTH_LONG).show();
        }
        if(userBean.getDob().isEmpty()){
            flag=false;
            dobText.setError("Please enter Date Of Birth!");
        }
        return flag;
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
}